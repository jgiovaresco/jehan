# Jehan

Jehan is a web interface which allows you to administrates severals Jenkins servers.

# Features
 
* Shows in the home page all jobs KO of all Jenkins servers
* Shows the jobs of many Jenkins servers with their name, last build status and the url to access them.
* Shows the Jenkins servers configured in JeHan.

# How to build

You require the following to build JeHan :

* [Oracle JDK 7](http://www.oracle.com/technetwork/java/)
* [Apache Maven 3.2.x](http://maven.apache.org/)
* [Apache Tomcat 7.0](http://tomcat.apache.org/)

# How to use

## Configuration file

The configuration file contains the list of jenkins server consulted by Jehan. 

    ```
    {
        "instances": [
            {
                "name": "jenkins_1",
                "url": "http://jenkins1:8180/jenkins/"
            },
            {
                "name": "jenkins_2",
                "url": "http://jenkins2:8280/jenkins/",
                "credentials" : {
                    "login": "my_username",
                    "token": "my_token"
                }
            }
        ]
    }
    ```
Jehan can be connected to a secured Jenkins server. You must provide credentials for this server

* the username 
* the API Token (see [Jenkins wiki](https://wiki.jenkins-ci.org/display/JENKINS/Remote+access+API) for more details) 

## Localization of the configuration file

The configuration file could be stored in many places :

* anywhere and provided to Jehan with a System property : -DJEHAN.DIR=<path_to_jehan.json>
* anywhere and provided to Jehan with an environment variable : JEHAN.DIR=<path_to_jehan.json>
* in a directory located in the user's home directory : <user.home>/.jehan/jehan.json
* in the WAR : WEB-INF/classes/jehan.json

Jehan locates the configuration file using these strategies in that order.
