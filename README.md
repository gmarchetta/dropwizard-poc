
Web Compiler Services
====================

This is a dropwizard module for the Web Compiler Service API.

# Configuration

### Example configuration:

```
server:
  adminConnectors:
    - type: http
      port: 8181
  applicationConnectors:
    - type: http
      port: 8180
database:
  driverClass: com.mysql.jdbc.Driver
  user: <MYSQL_USERNAME>
  password: <MYSQL_PASSWORD>
  url: jdbc:mysql://<HOST>:<PORT>/<DBNAME>
  validationQuery: SELECT 1

### Running  webcompiler in development mode

There are two files to configure to run this app in local environment:

##### 1. Make sure Maven is installed, and “MVN_HOME” variable is added as environment variable. It should point to the base maven folder (not the bin folder)

##### 2. Create a folder named "tmp"

##### 3. DB configuration
A database must exist in MySQL with the name "webcompiler". Nothing else needs to be done, since Liquibase takes care of the DB configuration process during the maven package phase (see at the end). The default username and password are gmarchetta / test. If you want to change these, edit the src/main/resources/changesets/changelog-webcompiler-1.0.xml file and change the info in the queries located there. 

Please, consider that if you ran mvn package once, you will need to clean the tables that liquibase creates automatically to manage changesets. Since this is a demo app, the easiest thing you can do is just delete the schema and recreate it. 

##### 4. pom.xml
Pom file should contain local database properties. 

For instance, to use MySql, db properties should be:

```
    <properties>
        <java.version>1.8</java.version>
        <driver>com.mysql.jdbc.Driver</driver>
        <url>jdbc:mysql://localhost:3306/webcompiler</url>
        <username>root</username>
        <password>root</password>
    </properties>
```

##### 5. webcompiler.yml
   In this file, properties should be like :

```
database:
  driverClass: com.mysql.jdbc.Driver
  user: root
  password: root
  url:  jdbc:mysql://localhost:3306/webcompiler
  validationQuery: SELECT 1
```

##### Optional: configure Splunk and uncomment/configure SplunkLoggingService class. This module is commented out since I wasn't able to test it properly.

##### Once the 2 files are properly configured: 

 1. Build the application and populate initial database schema. 
    Database schema must exist, even empty, on database server.
```
    mvn clean package
```
 2. After build is complete and successful, start the application:
```
    java -jar target/webcompiler.jar server ./target/classes/webcompiler.yml
```
