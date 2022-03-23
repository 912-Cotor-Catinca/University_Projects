from BoardRepo.RepoBoard import RepoBoard
from Service.BoardService import BoardService
from Service.Game import Game
from UI.Console import UI

if __name__ == '__main__':
    repo_board = RepoBoard()
    srv_board = BoardService(repo_board)
    srv_game = Game(repo_board)
    ui = UI(srv_game, srv_board)

    ui.start()