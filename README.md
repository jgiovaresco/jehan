# JeHan

JeHan is a web interface which allows you to administrates severals Jenkins servers.

# Features
 
* Shows all Jobs of many Jenkins servers with their name, last build status and the url to access them.
* Shows the Jenkins servers configured in JeHan.

# How to build

You require the following to build JeHan :

* [Oracle JDK 7](http://www.oracle.com/technetwork/java/)
* [Apache Maven 3.2.x](http://maven.apache.org/)
* [Apache Tomcat 8.0](http://tomcat.apache.org/)

# How to use

* Add your Jenkins server in `jehan.json` in `WEB-INF/classes`

    ```
    {
        "instances": [
            {
                "name": "jenkins_1",
                "url": "http://jenkins1:8180/jenkins/"
            },
            {
                "name": "jenkins_2",
                "url": "http://jenkins2:8280/jenkins/"
            }
        ]
    }
    ```
* You can point your browser to `http://localhost:8080/`