<div style="text-align: center;"><h1>Elevator System Control</h1></div>

A simple WEB application for controlling, simulating and monitoring the elevators' system.


## Documentation

<details>
<summary>Short description</summary>


The application allows to add up to 16 elevators. The behavior of each elevator is simulated by a dedicated asynchronous thread. 
After adding a new elevator, a new thread is created and after removing the elevator, the thread is terminated.

The WEB application allows to pick the next selected floors for each elevator. You are able to simulate selecting a floor inside the elevator by choosing the floor number, or you can call for an elevator outside by clicking the arrow up/down next to the corresponding floor number. It affects the algorithm that decides the next floor where the elevator should go. 

The elevator can be in 4 different states:
- 0 - Waiting on the floor: {currentFloor}
- 1 - Going to the floor: {next selected floor}
- 2 - Opening doors
- 3 - Closing doors

Note that after a change in the current state there is a delay before changing it again. The default delay is around 3s-5s.
For the state 1 the delay is calculated as follows:
3 seconds per floor between the current floor and the next selected floor.
If the selected floor was changed during elevator's movement, changing the current floor stops and status is not updated until the next iteration

</details>

<details>
<summary>Selecting the next floor algorithm</summary>

Managing the next selected floor for an elevator works as follows:


- if there are no selected floors, the elevator waits on the last floor where it was
- if there is only 1 selected floor, the elevator goes there
- if there are 2 or more selected floors, the algorithm proceeds as follows:
  - for the selected floors inside the elevator:
    - go to the first selected floor unless there is another selected floor chosen INSIDE the elevator BETWEEN 
      the current floor and the current selected floor - if so: change the current selected floor to the one in-between
      (always pick the floor closest to the current floor first)
  - for the selected floors outside the elevator:
    - go to the first selected floor unless there is another selected floor chosen OUTSIDE the elevator BETWEEN
      the current floor and the current selected floor and the DIRECTION of the chosen floor outside the elevator is
      the SAME as the current elevator movement - if so: change the current selected floor to the one in between
      (always pick the floor closest to the current floor first)

</details>

<details>
<summary>Increasing the number of elevators</summary>

By default the application can control up to 16 elevators. To change it:

Go to: elevator-backend/src/main/java/com/elevatorsystemcontrol/service/ElevatorService.java and change the line:
```
private final int MAX_ELEVATORS = 16;
```

Go to: elevator-frontend/src/app/elevator-management/management/management.component.ts and change:
```
readonly MAX_ELEVATORS: number = 16;
```
</details>
<details>
<summary>Changing the number of floors</summary>

By default elevators simulated in the application operates between -2 and 6 floor. To change it:

Go to: elevator-backend/src/main/java/com/elevatorsystemcontrol/model/ElevatorFloor.java and change the line:
```
private static final int MIN_FLOOR = -2;
private static final int MAX_FLOOR = 6;
```

Go to: elevator-frontend/src/app/elevator-management/elevator/elevator.component.ts and change:
```
readonly MIN_FLOOR: number = -2;
readonly MAX_FLOOR: number = 6;
```
</details>

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