--TrafficLight--

-Variables-
* redShade,yellow and green shades, specify the current color
* whether it is active or dim
* activeRed = stop, activeGreen= go, activeYellow= slow

1. station(); sets the location of the traffic light
	given the orientation as to whether it is horizontal
	or vertical and places it there
2. redLight(),yellowLight() and greenLight(), draws the
	the traffic light based on the location and 
	orientation and sets the color calling any of the
	corresponding active Color method i.e activeRed() etc
3. redColor(),yellowColor() and greenColor() returns the
 	current shade
4. activeRed(),activeYellow() and activeGreen() sets the 
	shades i.e redShade, etc
 