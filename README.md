# Mediscreen-P9

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
* Docker (version 23.0.5)
* Docker Compose (version v2.17.3)
* MySQL (Version 8.0.32)
* MongoDB (version v6.0.3)
* Angular CLI (version 15.2.7)
* Node.js (version 18.16.0)
* npm (version 8.19.2)

## Installation 
#### Follow the steps below to install and run the application locally:
* Clone the repository:
  -  git clone [https://github.com/your_username/your_repo.git](https://github.com/Subhi-DaJava/Mediscreen-OC-P9.git)
+ Configure `.env`
  - set the host ports, server ports(Angular, Microservices, MySQL, MongoDB), docker mapping ports, username, userpassword, database name for MySQL, MongoDB and the Endpoints(URLs) for Microservices APIs
- Ensure java 17, Docker, Node.js and mpn are installed as they are required for executing the microservices individually and testing.

## Execution/Deployment Appilication
##### - Start the Docker services using Docker Compose: `docker-compose up -d` 
        
##### - Then, access the application by navigating to `localhost:<angular-port>`

## Architecture 

## Running Tests 
To run the tests for each microservice (except `mediscreen-frontend`) and generate `JaCoCo reports` (and `JAR` file), execute the following command at the root of each microservice: `./gradlew test` or `./gradlew build`
> set the environment variables when testing `Rapport-Microservice` with IDE :
```
PATIENT_SERVICE_URL_DOCKER_PAT_ID=http://patient-microservice:${patient_server_port}/api/patients/{id};PATIENT_SERVICE_URL_DOCKER_PAT_NAME=http://patient-microservice:${patient_server_port}/api/patient?lastName={lastName};NOTE_SERVICE_URL_DOCKER_PAT_ID=http://note-microservice:${note_server_port}/api/notes/by-patId/{patId};NOTE_SERVICE_URL_DOCKER_PAT_NAME=http://note-microservice:${note_server_port}/api/notes/by-lastName/{lastName}
```
> set the environment variables when testing `Patient-Microservice` for MySQL with IDE :
```
PATIENT_DATABASE=${database_name};USER_PASSWORD=${password};server_port=${server_port};USERNAME=${username}
```
## JaCoCo Test Reports
1. Patient-Microservice
![patient-test-report](/test-reports/patient-test-jacoco-report.png)
2. Practitioner-Notes
![note-test-report](/test-reports/note-test-jacoco-report.png)
3. Rapport-Microservice
![rapport-test-report](/test-reports/rapport-test-jacoco-report.png)

## Some Application Feature Screenshots 
1. Home Page 
![home_page_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/244907fe-75fa-47c3-9a2c-f2bc31459d41)
2. Patient-Details
![patient_details_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/470926dc-e30f-465e-a54f-4cd9df39a970)
3. Add New Patient
![add_patient_form_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/b027f176-125c-4623-a6b5-477f78228a4b)
4. Update Patient
![patient_update_form_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/b81dd565-3049-43c5-a131-9a61a68eabd2)
5. Update Patient Note
![updtae_note_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/908da6f0-26df-4af2-9a3b-b28d3f532ccf)
6. Patient Report
![report_image](https://github.com/Subhi-DaJava/Mediscreen-OC-P9/assets/90509456/4614548a-1173-4310-88d6-ebabb49640e9)



