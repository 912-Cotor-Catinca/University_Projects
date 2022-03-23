from unittest import TestCase

from BoardRepo.RepoBoard import RepoBoard
from Entity.Board import Board
from Service.BoardService import BoardService
from Service.Game import Game


class TestBoard(TestCase):
    def setUp(self):
        self.board = Board()
        self.board_repo = RepoBoard()
        self.board_service = BoardService(self.board_repo)
        self.game = Game(self.board_repo)

    def test_dom(self):
        self.assertEqual(self.board.get_columns, 7)
        self.assertEqual(self.board.get_rows, 6)
        self.board[2][3] = 'X'
        str(self.board)

    def test_repo(self):
        b = self.board_repo.get_board()
        self.assertEqual(b[0][0], None)
        for r in range(b.get_rows):
            b[r][1] = 1
        self.assertEqual(self.board_repo.is_free(1), False)
        self.assertEqual(self.board_repo.is_free(2), True)
        self.board_repo.init_board()
        self.assertEqual(self.board_repo.is_free(1), True)

    def test_srv(self):
        self.board_service.initialize_board()
        board = self.board_service.display_board()
        try:
            self.board_service.move(board, 4, 0, 'L')
        except Exception as ve:
            self.assertEqual(str(ve), 'Bad symbol!')
        self.board_service.reverse_board()
        self.board_service.move(board, 5, 0, 'X')
        self.board_service.move(board, 5, 1, 'X')
        self.board_service.move(board, 5, 2, 'X')
        self.board_service.move(board, 5, 3, 'X')
        self.assertEqual(self.board_service.check_lines('X'), True)
        self.board_service.reverse_board()
        self.board_service.move(board, 5, 3, 'O')
        self.board_service.move(board, 4, 3, 'O')
        self.board_service.move(board, 3, 3, 'O')
        self.board_service.move(board, 2, 3, 'O')
        self.assertEqual(self.board_service.check_lines('O'), True)

        self.board_service.move(board, 5, 4, 'O')
        self.board_service.move(board, 3, 2, 'O')
        self.board_service.move(board, 2, 1, 'O')
        self.assertEqual(self.board_service.check_diagonals('O'), True)

        self.board_service.move(board, 4, 0, 'X')
        self.board_service.move(board, 3, 1, 'X')
        self.board_service.move(board, 2, 2, 'X')
        self.board_service.move(board, 1, 3, 'X')
        self.assertEqual(self.board_service.check_diagonals('X'), True)

    def test_game(self):
        self.board_repo.init_board()
        board = self.board_repo.get_board()
        try:
            self.game.move(board, 1, 1, 'L')
        except Exception as ex:
            self.assertEqual(str(ex), 'Bad symbol!')
        self.game.move(board, 0, 2, 'X')
        self.game.move(board, 0, 0, 'X')
        self.game.move(board, 0, 1, 'X')
        self.game.move(board, 0, 4, 'X')
        self.game.move(board, 0, 5, 'X')
        self.game.move(board, 0, 6, 'X')
        col = self.game.computer_move('O')
        self.assertEqual(self.game.is_valid_move(col), True)
        row = self.game.next_row(col)
        self.game.move(board, row, col, 'O')
        col1 = self.game.computer_move('O')
        self.assertEqual(self.game.is_valid_move(col1), True)
        self.game.move(board, 2, col1, 'O')
        self.game.move(board, 3, col1, 'O')
        self.game.move(board, 4, col1, 'O')
        self.game.move(board, 5, col1, 'O')
        self.assertEqual(self.game.is_valid_move(col1), False)

        col2 = self.game.computer_move('O')
        row2 = self.game.next_row(col2)
        self.game.move(board, row2, col2, 'O')

        avail = self.game.available_moves()
        self.assertEqual(len(avail), 6)
