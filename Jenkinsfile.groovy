node {
    def testClass = 'FindProductsWithinRangeTest'
    stage('Preparation') {
        git 'https://github.com/IlliaShabaiev/amazon-test.git'
    }
    stage('Run UI Tests') {
        bat 'mvn test -D=' + testClass
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
    }
}