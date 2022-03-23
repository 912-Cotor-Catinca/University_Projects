from time import sleep

import pygame
from pygame import KEYDOWN

from Constants import *


class Gui:
    def __init__(self, service, play_choice):
        self._service = service
        self._choice = play_choice

    def drone_map_image(self, x, y):
        imagine = pygame.Surface((420, 420))
        brick = pygame.Surface((20, 20))
        empty = pygame.Surface((20, 20))
        empty.fill(WHITE)
        brick.fill(BLACK)
        imagine.fill(GRAYBLUE)

        surface = self._service.get_dmap().get_surface()
        for i in range(self._service.get_rows()):
            for j in range(self._service.get_cols()):
                if surface[i][j] == 1:
                    imagine.blit(brick, (j * 20, i * 20))
                elif surface[i][j] == 0:
                    imagine.blit(empty, (j * 20, i * 20))

        drona = pygame.image.load("drona.png")
        imagine.blit(drona, (y * 20, x * 20))
        return imagine

    def environment_image(self, colour=BLUE, background=WHITE):
        imagine = pygame.Surface((420, 420))
        brick = pygame.Surface((20, 20))
        brick.fill(colour)
        imagine.fill(background)
        surface = self._service.get_environment().get_surface()
        for i in range(self._service.get_rows()):
            for j in range(self._service.get_cols()):
                if surface[i][j] == 1:
                    imagine.blit(brick, (j * 20, i * 20))

        return imagine

    def start(self):
        rows = self._service.get_rows()
        cols = self._service.get_cols()

        # initialize the pygame module
        pygame.init()
        # load and set the logo
        logo = pygame.image.load("logo32x32.png")
        pygame.display.set_icon(logo)
        pygame.display.set_caption("drone exploration")

        # create a surface on screen that has the size of 800 x 480
        screen = pygame.display.set_mode((800, 400))
        screen.fill(WHITE)
        screen.blit(self.environment_image(), (0, 0))

        running = True

        while running:
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    # change the value to False, to exit the main loop
                    running = False
                if self._choice == PLAY_BY_HAND and event.type == KEYDOWN:
                    self._service.move(self._service.get_dmap())
            if self._choice == MOVE_AUTOMATICALLY:
                self._service.moveDFS1()
                sleep(0.1)
            self._service.get_dmap().markDetectedWalls(self._service.get_environment(), self._service.get_drone().x, self._service.get_drone().y)
            screen.blit(self.drone_map_image(self._service.get_drone().x, self._service.get_drone().y), (400, 0))
            pygame.display.flip()

        pygame.quit()
