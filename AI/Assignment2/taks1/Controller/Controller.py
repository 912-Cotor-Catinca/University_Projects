from math import inf
from queue import PriorityQueue

from taks1.constants import DIRECTIONS, ROWS, COLS


class Controller:
    def __init__(self, drone, dmap, initialX, initialY, finalX, finalY):
        self.drone = drone
        self.dmap = dmap
        self.initialX = initialX
        self.initialY = initialY
        self.finalX = finalX
        self.finalY = finalY

    def get_drone(self):
        return self.drone

    def get_rows(self):
        return self.dmap.get_rows()

    def get_cols(self):
        return self.dmap.get_cols()

    def get_surface(self):
        return self.dmap

    def isValid(self, node):
        if 0 <= node[0] < self.dmap.n and 0 <= node[1] < self.dmap.m and self.dmap.surface[node[0]][node[1]] == 0:
            return True
        return False

    def searchAStar(self):
        inf = self.dmap.m + self.dmap.n
        found = False
        visited = [[False for i in range(COLS)] for j in range(ROWS)]
        prev = [[(i, j) for j in range(self.dmap.m)] for i in range(self.dmap.n)]
        dist = [[inf for i in range(self.dmap.m)] for j in range(self.dmap.n)]

        toVisit = PriorityQueue()
        toVisit.put((0, (self.initialX, self.initialY)))
        dist[self.initialX][self.initialY] = 0
        visited[self.initialX][self.initialY] = True
        while not toVisit.empty() and not found:
            if toVisit.empty():
                break
            node = toVisit.get()
            if node == (self.finalX, self.finalY):
                found = True
            for directions in DIRECTIONS:
                x = node[1][0]
                y = node[1][1]
                neighbour = (x + directions[0], y + directions[1])
                if self.isValid(neighbour) and not visited[neighbour[0]][neighbour[1]]:
                    prev[neighbour[0]][neighbour[1]] = node[1]
                    visited[neighbour[0]][neighbour[1]] = True
                    dist[neighbour[0]][neighbour[1]] = dist[node[1][0]][node[1][1]] + 1
                    # f(n) = h(n) + g(n)
                    cost_path = abs(self.finalX - neighbour[0]) + abs(self.finalY - neighbour[1]) + dist[neighbour[0]][neighbour[1]]
                    toVisit.put((cost_path, neighbour))
        if prev[self.finalX][self.finalY] == (self.finalX, self.finalY):
            return []
        path = []
        current = self.finalX, self.finalY
        while current != (self.initialX, self.initialY):
            path.append(current)
            current = prev[current[0]][current[1]]
        path.append(current)
        return list(reversed(path))

    def searchGreedy(self):
        inf = self.dmap.m + self.dmap.n
        found = False
        visited = [[False for i in range(COLS)] for j in range(ROWS)]

        prev = [[(i, j) for j in range(self.dmap.m)] for i in range(self.dmap.n)]
        dist = [[inf for i in range(self.dmap.m)] for j in range(self.dmap.n)]

        toVisit = PriorityQueue()
        toVisit.put((0, (self.initialX, self.initialY)))
        dist[self.initialX][self.initialY] = 0
        visited[self.initialX][self.initialY] = True
        while not toVisit.empty() and not found:
            if toVisit.empty():
                break
            node = toVisit.get()
            if node == (self.finalX, self.finalY):
                found = True
            for directions in DIRECTIONS:
                x = node[1][0]
                y = node[1][1]
                neighbour = (x + directions[0], y + directions[1])
                if self.isValid(neighbour) and not visited[neighbour[0]][neighbour[1]]:
                    prev[neighbour[0]][neighbour[1]] = node[1]
                    visited[neighbour[0]][neighbour[1]] = True
                    dist[neighbour[0]][neighbour[1]] = dist[node[1][0]][node[1][1]] + 1

                    cost_path = abs(self.finalX - neighbour[0]) + abs(self.finalY - neighbour[1])  # f(n) = h(n)
                    toVisit.put((cost_path, neighbour))
        if prev[self.finalX][self.finalY] == (self.finalX, self.finalY):
            return []
        path = []
        current = self.finalX, self.finalY
        while current != (self.initialX, self.initialY):
            path.append(current)
            current = prev[current[0]][current[1]]
        path.append(current)
        return list(reversed(path))
