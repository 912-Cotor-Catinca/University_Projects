'''
    Repository class
'''

from Entity.Board import Board


class RepoBoard:
    def __init__(self):
        self._board = Board()

    def get_board(self):
        return self._board

    def is_free(self, column):
        """
        Checks if the element from the last row of a given column is None
        :param column:
        :return:
        """
        if self._board[self._board.get_rows - 1][column] is None:
            return True
        return False

    def init_board(self):
        """
        Initialize the values of the board with None
        :return:
        """
        for r in range(self._board.get_rows):
            for c in range(self._board.get_columns):
                self._board[r][c] = None
        return self._board
