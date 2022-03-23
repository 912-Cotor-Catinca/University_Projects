import time
from random import randrange

from Controller.Controller import Controller
from Model.Drone import Drone
from Model.Map import Map
from View.GUI import GUI
from taks1.constants import ROWS, COLS


def main():
    map = Map()
    map.loadMap("test1.map")

    rows = map.n
    cols = map.m
    x = randrange(0, rows)
    y = randrange(0, cols)
    while map.surface[x][y] == 1:
        x = randrange(0, rows)
        y = randrange(0, cols)

    drone = Drone(x, y)

    fx = randrange(0, ROWS)
    fy = randrange(0, COLS)
    while map.surface[fx][fy] == 1 or fx == x and fy == y:
        fx = randrange(0, rows)
        fy = randrange(0, cols)

    print(x)
    print(y)
    print(fx)
    print(fy)

    service = Controller(drone, map, x, y, fx, fy)
    gui = GUI(service)
    gui.start()


if __name__ == '__main__':
    main()

