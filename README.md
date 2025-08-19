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
- As a user, I want to be able to view a vehicle in detail and see its rental history.
- As a user, I want to be able to record whether a vehicle is currently available for renting and mark when a vehicle is rented.
- As a user, I want to be able to save my locations and vehicles to a file if/when I choose to
- As a user, I want to be able to load my locations and vehicles using a file if/when I choose to
- As a user, I want to be reminded to save my locations and vehicles to a file when I close the application, and save them if I choose to. 

**Note:** *a "vehicle" here can be any type of vehicle, with it's type specified on construction.*

## TODO List:

1. Use enumerations and switches instead of if-else statements
2. fix errors with nextInt()
