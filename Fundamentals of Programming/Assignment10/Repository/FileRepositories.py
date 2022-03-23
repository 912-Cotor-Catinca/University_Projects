"""
File repos
"""
from Domain.Book import Book
from Domain.Client import Client
from Domain.Rental import Rental
from Repository.BookRepository import BookRepo
from Repository.ClientRepository import ClientRepo
from Repository.RentalRepository import RentalRepo


class FileBookRepo(BookRepo):
    def __init__(self, txt):
        BookRepo.__init__(self)
        self._txt = txt
        self._read_from_file()

    def add(self, item):
        BookRepo.add(self, item)
        self._save()

    def update(self, id, title, author):
        book = BookRepo.update(self, id, title, author)
        self._save()
        return book

    def find(self, id):
        return BookRepo.find(self, id)

    def __len__(self):
        return BookRepo.__len__(self)

    def get_all(self):
        return BookRepo.get_all(self)

    def find_book(self, id):
        return BookRepo.find_book(self, id)

    def delete(self, book_id):
        book = BookRepo.delete(self, book_id)
        self._save()
        return book

    def _read_from_file(self):
        with open(self._txt, "r") as f:
            self._book_list = []
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                if line != "":
                    parts = line.split(";")
                    book = Book(parts[0], parts[1], parts[2])
                    self._book_list.append(book)
        f.close()

    def _save(self):
        with open(self._txt, 'w') as f:
            for book in self._book_list:
                f.write(book.book_id + ";" + book.title + ";" + book.author + "\n")
        f.close()


class FileClientRepo(ClientRepo):
    def __init__(self, txt):
        ClientRepo.__init__(self)
        self._txt = txt
        self._read_from_file()

    def _read_from_file(self):
        with open(self._txt, "r") as f:
            self._client_list = []
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                if line != "":
                    parts = line.split(";")
                    client = Client(int(parts[0]), parts[1])
                    self._client_list.append(client)
        f.close()

    def _save(self):
        with open(self._txt, "w") as f:
            for client in self._client_list:
                f.write(str(client.client_id) + ";" + client.name + "\n")
        f.close()

    def add(self, client):
        ClientRepo.add(self, client)
        self._save()

    def delete(self, id):
        client = ClientRepo.delete(self, id)
        self._save()
        return client

    def update(self, id, name):
        ClientRepo.update(self, id, name)
        self._save()

    def find(self, id):
        return ClientRepo.find(self, id)

    def find_client(self, id):
        return ClientRepo.find_client(self, id)

    def __len__(self):
        return ClientRepo.__len__(self)

    def get_all(self):
        return ClientRepo.get_all(self)


class FileRentalRepo(RentalRepo):
    def __init__(self, txt):
        RentalRepo.__init__(self)
        self._txt = txt
        self._read_from_file()

    def _read_from_file(self):
        with open(self._txt, "r") as f:
            self._rental_list = []
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                if line != "":
                    parts = line.split(";")
                    rental = Rental(parts[0], parts[1], parts[2], parts[3], parts[4])
                    self._rental_list.append(rental)
        f.close()

    def add(self, rental):
        RentalRepo.add(self, rental)
        self._save()

    def delete(self, id):
        rental = RentalRepo.delete(self, id)
        self._save()
        return rental

    def update_return(self, id, returned_date1):
        RentalRepo.update_return(self, id, returned_date1)
        self._save()

    def find(self, id):
        return RentalRepo.find(self, id)

    def get_all(self):
        return RentalRepo.get_all(self)

    def __len__(self):
        return RentalRepo.__len__(self)

    def _save(self):
        with open(self._txt, "w") as f:
            for rental in self._rental_list:
                f.write(str(rental.rental_id) + ";" + str(rental.get_book) + ";" + str(rental.client) + ";" + str(rental.rented_date) + ";" + str(rental.returned_date) + ";" + "\n")
        f.close()
