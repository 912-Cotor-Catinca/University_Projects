from Domain.Rental import ValidationRentalId
from Domain.Book import ValidationBookId
from Domain.Client import ClientValidator
from Repository.BookRepository import BookRepo
from Repository.ClientRepository import ClientRepo
from Repository.RentalRepository import RentalRepo
from Repository.UndoRepo import RepoUndo
from Service.BookService import BookServiceUndo
from Service.ClientService import ClientServiceUndo
from Service.RentalService import RentalService, RentalServiceUndo
from Service.UndoService import UndoService
from UI.Console import UI

if __name__ == '__main__':
    validBook = ValidationBookId()
    validClient = ClientValidator()
    validRental = ValidationRentalId()
    repoUndo = RepoUndo()
    repoBook = BookRepo()
    repoClient = ClientRepo()
    repoRental = RentalRepo()
    undo_service = UndoService(repoUndo)
    srvRental = RentalServiceUndo(repoRental, validRental, repoBook, repoClient, repoUndo)
    srvBook = BookServiceUndo(repoBook, validBook, repoUndo)
    srvClient = ClientServiceUndo(repoClient, validClient, repoUndo)
    cons = UI(undo_service, srvBook, srvClient, srvRental)
    cons.start_uit()
