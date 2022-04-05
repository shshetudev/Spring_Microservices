## Story
- This is an photo application created in microservice architecture.
- As configuration services i used: Eureka discovery server, Zuul ApiGateway, Apache Kafka
- Photo application has these services:
	- Users Microservice
	- Photos Microservice
	- Photo Processing Microservice
	- Albums Microservice
	- Orders Microservice
	- Notification Microservice

## To Run this app:
- Download the zip file
- Unzip the folder and open in intellij or import the gradle dependencies.
- Run 2 instances of each service using these commands: `PORT=8021 ./gradlew bootRun`. Provide `PORT` as desired.
- Open the eureka server url `localhost:8010` in the browser.
- Make some requests in different services using postman.
	
