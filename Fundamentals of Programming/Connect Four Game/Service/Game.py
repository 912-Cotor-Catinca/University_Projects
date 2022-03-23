from random import choice


class Game:
    def __init__(self, board_repo):
        self._board = board_repo

    def available_moves(self):
        """
        Returns the list with available columns on which the player can make the next move
        :return: a list
        """
        available_moves = []
        b = self._board.get_board()
        for col in range(b.get_columns):
            if self.is_valid_move(col):
                available_moves.append(col)
        return available_moves

    def next_row(self, col):
        """
        Computes the next row on a given column which is not marked yet
        :param col:
        :return: the row
        """
        b = self._board.get_board()
        row = 0
        done = False
        while not done:
            for r in range(b.get_rows - 1, -1, -1):
                if b[r][col] is None:
                    row = r
                    done = True
        return row

    def computer_move(self, symbol):
        """
        The computer's move
        :param symbol:
        :return: the column where to make the move
        """
        maximum = -10000
        available = self.available_moves()
        b = self._board.get_board()
        col = choice(available)
        for c in available:
            r = self.next_row(c)
            self.move(b, r, c, symbol)
            score = self.score(b, symbol)
            b[r][c] = None
            if score > maximum:
                maximum = score
                col = c
        return col

    def is_valid_move(self, col):
        """
        Checks if the current move can be made -> if the position is None
        :param col:
        :return: True if the move is valid, False otherwise
        """
        b = self._board.get_board()
        position = b[b.get_rows - 1][col]
        if position is None:
            return True
        return False

    def score(self, board, symbol):
        """
        Compute the score in order to force the computer to make the best move.(to win on horizontal/vertical/diagonals)
        :param board:
        :param symbol:
        :return: The score
        """
        score = 0

        """
        Force the computer to make the move in center in order to have more chances to win
        """
        center = []
        for r in range(board.get_rows):
            i = board[r][int(board.get_columns/2)]
            center.append(i)
        center_count = center.count(symbol)
        score += center_count * 3

        # Horizontal
        for r in range(board.get_rows):
            rows = []
            for c in range(board.get_columns):
                i = board[r][c]
                rows.append(i)
            for c in range(board.get_columns-3):
                data = rows[c:c+4]
                score += self.score_calculation(data, symbol)

        # Vertical
        for c in range(board.get_columns):
            columns = []
            for r in range(board.get_rows):
                i = board[r][c]
                columns.append(i)
            for r in range(board.get_rows-3):
                data = columns[r:r+4]
                score += self.score_calculation(data, symbol)

        # Diagonals
        for r in range(board.get_rows - 3):
            for c in range(board.get_columns - 3):
                data = [board[r + 3 - i][c+i] for i in range(4)]
                score += self.score_calculation(data, symbol)

        for r in range(board.get_rows - 3):
            for c in range(board.get_columns - 3):
                data = [board[r + i][c+i] for i in range(4)]
                score += self.score_calculation(data, symbol)

        return score

    def score_calculation(self, data, symbol):
        """
        Compute the score of the player's move
        :param data: The list with 4 elements containing the symbols : X or O
        :param symbol: X or O
        :return: Depending on the number of the symbol in the list, the score is computing
        """
        symbols = ['X', 'O']
        if symbol in symbols:
            comp_symbol = symbol
            symbols.remove(symbol)
            player_symbol = symbols[0]
        score = 0
        if data.count(symbol) == 4:  # 4 elements with the same symbol
            score += 100
        elif data.count(symbol) == 3 and data.count(None) == 1:
            score += 10
        elif data.count(symbol) == 2 and data.count(None) == 2:
            score += 5

        if data.count(player_symbol) == 3 and data.count(None) == 1:  # for blocking when the opponent has 3 in a row
            score -= 80

        return score

    def move(self, b, x, y, symbol):
        """
        Makes a move on board.
        :param b:
        :param x: the row
        :param y: the column
        :param symbol: the player's symbol
        :return:
        """
        if symbol not in ['X', 'O']:
            raise Exception('Bad symbol!')
        b[x][y] = symbol
