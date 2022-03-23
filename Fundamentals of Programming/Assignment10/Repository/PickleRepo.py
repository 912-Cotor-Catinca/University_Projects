import pickle

from Repository.BookRepository import BookRepo
from Repository.ClientRepository import ClientRepo
from Repository.RentalRepository import RentalRepo


class BinaryBookRepo(BookRepo):
    def __init__(self, bin_file):
        BookRepo.__init__(self)
        self._bin_file = bin_file
        self._read_bin_file()

    def _read_bin_file(self):
        result = []
        try:
            f = open(self._bin_file, 'rb')
            self._book_list = pickle.load(f)
            f.close()
            return self._book_list
        except EOFError:
            return result

        except IOError as e:
            raise e

    def _save_bin_file(self):
        f = open(self._bin_file, "wb")
        pickle.dump(self._book_list, f)
        f.close()

    def add(self, item):
        super().add(item)
        self._save_bin_file()

    def delete(self, book_id):
        book = BookRepo.delete(self, book_id)
        self._save_bin_file()
        return book

    def update(self, id, title, author):
        book = BookRepo.update(self, id, title, author)
        self._save_bin_file()
        return book


class BinaryClientRepo(ClientRepo):
    def __init__(self, bin_file):
        ClientRepo.__init__(self)
        self._bin_file = bin_file
        self._read_bin_file()

    def _save_bin_file(self):
        f = open(self._bin_file, "wb")
        pickle.dump(self._client_list, f)
        f.close()

    def _read_bin_file(self):
        result = []
        try:
            f = open(self._bin_file, 'rb')
            self._client_list = pickle.load(f)
            f.close()
            return self._client_list
        except EOFError:
            return result

        except IOError as e:
            raise e

    def add(self, client):
        ClientRepo.add(self, client)
        self._save_bin_file()

    def delete(self, id):
        client = ClientRepo.delete(self, id)
        self._save_bin_file()
        return client

    def update(self, id, name):
        client = ClientRepo.update(self, id, name)
        self._save_bin_file()
        return client


class BinaryRentalRepo(RentalRepo):
    def __init__(self, bin_file):
        RentalRepo.__init__(self)
        self._bin_file = bin_file
        self._read_bin_file()

    def _read_bin_file(self):
        result = []
        try:
            f = open(self._bin_file, 'rb')
            self._rental_list = pickle.load(f)
            f.close()
            return self._rental_list
        except EOFError:
            return result

        except IOError as e:
            raise e

    def _save_bin_file(self):
        f = open(self._bin_file, "wb")
        pickle.dump(self._rental_list, f)
        f.close()

    def add(self, rental):
        RentalRepo.add(self, rental)
        self._save_bin_file()

    def delete(self, id):
        rental = RentalRepo.delete(self, id)
        self._save_bin_file()
        return rental