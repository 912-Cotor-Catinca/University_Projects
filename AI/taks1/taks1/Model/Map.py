import pickle
from random import random

import numpy as np


from taks1.constants import *


class Map():
    def __init__(self, n=ROWS, m=COLS):
        self.n = n
        self.m = m
        self.surface = np.zeros((self.n, self.m))

    def get_rows(self):
        return self.n

    def get_cols(self):
        return self.m

    def get_surface(self):
        return self.surface

    def randomMap(self, fill=0.2):
        for i in range(self.n):
            for j in range(self.m):
                if random() <= fill:
                    self.surface[i][j] = 1

    def __str__(self):
        string = ""
        for i in range(self.n):
            for j in range(self.m):
                string = string + str(int(self.surface[i][j]))
            string = string + "\n"
        return string

    def saveMap(self, numFile="test.map"):
        with open(numFile, 'wb') as f:
            pickle.dump(self, f)
            f.close()

    def loadMap(self, numfile):
        with open(numfile, "rb") as f:
            dummy = pickle.load(f)
            self.n = dummy.n
            self.m = dummy.m
            self.surface = dummy.surface
            f.close()