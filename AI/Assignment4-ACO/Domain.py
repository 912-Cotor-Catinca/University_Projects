import random
from random import randrange

import numpy as np

from utils import DIRECTION, BATTERY, SENSOR_COUNT, INFINITY, mapLength


class Drone:
    def __init__(self, x, y):
        self.__x = x
        self.__y = y

    def getX(self):
        return self.__x

    def getY(self):
        return self.__y

    def setXY(self, x, y):
        self.__x = x
        self.__y = y


class Map():
    def __init__(self, n=20, m=20):
        self.n = n
        self.m = m
        self.surface = np.zeros((self.n, self.m))

        self.randomMap()

    def set_Value_Sensors(self, x, y, val):
        self.surface[x][y] = val

    def randomMap(self, fill=0.2):
        for i in range(self.n):
            for j in range(self.m):
                if random.random() <= fill:
                    self.surface[i][j] = 1

    def __str__(self):
        string = ""
        for i in range(self.n):
            for j in range(self.m):
                string = string + str(int(self.surface[i][j]))
            string = string + "\n"
        return string


class Sensor:
    def __init__(self, x, y, dmap):
        self.x = x
        self.y = y
        self.dmap = dmap
        self.maxEnergy = 0
        self.path = [0 for _ in range(6)]

    def getMaxEnergy(self):
        return self.maxEnergy

    def getAccPath(self):
        return self.path

    def valid(self, x, y):
        if 0 <= x < self.dmap.n and 0 <= y < self.dmap.m and self.dmap.surface[x][y] != 1:
            return True
        return False

    def detectMaxEnergy(self):
        blocked = [False for _ in range(4)]
        for i in range(1, 6):
            self.path[i] = self.path[i - 1]
            for direction in range(4):
                if not blocked[direction]:
                    newX = self.x + DIRECTION[direction][0] * i
                    newY = self.y + DIRECTION[direction][1] * i
                    if self.valid(newX, newY):
                        self.path[i] += 1
                    else:
                        blocked[direction] = True

    def computeMaxEnergy(self):
        for energy in range(5):
            if self.path[energy] == self.path[energy + 1]:
                self.maxEnergy = energy
                return
        self.maxEnergy = 5

    def getCoord(self):
        coords = (self.x, self.y)
        return coords

    def getX(self):
        return self.x

    def getY(self):
        return self.y


class Ant:
    def __init__(self):
        self.battery = BATTERY
        self.size = SENSOR_COUNT
        self.ant_path = [random.randint(0, SENSOR_COUNT - 1)]
        self.__fitness = 0

    def possibleMoves(self, dist):
        moves = []
        current_sensor = self.ant_path[-1]

        for sensor in range(SENSOR_COUNT):
            if sensor != current_sensor and dist[current_sensor][sensor] != INFINITY and \
                    sensor not in self.ant_path and self.battery >= dist[current_sensor][sensor]:
                moves.append(sensor)
        return moves

    def go_to_next_sensor_prob(self, moves, alpha, beta, dist, pheromones):
        current_sensor = self.ant_path[-1]
        next_sensor = [0 for _ in range(SENSOR_COUNT)]

        for move in moves:
            dist_next_sensor = dist[current_sensor][move]
            pheromone_next = pheromones[current_sensor][move]
            probability = (dist_next_sensor ** beta) * (pheromone_next ** alpha)
            next_sensor[move] = probability
        return next_sensor

    def next_move(self, dist, alpha, beta, pheromones, q0):
        # For each ant of the colony
        # Increase the partial solution by an element (ant moves one step)
        moves = self.possibleMoves(dist)
        if not moves:
            return False
        next_sensor_prob = self.go_to_next_sensor_prob(moves, alpha, beta, dist, pheromones)
        if random.random() < q0:
            best_prob = max(next_sensor_prob)
            selected_sensor = next_sensor_prob.index(best_prob)
        else:
            selected_sensor = self.city_selected_by_probability(next_sensor_prob)

        self.battery -= dist[self.ant_path[-1]][selected_sensor]
        self.ant_path.append(selected_sensor)

        return True

    @staticmethod
    def city_selected_by_probability(next_sensor_prob):
        prob_sum = sum(next_sensor_prob)

        if prob_sum == 0:
            return random.randint(0, len(next_sensor_prob) - 1)
        # probability of transition of ant k from city i to city j
        p = [next_sensor_prob[0] / prob_sum]
        for i in range(1, len(next_sensor_prob)):
            p.append(p[i - 1] + next_sensor_prob[i] / prob_sum)

        pos = 0
        while random.random() > p[pos]:
            pos += 1
        return pos

    def compute_fitness(self, dist):
        self.__fitness = 0
        for i in range(1, len(self.ant_path)):
            self.__fitness += dist[self.ant_path[i - 1]][self.ant_path[i]]

    def get_fitness(self):
        return self.__fitness

    def getPath(self):
        return self.ant_path


class Sensors:
    def __init__(self, dmap):
        self.sensors = []
        self.dmap = dmap
        self.place_sensors()
        self.distances = [[0 for _ in range(SENSOR_COUNT)] for _ in range(SENSOR_COUNT)]
        self.distance_between_sensors()
        for sensor in self.sensors:
            sensor.detectMaxEnergy()
            sensor.computeMaxEnergy()

    def valid(self, x, y):
        if 0 <= x < mapLength and 0 <= y < mapLength and self.dmap.surface[x][y] != 1:
            return True
        return False

    def bfs(self, start_x, start_y, end_x, end_y):
        dist = {(start_x, start_y): 0}
        visited = [(start_x, start_y)]
        while visited:
            x, y = visited.pop(0)
            for direction in DIRECTION:
                new_x = x + direction[0]
                new_y = y + direction[1]
                if self.valid(new_x, new_y) and (new_x, new_y) not in dist:
                    dist[(new_x, new_y)] = dist[(x, y)] + 1
                    visited.append((new_x, new_y))
                    if new_x == end_x and new_y == end_y:
                        return dist[(end_x, end_y)]
        return INFINITY

    def place_sensors(self):
        self.sensors.clear()
        for sensor in range(SENSOR_COUNT):
            x = randrange(0, mapLength)
            y = randrange(0, mapLength)
            while self.dmap.surface[x][y] != 0:
                x = randrange(0, mapLength)
                y = randrange(0, mapLength)
            self.dmap.set_Value_Sensors(x, y, 3)
            self.sensors.append(Sensor(x, y, self.dmap))

    def distance_between_sensors(self):
        for i in range(len(self.sensors)):
            self.distances[i][i] = 0
            x, y = self.sensors[i].getX(), self.sensors[i].getY()
            for j in range(i + 1, len(self.sensors)):
                dist = self.bfs(x, y, self.sensors[j].getX(), self.sensors[j].getY())
                self.distances[i][j] = self.distances[j][i] = dist

    def getDistance(self):
        return self.distances

    def getSensors(self):
        return self.sensors