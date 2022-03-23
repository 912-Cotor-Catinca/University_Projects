"""
Implement the Rental Class
"""
from datetime import date


class RentalException(Exception):
    def __init__(self, msg):
        self._msg = msg

    def __str__(self):
        return str(self._msg)


class RentalValidationError(RentalException):
    def __init__(self, error_list):
        self._errors = error_list

    def __str__(self):
        result = ''
        for er in self._errors:
            result += er
            result += '\n'
        return result


class ValidationRentalId:
    def validate(self, rental):
        errors = []
        if rental.rental_id < 1:
            errors.append('Invalid rental id')
        if rental.client == '':
            errors.append('The client id was not given')
        if rental.get_book == '':
            errors.append('The book id was not given')
        if len(errors) > 0:
            raise RentalValidationError(errors)


class Rental:
    def __init__(self, rental_id, book_id, client_id, rented_date=date(1, 1, 1), returned_date=date(1, 1, 1)):
        self._rental_id = rental_id
        self._book_id = book_id
        self._client_id = client_id
        self._rented_date = rented_date
        self._returned_date = returned_date

    def get_book_id(self):
        return self._book_id

    def get_client_id(self):
        return self._client_id

    @property
    def rental_id(self):
        return self._rental_id

    @property
    def get_book(self):
        return self._book_id

    @property
    def client(self):
        return self._client_id

    @property
    def rented_date(self):
        return self._rented_date

    @property
    def returned_date(self):
        return self._returned_date

    @returned_date.setter
    def returned_date(self, other):
        self._returned_date = other

    @rented_date.setter
    def rented_date(self, other):
        self._rented_date = other

    def __eq__(self, other):
        return self._rental_id == other

    def __len__(self):
        if self._rented_date != date(1, 1, 1):
            return (self._returned_date - self._rented_date).days + 1
        today = date.today()
        return (today - self._rented_date).days + 1


class RentalDTO:
    def __init__(self, id_rental, book_id, client_id, rented_date, returned_date):
        self._rental_id = id_rental
        self._book_id = book_id
        self._client_id = client_id
        self._rented_date = rented_date
        self._returned_date = returned_date

    def __str__(self):
        return str(self._rental_id) + ' ' + str(self._book_id) + ' ' + str(self._client_id) + ' ' + str(self._rented_date) \
        + ' ' + str(self._returned_date)