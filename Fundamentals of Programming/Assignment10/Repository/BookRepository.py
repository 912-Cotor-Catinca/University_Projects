"""
Book Repository is implemented here
"""


from Domain.Book import BookException, Book
from Costumize.Iterator import OrderIterator
from collections.abc import Iterable


class BookRepoException(BookException):
    def __init__(self, msg):
        super(BookRepoException, self).__init__(msg)


class BookRepo:
    def __init__(self):
        self._book_list = OrderIterator()

    @property
    def book_list(self):
        return self._book_list

    def __len__(self):
        return len(self._book_list)

    def __iter__(self):
        return OrderIterator(self._book_list)

    def add(self, item):
        """
        Appends a new book to the list if there is not any student with the same id
        :param item: an instance of a Book object
        :return:
        """
        if item in self._book_list:
            raise BookRepoException('The book with given id not found')
        self._book_list.append(item)
        return item

    def update(self, id, title, author):
        """
        Update the title or the author attribute of the given id
        :param id: the instance of a Book object from the list that is to be updated
        :param title: the new title of the book (string)
        :param author: the new author of the book (string)
        :return:
        """
        done = False
        for book in self._book_list:
            if self.find(book.book_id) != -1 and book.book_id == id:
                book.title = title
                book.author = author
                return book
                done = True
        if not done:
            raise BookRepoException('Book with given id not found')

    def delete(self, book_id):
        """
        Delete a book from the list
        :param book_id: an instance of a Book object
        :return:
        """
        for books in self._book_list:
            if books.book_id == book_id:
                book = books
                self._book_list.remove(books)
                return book
        raise BookRepoException('The given id does not exist')

    def find(self, id):
        """
        Searches for a the index of the book in the list with a matching given [id] attribute
        :param id: book' s id(unique string)
        :return: the index if there exist or -1 otherwise
        """
        for i in range(len(self._book_list)):
            if self._book_list[i].book_id == id:
                return i
        return -1

    def find_book(self, id):
        """
        Searches for a book in the list with a matching given [id] attribute
        :param id: book' s id(unique string)
        :return: the book instance or raise Exception if the book is already rented
        """
        for b in self._book_list:
            if b.book_id == id:
                return b

    def get_all(self):
        """"
        :return: a shallow copy of the book list
        """
        return self._book_list[:]

    def __getitem__(self, item):
        return self._book_list[item]


book_list = BookRepo()
book_list.add(Book('ISBN 12-123-12345-22-5', 'Ion', 'Rebreanu'))
book_list.add(Book('ISBN 13-124-12435-23-7', 'Ioan', 'Rebreanu'))
book_list.add(Book('ISBN 14-123-13245-24-6', 'Iooon', 'Rebreanu'))
b1 = Book('ISBN 23-234-56789-76-4', 'ioan', 'reb')

for b in book_list:
    print(b)
print(b1 in book_list)


