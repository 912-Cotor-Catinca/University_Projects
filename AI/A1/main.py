# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.


# Press the green button in the gutter to run the script.
from random import randrange

import FileService
from Constants import PLAY_BY_HAND, MOVE_AUTOMATICALLY
from Domain import Environment, Drone, DMap
from Service import Service
from gui import Gui


def main():
    environment = Environment()
    environment.randomMap()
    FileService.saveEnvironment(environment, "test.map")

    rows = environment.get_rows()
    cols = environment.get_cols()
    x = randrange(0, rows)
    y = randrange(0, cols)
    while environment.wall(x, y):
        x = randrange(0, rows)
        y = randrange(0, cols)
    drone = Drone(x, y)
    drone_map = DMap(rows, cols)
    service = Service(environment, drone_map, drone)
    gui = Gui(service, MOVE_AUTOMATICALLY)
    gui.start()


if __name__ == '__main__':
    main()
