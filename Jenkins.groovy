#!/usr/bin/env groovy
pipeline {
    agent any
    triggers {
        pollSCM('*/15 * * * *')
    }
    options { disableConcurrentBuilds() }
    stages {
        stage('Permissions') {
            steps {
                echo "test"
            }


        }
    }
}
