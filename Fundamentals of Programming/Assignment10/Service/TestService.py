from datetime import date
from unittest import TestCase

from Domain.Book import ValidationBookId, Book
from Domain.Client import ClientValidator, Client
from Domain.Rental import ValidationRentalId
from Domain.Undo import UndoRedoActionDeleteC
from Repository.BookRepository import BookRepo
from Repository.ClientRepository import ClientRepo
from Repository.RentalRepository import RentalRepo
from Repository.UndoRepo import RepoUndo
from Service.BookService import BookServiceUndo
from Service.ClientService import ClientServiceUndo
from Service.RentalService import RentalServiceUndo
from Service.UndoService import UndoService


class BookTest(TestCase):
    def setUp(self):
        self.book_repo = BookRepo()
        self.book_val = ValidationBookId()
        self.undo_repo = RepoUndo()
        self.book = BookServiceUndo(self.book_repo, self.book_val, self.undo_repo)

    def test_book_service(self):
        # add
        self.book.add_book('ISBN 978-02-55640-80-6', 'Ion', 'Liviu Rebreanu')
        self.book.add_book('ISBN 978-12-56543-82-4', 'Bengal Nights', 'Mircea Eliade')
        self.assertEqual(len(self.book), 2)

        # delete
        self.book.delete_book('ISBN 978-02-55640-80-6')
        self.book.update_book('ISBN 978-12-56543-82-4', 'Nunta in cer', 'Mircea Eliade')
        # find
        assert self.book.find('ISBN 998-78-05458-63-8') == -1
        assert self.book.find('ISBN 978-12-56543-82-4') == 0
        self.book.add_book('ISBN 978-02-55640-80-6', 'Ion', 'Liviu Rebreanu')

        """
        Test search functions
        """
        title = 'on'
        result = self.book.search_title(title)  # test search_title
        self.assertEqual(len(result), 1)

        author = 'ea'
        result = self.book.search_author(author)  # test search_author
        self.assertEqual(len(result), 2)

        book_id = 'isbn'
        result = self.book.search_id(book_id)  # test search_id
        self.assertEqual(len(result), 2)

    def test_book(self):
        self.assertTrue('ok')


class ClientTest(TestCase):
    def setUp(self):
        self.client_repo = ClientRepo()
        self.client_val = ClientValidator()
        self.undo_repo = RepoUndo()
        self.client = ClientServiceUndo(self.client_repo, self.client_val, self.undo_repo)

    def test_client_service(self):
        # add
        self.client.add_client(1, 'Ioana Popa')
        self.client.add_client(2, 'Ana Stan')
        self.assertRaises(ValueError, self.client.add_client, 2, 'Gigu Gigu')

        # delete
        self.client.delete_client(1)
        # display
        clients = self.client.display()
        self.client.add_client(1, 'Ion Pop')
        self.client.add_client(3, 'Dan Stan')

        # update
        client = self.client.update_client(2, 'Dana Maria')

        # find
        index = self.client.find(3)

        """
        Test search 
        """
        name = 'an'
        result = self.client.search_client_name(name)
        self.assertEqual(len(result), 2)

        client_id = 1
        result = self.client.search_id(client_id)
        assert len(result) == 1


class RentalTest(TestCase):
    def setUp(self):
        self.rental_repo = RentalRepo()
        self.rental_val = ValidationRentalId()
        self.book_repo = BookRepo()
        self.book_repo.add(Book('ISBN 998-78-05458-63-8', 'Ion', 'liviu Rebreanu'))
        self.client1 = Client(1, 'Ana Stan')
        self.client2 = Client(2, 'Dana Man')
        self.client3 = Client(3, 'Dana Manu')
        self.client_repo = ClientRepo()
        self.client_repo.add(self.client1)
        self.client_repo.add(self.client2)
        self.undo_repo = RepoUndo()
        self.rental = RentalServiceUndo(self.rental_repo, self.rental_val, self.book_repo, self.client_repo, self.undo_repo)

    def test_rental_service(self):
        # add
        self.rental.add_rental(1, 'ISBN 978-02-55640-80-6', 2, date(2020, 4, 16), date(2020, 4, 19))
        self.rental.add_rental(2, 'ISBN 978-42-52042-11-6', 3, date(2019, 10, 10), date(2020, 10, 25))
        self.rental.add_rental(3, 'ISBN 998-78-05458-63-8', 5, date(2020, 5, 9), date(2020, 6, 7))
        self.rental.add_rental(4, 'ISBN 998-78-05458-63-8', 8, date(2020, 10, 10), date(1, 1, 1))
        self.assertRaises(ValueError, self.rental.add_rental, 4, 'ISBN 998-78-05458-63-8', 8, date(2020, 10, 10), date(1, 1, 1))

        self.assertEqual(self.rental.check_if_rent('ISBN 998-78-05458-63-8', date(1, 1, 1)), False)
        self.assertEqual(self.rental.check_if_rent('ISBN 998-78-05458-63-8', date(2020, 10, 1)), True)

        # update
        self.rental.update_return(4, date.today())

        # delete
        self.rental.delete_rental(2)
        self.rental.delete_book_rentals('ISBN 998-78-05458-63-8')
        self.assertEqual(len(self.rental.display()), 1)
        self.rental.delete_client_and_its_rentals(2)

        # find
        self.rental.find_book_id('ISBN 978-42-52042-11-6')
        index = self.rental.find_book_id('ISBN 998-78-05458-63-8')
        self.assertEqual(index, -1)
        self.rental.find_client_id(8)

        # statistics
        most_book = self.rental.most_rented_books()
        most_author = self.rental.most_author()
        most_clients = self.rental.most_clients()

        # filter
        client = Client(2, 'Ana Stan')
        book = Book('ISBN 978-02-55640-80-6', 'Ion', 'Liviu Rebreanu')
        clients = self.rental.filter_rentals(client, book)


class UndoServiceTest(TestCase):
    def setUp(self):
        self.undo_repo = RepoUndo()
        self.client1 = Client(1, 'Ana Stan')
        self.client2 = Client(2, 'Dana Man')
        self.client3 = Client(3, 'Dana Manu')
        self._client_repo = ClientRepo()
        self._client_repo.add(self.client1)
        self._client_repo.add(self.client2)
        self.undo_repo.push(UndoRedoActionDeleteC(self._client_repo, self.client1))
        self.undo_srv = UndoService(self.undo_repo)

    def test_undo_service(self):
        pass

