# File reader

A repository of a java program that reads a file, loads it to a database (MySQL/PostgreSQL), and then
checks if a specific IP address makes more than a certain number of requests for a specified period or
window of time.

The application uses a rate limit of 200 requests per hour, and 500 requests per day as limits. 


## Installation

Step 1: Clone repository

Step 2: Install dependencies: `cd` into the application root folder and run the following command: `mvn install`

Step 3: Run the following command in your desired MySQL DBMS client to set up the database and user configurations:

```sql

create database if not exists req_limit;

create user if not exists 'req_limit_user'@'localhost' identified by 'password';
grant all on req_limit.* to 'req_limit_user'@'localhost';

flush privileges;

```

Step 3: cd into the target folder and execute the Java program from the command line as JAR, with the following command-line
arguments:

 `java -cp java-program.jar <absolute/path/to/file> <start-date-time> <duration>`


Duration is either hourly or daily. 

Start date time must be of the format "yyyy-MM-dd.HH:mm:ss"
## Author

- [@olu-damilare](https://www.github.com/olu-damilare)

