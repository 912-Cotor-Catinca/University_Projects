"""
Implement the book service
"""
from Costumize.Iterator import Sort
from Domain.Book import Book
from Domain.Undo import UndoRedoActionDelete, UndoRedoActionAdd, UndoRedoActionUpdate


class BookService:
    def __init__(self, book_repo, book_val):
        self._book_repo = book_repo
        self._book_val = book_val

    def __len__(self):
        return len(self._book_repo)

    def add_book(self, id_book, title, author):
        """
        Instantiates a Book object with a given [id_book], a[title] and a[author] and adds it to the repo
        :param id_book: The Book's id
        :param title: Book's title
        :param author: Book's author
        :return:
        """
        book = Book(id_book, title, author)
        if self.find(book.book_id) != -1:
            raise ValueError('Book with given id already exist')
        else:
            self._book_val.validate(book)
            self._book_repo.add(book)
        return book

    def update_book(self, id, title, author):
        """
        Updates the title and the author of an instance of a Book object with a given id
        :param id: Book's id
        :param title: updated title
        :param author: updated author
        :return:
        """
        for b in self._book_repo.get_all():
            if b.book_id == id:
                old_title = b.title
                old_author = b.author
        new_book = self._book_repo.update(id, title, author)
        return new_book, old_title, old_author

    def find(self, id):
        """
        Find by id a book
        :param id: Book's id
        :return: the index if there exist or -1 otherwise
        """
        for i in range(len(self._book_repo)):
            if self._book_repo[i].book_id == id:
                return i
        return -1

    def delete_book(self, id):
        """
        Deletes a Book object with the given [id]
        :param id:
        :return:
        """
        book = self._book_repo.delete(id)
        return book

    def display(self):
        """
        Return a shallow copy of the list of books
        :return: the list of books
        """
        return self._book_repo.get_all()

    def search_title(self, title):
        return Sort.filter(self._book_repo, lambda book: title.lower() in book.title.lower())

    def search_author(self, name):
        return Sort.filter(self._book_repo, lambda book: name.lower() in book.author.lower())

    def search_id(self, book_id):
        return Sort.filter(self._book_repo, lambda book: book_id.lower() in book.book_id.lower())


class BookServiceUndo(BookService):
    def __init__(self, book_repo, book_val, undo_repo):
        BookService.__init__(self, book_repo, book_val)
        self._undo_repo = undo_repo

    def add_book(self, id_book, title, author):
        book = BookService.add_book(self, id_book, title, author)
        actor = self._book_repo
        obj = book
        undoAction = UndoRedoActionAdd(actor, obj)
        self._undo_repo.push(undoAction)

    def delete_book(self, id):
        book = BookService.delete_book(self, id)
        actor = self._book_repo
        obj = book
        undoAction = UndoRedoActionDelete(actor, obj)
        self._undo_repo.push(undoAction)

    def update_book(self, id, title, author):
        book, old_title, old_author = BookService.update_book(self, id, title, author)
        actor = self._book_repo
        obj = book
        undoAction = UndoRedoActionUpdate(actor, obj, title, author, old_title, old_author)
        self._undo_repo.push(undoAction)
