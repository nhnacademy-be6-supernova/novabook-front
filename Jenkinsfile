pipeline {
    agent any

    environment {
        FRONT_SERVER_1 = 'ubuntu@125.6.36.57'
        FRONT_SERVER_2 = 'ubuntu@125.6.36.57'
        DEPLOY_PATH_1 = '/home/zei/nova-front'
        DEPLOY_PATH_2 = '/home/zei/nova-front'
        REPO_URL = 'https://github.com/nhnacademy-be6-supernova/novabook-front.git'
        ARTIFACT_NAME = 'novabook_front-0.0.1-SNAPSHOT.jar'
    }

    stages {
        stage('Checkout') {
            steps {
                git(
                    url: 'https://github.com/nhnacademy-be6-supernova/novabook-front.git',
                    branch: 'feature/jenkins',
                    credentialsId: 'zei'
                )
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deploy to Front Server 1') {
            steps {
                deployToServer(FRONT_SERVER_1, DEPLOY_PATH_1, 8080)
            }
        }
        stage('Deploy to Front Server 2') {
            steps {
                deployToServer(FRONT_SERVER_2, DEPLOY_PATH_2, 8081)
            }
        }
        stage('Verification') {
            steps {
                verifyDeployment(FRONT_SERVER_1, 8080)
                verifyDeployment(FRONT_SERVER_2, 8081)
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
        ssh -i ${PEM_FILE} ${server} 'mkdir -p ${deployPath}'
        scp -i ${PEM_FILE} target/${ARTIFACT_NAME} ${server}:${deployPath}
        ssh -i ${PEM_FILE} ${server} 'nohup java -jar ${deployPath}/${ARTIFACT_NAME} --server.port=${port} > ${deployPath}/app.log 2>&1 &'
        """
    }
}

def verifyDeployment(server, port) {
    sh """
    curl -s --head http://${server}:${port} | head -n 1
    """
}
