# -*- coding: utf-8 -*-

import pickle

import random
from Domain import *


class Repository:
    def __init__(self):
        self.populations = None
        self.cmap = Map()
        self.drone = Drone(randint(0, 19), randint(0, 19))
        
    def createPopulation(self, population_size, individual_size):
        self.populations = Population(self.drone, self.cmap, population_size, individual_size)

    def set_new_population(self, population):
        self.populations = Population(self.drone, self.cmap, v=population)

    def load_random(self):
        self.cmap.randomMap()

    def load_map(self, numfile):
        with open(numfile, "rb") as f:
            self.cmap = pickle.load(f)

    def save_map(self, numFile):
        with open(numFile, 'wb') as f:
            pickle.dump(self.cmap, f)

            