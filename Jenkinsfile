pipeline {

    agent {
        label "master"
    }

    tools {
        // Note: this should match with the tool name configured in your jenkins instance (JENKINS_URL/configureTools/)
        maven "maven"
    }

    environment {
        // This can be nexus3 or nexus2
        NEXUS_VERSION = "nexus3"
        // This can be http or https
        NEXUS_PROTOCOL = "http"
        // Where your Nexus is running
        NEXUS_URL = "127.0.0.1:8081"
        // Repository where we will upload the artifact
        NEXUS_REPOSITORY = "maven-releases"
        // Jenkins credential id to authenticate to Nexus OSS
        NEXUS_CREDENTIAL_ID = "nexus-credentials"
    }

    stages {
        /*
        stage("clone code") {
            steps {
                script {
                    git 'https://github.com/xmhai/PersonalFinanceAssistant.git';
                }
            }
        }

        stage("mvn build") {
            steps {
                script {
                    sh "mvn clean install -DskipTests=true"
                }
            }
        }
        */

        stage("publish to nexus") {
            steps {
                script {
                    def files = findFiles() 
                    files.each{ f -> 
                        if(f.directory) {
                            echo "This is directory: ${f.name} "
                            pom = readMavenPom file: "${f.name}/pom.xml";
                            if (pom) {
                                echo "${pom.artifactId}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}"
                            }
                        }
                    }                    

                    /*
                    // Read POM xml file using 'readMavenPom' step , this step 'readMavenPom' is included in: https://plugins.jenkins.io/pipeline-utility-steps
                    pom = readMavenPom file: "pom.xml";
                    echo "${pom.artifactId}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}"
                    if ("${pom.packaging}" == "jar") {
                        // Find built artifact under target folder
                        filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                        // Print some info from the artifact found
                        echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                        // Extract the path from the File found
                        artifactPath = filesByGlob[0].path;
                        // Assign to a boolean response verifying If the artifact name exists
                        artifactExists = fileExists artifactPath;

                        if(artifactExists) {
                            echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";

                            nexusArtifactUploader(
                                nexusVersion: NEXUS_VERSION,
                                protocol: NEXUS_PROTOCOL,
                                nexusUrl: NEXUS_URL,
                                groupId: pom.groupId,
                                version: pom.version,
                                repository: NEXUS_REPOSITORY,
                                credentialsId: NEXUS_CREDENTIAL_ID,
                                artifacts: [
                                    [artifactId: pom.artifactId, classifier: '', file: artifactPath, type: pom.packaging], 
                                    [artifactId: pom.artifactId, classifier: '', file: "pom.xml", type: "pom"]
                                ]
                            );

                        } else {
                            error "*** File: ${artifactPath}, could not be found";
                        }
                    }
                    */
                }
            }
        }

    }
}