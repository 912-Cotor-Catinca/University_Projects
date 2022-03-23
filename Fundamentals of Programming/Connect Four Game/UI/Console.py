from random import randint, choice

from Service.Game import Game


class UI:
    def __init__(self, game_srv, board_srv):
        self._game = game_srv
        self._board = board_srv

    def player_input(self):
        player1 = input("Please choose your marker: X or O: ")
        while True:
            if player1.upper() == 'X':
                computer = 'O'
                return player1.upper(), computer
            elif player1.upper() == 'O':
                computer = 'X'
                return player1.upper(), computer
            else:
                player1 = input("Please choose your marker: 'X' or 'O': ")

    def start(self):
        done = False
        board = self._board.display_board()
        while not done:
            print(board)
            player1, computer = self.player_input()
            if player1 == 'X':
                i = 1
            else:
                i = 0
            win = False
            while not win:
                if i % 2 == 0:
                    current_player = 'Computer'
                    symbol = computer

                    col = self._game.computer_move(symbol)
                    if self._game.is_valid_move(col):
                        row = self._game.next_row(col)
                        self._board.move(board, row, col, symbol)

                    print(self._board.reverse_board())
                    if self._board.check_lines(symbol) or self._board.check_diagonals(symbol):
                        win = True
                        print(current_player + ' is the winner!')
                        r = False
                        while not r:
                            replay = input('Do you want to play again? (Y/N) ? ')
                            if replay.lower() == 'n':
                                done = True
                                r = True
                                print('Game over!')
                                return
                            elif replay.lower() == 'y':
                                print('New game!')
                                self._board.initialize_board()
                                r = True
                                self.start()
                            else:
                                print('Bad input. Do you want to play again? (Y/N) ? ')
                                r = False
                else:
                    current_player = 'Player'
                    symbol = player1

                    col = int(input(current_player + ' please choose your move: from 0-6 - '))
                    if self._game.is_valid_move(col):
                        row = self._game.next_row(col)
                        self._board.move(board, row, col, symbol)

                    print(self._board.reverse_board())
                    if self._board.check_lines(symbol) or self._board.check_diagonals(symbol):
                        win = True
                        print(current_player + ' is the winner!')
                        r = False
                        while not r:
                            replay = input('Do you want to play again? (Y/N) ? ')
                            if replay.lower() == 'n':
                                done = True
                                r = True
                                print('Game over!')
                                return
                            elif replay.lower() == 'y':
                                print('New game!')
                                self._board.initialize_board()
                                r = True
                                self.start()
                            else:
                                print('Bad input. Do you want to play again? (Y/N) ? ')
                                r = False

                i += 1
