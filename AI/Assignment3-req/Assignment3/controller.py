import statistics
from copy import deepcopy

from Assignment3.Domain import Individual


class Controller:
    def __init__(self, repository):
        self.repository = repository

    def iteration(self, population_size):
        new_population = []
        for i in range(population_size):
            parent1 = self.repository.populations.selection()
            parent2 = self.repository.populations.selection()
            offspring1, _ = Individual.crossover(self.repository.drone, self.repository.cmap, parent1, parent2)
            offspring1.mutate()
            new_population.append(offspring1)
        self.repository.set_new_population(new_population)
        
    def run(self, population_size, nr_of_runs):
        fitness_list_avg = []
        fitness_list_max = []
        best_solution = None
        for _ in range(nr_of_runs):
            self.iteration(population_size)
            fitness_list_avg.append(statistics.mean([individual.f for individual in self.repository.populations.v]))
            fitness_list_max.append(max(
                [individual.f for individual in self.repository.populations.v]
            ))
            for individual in self.repository.populations.v:
                if best_solution is None or best_solution.f < individual.f:
                    best_solution = deepcopy(individual)
        path = best_solution.path()
        return path, fitness_list_avg, fitness_list_max, best_solution.f
    
    def solver(self, population_size, individual_size, nr_of_runs):
        self.repository.createPopulation(population_size, individual_size)
        return self.run(population_size, nr_of_runs)
       