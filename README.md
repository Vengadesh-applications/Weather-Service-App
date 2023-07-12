# Weather-Service-App

README
============
(Must Read before running the Application)
---
The Weather Service is a Spring Boot application that provides weather information for cities using external APIs.


APIs:
=========
Get Current Weather by City

Endpoint: /weather/live/?city={cityName}

Method: GET
Path Parameters:
{cityName}: The name of the city to retrieve weather information for.
Response: Returns the weather details for the specified city in View format.

Get Forecast by City (6 days)

Endpoint: /weather/forecast/?city={cityName}

Method: GET
Path Parameters:
{cityName}: The name of the city to retrieve the forecast for.
Response: Returns the weather forecast details for the specified city in View format.


Configuration:
==============
The Weather Service requires a MySQL database for storing weather information. Please follow the steps below to configure the database:

Ensure that you have MySQL installed and running on your system.

Create a new database for the Weather Service. You can use the following command:

sql
Copy code
CREATE DATABASE WeatherDB;

Configure the database connection in the application.properties file. 
Open the src/main/resources/application.properties file and modify the following properties:

bash
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/WeatherDB

spring.datasource.username=<your_database_username>

spring.datasource.password=<your_database_password>

Replace <your_database_username> and <your_database_password> with your actual MySQL database credentials.


Build and Run:
==============
To build and run the Weather Service application, follow these steps:

Clone the repository or download the source code.

Navigate to the project root directory.

Build the application using Maven:
--

Copy code
mvn clean install

Run the application using Maven:

mvn spring-boot:run
The Weather Service will start running on http://localhost:8082/weather/

Usage:
You can now access the Weather Service APIs using tools like cURL or Postman. For example:

http://localhost:8082/weather/

To get the live weather for a specific city: 
1. Enter city name in text box 
2. Click on 'Get Live Data' button

To get the forecast for a specific city:
1. Enter city name in text box 
2. Click on 'Get Forecast' button
