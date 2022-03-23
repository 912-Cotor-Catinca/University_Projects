from texttable import Texttable


class Board:
    def __init__(self):
        self._rows = 6
        self._columns = 7
        self.board = [[None for i in range(self.get_columns)] for j in range(self.get_rows)]

    @property
    def get_rows(self):
        return self._rows

    @property
    def get_columns(self):
        return self._columns

    def __str__(self):
        """
        Displaying as a texttable
        :return:
        """
        t = Texttable()
        for row in range(6):
            rows = []
            for index in self.board[row]:
                if index is None:
                    rows.append(' ')
                elif index == 'X' or index == 'O':
                    rows.append(index)
            t.add_row(rows)
        return t.draw()

    def __getitem__(self, item):
        return self.board[item]