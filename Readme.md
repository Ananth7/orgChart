Steps for installing and running the service:
1. Run the java jar as follows "java -jar demo-0.0.1-SNAPSHOT.jar"
2. Install SQL if it is not already present in your machine. 
3. After SQL is installed, please run the steps mentioned in the sqlcommands.sql file one by one. 
4. After this the application is ready to use. 

There are 5 main APIs. 
1. localhost:8080/api/v1/createAdminUser - Used to create an admin user who can access the system. 
2. localhost:8080/api/v1/authenticateUser - Used to authenticate a user with his password and returns a valid session-id which will be used in the rest of the APIs

3. localhost:8080/api/v1/postOrgChart - Used to post the org chart - Takes the sessionid as a header and the JSON as a request body
4. localhost:8080/api/v1/getOrgChart - Used to get the org chart - Takes session id as a header for authentication
5. localhost:8080/api/v1/getManagers?employee=name - Used to get the manager details of a employee - Takes session id as a header and employee as a query parameter

Attaching the postman collection for easier use - https://www.getpostman.com/collections/343bb582b26ac2debf6b
