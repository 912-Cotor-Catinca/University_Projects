
from Domain.Rental import Rental, RentalDTO
from Domain.Undo import UndoRedoActionAddR, UndoRedoActionDeleteR, UndoRedoActionDeleteClientRental, \
    UndoRedoActionDeleteBookRental, UndoRedoActionUpdateR
from Costumize.Iterator import OrderIterator, Sort


class BookRentalCountDTO:
    def __init__(self, book, count):
        self._book = book
        self._count = count

    @property
    def book(self):
        return self._book

    @property
    def count(self):
        return self._count

    def __str__(self):
        return str(self._book) + ' ' + str(self._count)

    def __ge__(self, other):
        return self._count >= other.count


class ClientRentalDTO:
    def __init__(self, client, days):
        self._client = client
        self._days = days

    @property
    def client_(self):
        return self._client

    @property
    def days(self):
        return self._days

    def __str__(self):
        return str(self._client) + ' ' + str(self._days)

    def __ge__(self, other):
        return self._days >= other.days


class BookAuthorCountDTO:
    def __init__(self, author, count):
        self._author = author
        self._count = count

    @property
    def author(self):
        return self._author

    @property
    def count(self):
        return self._count

    def __str__(self):
        return self.author + ' ' + str(self.count)


class RentalService:
    def __init__(self, rental_repo, rental_val, book_repo, client_repo):
        self._rental_repo = rental_repo
        self._rental_val = rental_val
        self._book_repo = book_repo
        self._client_repo = client_repo

    def __iter__(self):
        return OrderIterator(self._rental_repo)

    def add_rental(self, id_rental, id_book, id_client, rental_date, returned_date):
        """
        Adds in a list if a client rented a book
        :param returned_date:
        :param id_rental:
        :param id_book:
        :param id_client: int
        :param rental_date:
        :return:
        """
        book = self._book_repo.find_book(id_book)
        client = self._client_repo.find_client(id_client)
        rental = Rental(id_rental, book, client, rental_date, returned_date)
        if self.find_id_rental(rental.rental_id) != -1:
            raise ValueError('Rental already exists!')
        else:
            self._rental_val.validate(rental)
            self._rental_repo.add(rental)
        return rental

    def delete_rental(self, rental_id, true=False):
        rental = self._rental_repo.delete(rental_id)
        return rental

    def delete_client_and_its_rentals(self, id_client):
        rentals = self._rental_repo.get_all()
        to_delete = []
        for rental in rentals:
            if rental.client.client_id == id_client:
                to_delete.append(rental)
                self._rental_repo.delete(rental.rental_id)
        self._client_repo.delete(id_client)
        return to_delete

    def delete_book_rentals(self, id_book):
        rentals = self._rental_repo.get_all()
        to_delete = []
        for rental in rentals:
            if rental.get_book == id_book:
                to_delete.append(rental)
                self._rental_repo.delete(rental.rental_id)
        self._book_repo.delete(id_book)
        return to_delete

    def update_return(self, id, return_date):
        """
       Updates the returned date of an instance of a Rental object with a given id
        :param return_date:
       :param id: Rental's id
       :return:
       """
        for r in self._rental_repo.get_all():
            if r.rental_id == id:
                old_date = r.returned_date
        new_rental = self._rental_repo.update_return(id, return_date)
        return new_rental, old_date

    def check_if_rent(self, book_id, returned):
        rentals = self._rental_repo.get_all()
        done = True
        for rental in rentals:
            if rental.get_book == book_id:
                if rental.rented_date > rental.returned_date and (rental.returned_date == returned):
                    return False
                    done = True
        if not done:
            return False
        else:
            return True

    def display(self):
        """
        Display the Rental list
        :return:
        """
        rental = self._rental_repo.get_all()
        rental_dtos = []
        for r in rental:
            id_rental = r.rental_id
            book_id = r.get_book.book_id
            client_id = r.client.client_id
            rented_date = r.rented_date
            returned_date = r.returned_date
            rental_dto = RentalDTO(id_rental, book_id, client_id, rented_date, returned_date)
            rental_dtos.append(rental_dto)
        return rental_dtos

    def find_id_rental(self, id):
        """
        Search the given id_rental is in the list of rentals
        :param id: The Rental's id
        :return: the index of the rental or -1 if there does not exist
        """
        for i in range(len(self._rental_repo)):
            if self._rental_repo[i].rental_id == id:
                return i
        return -1

    def find_client_id(self, id_client):
        for i in range(len(self._rental_repo)):
            if self._rental_repo[i].client.client_id == id_client:
                return i
        return -1

    def find_book_id(self, book_id):
        for i in range(len(self._rental_repo)):
            if self._rental_repo[i].get_book == book_id:
                return i
        return -1

    def most_rented_books(self):
        """
        The list of books, sorted in descending order by the number of times they were rented
        :return: A list of books
        """
        result = []
        for book in self._book_repo.get_all():
            dto = BookRentalCountDTO(book, self._create_book_rental_count(book))
            result.append(dto)

        return Sort.sort(result, lambda i, j: i.count < j.count)

    def most_author(self):
        """
        The list of authors, sorted in descending order by the number of times their books were rented
        :return: A list of authors
        """
        result = []
        dict = {}
        for book in self._book_repo.get_all():
            if book.author not in dict:
                dict[book.author] = self._create_book_rental_count(book)
            else:
                dict[book.author] += self._create_book_rental_count(book)
        for b in dict:
            result.append(BookAuthorCountDTO(b, dict[b]))

        # result.sort(reverse=True, key=lambda author_dto: author_dto.count)
        return Sort.sort(result, lambda i, j: i.count < j.count)

    def most_clients(self):
        """
        The list of clients, sorted in descending order by the number of book rental days they have
        :return: A list of clients
        """
        result = []
        for client in self._client_repo.get_all():
            count = 0
            for rental in self._rental_repo.get_all():
                if client.client_id == rental.client.client_id:
                    count += len(rental)
            days = self._create_client_count(client) * count
            dto = ClientRentalDTO(client, days)
            result.append(dto)

        # result.sort(reverse=True, key=lambda client_dto: client_dto.days)
        return Sort.sort(result, lambda i, j: i.days < j.days)

    def _create_book_rental_count(self, book):
        count = 0
        result = Sort.filter(self._rental_repo, lambda x: self.is_book_in_rentals(book))
        for r in result:
            if r.get_book == book:
                count += 1
        return count

    def _create_client_count(self, client):
        count = 0
        result = Sort.filter(self._rental_repo, lambda x: self.is_client_in_rentals(client))
        for r in result:
            if r.client == client:
                count += 1
        return count

    def is_client_in_rentals(self, client):
        for rental in self._rental_repo.get_all():
            if client is not None and rental.client == client:
                return True
        return False

    def is_book_in_rentals(self, book):
        for rental in self._rental_repo.get_all():
            if book is not None and rental.get_book == book:
                return True
        return False


class RentalServiceUndo(RentalService):
    def __init__(self, rental_repo, rental_val, book_repo, client_repo, undoRepo):
        RentalService.__init__(self, rental_repo, rental_val, book_repo, client_repo)
        self._undoRepo = undoRepo

    def add_rental(self, id_rental, id_book, id_client, rental_date, returned_date):
        rental = RentalService.add_rental(self, id_rental, id_book, id_client, rental_date, returned_date)
        actor = self._rental_repo
        obj = rental
        undoAction = UndoRedoActionAddR(actor, obj)
        self._undoRepo.push(undoAction)

    def delete_rental(self, rental_id, true=False):
        rental = RentalService.delete_rental(self, rental_id, true=False)
        actor = self._rental_repo
        obj = rental
        undoAction = UndoRedoActionDeleteR(actor, obj)
        self._undoRepo.push(undoAction)

    def delete_client_and_its_rentals(self, id_client):
        rental = RentalService.delete_client_and_its_rentals(self, id_client)
        actor = self._rental_repo
        client = rental[0].client
        undoAction = UndoRedoActionDeleteClientRental(actor, client, self._client_repo, rental)
        self._undoRepo.push(undoAction)

    def delete_book_rentals(self, id_book):
        rental = RentalService.delete_book_rentals(self, id_book)
        actor = self._rental_repo
        book = rental[0].get_book
        undoAction = UndoRedoActionDeleteBookRental(actor, book, self._book_repo, rental)
        self._undoRepo.push(undoAction)

    def update_return(self, id, return_date):
        rental, old_date = RentalService.update_return(self, id, return_date)
        actor = self._rental_repo
        obj = rental
        undoAction = UndoRedoActionUpdateR(actor, obj, return_date, old_date)
        self._undoRepo.push(undoAction)