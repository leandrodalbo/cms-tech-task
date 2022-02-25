# CMS Tech Task

Below is an example of the type of tickets we work with on a day to day basis. 
Admitedly we would probably break this ticket down into smaller chunks, but for the sake of this exercise there are a few different requirements to cover different areas of your technical knowledge.

Please timebox this exercise to no more than 3 hours. 
If there are requirements left after that time then have a think about how you would address them going forwards.

# The Ticket

## Context

Your mission (if you choose to accept it) is to try and build a very simple version of the Content Management Service service.
The Content Management Service is a tool internal staff use to create ordered lists of sports/competitions/events/markets etc which are shown on site.
For example, the site may have a list of "popular sports", and during a weekend of football we may want that to be top of the list, and other sports lower down.
Because from our point of view we dont differentiate between sports/competitions/events/markets/etc, we collectively refer to them as "nodes".

As a member of a team that depends on the Content Management Service
I want an API that exposes data from the Sports Book
So that I can query Sports and Competitions

## Acceptance Criteria

##### Scenario: A user should be able to query a list of Sports

Given the API is running
When I GET /nodes?type=SPORT
Then I get a response code 200 (OK)
Then I get a response body containing
```json
[
  {
   "id": "the sports book id of the sport",
   "name": "the name of the sport",
   "type": "SPORT"
  }
]
```

##### Scenario: A user should be able to create an ordered list of Nodes

Given the API is running
Given a Sport exists in the Sports Book with id S1
Given a Sport exists in the Sports Book with id S2
When I POST /list with payload
```json
{
 "name": "the list name",
 "nodes": [
  {
   "id": "S1",
   "type": "SPORT",
   "position": 0
   },
   {
   "id": "S2",
   "type": "SPORT",
   "position": 1
   }
 ]
}
```
Then I get a response code 200 (OK)
Then I get a response body containing
```json
{
 "id": "the list id",
 "name": "the list name",
 "nodes": [
  {
   "id": "S1",
   "type": "SPORT",
   "position": 0
  },
  {
   "id": "S2",
   "type": "SPORT",
   "position": 1
  }
 ]
}
```

##### Scenario: A user should be able to retrieve an ordered list of Nodes

Given the API is running
Given a Sport exists in the Sports Book with id S1
Given a Sport exists in the Sports Book with id S2
Given Sports S1 and S2 have been added to a list
When I GET /list/{list-id}
Then I get a response code 200 (OK)
Then I get a response body containing
```json
{
 "id": "the list id",
 "name": "the list name",
 "nodes": [
  {
   "id": "S1",
   "type": "SPORT",
   "position": 0
  },
  {
   "id": "S2",
   "type": "SPORT",
   "position": 1
  }
 ]
}
```

## Tech Elab

So that this application fits in with the rest of our tech stack, we need it to be:
- A Spring Boot App
- Written in Java or Kotlin (whichever you feel most comfortable with)
- Built using Maven or Gradle
- Runnable via Docker

There is a Docker Compose file in this repository which runs the Sports Book API on http://localhost:8090
The API contains one POST endpoint which accepts a json payload in the form:
```json
{
 "locale": "en_GB",
 "attachments": ["SPORT"], // An array of the types you want to be returned in the response
}
```

## Test Elab

Ensure all code is well unit tested
Ensure there is sufficient end to end tests to give confidence the Acceptance Criteria are met

