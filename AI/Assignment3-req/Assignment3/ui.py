# -*- coding: utf-8 -*-
import statistics

from gui import *
from controller import *
from repository import *
from matplotlib import pyplot

# create a menu
#   1. map options:
#         a. create random map
#         b. load a map
#         c. save a map
#         d visualise map
#   2. EA options:
#         a. parameters setup
#         b. run the solver
#         c. visualise the statistics
#         d. view the drone moving on a path
#              function gui.movingDrone(currentMap, path, speed, markseen)
#              ATENTION! the function doesn't check if the path passes trough walls


def main():
    repository = Repository()
    controller = Controller(repository)
    map = False
    param_setup = False
    program_solved = False
    population_size = None
    individual_size = None
    number_of_runs = None
    battery = None
    fitness_avg = None
    fitness_max = None
    path = None
    while True:
        print("1. Map options")
        print("2. EA options")
        print("0. Exit")
        command = input("Enter your choice: ")
        if command == "1":
            print("1. Create a random map")
            print("2. Load a map")
            print("3. Save a map")
            print("4. Visualise a map")
            command2 = input("Enter your choice: ")
            if command2 == "1":
                map = True
                controller.repository.load_random()
            elif command2 == "2":
                map = True
                controller.repository.load_map("file.map")
            elif command2 == "3":
                if map:
                    controller.repository.save_map("file.map")
                else:
                    print("Map is not loaded yet")
            elif command2 == "4":
                if map:
                    drone_map_image(controller.repository.cmap, controller.repository.cmap.x, controller.repository.cmap.y)
                else:
                    print("Map is not loaded yet")
            else:
                print("Incorrect choice")
        elif command == "2":
            if not map:
                print("Map is not loaded yet")
                continue
            print("1. Parameters setup")
            print("2. Run the solver")
            print("3. Visualise the statistics")
            print("4. View the drone moving on a path")
            print("5. Run the solver more than once an compute the standard deviation")
            command2 = input("Enter your choice: ")
            if command2 == "1":
                param_setup = True
                program_solved = False
                battery = input("battery (30): ")
                if len(battery) != 0:
                    battery = int(battery)
                else:
                    battery = 30
                population_size = input("population size (100): ")
                if len(population_size) != 0:
                    population_size = int(population_size)
                else:
                    population_size = 100
                individual_size = input(f"individual size ({battery * 2}): ")
                if len(individual_size) != 0:
                    individual_size = int(individual_size)
                else:
                    individual_size = battery * 2
                number_of_runs = input("number of runs (100): ")
                if len(number_of_runs) != 0:
                    number_of_runs = int(number_of_runs)
                else:
                    number_of_runs = 100
            elif command2 == "2":
                if not param_setup:
                    print("Parameters not set up")
                    continue
                if program_solved:
                    print("program already solved")
                    continue
                start = time.time()
                path, fitness_avg, fitness_max, fitness = controller.solver(population_size, individual_size, number_of_runs)
                end = time.time()
                print(f"Evolutionary algorithm ran in {end - start} seconds")
                program_solved = True
            elif command2 == "3":
                if not program_solved:
                    print("Program not yet solved")
                    continue
                stdev = statistics.stdev(fitness_avg)
                print(f"Average solution fitness {statistics.mean(fitness_avg)} and it has a standard deviation of{stdev}")
                pyplot.plot(fitness_avg)
                pyplot.plot(fitness_max)
                pyplot.savefig("fitness.png")
                pyplot.close()
            elif command2 == "4":
                movingDrone(controller.repository.cmap, path)
            elif command2 == "5":
                values = []
                for i in range(30):
                    seed(i)
                    _, _, _, fitness = controller.solver(population_size, individual_size, number_of_runs)
                    values.append(fitness)
                avg = statistics.mean(values)
                stdev = statistics.stdev(values)
                print(f"Average solution fitness was found to be {avg} and it has a stdev of {stdev}")

        elif command == "0":
            exit()
        else:
            print("Incorrect choice")


if __name__ == '__main__':
    main()