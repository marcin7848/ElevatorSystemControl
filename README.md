<div style="text-align: center;"><h1>Elevator System Control</h1></div>

## Documentation


## Development setup

### Prerequisites
You can use Docker to build and run the app - skip to [Docker Setup](#Docker-setup "Goto docker-Setup")

- Install [Java JDK 19][JavaJDK]
- Install [Node.js 18][Node.js]
- Install [Gradle 7.6][Gradle]

### Building the project
Open the command line tool in the project directory and run:

```
gradle build
```

Run the application:
```
java -jar app/elevator-system-control-1.0.jar
```
Open the browser and go to [http://localhost:8080/][localhost]

Notes: On the application startup, the sample data are loaded into the database.
The application uses the H2 in-memory database. As a result, the database is cleared on the application shutdown.


### Docker setup 


[JavaJDK]: https://www.oracle.com/java/technologies/downloads/
[node.js]: https://nodejs.org/
[Gradle]: https://gradle.org/install/
[localhost]: http://localhost:8080/