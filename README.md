# CPSC 210 Project: Bike Rental System

## Overview

As a student at UBC, I typically have about 10 minutes to travel between consecutive classes. However, some distances are too far to cover on foot within that time, so I occasionally bring a bike or scooter to campus—though this can be inconvenient. While rental bike stations exist around campus, they are not always easily accessible.

To start addressing this, I want to develop a vehicle rental system that streamlines the management of bike, scooter, and other vehicle rentals. This application would allow a rental manager to add and manage various rental locations and assign vehicles to them. It would handle many of the tasks needed to manage a vehicle rental system. 

## User Stories
- As a user, I want to be able to add a rental location to my system and specify the location and maximum capacity.
- As a user, I want to be able to add vehicles to a rental location **if there is still capacity left** and specify their type, rental rate, availability, and location.
- As a user, I want to be able to remove a vehicle from a location or a rental location from my system.
- As a user, I want to be able to view the list of rental locations in my system.
- As a user, I want to be able to view the list of vehicles at a rental location.
- As a user, I want to be able to view the list of vehicles not currently being rented at a rental location.
- As a user, I want to be able to view a vehicle in detail and see its rental history.
- As a user, I want to be able to record whether a vehicle is currently available for renting and mark when a vehicle is rented.
- As a user, I want to be able to save my locations and vehicles to a file if/when I choose to
- As a user, I want to be able to load my locations and vehicles using a file if/when I choose to
- As a user, I want to be reminded to save my locations and vehicles to a file when I close the application, and save them if I choose to. 

**Note:** *a "vehicle" here can be any type of vehicle, with it's type specified on construction.*

## Instructions for End User

- **To add an X to a Y**, run the project and click the button "Add a new location".
- **To view the X's you have added to a Y**, one method is to run the project and click the button "View my locations" 
- **You can generate the first required action related to the user story "adding multiple Xs to a Y" (removing an X from a Y)** by running the project and choosing "Remove a location". 
    - Alternatively you can add a location through "Add a Location", choose to manage it through "Manage a Location", then choose "Remove a Vehicle"
- **You can generate the second required action related to the user story "adding multiple Xs to a Y" (Alternative displaying of Xs in a Y)** by running the program, adding a location through "Add a Location", choosing to manage it through "Manage a Location", then choosing "View Available Vehicles"
- **You can locate my visual component** by running the program and watching the Splash Screen
- **You can save the state of my application** by running the program and clicking the button "Save my locations" or entering "yes" to the prompt when you click the button "Quit Application"
- **You can reload the state of my application** by running the program and clicking the button "Load my locations".

## Phase 4: Task 2

Rental System created\
Created new Rental Location: HOME\
Vehicle of type: CAR, and ID: 0 added to HOME\
Vehicle with ID: 0 at HOME rented by SOPHIE\
Vehicle with ID: 0 at HOME returned\
Vehicle of type: BIKE, and ID: 1 added to HOME\
Vehicle of id: 1 removed from HOME\
Created new Rental Location: SCHOOL\
Vehicle of type: SCOOTER, and ID: 0 added to SCHOOL\
Vehicle with ID: 0 at SCHOOL rented by TERRI\
Vehicle with ID: 0 at SCHOOL returned\
Vehicle with ID: 0 at SCHOOL rented by PERCY\
Vehicle of type: SKATEBOARD, and ID: 1 added to SCHOOL\
Vehicle with ID: 1 at SCHOOL rented by BILL\
Location at SCHOOL removed\

## Phase 4: Task 3
Within my project, I’ve realized that the Vehicle class may need some refactoring. Currently, the class is responsible not only for maintaining its own details (ie. ID, type, and location) but also for managing rental information (a list of people who have rented that vehicle). This creates a design issue because it violates the Single Responsibility Principle: the class is handling two separate concerns. Although this isn’t a big issue so far, as the rental history is just a simple list of String names, the design could become problematic as the system evolves. For example, if the rental history is expanded to include a new Renter class or timestamps, the Vehicle class could become over-complicated and harder to maintain.

There are various ways to address this. One way is to create a new class that would contain a map mapping vehicles to a list of renters. Each rental location would then have one of this new class. This is very similar to many of the projects we have seen in 210, for instance the TeamBuilder practical midterm 2 practice problem. Another solution is to introduce a new class that would contain a reference to a Vehicle instance and the rental history just for that vehicle. A rental system would then contain some number of that new class. Both approaches separate concerns cleanly: the Vehicle class remains focused on vehicle-specific data, while the new class handles the logic related to keeping track of renters. 

