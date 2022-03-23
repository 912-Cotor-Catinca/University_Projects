from datetime import date
from unittest import TestCase

from Domain.Rental import Rental, RentalException, RentalDTO, ValidationRentalId, RentalValidationError
from Domain.Book import BookException, Book, ValidationBookId, BookValidationError
from Domain.Client import Client, ClientException, ClientValidator, ClientValidationError
from Domain.Undo import UndoRedoAction, UndoRedoActionAdd, UndoRedoActionDelete, UndoRedoActionUpdate, \
    UndoRedoActionAddC, UndoRedoActionDeleteC, UndoRedoActionUpdateC, UndoRedoActionAddR, UndoRedoActionDeleteR, \
    UndoRedoActionDeleteBookRental, UndoRedoActionDeleteClientRental
from Repository.BookRepository import BookRepo
from Repository.ClientRepository import ClientRepo
from Repository.RentalRepository import RentalRepo


class BookTest(TestCase):
    def test_init(self):
        book = Book('ISBN 978-02-55640-80-6', 'Ion', 'Liviu Rebreanu')
        self.assertEqual(book.book_id, 'ISBN 978-02-55640-80-6')
        self.assertEqual(book.title, 'Ion')
        self.assertEqual(book.author, 'Liviu Rebreanu')
        book.title = 'Jocul Ielelor'
        book.author = 'Camil Petrescu'
        self.assertEqual(book.title, 'Jocul Ielelor')
        self.assertEqual(book.author, 'Camil Petrescu')
        self.assertEqual(book.id(), 'ISBN 978-02-55640-80-6')

    def test_book_validation(self):
        valid = ValidationBookId()
        error = []
        book = Book('ISBN 978-02-55640-80-6', 'Ion', 'Liviu Rebreanu')
        assert(valid.is_book_id_valid(book.book_id) is True)
        assert(valid.is_book_id_valid('ISBN 1-222-2-3-4-3') is False)
        self.assertRaises(BookValidationError, valid.validate, Book('1', 'Ana', 'stan'))
        try:
            book = Book('isbn', 90, 10)
            assert False
        except BookException as be:
            self.assertEqual(str(be), 'Invalid value for book_id')
        try:
            book = Book('ISBN 978-02-55640-80-6', 'ion', 4)
            assert False
        except BookException as bv:
            self.assertEqual(str(bv), 'Invalid value for book_id')

        try:
            book = Book(1, 'ion', 'name')
            assert False
        except BookException as br:
            self.assertEqual(str(br), 'Invalid value for book_id')


class ClientTest(TestCase):
    def test_init_client(self):
        client1 = Client(1, 'Ioana Popa')
        self.assertEqual(client1.name, 'Ioana Popa')
        client2 = Client(2, 'Dan Pop')
        self.assertEqual(client2.client_id, 2)
        client3 = Client(3, 'Ion Moldovan')
        self.assertNotEqual(client1.client_id, client2.client_id)
        client4 = Client(4, 'Ana Stan')
        self.assertEqual(str(client4), '4 Ana Stan')
        client4.name = 'Dana Man'
        self.assertEqual(client4.name, 'Dana Man')
        self.assertEqual(client4.name_client(), 'Dana Man')
        self.assertEqual(client4.id(), 4)
        try:
            client = Client('asd', 3345)
            self.assertFalse('Invalid params for client')
        except ClientException as ce:
            self.assertEqual(str(ce), 'Invalid value for client id')

        try:
            client = Client(1, 234)
            self.assertFalse('Invalid params for client')
        except ClientException as cv:
            self.assertEqual(str(cv), 'Invalid value for name')

    def test_validation_client(self):
        validation = ClientValidator()
        client1 = Client(1, 'Ioana Popa')
        assert(validation.is_valid_name(client1.name) is True)
        self.assertRaises(ClientValidationError, validation.validate, Client(0, 'Ana'))
        try:
            client2 = Client(0, 'Dan')
        except ClientValidationError as cr:
            self.assertEqual(str(cr), 'Invalid')


class RentalTest(TestCase):
    def test_init_rentals(self):
        rental1 = Rental(2, 'ISBN 978-42-52042-11-6', 2, (2019, 10, 10), (2020, 10, 25))
        self.assertEqual(rental1.rented_date, (2019, 10, 10))
        self.assertEqual(rental1.rental_id, 2)

        rental2 = Rental(3, 'ISBN 978-12-56543-82-4', 3, date(2020, 6, 16), date(2020, 7, 19))
        self.assertEqual(len(rental2), 34)
        rental2.rented_date = date(1, 1, 1)
        self.assertEqual(len(rental2), 737762)
        self.assertEqual(rental1.rental_id, rental1.rental_id)
        self.assertEqual(rental2.client, 3)
        self.assertEqual(rental2.returned_date, date(2020, 7, 19))
        rental3 = Rental(4, 'ISBN 978-35-52381-75-5', 4, (2010, 3, 2), (2020, 3, 10))
        self.assertEqual(rental3.get_book, 'ISBN 978-35-52381-75-5')
        rental3.returned_date = date(2020, 10, 12)
        self.assertEqual(rental3.returned_date, date(2020, 10, 12))
        rental3.rented_date = (2020, 4, 5)
        self.assertEqual(rental3.rented_date, (2020, 4, 5))
        self.assertEqual(rental3.get_book_id(), 'ISBN 978-35-52381-75-5')
        self.assertEqual(rental3.get_client_id(), 4)

    def test_rental_validation(self):
        valid = ValidationRentalId()
        self.assertRaises(RentalValidationError, valid.validate, Rental(0, '', 0, date(2019, 10, 10), date(2020, 10, 25)))

    def test_rental(self):
        rentals = Rental(1, 'ISBN 978-02-55640-80-6', 1, (2020, 4, 16), (2020, 4, 19))
        self.assertEqual(rentals.get_book, 'ISBN 978-02-55640-80-6')
        self.assertEqual(rentals.client, 1)
        self.assertTrue('ok')


class DTOTest(TestCase):
    def setUp(self):
        self.rental = RentalDTO(1, 'ISBN 978-02-55640-80-6', 1, date(2020, 3, 5), date(2020, 6, 5))

    def test_dto(self):
        self.assertEqual(str(self.rental), '1 ISBN 978-02-55640-80-6 1 2020-03-05 2020-06-05')


class UndoTest(TestCase):
    def setUp(self):
        self.book1 = Book('ISBN 978-02-55640-80-6', 'Ion', 'Liviu Rebreanu')
        self.book2 = Book('ISBN 988-12-05458-36-1', 'Mara', 'Ioan Slavici')
        self.book3 = Book('ISBN 988-12-05458-36-2', 'Moara cu Noroc', 'Ioan Slavici')
        self._book_repo = BookRepo()
        self._book_repo.add(self.book1)
        self._book_repo.add(self.book2)

        self.client1 = Client(1, 'Ana Stan')
        self.client2 = Client(2, 'Dana Man')
        self.client3 = Client(3, 'Dana Manu')
        self._client_repo = ClientRepo()
        self._client_repo.add(self.client1)
        self._client_repo.add(self.client2)

        self.rental1 = Rental(1, self.book1, self.client1, date(2020, 2, 3), date(2020, 6, 3))
        self.rental2 = Rental(2, self.book2, self.client2, date(2020, 1, 6), date(2020, 9, 6))
        self.rental3 = Rental(3, self.book3, self.client3, date(2020, 1, 6), date(2020, 9, 6))
        self._rental_repo = RentalRepo()
        self._rental_repo.add(self.rental1)
        self._rental_repo.add(self.rental2)

    def test_UndoAction(self):
        action = UndoRedoAction(self._book_repo, self.book2)
        action.execute_action()
        action.execute_rev_action()

    def test_undo_book_add(self):
        action = UndoRedoActionAdd(self._book_repo, self.book2)
        action.execute_action()
        self.assertEqual(len(self._book_repo), 1)
        action.execute_rev_action()
        self.assertEqual(len(self._book_repo), 2)

    def test_undo_book_del(self):
        action2 = UndoRedoActionDelete(self._book_repo, self.book3)
        action2.execute_action()
        self.assertEqual(len(self._book_repo), 3)
        action2.execute_rev_action()
        self.assertEqual(len(self._book_repo), 2)

    def test_update(self):
        new_title = 'Nou Nume'
        new_author = 'Nou Autor'
        action = UndoRedoActionUpdate(self._book_repo, self.book1, new_title, new_author, self.book1.title, self.book1.author)
        action.execute_action()
        self.assertEqual(self.book1.author, 'Liviu Rebreanu')
        action.execute_rev_action()
        self.assertEqual(self.book1.author, 'Nou Autor')

    def test_undo_client_add(self):
        action = UndoRedoActionAddC(self._client_repo, self.client2)
        action.execute_action()
        self.assertEqual(len(self._client_repo), 1)
        action.execute_rev_action()
        self.assertEqual(len(self._client_repo), 2)

    def test_undo_client_del(self):
        action = UndoRedoActionDeleteC(self._client_repo, self.client3)
        action.execute_action()
        self.assertEqual(len(self._client_repo), 3)
        action.execute_rev_action()
        self.assertEqual(len(self._client_repo), 2)

    def test_update1(self):
        new_name = 'Nou Nume'
        action = UndoRedoActionUpdateC(self._client_repo, self.client2, new_name, self.client2.name)
        action.execute_action()
        self.assertEqual(self.client2.name, 'Dana Man')
        action.execute_rev_action()
        self.assertEqual(self.client2.name, 'Nou Nume')

    def test_undo_rental_add(self):
        action = UndoRedoActionAddR(self._rental_repo, self.rental1)
        action.execute_action()
        action.execute_rev_action()

    def test_undo_rental_del(self):
        action = UndoRedoActionDeleteR(self._rental_repo, self.rental3)
        action.execute_action()
        self.assertEqual(len(self._rental_repo), 3)
        action.execute_rev_action()
        self.assertEqual(len(self._rental_repo), 2)

    def test_book_rental(self):
        rentals = self._rental_repo.get_all()
        action = UndoRedoActionDeleteBookRental(self._rental_repo, self.book1, self._book_repo, rentals)
        action.execute_rev_action()
        self.assertEqual(len(self._rental_repo), 1)
        action.execute_action()
        self.assertEqual(len(self._rental_repo), 2)

    def test_client_rental(self):
        rentals = self._rental_repo.get_all()
        action = UndoRedoActionDeleteClientRental(self._rental_repo, self.client1, self._client_repo, rentals)
        self.assertEqual(str(self.client1), '1 Ana Stan')
        action.execute_rev_action()
        self.assertEqual(len(self._rental_repo), 1)
        action.execute_action()
        self.assertEqual(len(self._rental_repo), 2)



