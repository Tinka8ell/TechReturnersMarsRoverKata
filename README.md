# Tech Returners Mars Rover Kata

## Summary of [The Brief]() 

From the brief we have:
* A Plateau - rectangular (Integer dimension) but no limit, provided by user on first input.
* One or more Rovers:
  * Location on grid - x, y (both Integers)
  * Direction d, relative to grid - one of {N, S, E, W} 
    * N => +ve y or delta grid of (0, 1)
    * S => -ve y or delta grid of (0, -1)
    * E => +ve x or delta grid of (1, 0)
    * W => -ve x or delta grid of (-1, 0)
  * Location and direction given on one line, space separated in order x, y, d
  * Receive instructions (commands) as a string of (initially) characters from {L, R, M}
    * L => turn left by 90 degrees
    * R => turn right by 90 degrees
    * M => move forward one unit
* Control device receives:
  * Plateau dimensions as inclusive maximum in order x then y - no response
  * Address of a rover in order x then y then d - no response
  * Command string - response is the final address of the rover as x then y then d 

## Initial design

* Three main classes:
  * Controller receives commands and actions them and responds if required
  * Plateau where the rovers roam - contains the rovers and their locations
  * Rovers move according to commands received - contain a direction

It may be simple, but we will have a [UML](doc/Mars.drawio). 
Drawing the [Initial UML](doc/Mars1UML.jpg) enabled me to identify some more utility classes:

* Direction - enum mapping to Deltas
* Delta - extends IntVector2D
* Location - extends IntVector2D
* IntVector2D - x & y container

On top of this, we will have a main method class to act as the console device.
To keep things clean we will put everything in its own package: com.tinkabell.rover

## Testing Strategy

Of course, we will be using TDD, but what do we need to support this?  As much of the class data is private,
and we don't want to break encapsulation, I will add protected inspectors, so we can see (but not touch) 
internal state to check void returning methods have changes state.  
There is no point in calling them if they do nothing!

In addition to this, many of the methods take integer and character inputs as space separated Strings.  
This will mean that there will be input format validation, so we will have to check for correct 
Invalid Input Exceptions (whatever they will be, but probably runtime exceptions) being thrown.

As the classes and supporting test classes evolve, they will be documented in the Git commit history. 