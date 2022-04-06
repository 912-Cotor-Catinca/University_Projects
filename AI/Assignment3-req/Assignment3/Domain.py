# -*- coding: utf-8 -*-
from copy import copy, deepcopy
from random import *
from utils import *
import numpy as np


class Drone:
    def __init__(self, x, y, battery=30):
        self.__x = x
        self.__y = y
        self.__battery = battery

    def getX(self):
        return self.__x

    def getY(self):
        return self.__y

    def getBattery(self):
        return self.__battery


class Individual:
    def __init__(self, drone, dmap, size=0, x=None):
        self.__size = size
        if x is None:
            x = [randrange(0, 4) for i in range(size)]
        self.x = x
        self.f = None
        self.drone = drone
        self.dmap = dmap
        self.__visited = []

    def chromosome(self):
        return self.x

    def getDrone(self):
        return self.drone

    def isValid(self, x, y):
        if 0 <= x < self.dmap.n and 0 <= y < self.dmap.m and self.dmap.surface[x][y] != 1:
            return True
        return False

    def path(self):
        drone = [self.dmap.x, self.dmap.y]
        path = [drone]
        for i in range(len(self.x)):
            new_drone = [drone[0] + DIRECTION[self.x[i]][0], drone[1] + DIRECTION[self.x[i]][1]]
            if self.isValid(new_drone[0], new_drone[1]):
                if self.drone.getBattery() >= len(path):
                    drone = new_drone
                    path.append(drone)
        return path

    def update_fitness(self):
        path = self.path()
        marked = [[0 for _ in range(self.dmap.m)] for _ in range(self.dmap.n)]
        for pos in path:
            marked[pos[0]][pos[1]] = 1
            for direction in DIRECTION:
                cell = deepcopy(pos)
                while True:
                    cell[0] += direction[0]
                    cell[1] += direction[1]
                    if not self.isValid(cell[0], cell[1]):
                        break
                    marked[cell[0]][cell[1]] = 1
        self.f = sum([sum(row) for row in marked])

    def get_fitness(self):
        self.update_fitness()
        return self.f

    def mutate(self, mutateProbability=0.04):
        if random() < mutateProbability and len(self.x) >= 2:
            i = randrange(len(self.x))
            j = randrange(len(self.x))
            while i == j:
                i = randrange(len(self.x))
                j = randrange(len(self.x))
            self.x[i], self.x[j] = self.x[j], self.x[i]

    @staticmethod
    def crossover(drone, dmap, firstParent, otherParent, crossoverProbability=0.8):
        size = len(firstParent.x)
        offspring1, offspring2 = Individual(drone, dmap, size), Individual(drone, dmap, size)
        if random() < crossoverProbability:
            cutting_point = randint(0, size)
            offspring1.x = firstParent.x[:cutting_point] + otherParent.x[cutting_point:]
            offspring2.x = otherParent.x[:cutting_point] + firstParent.x[cutting_point:]
        return offspring1, offspring2


class Population:
    def __init__(self, drone, dmap, populationSize=10, individualSize=10, v=None):
        self.__populationSize = populationSize
        if v is None:
            v = [Individual(drone, dmap, individualSize) for x in range(populationSize)]
        self.v = v
        self.evaluate()

    def evaluate(self):
        # evaluates the population
        for x in self.v:
            x.update_fitness()

    def selection(self, k=2):
        return sorted(sample(self.v, k), key=lambda x: x.f, reverse=True)[0]


class Map():
    def __init__(self, n=20, m=20, x=None, y=None):
        self.n = n
        self.m = m
        self.surface = np.zeros((self.n, self.m))
        if x is None:
            x = randrange(n)
        if y is None:
            y = randrange(m)
        self.x = x
        self.y = y

    def randomMap(self, fill=0.2):
        for i in range(self.n):
            for j in range(self.m):
                if random() <= fill and (i != self.x or j != self.y):
                    self.surface[i][j] = 1

    def __str__(self):
        string = ""
        for i in range(self.n):
            for j in range(self.m):
                string = string + str(int(self.surface[i][j]))
            string = string + "\n"
        return string

