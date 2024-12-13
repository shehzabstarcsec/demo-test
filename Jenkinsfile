pipeline {
    agent any

    environment {
        // Define the webhook URL as an environment variable
        WEBHOOK_URL = 'http://localhost:8080/jenkins/v1/webhook'
        JENKINS_URL = 'http://localhost:8090'  // Jenkins URL for CSRF token
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
                def crumbResponse = httpRequest(
                    url: "${JENKINS_URL}/jenkins/crumbIssuer/api/json",
                    httpMode: 'GET',
                    validResponseCodes: '200'
                )
                def crumb = readJSON(text: crumbResponse).crumb
                echo "CSRF Token retrieved: ${crumb}"

                // Trigger the webhook based on the build result
                def status = currentBuild.result ?: 'SUCCESS'
                def response = httpRequest(
                    url: "${WEBHOOK_URL}",
                    httpMode: 'POST',
                    contentType: 'APPLICATION_JSON',
                    customHeaders: [
                        [name: 'Jenkins-Crumb', value: crumb]  // CSRF token header
                    ],
                    requestBody: """
                    {
                        "status": "${status}", 
                        "message": "Build ${status}", 
                        "build_url": "${BUILD_URL}", 
                        "commit": "${GIT_COMMIT}"
                    }
                    """
                )
                echo "Response from webhook: ${response}"
            }
        }

        // This will trigger after a successful build
        success {
            script {
                echo 'Build succeeded! Triggering webhook...'
            }
        }

        // This will trigger if the build fails
        failure {
            script {
                echo 'Build failed! Triggering webhook...'
            }
        }

        // This will trigger when the pipeline is aborted
        aborted {
            script {
                echo 'Build was aborted! Triggering webhook...'
            }
        }

        // This will trigger if any error occurs in the pipeline
        unstable {
            script {
                echo 'Build is unstable! Triggering webhook...'
            }
        }
    }
}
