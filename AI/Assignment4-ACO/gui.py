import pygame
from pygame import KEYDOWN

from utils import WHITE, BLUE, mapLength, GREEN


class GUI:
    def __init__(self, service):
        self.service = service
        self.init_PyGame()
        self.screen = pygame.display.set_mode((400, 400))
        self.screen.fill(WHITE)

    def init_PyGame(self):
        pygame.init()
        logo = pygame.image.load("logo32x32.png")
        pygame.display.set_icon(logo)
        pygame.display.set_caption("drone exploration with ACO")

    def get_MapImage(self, colour=BLUE, background=WHITE):
        currentMap = self.service.getMap()
        imagine = pygame.Surface((400, 400))
        sensor = pygame.Surface((20, 20))
        brick = pygame.Surface((20, 20))
        brick.fill(colour)
        sensor.fill(GREEN)
        imagine.fill(background)
        for i in range(mapLength):
            for j in range(mapLength):
                if currentMap.surface[i][j] == 1:
                    imagine.blit(brick, (j * 20, i * 20))
                elif currentMap.surface[i][j] == 3:
                    imagine.blit(sensor, (j * 20, i * 20))

        return imagine

    def drone_map_image(self):
        drona = pygame.image.load("drona.png")
        pathImage = self.get_MapImage()
        pathImage.blit(drona, (self.service.drone.getY() * 20, self.service.drone.getX() * 20))
        self.screen.blit(pathImage, (0, 0))
        pygame.display.update()
        while True:
            for event in pygame.event.get():
                if event.type == KEYDOWN:
                    return
            pygame.time.wait(1)

    def start(self):
        self.service.run()
        self.drone_map_image()
