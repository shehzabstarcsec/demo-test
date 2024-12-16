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
                    url: "${JENKINS_URL}/crumbIssuer/api/json",
                    httpMode: 'GET',
                    validResponseCodes: '200'
                )
                echo "Response from crumb: ${crumbResponse}"
                // def crumb = readJSON(text: crumbResponse).crumb
                def crumb="519f956146699c03ff4b37c8ff141315822bf2b06434696cc419a294d5bc8fff"
                echo "CSRF Token retrieved: ${crumb}"

                // Step 2: Trigger the webhook with CSRF token
                def status = currentBuild.result ?: 'SUCCESS'
                def response = httpRequest(
                    url: "${WEBHOOK_URL}",
                    httpMode: 'POST',
                    contentType: 'APPLICATION_JSON',
                    customHeaders: [
                         [name: 'Jenkins-Crumb', value: crumb],
                         [name: 'Origin', value: 'http://localhost:8090']
                    ],
                    requestBody: """
                        {
                            "status": "asds",
                            "message": "dasfdsafd",
                            "build_url": "fedwfewf",
                            "commit": "qwewewqe"
                        }
                    """
                )
                echo "Response from webhook: ${response}"
            }
        }

        // Additional post conditions...
    }
}
