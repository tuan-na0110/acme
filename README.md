# ACME

A sample booking cinema system in a pandemic.

### Installation
The sample is written by Spring Boot.
You need to run
----
	$ ./gradlew bootRun
----

to start the web service in localhost:8080

In this sample, the cinema has only one room. <br/>
You can configure this only room's information in the `src/main/resources/application.properties` file. 
The configurable pieces of information are:
----
	cinema.room.rows = 20 // number of rows of seats
    cinema.room.columns = 20 // number of columns of seats
    cinema.room.min_distance = 2 // the minimum Manhattan distance between different parties of people
    cinema.room.version = 1 // version of this information
----

If you use Gradle >= 4.9, 
you can pass configuration as an argument of the bootRun command, for example:
----
	$ ./gradlew bootRun --args ' --cinema.room.version=23 --cinema.room.rows=100 --cinema.room.columns=100 --cinema.room.max_distance=100'
----
(Note: if you are using a Mac computer, a space before the first -- is important. Don't remove it. <br/>
And if you pass an invalid argument (string for an integer, etc), the service won't run properly.)

The data is stored in variables. So if you restart the server, it will be lost. <br/>
For a real application, I intend to use a key-value database for storing the booking information of a cinema, with the key is a room's ID and the value is an object which stores the booking state of this room.

### APIS
This sample has two APIs:

##### 1. GET /api/v1/booking?num=
This API is used to query for a set of seats currently available. <br>
Parameter num will be a number that specifies how many seats are needed. <br>
If num is not passed, its default value is 1.
For example, send the below request:
----
	$ curl -H "Token:simpleToken" localhost:8080/api/v1/booking?num=1
----

It will return:

----
    {
	    "statusCode": 0,
	    "message": null,
	    "seats": [          // array of available seat objects.
	        {               // the seat Object has two attributes: row and column, which define the position of a seat.
	            "row": 0,
	            "column": 0
	        }
	    ]
	    "version": 1 // version of this information
    }
----

When a set of available seats can not be found, it will return HTTP Status 404 NotFound with no body.

##### 2. POST /api/v1/booking
This API is used to reserve a given set of seats <br>
The client needs to send request data in the below JSON format: 
----
    {
	    "seats": [          // array of seat objects which the client wants to reserve.
	        {               // the seat Object has two attributes: row and column, which define the position of a seat.
	            "row": 0,
	            "column": 0
	        }
	    ]
	    version: 1 // the version of cinema's information the client is holding
    }
----

The seats the client sends in this request will be assumed in the same party, so there is no restriction between them.

For example, send a request using curl:

----
	$ curl -H "Content-Type: application/json" -H "Token:simpleToken" -X POST 
		-d '{"seats":[{"row":3,"column":3}],"version":1}' localhost:8080/api/v1/booking?num=1
----

If the reservation succeeds, it will return HTTP Status 200 OK with the below body:

----
	{
	     "statusCode": 0,
	     "message": null,
    }
----
 
If the reservation fails because the version the client sent is a mismatch <br>
	or the seats the client sent have been reserved before, it will return HTTP Status 409 Conflict with the below body:
	
----
	{
        "statusCode": 409,
        "message": "please run the query again"
    }
----

The client needs to query again to get the newest state of the seats in the camera's room.

##### Authentication

You need to put "Token: simpleToken" in the header to authenticate with the APIs. <br/>
If not, it will return HTTP Status 401 Unauthorized with the below body:
----
	{
    	"statusCode": 401,
    	"message": "invalid token"
    }
----

For a real application, I will use JWT Token + Spring Security for authentication.

##### Common Error

If the request has an invalid parameter, it will return HTTP Status 400 Bad Request with the below body:

----
	{
    	"statusCode": 400,
    	"message": "..." // Not friendly message. I am keeping the exception message of the system.
    }
----

If an unhandled exception occur, it will return HTTP Status 500 Internal Server Error with the below body:

----
	{
    	"statusCode": 500,
    	"message": "..." // Not friendly message. I am keeping the exception message of the system.
    }
----

#### Ending
Thanks.

