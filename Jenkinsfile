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

        REGISTRY_URL = "192.168.86.43:9080/library"
        REGISTRY_USERNAME = "admin"
        REGISTRY_PASSWORD = "admin"
    }

    stages {
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

        /*
        stage("publish to nexus") {
            steps {
                script {
                    // read parent pom
                    parentpom = readMavenPom file: "pom.xml";

                    def files = findFiles() 
                    files.each{ f -> 
                        if(f.directory) {
                            def pomExists = fileExists "${f.name}/pom.xml"
                            if (pomExists) {
                                pom = readMavenPom file: "${f.name}/pom.xml";
                                echo "${pom.artifactId}, group: ${parentpom.groupId}, packaging: ${pom.packaging}, version ${pom.version}"

                                if ("${pom.packaging}" == "jar") {
                                    // Find built artifact under target folder
                                    filesByGlob = findFiles(glob: "${f.name}/target/*.${pom.packaging}");
                                    boolean exists = filesByGlob.length > 0

                                    if (exists) {
                                        // Print some info from the artifact found
                                        echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                                        // Extract the path from the File found
                                        artifactPath = filesByGlob[0].path;

                                        nexusArtifactUploader(
                                            nexusVersion: NEXUS_VERSION,
                                            protocol: NEXUS_PROTOCOL,
                                            nexusUrl: NEXUS_URL,
                                            groupId: parentpom.groupId,
                                            version: pom.version,
                                            repository: NEXUS_REPOSITORY,
                                            credentialsId: NEXUS_CREDENTIAL_ID,
                                            artifacts: [
                                                [artifactId: pom.artifactId, classifier: '', file: artifactPath, type: pom.packaging], 
                                                [artifactId: pom.artifactId, classifier: '', file: "${f.name}/pom.xml", type: "pom"]
                                            ]
                                        );
                                    }
                                }
                            } else {
                                echo "${f.name}/pom.xml doesn't exist"
                            }
                        }
                    }                    
                }
            }
        }
        */

        stage("publish to harbor") {
            steps {
                script {
                    sh "echo \\"${REGISTRY_PASSWORD}\\" | docker login ${REGISTRY_URL} -u ${REGISTRY_USERNAME} --password-stdin"

                    // read parent pom
                    parentpom = readMavenPom file: "pom.xml";

                    def files = findFiles() 
                    files.each{ f -> 
                        if(f.directory) {
                            def pomExists = fileExists "${f.name}/pom.xml"
                            if (pomExists) {
                                pom = readMavenPom file: "${f.name}/pom.xml";
                                echo "${pom.artifactId}, group: ${parentpom.groupId}, packaging: ${pom.packaging}, version ${pom.version}"

                                if ("${pom.packaging}" == "jar") {
                                    // Find built artifact under target folder
                                    filesByGlob = findFiles(glob: "${f.name}/target/*.${pom.packaging}");
                                    boolean exists = filesByGlob.length > 0

                                    if (exists) {
                                        // Print some info from the artifact found
                                        echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"

                                        sh "docker build -t ${REGISTRY_URL}/${pom.artifactId} ./${f.name}"
                                        sh "docker push ${REGISTRY_URL}/${pom.artifactId}"
                                    }
                                }
                            } else {
                                echo "${f.name}/pom.xml doesn't exist"
                            }
                        }
                    }                    

                    sh "docker system prune --volumes -f"
                }
            }
        }
    }
}