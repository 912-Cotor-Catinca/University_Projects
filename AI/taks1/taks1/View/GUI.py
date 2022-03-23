import time

import pygame
from pygame.constants import KEYDOWN, K_UP, K_DOWN

from taks1.constants import BLUE, WHITE, GREEN, PINK


class GUI:
    def __init__(self, service):
        self.service = service

    def mapWithDrone(self, mapImage, x, y):
        drona = pygame.image.load("drona.png")
        mapImage.blit(drona, (y * 20, x * 20))

        return mapImage

    def image(self, colour=BLUE, background=WHITE):
        imagine = pygame.Surface((400, 400))
        brick = pygame.Surface((20, 20))
        brick.fill(colour)
        imagine.fill(background)
        surface = self.service.get_surface().get_surface()
        for i in range(self.service.get_rows()):
            for j in range(self.service.get_cols()):
                if surface[i][j] == 1:
                    imagine.blit(brick, (j * 20, i * 20))

        return imagine

    def dummysearch(self):
        # example of some path in test1.map from [5,7] to [7,11]
        return [[5, 7], [5, 8], [5, 9], [5, 10], [5, 11], [6, 11], [7, 11]]

    def displayWithPath(self, image, path):
        mark = pygame.Surface((20, 20))
        mark.fill(GREEN)
        for move in path:
            image.blit(mark, (move[1] * 20, move[0] * 20))

        return image

    def displayWithPath2(self, image, path):
        mark = pygame.Surface((20, 20))
        mark.fill(PINK)
        for move in path:
            image.blit(mark, (move[1] * 20, move[0] * 20))

        return image

    def start(self):
        pygame.init()
        # load and set the logo
        logo = pygame.image.load("logo32x32.png")
        pygame.display.set_icon(logo)
        pygame.display.set_caption("Path in simple environment")

        screen = pygame.display.set_mode((400, 400))
        screen.fill(WHITE)
        running = True
        # main loop
        final_time = 0
        final_time1 = 0
        path_greedy = []
        path_astar = []
        while running:
            # event handling, gets all event from the event queue
            for event in pygame.event.get():
                # only do something if the event is of type QUIT
                if event.type == pygame.QUIT:
                    # change the value to False, to exit the main loop
                    running = False

            screen.blit(self.mapWithDrone(self.image(), self.service.get_drone().x, self.service.get_drone().y), (0, 0))
            pygame.display.flip()
            pressed_keys = pygame.key.get_pressed()
            if pressed_keys[K_UP]:
                start_time = time.time()
                path_greedy = self.service.searchGreedy()
                final_time = time.time() - start_time
                screen.blit(self.displayWithPath(self.image(), path_greedy), (0, 0))
                pygame.display.flip()
                time.sleep(2)
                running = True
            elif pressed_keys[K_DOWN]:
                start_time = time.time()
                path_astar = self.service.searchAStar()
                final_time1 = time.time() - start_time
                screen.blit(self.displayWithPath2(self.image(), path_astar), (0, 0))
                pygame.display.flip()
                time.sleep(2)
                running = True

        print("--- %s Greedy seconds ---" % final_time)
        print("--- %s A* seconds ---" % final_time1)
        print("Path Greedy : ", end="")
        for step in path_greedy:
            print(step, end="")
        print("/n")
        print("Path A Star : ", end="")
        for step in path_astar:
            print(step, end="")
        time.sleep(2)
        pygame.quit()
