from Domain.Rental import ValidationRentalId
from Domain.Book import ValidationBookId
from Domain.Client import ClientValidator
from Repository.BookRepository import BookRepo
from Repository.ClientRepository import ClientRepo
from Repository.FileRepositories import FileBookRepo, FileClientRepo, FileRentalRepo
from Repository.PickleRepo import BinaryBookRepo, BinaryClientRepo, BinaryRentalRepo
from Repository.RentalRepository import RentalRepo
from Repository.UndoRepo import RepoUndo
from Service.BookService import BookServiceUndo
from Service.ClientService import ClientServiceUndo
from Service.RentalService import  RentalServiceUndo
from Service.UndoService import UndoService
from Settings import Settings
from UI.Console import UI


if __name__ == '__main__':
    validBook = ValidationBookId()
    validClient = ClientValidator()
    validRental = ValidationRentalId()
    repoUndo = RepoUndo()
    settings = Settings()
    if settings.type_of_repo == 'inmemory':
        repoBook = BookRepo()
        repoClient = ClientRepo()
        repoRental = RentalRepo()
        srvRental = RentalServiceUndo(repoRental, validRental, repoBook, repoClient, repoUndo)
        srvBook = BookServiceUndo(repoBook, validBook, repoUndo)
        srvClient = ClientServiceUndo(repoClient, validClient, repoUndo)
        undo_service = UndoService(repoUndo)
        cons = UI(undo_service, srvBook, srvClient, srvRental)
        cons.start_uit()
    elif settings.type_of_repo == "files":
        rep_book = FileBookRepo(settings.book_repo)
        rep_client = FileClientRepo(settings.clients_repo)
        rep_rental = FileRentalRepo(settings.rentals_repo)
        srvRental = RentalServiceUndo(rep_rental, validRental, rep_book, rep_client, repoUndo)
        srvBook = BookServiceUndo(rep_book, validBook, repoUndo)
        srvClient = ClientServiceUndo(rep_client, validClient, repoUndo)
        undo_service = UndoService(repoUndo)
        cons = UI(undo_service, srvBook, srvClient, srvRental)
        cons.start_uit()
    elif settings.type_of_repo == "binary files":
        repo_book = BinaryBookRepo(settings.book_repo)
        repo_client = BinaryClientRepo(settings.clients_repo)
        repo_rental = BinaryRentalRepo(settings.rentals_repo)
        srvRental = RentalServiceUndo(repo_rental, validRental, repo_book, repo_client, repoUndo)
        srvBook = BookServiceUndo(repo_book, validBook, repoUndo)
        srvClient = ClientServiceUndo(repo_client, validClient, repoUndo)
        undo_service = UndoService(repoUndo)
        cons = UI(undo_service, srvBook, srvClient, srvRental)
        cons.start_uit()

