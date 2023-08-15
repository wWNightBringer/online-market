pipeline {
    agent any

    triggers {
        pollSCM '* * * * *'
    }

    stages {
        stage('Checkstyle Main') {
                steps {
                    sh './gradlew checkstyleMain'
                }
            }
         stage('Test') {
                     steps {
                         sh './gradlew test'
                     }
                 }
        stage('Build') {
            steps {
                sh './gradlew clean build -x test -x integrationTest'
            }
        }
    }
}
