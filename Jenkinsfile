pipeline {
    agent any

    environment {
        FRONT_SERVER_1 = 'zei@125.6.36.57'
        DEPLOY_PATH_1 = '/home/zei/nova-front'
        REPO_URL = 'https://github.com/nhnacademy-be6-supernova/novabook-front.git'
        ARTIFACT_NAME = 'front-0.0.1-SNAPSHOT.jar'
        JAVA_OPTS = '-XX:+EnableDynamicAgentLoading -XX:+UseParallelGC'
    }

    tools {
        jdk 'jdk-21'
        maven 'maven-3.9.7'
    }

    stages {
        stage('Checkout') {
            steps {
                git(
                    url: REPO_URL,
                    branch: 'main',
                    credentialsId: 'zei'
                )
            }
        }
        stage('Build') {
            steps {
                withEnv(["JAVA_OPTS=${env.JAVA_OPTS}"]) {
                    sh 'mvn clean package -DskipTests=true'
                }
            }
        }
        stage('Test') {
            steps {
                withEnv(["JAVA_OPTS=${env.JAVA_OPTS}"]) {
                    sh 'mvn test -Dsurefire.forkCount=1 -Dsurefire.useSystemClassLoader=false'
                }
            }
        }
        stage('Add SSH Key to Known Hosts') {
            steps {
                script {
                    def remoteHost1 = '125.6.36.57'
                    sh """
                        mkdir -p ~/.ssh
                        ssh-keyscan -H ${remoteHost1} >> ~/.ssh/known_hosts
                    """
                }
            }
        }
        stage('Deploy to Front Server 1') {
            steps {
                deployToServer(FRONT_SERVER_1, DEPLOY_PATH_1, 8080)
                showLogs(FRONT_SERVER_1, DEPLOY_PATH_1)
            }
        }

        stage('Verification') {
            steps {
                verifyDeployment(FRONT_SERVER_1, 8080)
            }
        }
    }
    post {
        success {
            echo 'Deployment succeeded!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}

def deployToServer(server, deployPath, port) {
    withCredentials([sshUserPrivateKey(credentialsId: 'zei', keyFileVariable: 'PEM_FILE')]) {
        sh """
        scp -o StrictHostKeyChecking=no -i \$PEM_FILE target/${ARTIFACT_NAME} ${server}:${deployPath}
        ssh -o StrictHostKeyChecking=no -i \$PEM_FILE ${server} 'nohup /home/jdk-21.0.3+9/bin/java -jar ${deployPath}/${ARTIFACT_NAME} --server.port=${port} ${env.JAVA_OPTS} > ${deployPath}/app.log 2>&1 &'
        """
    }
}

def showLogs(server, deployPath) {
    withCredentials([sshUserPrivateKey(credentialsId: 'zei', keyFileVariable: 'PEM_FILE')]) {
        sh """
        ssh -o StrictHostKeyChecking=no -i \$PEM_FILE ${server} 'tail -n 100 ${deployPath}/front_app.log'
        """
    }
}

def verifyDeployment(server, port) {
    sh """
    curl -s --head http://${server}:${port} | head -n 1
    """
}
