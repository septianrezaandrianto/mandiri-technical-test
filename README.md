# mandiri-technical-test

Actually the requirement must be use Java JDK 17 and spring boot 3,
But when I try to upgrade my jdk version, my OS not support because I use old macbook pro (2012).
So I use exisiting jdk version in my laptop java JDK 11.

## General Info, this project using :
1. Java JDK 11,
2. Java Spring Boot 2.7.10-SNAPSHOT,
3. Maven.

## Library :
1. Spring Web,
2. Spring Boot DevTools,
3. Lombok,
4. Spring Data JPA,
5. H2 Database,
6. Gson,
7. JUnit 5,
8. Spring validation.

## Rules :
1. Import the postman collection from postman-collection folder in this project to your postman.
2. mvn spring-boot:run : Use this script for running this project using CMD / Terminal.
3. mvn clean test : Use this script for running unit testing this project using CMD / Terminal.
4. open this url on your browser http://localhost:8080/h2-console for accessing database.


## I provide some services:
1. Create (localhost:8080/v1/users)
   - This API I provide to create data in user table.
   
2. Get All Data (localhost:8080/v1/users)
   - This API I provide for fetching all data from the database.

3. Get User By Id (localhost:8080/v1/users/{id})
   - This API I provide for fetching user data by user id.
  
4. Update User (localhost:8080/v1/users/{id})
   - This API I provide to update data in user table.   
   
5. Update User Setting (localhost:8080/v1/users/{id}/settings)
   - This API I provide to update data in user setting table.

6. Delete Data (localhost:8080/v1/users/{id})
   - This API I provide to delete data in user table
  
7. Refresh User (localhost:8080/v1/users/{1}/refresh)
   - This API I provide to reactive user has been deleted in user table.
   
   
  
