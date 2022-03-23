"""
Implement the book class
"""


class BookException(Exception):
    def __init__(self, msg):
        self._msg = msg

    def __str__(self):
        return self._msg


class BookValidationError(BookException):
    def __init__(self, error_list):
        self.errors = error_list

    def __str__(self):
        result = ''
        for er in self.errors:
            result += er
            result += '\n'
        return result


class ValidationBookId:
    @staticmethod
    def is_book_id_valid(book_id):
        """
        An id book starts with ISBN, followed by 13 digits
        :param book_id:
        :return:
        """
        tokens = book_id.split(' ', 1)
        if tokens[0] != 'ISBN':
            return False
        else:
            elements = tokens[1].split('-')
            if len(elements[0]) != 3 or len(elements[1]) != 2 or len(elements[2]) != 5 or len(elements[3]) != 2 or \
                    len(elements[4]) != 1:
                return False
        return True

    def validate(self, book):
        errors = []
        if not self.is_book_id_valid(book.book_id):
            errors.append('Invalid book id')
        if len(errors) > 0:
            raise BookValidationError(errors)


class Book:
    def __init__(self, book_id='', title='', author=''):
        if not isinstance(book_id, str):
            raise BookException('Invalid value for book_id')
        if not isinstance(title, str):
            raise BookException('Invalid value for book_id')
        if not isinstance(author, str):
            raise BookException('Invalid value for book_id')

        self._book_id = book_id
        self._title = title
        self._author = author

    def id(self):
        return self._book_id

    @property
    def book_id(self):
        return self._book_id

    @property
    def title(self):
        return self._title

    @property
    def author(self):
        return self._author

    @title.setter
    def title(self, other):
        self._title = other

    @author.setter
    def author(self, other):
        self._author = other

    def __eq__(self, other):
        return self._book_id == other

    def __ge__(self, other):
        return self._book_id >= other.book_id

    def __str__(self):
        return str(self._book_id) + ' ' + str(self._title) + ' de ' + str(self._author)