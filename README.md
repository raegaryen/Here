Coding challenge
================

![Build Status - Master](https://api.travis-ci.org/raegaryen/here.svg?branch=master)

## User stories
- As a user, I want to search for places or addresses and see a list of results
- ~~As a user, I want to choose a start and destination from the search results~~
- ~~As a user, I want to see a route between the start and destination on a map~~
- Instead, I want to choose my destination from the search results and I see the route between me and the destination. As long as I use the Route API.

## General infos
To save time as a developer, I simplified the navigation with between 2 Activities : __MapActivity__ and __ListPlaceActivity__ , a sub-Activity.
The reason : most map apps display the interaction in a single Activity , the architecture will be more complex with nested Fragments  ... 