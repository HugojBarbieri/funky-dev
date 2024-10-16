pipeline {
    agent any

    tools {
        maven 'mavenjenkins'

        stages {
            stage('Build') {
                steps {
                    script {
                        // List of environment variables and their corresponding secret IDs
                        def envVars = [
                                'USER_AGENT'    : 'USER_AGENT',
                                'BASE_URL'      : 'BASE_URL',
                                'AUTH_TOKEN'    : 'AUTH_TOKEN',
                                'EMAIL_HOST'    : 'EMAIL_HOST',
                                'EMAIL_PORT'    : 'EMAIL_PORT',
                                'EMAIL_PASSWORD': 'EMAIL_PASSWORD',
                                'EMAIL_SENDER'  : 'EMAIL_SENDER',
                                'EMAIL_USERNAME': 'EMAIL_USERNAME'
                        ]

                        // Load environment variables from credentials
                        envVars.each { var, credId ->
                            withCredentials([string(credentialsId: credId, variable: var)]) {
                                env."${var}" = env."${var}"
                            }
                        }

                        dir('application') {
                            // Inside 'application' folder, run mvnw with all env variables
                            sh '''
                            ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=hjbarbieri/funkyapp:1.2
                        '''
                        }
                    }
                }
            }
            stage('Test') {
                steps {
                    dir('application') {
                        echo './mvnw test'
                    }
                }
            }
            stage('Deploy') {
                steps {
                    script {
                        // Accessing the Docker Hub credentials
                        withCredentials([usernamePassword(credentialsId: 'your-credentials-id', passwordVariable: 'DOCKERHUB_CREDENTIALS_PSW', usernameVariable: 'DOCKERHUB_CREDENTIALS_USR')]) {
                            // Docker login and push
                            sh "echo ${DOCKERHUB_CREDENTIALS_PSW} | docker login -u ${DOCKERHUB_CREDENTIALS_USR} --password-stdin"
                            sh 'docker push hjbarbieri/funkyapp:1.2'
                        }
                    }
                }
            }
        }
    }
}
