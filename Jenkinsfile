// ============================================================
// Pipeline deklarativ CI/CD për "Sistemi i Menaxhimit të Studentëve"
// Autor: Andis Ramja
// ============================================================
pipeline {
    agent any

    options {
        // Shton vulë kohore në secilën rresht të logut
        timestamps()
    }

    stages {

        // Stage 1: merr kodin më të fundit nga sistemi i kontrollit të versioneve
        stage('Marrja e Kodit') {
            steps {
                echo 'Po merret kodi nga depoja (SCM)...'
                checkout scm
            }
        }

        // Stage 2: ndërton projektin pa ekzekutuar testet
        stage('Ndërtimi') {
            steps {
                echo 'Po ndërtohet projekti me Maven...'
                sh 'chmod +x mvnw && ./mvnw clean install -DskipTests'
            }
        }

        // Stage 3: ekzekuton testet unitare
        stage('Testimi') {
            steps {
                echo 'Po ekzekutohen testet unitare...'
                sh './mvnw test'
            }
            post {
                always {
                    // Publikon raportet e testeve në Jenkins
                    junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }

        // Stage 4: paketon aplikacionin në një file .jar
        stage('Paketimi') {
            steps {
                echo 'Po paketohet aplikacioni...'
                sh './mvnw package -DskipTests'
            }
        }

        // Stage 5: ruan artifaktet e gjeneruara (.jar)
        stage('Ruajtja Artifaktit') {
            steps {
                echo 'Po ruhen artifaktet (.jar)...'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Pipeline-i përfundoi me sukses!'
        }
        failure {
            echo 'Pipeline-i dështoi. Kontrolloni logun për detaje.'
        }
    }
}
