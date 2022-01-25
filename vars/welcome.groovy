@Library('jenkins-lib') _

pipeline{
    agent any
    options {
      timeout(30)
    }
    stages{
        
        stage('Maven'){
             stage('Mvn Build'){
                steps{
                    sh 'mvn clean package'
                }
            }
        
     
        }
     
    }
}
