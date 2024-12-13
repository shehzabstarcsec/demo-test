
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building...'
            }
        }
    }
    post {
        success {
            script {
                // Send the HTTP request here
                def response = httpRequest(
                    url: 'http://localhost:8080/jenkins/v1/webhook',
                    httpMode: 'POST',
                    contentType: 'APPLICATION_JSON',
                    requestBody: '{"status": "SUCCESS"}'
                )
            }
        }
        failure {
            script {
                // Handle failure scenario
            }
        }
    }
}
