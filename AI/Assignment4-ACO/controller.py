from random import randrange

from Domain import Drone, Map, Sensors, Ant
from utils import *


class Controller:
    def __init__(self):
        self.drone = Drone(0, 0)
        self.map = Map()
        self._place_drone()
        self.sensors_list = Sensors(self.map)
        self.pheromones = [[1.0 for _ in range(SENSOR_COUNT)] for _ in range(SENSOR_COUNT)]
        self.dist = self.sensors_list.getDistance()

    def _place_drone(self):
        x = randrange(0, self.map.n)
        y = randrange(0, self.map.m)
        while self.map.surface[x][y] == 1:
            x = randrange(0, mapLength)
            y = randrange(0, mapLength)
        self.drone.setXY(x, y)

    def getMap(self):
        return self.map

    def ants_move(self, ants, alpha, beta, q0):
        lives = [True for _ in ants]
        for i in range(len(ants)):
            ant = ants[i]
            for step in range(SENSOR_COUNT - 1):
                move = ant.next_move(self.dist, alpha, beta, self.pheromones, q0)
                if not move:
                    lives[i] = False
                    break
        remaining_ants = []
        for i in range(len(ants)):
            if lives[i]:
                ants[i].compute_fitness(self.dist)
                remaining_ants.append(ants[i])
        return remaining_ants

    @staticmethod
    def best_ant(ants):
        best = ants[0]
        best_fitness = 0
        for ant in ants:
            if best_fitness < ant.get_fitness():
                best_fitness = ant.get_fitness()
                best = ant
        return best

    def paths_covered_by_all_ants(self, nr_ants, alpha, beta, q0, rho):
        # rho- define the evaporation rate
        ants = [Ant() for _ in range(nr_ants)]
        ants = self.ants_move(ants, alpha, beta, q0)

        # the amount of pheromone
        for i in range(SENSOR_COUNT):
            for j in range(SENSOR_COUNT):
                self.pheromones[i][j] = (1 - rho) * self.pheromones[i][j]

        if not ants:
            return None
        # the new pheromone level that should be deposited by all ends
        new_pheromones = [1.0 / ant.get_fitness() for ant in ants]

        for i in range(len(ants)):
            current = ants[i].getPath()
            for j in range(len(current) - 1):
                sensor = current[j]
                next = current[j + 1]
                # Compute the intensity of pheromone trail as a sum of old pheromone evaporation
                # and the new deposited pheromone
                self.pheromones[sensor][next] += new_pheromones[i]

        return self.best_ant(ants)

    def charge(self, battery, acc_sensors):
        sensors = []
        for i in range(len(self.sensors_list.getSensors())):
            if i in acc_sensors:
                sensors.append(self.sensors_list.getSensors()[i])
        energy = [0 for _ in sensors]
        if battery <= 0:
            return energy

        sensors.sort(key=lambda s: (s.getAccPath()[-1] / s.getMaxEnergy()))
        i = 0
        while i < len(sensors) and battery > 0:
            sensor_energy = sensors[i].getMaxEnergy()
            if battery > sensor_energy:
                battery -= sensor_energy
                energy[i] = sensor_energy
            else:
                energy[i] = battery
                battery = 0
            i += 1
        return energy

    def best_solution(self, best_sol):
        solution = self.paths_covered_by_all_ants(ANT_COUNT, 1.9, 1.0, 0.5, 0.05)
        if solution is None:
            return best_sol
        path_length = len(solution.getPath())
        if best_sol is None or path_length > len(best_sol.getPath()) or (path_length == len(best_sol.getPath()) and solution.get_fitness() < best_sol.get_fitness()):
            return solution
        return best_sol

    def run(self):
        best_sol = None
        print("Starting")
        for i in range(1000):
            best_sol = self.best_solution(best_sol)

        energy = self.charge(BATTERY - best_sol.get_fitness(), best_sol.getPath())
        print("Path: ")
        for index in best_sol.getPath():
            print(self.sensors_list.getSensors()[index].getCoord(), end=" ")
        print("\nEnergy in each sensor: ", energy)
