### Prefetch is simple text based web crawler written using Spring Boot to archive entire text of websites into a database.

It uses [jsoup](https://github.com/jhy/jsoup) and [PostgreSQL](https://www.postgresql.org/).

# Configuration
### Requirements
* Java 11
* PostgreSQL
* Any IDE which supports Java

### Steps
1. Import the project into an IDE
2. Install the required Maven Dependencies
3. Install PostgreSQL if not already installed and create a database named `prefetch`
3. Change the `application.properties` file as per your PostgreSQL Access Credentials
4. Run the application


You may also use other databases by simply replacing their dependencies in `pom.xml` file and changing the `application.properties` file
See also
* [Using MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Using MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Other Docs](https://spring.io/guides/gs/accessing-data-jpa/)
