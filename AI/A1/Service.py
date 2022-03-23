import pygame
from pygame import *

from Constants import DIRECTIONS, ROWS, COLS


class Service:
    def __init__(self, environment, dmap, drone):
        self._env = environment
        self._dmap = dmap
        self._drone = drone
        self.__generator = None
        self.visited_cells = None

    def get_rows(self):
        return self._env.get_rows()

    def get_cols(self):
        return self._env.get_cols()

    def get_dmap(self):
        return self._dmap

    def get_drone(self):
        return self._drone

    def get_environment(self):
        return self._env

    def moveDFS1(self):
        self._drone.moveDFS1(self._dmap)

    def move(self, detectedMap):
        self._drone.move(detectedMap)
