@Library('jenkins-libs') _

pipeline{
    agent any
    options {
      timeout(30)
    }
    stages{
        
        stage('Maven and Sonar'){
            
            parallel{
            stage('Sonar Analysis'){
                steps{
                    withSonarQubeEnv('sonar7') {
                        sh 'mvn sonar:sonar'
                    }
                    
                    timeout(time: 1, unit: 'HOURS') {
                        script{
                          def qg = waitForQualityGate()
                          if (qg.status != 'OK') {
                              error "Pipeline aborted due to quality gate failure: ${qg.status}"
                          }
                        }
                  }
                }
            }
            
             stage('Mvn Build'){
                steps{
                    sh 'mvn clean package'
                }
            }
        
     
        }
        
        }
        stage("artifactory Deploy"){
            steps{
                script{
                    def pomFile = readMavenPom file: 'pom.xml'
                    ArtifactUploader artifacts: [[artifactId: 'jfrog', classifier: '', file: "target/jfrog-${pomFile.version}.war", type: 'war']], 
                                      credentialsId: 'Admin@123', 
                                      groupId: 'admin', 
                                      artifactoryUrl: '192.168.150.232:31681', 
                                      artifactoryVersion: 'jfrog7', 
                                      protocol: 'http', repository: 'javahome-my-app', 
                                      version: pomFile.version
                }
            }
        }
          
        stage('Tomcat Deploy'){
            steps{
                tomcatDeploy("192.168.150.233","audax","slave2")
            }
        }
     
    }
}
