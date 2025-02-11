pipeline {
    agent any

    environment {
        WEBHOOK_URL = 'https://cleaning-zinc-hardly-exhibition.trycloudflare.com/pipeline/v1/jenkins/webhook'
        JENKINS_URL = 'http://13.232.153.140:8080'
        JENKINS_ACCESS_TOKEN = "YWRtaW46MTE3OTcyZmRlNzE1Njg1OTkzY2RkNWY2ZDVkOGEwNGZhMg=="
    }

    stages {
        stage('Checkout') {
            steps {
                // Example checkout from GitHub repository
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    echo 'Building the application...'
                    // Simulate build logic here
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo 'Running tests...'
                    // Simulate test logic here
                }
            }
        }
    }

    post {
        always {
            script {
               def crumbResponse = httpRequest(
                   url: "${JENKINS_URL}/crumbIssuer/api/json",
                   httpMode: 'GET',
                   validResponseCodes: '200',
                   customHeaders: [
                       [name: 'Authorization', value: "Basic ${JENKINS_ACCESS_TOKEN}"]
                   ]
               )
                def responseBody = crumbResponse.getContent() // Extract content from the response
                def jsonSlurper = new groovy.json.JsonSlurper()
                def crumbJson = jsonSlurper.parseText(responseBody)  // Parse the JSON response
                def crumb = crumbJson.crumb  // Extract CSRF token (crumb)
                echo "CSRF Token retrieved: ${crumb}"

                def repoUrl = env.GIT_URL ?: 'Unknown'
                def branch = env.GIT_BRANCH ?: 'Unknown'
                def commit = env.GIT_COMMIT ?: 'Unknown'
                def pullRequest = env.CHANGE_URL ?: 'N/A'
                def prAuthor = env.CHANGE_AUTHOR ?: 'N/A'


                def actionType = ""
                def sourceBranch = ""
                def targetBranch = ""

                if (env.CHANGE_ID) {
                    actionType = "Pull Request"
                    sourceBranch = env.CHANGE_BRANCH ?: "Unknown" // Source branch of the PR
                    targetBranch = env.CHANGE_TARGET ?: "Unknown" // Target branch of the PR
                } else if (env.BRANCH_NAME) {
                    actionType = "Branch Commit"
                    sourceBranch = env.BRANCH_NAME 
                    targetBranch = "" 
                } else if (env.GIT_BRANCH) {
                    actionType = "Branch Commit"
                    sourceBranch = env.GIT_BRANCH
                    targetBranch = ""
                } else {
                    actionType = "Build"
                    sourceBranch = "Unknown"
                    targetBranch = "Unknown"
                }

                def requestBody = """
                {
                "status": "${currentBuild.result ?: 'SUCCESS'}",
                "message": "Build ${currentBuild.result ?: 'SUCCESS'}",
                "build_url": "${env.BUILD_URL}",
                "repo_url": "${repoUrl}",
                "branch": "${branch}",
                "commit": "${commit}",
                "pr_url": "${pullRequest}",
                "pr_author": "${prAuthor}",
                "job_name": "${env.JOB_NAME}",
                "build_number": "${env.BUILD_NUMBER}",
                "action_type": "${actionType}",
                "source_branch": "${sourceBranch}",
                "target_branch": "${targetBranch}",
                "jenkins_url": "${JENKINS_URL}",
                "trigger_name": "${env.TRIGGER_NAME ?: 'N/A'}",
                "branch_name": "${env.BRANCH_NAME ?: 'N/A'}",
                "change_id": "${env.CHANGE_ID ?: 'N/A'}",
                "change_url": "${env.CHANGE_URL ?: 'N/A'}",
                "change_branch": "${env.CHANGE_BRANCH ?: 'N/A'}",
                "change_target": "${env.CHANGE_TARGET ?: 'N/A'}"
            }
            """

                def status = currentBuild.result ?: 'SUCCESS'
                def response = httpRequest(
                    url: "${WEBHOOK_URL}",
                    httpMode: 'POST',
                    contentType: 'APPLICATION_JSON',
                    customHeaders: [
                         [name: 'Jenkins-Crumb', value: crumb],
                         [name: 'Origin', value: 'http://13.232.153.140:8080']
                    ],
                    requestBody:requestBody
                )
                echo "Response from webhook: ${response}"
            }
        }
    }
}
