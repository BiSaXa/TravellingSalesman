# TravellingSalesman
A brute-force searching program in order to calculate the minimum distance while visiting all destination points from the starting point and ending there.   

[Heap's algorithm](https://en.wikipedia.org/wiki/Heap%27s_algorithm) was used to calculate all permutations of possible routes.   

[StdDraw](https://introcs.cs.princeton.edu/java/stdlib/stdlib.jar) is used to plot the points and the roads.

## How To Use?

Make sure you imported stdlib.jar into your project. After that, run Main.java   

In the following StdDraw window, first press 'S' key to place the starting point.   

After that, you can place destination points with the 'D' key. You can place as much as you want but be careful as time it takes to calculate is exponential.   

When you feel like you are done with destination points, press 'X' key to start calculating.   

Estimated times to calculate according to point number; (Please note that these times can change based on system specs.)

> <=6 Points = 100ms   
> 7 Points = 150ms   
> 8 Points = 250ms   
> 9 Points = 450ms   
> 10 Points = 3s   
> 11 Points = 33s   
> 12 Points = 7.5m   
> \>12 Points = Too much.

You can clearly see that it increases very quickly, making it exponential. Our time complexity is O(n!).

## Screenshots

<img width="450" alt="3" src="https://user-images.githubusercontent.com/1669855/124183757-58e6dc00-dac1-11eb-9bd0-d9f8ddf8b1ac.PNG">
<img width="450" alt="4" src="https://user-images.githubusercontent.com/1669855/124183763-5b493600-dac1-11eb-9dbb-a86c3803ec4c.PNG">



