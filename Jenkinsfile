pipeline {
    agent any

    environment {
        // Define the webhook URL as an environment variable
        WEBHOOK_URL = 'https://millennium-champion-positions-nylon.trycloudflare.com/jenkins/v1/webhook'
        JENKINS_URL = ' https://heights-pakistan-li-penalties.trycloudflare.com'  // Jenkins URL for CSRF token
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
        // Trigger CSRF token before any post actions
        always {
            script {
                // Step 1: Get CSRF token from Jenkins
                // def crumbResponse = httpRequest(
                //     url: "${JENKINS_URL}/crumbIssuer/api/json",
                //     httpMode: 'GET',
                //     validResponseCodes: '200',
                //     customHeaders: [
                //         [name: 'Authorization', value: 'Basic YWRtaW46MTE1ZWY4NjYwZTRmODBmMmQ2NTY1MTAwYWYxZjMwNDdhNA==']
                //     ]
                // )
                // def crumbJson = readJSON(text: crumbResponse)
                // def crumb = crumbJson.crumb  // Extract CSRF token (crumb)
                // def responseBody = crumbResponse.getContent() // Extract content from the response
                // def jsonSlurper = new groovy.json.JsonSlurper()
                // def crumbJson = jsonSlurper.parseText(responseBody)  // Parse the JSON response
                // def crumb = crumbJson.crumb  // Extract CSRF token (crumb)
                 def crumb="b8484a3a5006812acdb21c1b0761ee3e10850cedf310fd6bd20c2e3e0b82f546"
                echo "CSRF Token retrieved: ${crumb}"

                def repoUrl = env.GIT_URL ?: 'Unknown'
                def branch = env.GIT_BRANCH ?: 'Unknown'
                def commit = env.GIT_COMMIT ?: 'Unknown'
                def pullRequest = env.CHANGE_URL ?: 'N/A'
                def prAuthor = env.CHANGE_AUTHOR ?: 'N/A'


                def actionType = ""
                def sourceBranch = ""
                def targetBranch = ""

                // Determine the type of build and capture relevant information
                if (env.CHANGE_ID) {
                    actionType = "Pull Request"
                    sourceBranch = env.CHANGE_BRANCH ?: "Unknown" // Source branch of the PR
                    targetBranch = env.CHANGE_TARGET ?: "Unknown" // Target branch of the PR
                } else if (env.BRANCH_NAME) {
                    actionType = "Branch Commit"
                    sourceBranch = env.BRANCH_NAME 
                    targetBranch = "" 
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
                                    "target_branch": "${targetBranch}"
                                    }
                """
                                                    
                // Step 2: Trigger the webhook with CSRF token
                def status = currentBuild.result ?: 'SUCCESS'
                def response = httpRequest(
                    url: "${WEBHOOK_URL}",
                    httpMode: 'POST',
                    contentType: 'APPLICATION_JSON',
                    customHeaders: [
                         [name: 'Jenkins-Crumb', value: crumb],
                         [name: 'Origin', value: ' https://heights-pakistan-li-penalties.trycloudflare.com']
                    ],
                    requestBody:requestBody
                )
                echo "Response from webhook: ${response}"
            }
        }

        // Additional post conditions...
    }
}


