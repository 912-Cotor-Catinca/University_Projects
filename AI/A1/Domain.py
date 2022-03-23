from random import random

import pygame
from pygame.constants import *

from Constants import *
import numpy as np


class Environment:
    def __init__(self, rows=ROWS, cols=COLS):
        self._rows = rows
        self._cols = cols
        self._surface = np.zeros((self._rows, self._cols))

    def get_rows(self):
        return self._rows

    def get_cols(self):
        return self._cols

    def get_surface(self):
        return self._surface

    def set_environment(self, rows, cols, surface):
        self._rows = rows
        self._cols = cols
        self._surface = surface

    def randomMap(self, fill=0.2):
        for i in range(self._rows):
            for j in range(self._cols):
                if random() <= fill:
                    self._surface[i][j] = 1

    def __str__(self):
        string = ""
        for i in range(self._rows):
            for j in range(self._cols):
                string = string + str(int(self._surface[i][j]))
            string = string + "\n"
        return string

    def readUDMSensors(self, x, y):
        readings = [0, 0, 0, 0]
        # UP
        xf = x - 1
        while (xf >= 0) and (self._surface[xf][y] == 0):
            xf = xf - 1
            readings[UP] = readings[UP] + 1
        # DOWN
        xf = x + 1
        while (xf < self._rows) and (self._surface[xf][y] == 0):
            xf = xf + 1
            readings[DOWN] = readings[DOWN] + 1
        # LEFT
        yf = y + 1
        while (yf < self._cols) and (self._surface[x][yf] == 0):
            yf = yf + 1
            readings[LEFT] = readings[LEFT] + 1
        # RIGHT
        yf = y - 1
        while (yf >= 0) and (self._surface[x][yf] == 0):
            yf = yf - 1
            readings[RIGHT] = readings[RIGHT] + 1

        return readings

    def wall(self, x, y):
        return self._surface[x][y] == 1


class DMap:
    def __init__(self, rows=ROWS, cols=COLS):
        self._rows = rows
        self._cols = cols
        self.surface = np.zeros((self._rows, self._cols))
        for i in range(self._rows):
            for j in range(self._cols):
                self.surface[i][j] = -1

    def get_surface(self):
        return self.surface

    def not_wall(self, x, y):
        return self.surface[x][y] == 0

    def markDetectedWalls(self, e, x, y):
        #   To DO
        # mark on this map the wals that you detect
        wals = e.readUDMSensors(x, y)
        i = x - 1
        if wals[UP] > 0:
            while (i >= 0) and (i >= x - wals[UP]):
                self.surface[i][y] = 0
                i = i - 1
        if i >= 0:
            self.surface[i][y] = 1

        i = x + 1
        if wals[DOWN] > 0:
            while (i < self._rows) and (i <= x + wals[DOWN]):
                self.surface[i][y] = 0
                i = i + 1
        if i < self._rows:
            self.surface[i][y] = 1

        j = y + 1
        if wals[LEFT] > 0:
            while (j < self._cols) and (j <= y + wals[LEFT]):
                self.surface[x][j] = 0
                j = j + 1
        if j < self._cols:
            self.surface[x][j] = 1

        j = y - 1
        if wals[RIGHT] > 0:
            while (j >= 0) and (j >= y - wals[RIGHT]):
                self.surface[x][j] = 0
                j = j - 1
        if j >= 0:
            self.surface[x][j] = 1

        return None


class Drone:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.visited = np.zeros((ROWS, COLS))
        self.st = [(x, y)]

    def move(self, detectedMap):
        pressed_keys = pygame.key.get_pressed()
        if self.x > 0:
            if pressed_keys[K_UP] and detectedMap.surface[self.x - 1][self.y] == 0:
                self.x = self.x - 1
        if self.x < 19:
            if pressed_keys[K_DOWN] and detectedMap.surface[self.x + 1][self.y] == 0:
                self.x = self.x + 1

        if self.y > 0:
            if pressed_keys[K_LEFT] and detectedMap.surface[self.x][self.y - 1] == 0:
                self.y = self.y - 1
        if self.y < 19:
            if pressed_keys[K_RIGHT] and detectedMap.surface[self.x][self.y + 1] == 0:
                self.y = self.y + 1

    def unvisited_cells(self, row, col, my_map):
        # returns the unvisited positions of the drone
        self.visited[(row, col)] = True
        unviseted = []
        for direction in DIRECTIONS:
            x2 = row + direction[0]
            y2 = col + direction[1]
            if 0 <= x2 < ROWS and 0 <= y2 < COLS and self.visited[(x2,y2)] == 0 and my_map.not_wall(x2, y2):
                unviseted.append([x2, y2])
        return unviseted

    def moveDFS1(self, detectedMap):
        unvisited = self.unvisited_cells(self.x, self.y, detectedMap)
        if len(unvisited) == 0:
            if len(self.st) == 0:
                self.x = None
                self.y = None
                return False
            self.x, self.y = self.st.pop()
        else:
            # push on the stack the current position of the drone
            # pop the next position of the drone
            # mark the position visited
            self.st.append((self.x, self.y))
            self.x, self.y = unvisited.pop()
            self.visited[(self.x, self.y)] = True
