# Planet Assessment Project
Project created to complete technical assessment as requested by Planet's interviewing process.

The exercise consisted of the creation of a backend service that received data files containing data from users and would be able to export this data in a desired format, from CSV, TXT, XLS and XLSX.

## Deployment

The project needs an environment capable of creating docker containers in order to manually test.
Build the project using the command: `./gradlew clean`
Make sure the .env file sent via email when the project was submitted is in the root of the project.
Deploy the project via `docker-compose up --build`.

## Project Structure and Architecture

This service consists of a REST API that receives requests to import or export data files, and a PostgreSql database to persist the information received.
It is structured via hexagonal architecture, divided into adapter layer, application layer and domain layer, with the intent to isolate business logic from the API and persistence layer.
The project uses OpenApi to create the rest controller, endpoints and object models for the REST API. It uses Spring Boot to structure the entire service, as well as certain libraries from Spring such as Spring Data JPA to access the persistence layer.
It uses Gradle as the build automation tool.

This project was coded in Kotlin due to two main reasons:

- Kotlin has a stronger handling of null references due to being controlled by the type system. As this project envisioned a flexible number of columns received, having a easy to setup and expressive manner to control (such as through Elvis operators) null values seemed appropriate.
- Due to personal reasons I had less time than I initially estimated, and hence using a programming language I am more accustomed to reduced development time.

Similarly, PostgreSQL was chosen mostly due to familiarity in an environment with less development time than I had hoped. MYSql for this project due to being faster in simpler environments might have been ideal.

### Libraries and Plugins

As a quick summary in a list:
- Core: Spring Boot (web, data-jpa, validation, actuator, aop) and Spring Web MVC; OpenAPI via springdoc.
- Language/runtime: Kotlin and kotlin-reflect.
- Data & file handling: Apache Commons CSV and Apache POI (poi, poi-ooxml) for CSV/XLS/XLSX processing.
- Database: PostgreSQL JDBC driver in order to interact with the database via ORM.
- Utilities: Libphonenumber and Apache Commons Validator for quick validation of email and phone number strings. 
- Testing: Jakarta Servlet API, SLF4J for logging; tests use JUnit 5 and Mockito-Kotlin.
- Lint: Project linting via KTLint

## Decisions on Service Behavior

### Behavior for missing, unknown and invalid fields from the CSV

As a multitude of users can be imported via the CSV, and the exercise envisioned the handling of examples with records missing values for certain defined columns, it was necessary to choose a solution for these behaviors.

#### Columns on CSV define what will be saved and updated but Id is always assumed 
The exercise showcased 6 different columns for the User object, those being "id", "name", "email", "age, "country" and "phone".
It was assumed that those are the only columns expected for this table. It was also assumed that id was both unique and the primary key through which we identified users.
This services expects an imported CSV to define which columns it will save by defining them in the header. 
If the service detects a column that doesn't belong to the 6 aforementioned ones, it will not process the CSV and will send a 400 response. Similarly, if it has duplicate columns, it also will send a 400 response.
In addition, if the id column is missing, it also responds with an error code 400.
If it receives anything except a multipart/form-data with a csv, it will respond with 415. 

From the point it begins processing a CSV, the service will ignore the columns not inserted, not validating them, and considering them null. If it is saving the User for the first time, it will save on the DB as NULL. If the user already exists and that column is not null it does not save over it.
The received data will be validated, and if data for a received column is blank or invalid that row of User will be discarded, without discarding the whole CSV. The rest response will still be 200 even on this case, and will have the information of which users were processed and which were discarded.
Due to lack of time and a mistake (check Tech Debt section), entirely missing values due to failure to format the CSV correctly will not be stated in the response message, but will still be discarded without making the whole CSV invalid. 

### Imported Information when same User is received multiple times
When the same user is received multiple times (identified by the id column), the service considers that an update to the User, and updates the columns that are received and only those.
This means that if I receive a CSV with "id,name,email" "1,John,john@example.com" and another with "id,name,country" "1,Jonathan,Portugal", the final result for the saved object would be:

| id | name     | email            | age    | country  | phone  |
|----|----------|------------------|--------|----------|--------|
| 1  | Jonathan | john@example.com | [null] | Portugal | [null] |

### Data Validation
For each column this service makes the following validations for each user (which, again, are only made if the column is defined in the CSV):
- 'id' must not be null or blank
- 'name' must not be blank
- 'email' must be in the format of an email, and must not be blank
- 'age' must be an integer, and must not be blank
- 'country' must not be blank
- 'phone' must be in the structure of a phone number with a country code identifier, or if missing in the structure of a phone number from Portugal

### Logging
This application contains logs that express the following:
- Success at saving or updating an User
- Discarded User due to blank, invalid or missing value
- Bad Requests for both endpoints
- Reception of API requests

## API Endpoints

### CSV Import Endpoint

- `/api/v1/import/csv`
- Must be of content-type multipart/form-data.
- Body must contain file: {filetoimport.csv}
- On correct request receives a 200 with a list of processed and discarded users.
- On incorrect mapping and formatting of csv receives a 400 response
- On incorrect content type receives a 415 response

### Export Endpoint
- `api/v1/export`
- Must have parameters:
  - format - Which must be CSV, TXT, XLS or XLSX
  - columns - columns which you want in the file (These must be a list divided by commas, and can only contain id, name, age, email, country and phone)
  - If either of these parameters don't follow these prerequisites, it receives a 400 response, denoting a Bad Request

## Database Authentication
In order to deploy the database it is necessary to utilize the secrets provided via the email annex. They are not in this repository in order to lightly simulate secrets obtained from a secret storage, although nothing of the sort is implemented, merely obtaining the username password and database name from the .env file provided. 

## Tech Debt and Improvements

As stated previously, due to some personal issues that took more time to resolve than expected, I ended up too optimistically estimating the time I had to conclude this assignment.
Hence, there are certain improvements I would make to this project in order to make it a more robust service. They are as follows:

### Change id to be UUID instead of long
Ids shouldn't be Longs, and the usage of UUID would ensure a much more solid primary key.

### More expressive error and discard responses in the API

The API responses currently are able to list all users that were discarded due to validations on import, but is unable to properly express which were discarded due to bad formatting. This can make some responses more difficult to parse in order to identify issues with the request.
The Bad Request responses for the export endpoint don't give details as to why the request was badly constructed, potentially causing difficulty in diagnosing issues with the request.

### Lack of authentication for the REST API

As it stands it is not necessary to authenticate as a user of any kind for the usage of the API, which on a normal product meant to be called by other services would cause security issues.

### Stabler dependency management for Spring
All libraries and plugins were pointed to be dependent on Spring Boot 4.1.0. However, this could have been achieved via a more stable and easy to maintain manner, by removing lone versions and depending on a plugin version.

### Isolate CSV parsing behavior in application layer
While I decided to maintain the logic of parsing the CSV in the adapter domain, due to an initial belief that it would be the responsibility of the adapter to ensure a correct input, too much logic ended up being necessary, which leads me to believe using a chain of responsibility pattern in the application layer would have been more proper for my chosen approach.

### Change Export column list to be a list of a typed object instead of String
As it is, this service could become vulnerable to SQL Injection, and so, making it a list of an object created via Enum would be more appropriate

### Alter Import and Export logic to be able to handle bigger loads
The service as was created was not made to be able to handle CSVs with tens of millions of rows, nor would it perform well if after a long period, if the user table had that many number of rows to be exported.
In order to solve this, instead of materializing and searching for users in the direct way it's currently handled, for import I would use a streaming reader (probably via a specific CSV streaming library) or insert in the database in batches, instead of one by one as it is being done.
For the export, similarly, I would use a streaming writer that writes each row to the CSV and TXT. Apache POI already handles this for XLSX so it wouldn't be as necessary. XLS however due to being legacy has a hard cap of 65536 row limit at a time, making large exports difficult.

### Addition of a cache and better indexing
Due to the potentiality of the same query being performed multiple times to the DB from the Export, adding a cache seems ideal, reducing query times, in case the table gets too large.
On top of it, if a specific set of columns was noticed to be requested frequently adding a index for that kind of call could likely speed up querying times significantly.

### More extensive integration testing, and addition of end-to-end and mutation tests
This service has heavy coverage from unit tests, and has a small suite of integration tests to ensure the functionality of the API. However, I feel like more integration tests could have been made, especially ones that don't mock database calls.
In addition, this service would benefit from end-to-end testing to ensure the functionality of it through and through, and mutation testing to ensure that the inputs from the REST API are properly handled.
