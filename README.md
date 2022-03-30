# Spring_Microservices

In this tutorial, we will create a microservice using Spring boot and Spring cloud. The components of the microservie will be:

* User Microservice: User Login, User Registration, JWT.
* Account Microservice: Responsible for account management.
* Eureka: Discovery service which register the microservices.
* Zuul API gateway: Handles client side requests and load balancing.
* H2: Lighweight database.
* Spring Boot + Spring Data JPA + Spring Security
* Spring cloud config: Spring cloud Bus
* Git Repo

## What is a microservice?
* A Web serivce.
* Small and Responsible for one thing. Example: Search, Password Reset, Email Verification.
* Configured to work in the cloud and is easily scalable.

## Micoservices vs Monolithic Application
Add an  image

## Sample Microservices architecture
Add an image

Each micorservice is designed around a single domain. Each ms communicates through http.
* User MS: Focuses on user details work and has single database and stores user information.
* Photo MS: Uploads photo and return metadata about the photos.
* Photo Process MS: Scale the uploaded photos into small photos and saves in different database like: aws S3 db.
* Album MS: Manages Album.
* Orders MS: Manages order.
* Notifcation MS: Sends notification email to user that their order has been successfully accepted. Collects task from a Queues.
* Queue:
* Discovery service: Registers all the microservices.
* Config service: Creates centralized configurations, when you push the updated value all the microservices will notify them automatically.
* Load Balancer: Balances http requests.
* API Gateway: Single entry point for http requests from clients.

## API Naming Tips
* Nouns as opposed to verbs or actions. example: `/users` in place of /`getUsers/`.
* Plurals. example: `/users/1` in place of `user/1`.
* Predictable. For example:
    - `users/1/messages` in place of `users/1/publishedMessages`
    - `users/1/messagess/2/likes` in place of `users/1/publishedMessages/2/allLikes`.

## HTTP methods

**Non Restful**
* POST: Create User
* GET: Read user details
* POST: update user details
* GET: Delete user details

**Restful**
* POST: Create user: `/users`
* GET: Read user details: `/users/1`
* PUT: Update user details: `/users/1`
* DELETE: Delete user details: `/users/1`

## HTTP Headers Accept and Content type
In the header we should add:
* If we want request type of json, we can write: `Content-Type`: application/json
* If we want a json response, we can write: `Accept`: application/json
* If we want an xml response, we can write: `Accept`: application/json

## Consumes, Produces in Spring Boot controller
* `consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_Value}`: consumes both xml and json request
* `produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_Value}`: produces both xml and json response