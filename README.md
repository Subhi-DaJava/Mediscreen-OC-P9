# Mediscreen-P9
#### [GITLAB-CI/CD-PIPELINE](https://gitlab.com/Subhi-DaJava/Mediscreen-OC-P9/-/pipelines/888195287)
Mediscreen is an application that focuses on preventive care. It aims to help doctors identify patients at risk of developing type 2 diabetes by analyzing their demographic data.

By providing basic patient information such as name and age, Mediscreen enables healthcare professionals to input and review essential details for each patient. 
The application acknowledges that the risk of diabetes rises with age and the persistence of unhealthy eating habits. Its primary goal is to assist doctors in identifying high-risk patients and promoting early intervention and lifestyle modifications to mitigate the risk of developing type 2 diabetes.

![page-home](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/e7fd1818-adbb-4f5e-b9ba-9a06608af8b0)

## Presentation 

The application consists of several microservices that interact with each other to provide a seamless and secure user experience. Here are the included microservices:

- `patient_microservice`: Manages patient information. (Spring Boot)
* `practitioner_notes`: Stores medical notes associated with each patient. (Spring Boot)
+ `rapport_microservice`: Generates medical reports based on collected information for each patient. (Spring Boot)
- `mediscreen_frontend`: User interface for interacting with the microservices. (Angular)

## Technologies Used:

* Java 17
* Spring Boot (version 2.7.10)
* Gradle 7.6.1
* H2 (Version 2.1.214)
* Logger-slf4j (version 1.7.36)
* Swagger-OpenApi(version 1.7.0)
* Docker (version 23.0.5)
* Docker Compose (version v2.17.3)
* Compose file (version 3.8)
* MySQL (Version 8.0.32)
* MongoDB (version v6.0.3)
* Angular CLI (version 15.2.7)
* Node.js (version 18.16.0)
* npm (version 8.19.2)
* JaCoCo (Version 0.8.8)

## Installation 
#### Follow the steps below to install and run the application locally:
* Clone the repository:
  -  git clone [https://github.com/your_username/your_repo.git](https://github.com/Subhi-DaJava/Mediscreen-OC-P9.git)
+ Configure `.env`[^3]
  - set the host ports, server ports(Angular, Microservices, MySQL, MongoDB), docker mapping ports, username, user-password, database name for MySQL, MongoDB and the Endpoints(URLs) for Microservices APIs
- Ensure java 17, Docker, Node.js and mpn are installed as they are required for executing the microservices individually and testing.

## Execution/Deployment Appilication
##### - Navigate to the root directory where the `docker-compose.yml` file is located.
##### - Start the Docker services using Docker Compose command: `docker-compose up -d` 
##### - Then, access the application by navigating to `localhost:<angular-port>`
##### - To interact with the application, you can start by adding a new patient and then adding a new note for the patient

## Run Mediscreen-Frontend Microservice separately
* Navigate to the root directory of `mediscreen-frontend`
* Execute the `npm install` command to install the required Node packages
* Execute the `npm start` or `ng serve` command
* Access the application by navigating to `localhost:4200`(by default).

## Architecture 
![architecture_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/61fc75f4-ff6c-4bd6-b5b2-713516e48329)


## Running Tests 
To run the tests for each microservice (except `mediscreen-frontend`) and generate `JaCoCo reports` (and `JAR` file), execute the following command at the root of each microservice: `./gradlew test` or `./gradlew build`
> set the environment variables when testing the `Rapport-Microservice` whole package with IDE[^1] :
```
PATIENT_SERVICE_URL_DOCKER_PAT_ID=http://patient-microservice:${patient_server_port}/api/patients/{id};PATIENT_SERVICE_URL_DOCKER_PAT_NAME=http://patient-microservice:${patient_server_port}/api/patient?lastName={lastName};NOTE_SERVICE_URL_DOCKER_PAT_ID=http://note-microservice:${note_server_port}/api/notes/by-patId/{patId};NOTE_SERVICE_URL_DOCKER_PAT_NAME=http://note-microservice:${note_server_port}/api/notes/by-lastName/{lastName}
```
> set the environment variables when testing the `Patient-Microservice`(whole package, build and test[verification]) for MySQL with IDE[^2] :
```
PATIENT_DATABASE=${database_name};USER_PASSWORD=${password};server_port=${server_port};USERNAME=${username}
```
## Accessing the MySQL and MongoDB Databases
 #### MySQL 
       create new MySQL connection (with MySQL Workbench) with the username, password, port configured in `.env` file. Refer to the example image below for guidance:
      
 ![mysql_newConnection_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/d309dd36-6aeb-47ef-9440-a4ebefe00e0d)

 #### MongoDB
      Create a new connection (with MongoDB Compass) using, for example, the URI `mongodb://localhost:27018` (if port 27018 is configured for `Docker MongoDB`) as specified in the `.env` file. Refer to the example image below for guidance:
  ![mongodb_newConnection_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/f0225d08-c472-4911-9bf8-f6e2ed93eaae)

## JaCoCo Test Reports
#### 1. Patient-Microservice
![patient-test-report](/test-reports/patient-test-jacoco-report.png)
#### 2. Practitioner-Notes
![note-test-report](/test-reports/note-test-jacoco-report.png)
#### 3. Rapport-Microservice
![rapport-test-report](/test-reports/rapport-test-jacoco-report.png)

## Some Application Feature Screenshots 
#### 1. Home Page 
![home_page_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/244907fe-75fa-47c3-9a2c-f2bc31459d41)
#### 2. Patient-Details
![patient_details_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/470926dc-e30f-465e-a54f-4cd9df39a970)
#### 3. Add New Patient
![add_patient_form_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/b027f176-125c-4623-a6b5-477f78228a4b)
#### 4. Negative Cases (fields mandatory)
#### ![mandatory_cases_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/0ef11e1c-5121-485a-94a7-e25d42401e90)
#### 5. Negative Cases(format and others)
#### ![format_cases_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/af71e1eb-4a70-4369-8378-98b5a882f847)
#### 6. Update Patient
![patient_update_form_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/b81dd565-3049-43c5-a131-9a61a68eabd2)
#### 7. Negative Cases(format and others)
#### ![format_cases_update_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/11138216-23ff-401b-9f48-b0d347fbb88d)
#### 8. Update Patient Note
![update_note_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/908da6f0-26df-4af2-9a3b-b28d3f532ccf)
#### 9. Patient Report
![report_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/4614548a-1173-4310-88d6-ebabb49640e9)

[^1]:replace ${patient_server_port} with 8081 (or else), and ${note_server_port} with 8082 (or else)
[^2]: replace ${USERNAME} with the name of User to connect to MySQL server
[^3]: The `.env` file is a crucial component of your application's configuration, providing a centralized storage for sensitive configuration information.
