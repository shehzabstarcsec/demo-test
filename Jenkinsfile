pipeline {
    agent any

    environment {
        // Define the webhook URL as an environment variable
        WEBHOOK_URL = 'http://localhost:8080/jenkins/v1/webhook'
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

    // Post actions: Trigger webhook based on various events
    post {
        // This will trigger after a successful build
        success {
            script {
                echo 'Build succeeded! Triggering webhook...'
                def response = httpRequest(
                    url: "${WEBHOOK_URL}",
                    httpMode: 'POST',
                    contentType: 'APPLICATION_JSON',
                    requestBody: '{"status": "SUCCESS", "message": "Build succeeded", "build_url": "${BUILD_URL}", "commit": "${GIT_COMMIT}"}'
                )
                echo "Response from webhook: ${response}"
            }
        }

        // This will trigger if the build fails
        failure {
            script {
                echo 'Build failed! Triggering webhook...'
                def response = httpRequest(
                    url: "${WEBHOOK_URL}",
                    httpMode: 'POST',
                    contentType: 'APPLICATION_JSON',
                    requestBody: '{"status": "FAILURE", "message": "Build failed", "build_url": "${BUILD_URL}", "commit": "${GIT_COMMIT}"}'
                )
                echo "Response from webhook: ${response}"
            }
        }

        // This will trigger after the build finishes, whether it is successful or failed
        always {
            script {
                echo 'Build completed (success/failure). Triggering webhook...'
                def response = httpRequest(
                    url: "${WEBHOOK_URL}",
                    httpMode: 'POST',
                    contentType: 'APPLICATION_JSON',
                    requestBody: '{"status": "COMPLETED", "message": "Build completed", "build_url": "${BUILD_URL}", "commit": "${GIT_COMMIT}"}'
                )
                echo "Response from webhook: ${response}"
            }
        }

        // This will trigger when the pipeline is aborted
        aborted {
            script {
                echo 'Build was aborted! Triggering webhook...'
                def response = httpRequest(
                    url: "${WEBHOOK_URL}",
                    httpMode: 'POST',
                    contentType: 'APPLICATION_JSON',
                    requestBody: '{"status": "ABORTED", "message": "Build aborted", "build_url": "${BUILD_URL}", "commit": "${GIT_COMMIT}"}'
                )
                echo "Response from webhook: ${response}"
            }
        }

        // This will trigger if any error occurs in the pipeline
        unstable {
            script {
                echo 'Build is unstable! Triggering webhook...'
                def response = httpRequest(
                    url: "${WEBHOOK_URL}",
                    httpMode: 'POST',
                    contentType: 'APPLICATION_JSON',
                    requestBody: '{"status": "UNSTABLE", "message": "Build unstable", "build_url": "${BUILD_URL}", "commit": "${GIT_COMMIT}"}'
                )
                echo "Response from webhook: ${response}"
            }
        }
    }
}
