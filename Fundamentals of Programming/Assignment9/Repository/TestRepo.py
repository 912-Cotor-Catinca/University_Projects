from datetime import date
from unittest import TestCase

from Domain.Rental import Rental
from Domain.Book import Book
from Domain.Client import Client
from Domain.Undo import UndoRedoAction
from Repository.BookRepository import BookRepo, BookRepoException
from Repository.ClientRepository import ClientRepo, ClientRepoException
from Repository.RentalRepository import RentalRepo, RentalRepoException
from Repository.UndoRepo import RepoUndo, UndoRepoException


class BookTest(TestCase):
    def setUp(self):
        self.book_list = BookRepo()

    def test_repo_book(self):
        # add
        self.book_list.add(Book('ISBN 978-02-55640-80-6', 'Ion', 'Liviu Rebreanu'))
        self.book_list.add(Book('ISBN 978-12-56543-82-4', 'Bengal Nights', 'Mircea Eliade'))
        self.assertRaises(BookRepoException, self.book_list.add, Book('ISBN 978-12-56543-82-4', 'No name', 'Mircea Eliade'))

        # update
        book = Book('ISBN 978-02-55640-80-6', 'Adam si Eva', 'Liviu Rebreanu')
        self.book_list.update('ISBN 978-02-55640-80-6', 'Adam si Eva', 'Liviu Rebreanu')
        self.assertRaises(BookRepoException, self.book_list.update, 'ISBN 978-02-55634-21-5', 'Adam si Eva', 'Liviu rebreanu')
        self.assertEqual(book.author, 'Liviu Rebreanu')
        self.assertEqual(book.book_id, 'ISBN 978-02-55640-80-6')
        self.assertEqual(book.title, 'Adam si Eva')

        # delete
        self.assertEqual(len(self.book_list), 2)
        self.book_list.delete('ISBN 978-12-56543-82-4')
        self.assertEqual(len(self.book_list), 1)
        self.assertRaises(BookRepoException, self.book_list.delete, 'ISBN 978-02-55634-21-5')

        self.book_list.add(Book('ISBN 978-12-56543-82-4', 'Bengal Nights', 'Mircea Eliade'))

        # find
        index = self.book_list.find('ISBN 978-02-55634-21-5')
        assert(index == -1)
        book1 = self.book_list.find_book('ISBN 978-12-56543-82-4')
        self.assertEqual(str(book1), 'ISBN 978-12-56543-82-4 Bengal Nights de Mircea Eliade')

        books = self.book_list.get_all()
        self.assertEqual(str(books[1]), 'ISBN 978-12-56543-82-4 Bengal Nights de Mircea Eliade')
        book2 = self.book_list[1]
        self.assertEqual(str(book2), 'ISBN 978-12-56543-82-4 Bengal Nights de Mircea Eliade')


class ClientTest(TestCase):
    def setUp(self):
        self.client_list = ClientRepo()

    def test_repo_client(self):
        # add
        self.client_list.add(Client(1, 'Ioana Popa'))
        self.client_list.add(Client(2, 'Ana Stan'))
        self.assertEqual(len(self.client_list), 2)
        # self.assertRaises(ClientRepoException, self.client_list.add, Client(2, 'Ana San'))

        # delete
        self.assertRaises(ClientRepoException, self.client_list.delete, 3)
        self.client_list.delete(1)
        self.assertEqual(len(self.client_list), 1)

        self.client_list.add(Client(1, 'Ioana Popa'))

        # update
        client = Client(3, 'Ana Banana')
        self.client_list.add(client)
        self.client_list.update(3, 'Dana Maria')
        self.assertEqual(client.name, 'Dana Maria')
        self.assertRaises(ClientRepoException, self.client_list.update, 4, 'Dan Ion')

        # find
        index = self.client_list.find(5)
        assert (index == -1)
        client1 = self.client_list.find_client(3)
        self.assertEqual(str(client1), '3 Dana Maria')


        # get all
        clients = self.client_list.get_all()
        self.assertEqual(len(clients), 3)
        client2 = self.client_list[1]
        self.assertEqual(str(client2), '1 Ioana Popa')
        client_list = self.client_list.client_list
        self.assertEqual(len(client_list), 3)


class RentalTest(TestCase):
    def setUp(self):
        self.rental_list = RentalRepo()

    def test_repo_rental(self):
        # add
        self.rental_list.add(Rental(1, 'ISBN 978-02-55640-80-6', 1, date(2020, 4, 16), date(2020, 4, 19)))
        self.rental_list.add(Rental(2, 'ISBN 978-42-52042-11-6', 2, date(2019, 10, 10), date(2020, 10, 25)))
        self.assertEqual(len(self.rental_list), 2)
        self.assertRaises(RentalRepoException, self.rental_list.add, Rental(2, 'ISBN 978-42-52042-11-6', 2, date(2019, 10, 10), date(2020, 10, 25)))

        # delete
        self.rental_list.delete(2)
        self.assertEqual(len(self.rental_list), 1)
        self.assertRaises(RentalRepoException, self.rental_list.delete, 3)
        self.rental_list.add(Rental(2, 'ISBN 978-42-52042-11-6', 2, date(2019, 10, 10), date(2020, 10, 25)))

        # update
        self.rental_list.update(2, date(2020, 10, 5), date(2020, 10, 10))
        self.assertRaises(RentalRepoException, self.rental_list.update, 3, date(2020, 10, 5), date(2020, 10, 10))
        self.rental_list.add(Rental(3, 'ISBN 978-42-52042-10-8', 3, date(2019, 10, 10), date(1, 1, 1)))
        self.rental_list.update_return(3, date.today())
        rental = self.rental_list[2]
        self.assertEqual(rental.returned_date, date.today())
        rentals = self.rental_list.get_all()


class UndoTest(TestCase):
    def setUp(self):
        self.client1 = Client(1, 'Ana Stan')
        self.client2 = Client(2, 'Dana Man')
        self.client3 = Client(3, 'Dana Manu')
        self._client_repo = ClientRepo()
        self._client_repo.add(self.client1)
        self._client_repo.add(self.client2)

        self.undoAction = UndoRedoAction(self._client_repo, self.client1)
        self.list = RepoUndo()
        self.index = RepoUndo()

    def test_undo_repo(self):
        self.list.push(self.undoAction)
        self.list.pop()
        self.assertEqual(self.list.size(), 0)
        self.assertRaises(ValueError, self.list.pop)
        self.assertRaises(UndoRepoException, self.list.pull)
        self.list.push(self.undoAction)
        self.assertEqual(self.list.full(), True)
        self.list.peek()
        self.list.pull()
