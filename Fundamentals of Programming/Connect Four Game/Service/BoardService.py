'''
Board Service
'''

from texttable import Texttable


class BoardService:
    def __init__(self, board_repo):
        self._board = board_repo

    def display_board(self):
        return self._board.get_board()

    def initialize_board(self):
        return self._board.init_board()

    def move(self, board, x, y, symbol):
        """
        Makes a move o the board
        :param board:
        :param x: the row
        :param y: the column
        :param symbol: X or O
        :return:
        """
        if symbol not in ['X', 'O']:
            raise Exception('Bad symbol!')
        board[x][y] = symbol

    def reverse_board(self):
        """
        The reversed board. The game shows the reversed board
        :return:
        """
        new_board = Texttable()
        b = self._board.get_board()
        for rn in range(b.get_rows - 1, -1, -1):
            rows = []
            for index in b[rn]:
                if index is None:
                    rows.append(' ')
                else:
                    rows.append(index)
            new_board.add_row(rows)
        return new_board.draw()

    def check_lines(self, symbol):
        """
        Checks if the current player wins. If there are 4 with the same symbol on the horizontal or vertical line.
        :param symbol:
        :return:
        """
        b = self._board.get_board()
        # Horizontal line
        for col in range(b.get_columns - 3):
            for row in range(b.get_rows):
                if b[row][col] == b[row][col+1] == b[row][col+2] == b[row][col+3] == symbol:
                    return True
        # Vertical Line
        for col in range(b.get_columns):
            for row in range(b.get_rows - 3):
                if b[row][col] == b[row+1][col] == b[row+2][col] == b[row+3][col] == symbol:
                    return True

    def check_diagonals(self, symbol):
        """
        Checks if the current player wins. If there are 4 with the same symbol on the diagonals.
        :param symbol:
        :return:
        """
        b = self._board.get_board()
        for col in range(b.get_columns - 3):
            for row in range(b.get_rows - 3):
                if b[row][col] == b[row+1][col+1] == b[row+2][col+2] == b[row+3][col+3] == symbol:
                    return True

        for col in range(b.get_columns - 3):
            for row in range(3, b.get_rows):
                if b[row][col] == b[row-1][col+1] == b[row-2][col+2] == b[row-3][col+3] == symbol:
                    return True

