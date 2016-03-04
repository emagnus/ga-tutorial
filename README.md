# Genetic algorithms 101

A brief introduction to genetic algorithms, structured as a series of exercises for you to implement step-by-step. Two example optimisation problems are used: a 2D driving simulator and the [travelling salesman problem][tsp-link].

## Requirements
* Java 1.7+
* Maven

## Setup
1. Clone this repo
2. Run `mvn compile` to fetch the dependencies (or let your IDE do it for you)

## An overview of the code
The GeneticAlgorithm class contains the core of the genetic algorithm. It's meant to be general enough to cover both the problems described in this README, and uses methods in other classes to do most of the work. It is also meant to be easy to read, giving you a reference for what the major steps of a genetic algorithm are.

There are two starting points for running the genetic algorithm - one for each of the two problems. These starting points are where you can adjust parameters to configure the genetic algorithm.

A few unit tests are provided. You are encouraged to implement more as you go along.

## Problem 1: Finding the best car for traversing difficult terrain

Let's get started! 

The objective is to find the optimal shape, size and weight of a car driving through difficult terrain. We define the optimal combination to be the one that lets the car travel the furthest before getting stuck or running out of time.

### Checking that everything is running OK
1. Run CarGeneticAlgorithmStarter's main method. By default it runs the simulation with visualization enabled. You should see a car being dropped into a 2D world and driving off to the right.
2. Change the POPULATION_SIZE parameter to 10, and run it again. Some chaos should ensue.

### Some batteries included
Some functionality is provided. This includes the code to create a car from a bit string genotype, the code that runs the simulation, and collecting some simple statistics along the way. The genetic algorithm itself is not much more than a for loop, running the same simulation over and over again. Not very interesting. It's your job to fill in the missing snippets of code to make it actually work. Look for the TODOs.  

### The initial population
The cars you saw in the previous step were not very diverse. In fact, only the size of the front wheels were different. The code that is generating their genotypes needs some work.

We want the initial population to be heterogeneous, covering as wide a variety of solutions as possible. An easy way of achieving this is by starting out with completely random genotypes.

1. The BitStringIndividualGenerator class is used to generate the initial population of cars' genotypes. Fix it! 
2. Verify that it is working by running CarGeneticAlgorithmStarter again.

### The fitness function
The fitness function should separate the fit from the weak. In this case, we've set the objective to be driving the furthest.

1. The CarFitnessEvaluator class is supposed to assign a fitness value to the individuals based on the distance the cars have driven. Fix it!
2. Verify that it is working by running CarGeneticAlgorithmStarter again. Check out the statistics window that pops up at the end. 

### Selection
Now that we've assigned a fitness value to each individual, it's time to select the lucky individuals who get to reproduce and create the next generation of cars!

There are several ways of determining which individuals should be selected for reproduction. The basic idea is that individuals with high fitness values should have a better chance of being selected for reproduction than individuals with lower fitness values.

1. One way of selecting individuals for reproduction is by setting the probability that they reproduce proportional to their fitness value. Implement such a selection scheme in the FitnessProportionateSelector class.
2. Run the CarGeneticAlgorithmStarter again. What do you see? What is the statistics plot telling you?

### Crossover
We finally have a way of selecting the most fit individuals for reproduction. It's time to start producing some interesting offspring!

Since we are using bit strings as genotypes, there is only one requirement for the child genotypes. They have to be of the same length as their parents.

One way of combining two genotypes into two new genotypes is crossover. https://en.wikipedia.org/wiki/Crossover_%28genetic_algorithm%29.

1. Implement one-point crossover in the BitStringIndividualRecombinator class. The method should produce a list of two offspring.
2. Adjust the CROSSOVER_RATE parameter.
3. Run the CarGeneticAlgorithmStarter! What does the statistics plot tell you? Play with the CROSSOVER_RATE parameter.

### Mutation
To avoid having the population become too homogeneous, we can use mutation to introduce some diversity. On bit string genotypes it is common to implement mutation as a bit flip or a bit swap. You can choose either.

When a new generation has been produced through reproduction, there should be a slight chance that mutation occurs.

1. Implement mutation in the BitStringIndividualMutator class.
2. Run the CarGeneticAlgorithmStarter! 
3. Try setting the MUTATION_RATE paramter to something higher (>0.5). Run the CarGeneticAlgorithmStarter. What happens?

### Elitism
The objective of the genetic algorithm is to find the most optimal solutions to a problem. In this case this means finding the best car. Right now, there is no guarantee that the best car makes it to the end of the evolution. 

To remedy this, we can make sure that the fittest individual(s) always make it to the next generation, unmodified. This ensures that we always keep the best car _so far_, and that max fitness will never decrease from one generation to the next.

1. Implement elitism in the GeneticAlgorithm class.
2. Run the CarGeneticAlgorithmStarter. Did the elitism have any effect on the fitness over time?

### Play with it
Now you have a working genetic algorithm. Half of the fun is experimenting with the parameters to find better solutions. What happens if you increase the population size? Does running the genetic algorithm over more generations help?

Remember that you can disable the visualization of the car simulation to make the genetic algorithm run faster. 


## Problem 2: Finding the shortest route visiting a given set of cities

On to a more real world problem. The travelling salesman problem is a classic: 

_Given a set of cities and the distance between each pair of cities, find the shortest possible route that visits them all once and returns to the origin city._

### Checking that everything is running OK
1. Run TspGeneticAlgorithmStarter's main method. By default it runs the simulation with visualization enabled. You should see a plot where the cities are black dots and an example route is visualized in blue.

### The initial population
We want the genotype to encode a valid route. The bit strings used in Problem 1 are unsuitable. Instead we can label the N cities using numbers 1..N and define a route as a permutation of that sequence. 

1. The TspIndividualGenerator class is lacking in functionality. Implement it so that random, valid routes are generated. 

### The fitness function
The objective is to find the shortest route, so we want our fitness function to reflect that. Since we want to minimize the distance, we have to make a choice:
* If we want to maximize fitness (as in the previous problem), we need to make sure that a shorter distance equals a higher fitness. This can be done by using the inverse of the distance, or just negating the distance.
* If not, rewrite the selection code so that it favors the individuals with the lowest fitness. If you don't do this, the genetic algorithm will end up trying to maximize the total distance, aka finding the worst route :)

Time to break out the old high school maths. Each city is represented as a point (x,y) in a 2D coordinate system. The distance between them is the euclidean distance. Given a route 1-3-4-2-1, we want to sum up the distances between cities 1-3, 3-4, 4-2 and 2-1 to find the total.

1. Implement it!!

### Selection
Optional! The selection code you wrote during Problem 1 should still work.

### Crossover
Now here comes the tricky bit. We can't use the crossover code from Problem 1, because the offspring produced is not guaranteed to encode valid solutions.

1. Implement it!!

### Mutation

1. Implement it!!

### Elitism
Should still work :)

### Play with it
Check out the larger datasets!!



Created by Magnus Westergaard.


2D car simulation inspired by http://rednuht.org/genetic_cars_2/.

TSP datasets derived from datasets available at from http://www.math.uwaterloo.ca/tsp/world/countries.html.


[tsp-link]: https://en.wikipedia.org/wiki/Travelling_salesman_problem