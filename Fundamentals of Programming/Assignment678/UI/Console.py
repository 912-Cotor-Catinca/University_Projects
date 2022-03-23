import datetime
from random import randrange, choice, randint

from Domain.Book import BookValidationError
from Domain.Client import ClientValidationError
from Domain.Rental import RentalValidationError
from Repository.ClientRepository import ClientRepoException
from Repository.RentalRepository import RentalRepoException
from Repository.UndoRepo import UndoRepoException


class UI:
    def __init__(self, undo_service, srv_book, srv_client, srv_rental):
        self._undo_service = undo_service
        self._srvBook = srv_book
        self._srvClient = srv_client
        self._srvRental = srv_rental
        self._commands = {'add_book': self._add_book_ui, 'list_books': self._display_books,
                          'delete_book': self._delete_book, 'update_book': self._update_book,
                          'list_clients': self._display_clients, 'delete_client': self._delete_client, 'update_client':
                              self._update_client, 'list_rentals': self._display_rentals,
                          'add_client': self._add_client_ui,
                          'return_book': self._return_book, 'rent_book': self._rent_book,
                          'statistic1': self._statistic1,
                          'statistic2': self._statistic3, 'statistic3': self._statistic2,
                          'search_by_client_name': self._search_client_name, 'search_by_title': self._search_by_title,
                          'search_by_author': self._search_by_author, 'search_by_book_id': self._search_book_id,
                          'search_by_client_id': self._search_client_id, 'undo': self._undo_ui, 'redo': self._redo_ui}
        self.test_init_clients()
        self.test_init_books()
        self.test_init_rentals()

    @staticmethod
    def print_menu():
        print('\t Book Rentals Register')
        print('\033[4m' + "Book Data:" + '\033[m')
        print('\t To add a new book use ''add_book'' command')
        print('\t To list all books use list_books command')
        print('\t To delete a book use ''delete_book'' command')
        print('\t To update a book parameters use ''update_book'' command')
        print('\t To search by id book use ''search_by_book_id'' command')
        print('\t To search by title use ''search_by_title'' command')
        print('\t To search by author use ''search_by_author'' command')
        print('\033[4m' + "Client Data:" + '\033[m')
        print('\t To add a new client use ''add_client'' command')
        print('\t To list all client use ''list_clients'' command')
        print('\t To delete a client use ''delete_book'' command')
        print('\t To update a client parameters use ''update_book'' command')
        print('\t To search by id client use ''search_by_client_id'' command')
        print('\t To search by name use ''search_by_name'' command')
        print('\033[4m' + "Rental Data:" + '\033[m')
        print('\t To list all rentals use ''list_rentals'' command')
        print('\t To add a new client use ''add_client'' command')
        print('\t To rent a book ''rent_book'' command')
        print('\t To return a book ''return_book'' command\n')
        print('\033[4m' + "Statistics:" + '\033[m')
        print('\t To see the most rented books use ''statistic1'' command')
        print('\t To see the most active clients use ''statistic2'' command')
        print('\t To see the most rented authors use ''statistic3'' command')

    def split_command(self, command):
        command = command.strip()
        tokens = command.split(' ', 1)
        cmd_word = tokens[0].strip()
        cmd_params = tokens[1].strip if len(tokens) == 2 else ''
        return cmd_word, cmd_params

    def read_book(self):
        book_id = None
        title = None
        author = None
        try:
            book_id = input('id: ')
            title = input('title: ')
            author = input('author: ')
        except ValueError:
            print('Invalid parameters')
        return [book_id, title, author]

    def read_client(self):
        client_id = None
        name = None
        try:
            client_id = int(input('id: '))
            name = input('name: ')
        except ValueError:
            print('Invalid Parameters!')
        return [client_id, name]

    def read_rental(self):
        rental_id = None
        rental_date = None
        book_id = None
        client_id = None
        returned_date = None
        try:
            rental_id = int(input('id_rental: '))
            book_id = input('id_book: ')
            client_id = int(input('id_client: '))
            rental_date = datetime.date.today()
            returned_date = datetime.date(1, 1, 1)
        except ValueError:
            print('Invalid Parameters!')
        return [rental_id, book_id, client_id, rental_date, returned_date]

    def start_uit(self):
        self.print_menu()

        done = False
        while not done:
            command = input('command>')
            if command == 'exit':
                return
            cmd_word, cmd_params = self.split_command(command)
            if command in self._commands:
                try:
                    self._commands[cmd_word](cmd_params)
                except ValueError:
                    print('Invalid Parameters')
                except BookValidationError as be:
                    print('validation error: ' + str(be))
                except ClientValidationError as ce:
                    print('validation error: ' + str(ce))
                except RentalValidationError as re:
                    print('validation error: ' + str(re))
               # except BookRepoException as br:
                   # print('book repo error:' + str(br))
                except ClientRepoException as cr:
                    print('book repo error:' + str(cr))
                except RentalRepoException as rr:
                    print('book repo error:' + str(rr))
                except UndoRepoException as ur:
                    print('book repo error:' + str(ur))

            else:
                print('Bad command!')

    def _add_book_ui(self, params):
        done = False
        while not done:
            b = self.read_book()
            book = self._srvBook.add_book(b[0], b[1], b[2])
            return book
            cmd = input()
            if cmd == 's' or cmd == 'S':
                done = True

    def _display_books(self, params):
        book = self._srvBook.display()
        if len(book) == 0:
            print('no books')
            return
        for b in book:
            print(b)

    def _delete_book(self, params):
        book_id = input('book id: ')
        if self._srvRental.find_book_id(book_id) == -1:
            self._srvBook.delete_book(book_id)
        else:
            self._srvRental.delete_book_rentals(book_id)

    def _add_client_ui(self, params):
        done = False
        while not done:
            c = self.read_client()
            client = self._srvClient.add_client(c[0], c[1])

            cmd = input()
            if cmd == 's' or cmd == 'S':
                done = True
            return client

    def _display_rentals(self, params):
        rental = self._srvRental.display()
        if len(rental) == 0:
            print('no rentals')
            return
        for r in rental:
            print(r)

    def _display_clients(self, params):
        client = self._srvClient.display()
        if len(client) == 0:
            print('no clients')
            return
        for c in client:
            print(c)

    def _delete_client(self, params):
        client_id = int(input('id: '))
        if self._srvRental.find_client_id(client_id) == -1:
            self._srvClient.delete_client(client_id)
        else:
            self._srvRental.delete_client_and_its_rentals(client_id)

    def _return_book(self, params):
        rental_id = int(input('id_rental: '))
        if self._srvRental.find_id_rental(rental_id) != -1:
            self._srvRental.update_return(rental_id, datetime.date.today())
        else:
            print('The given id was not found')

    def _rent_book(self, params):
        r = self.read_rental()
        if self._srvRental.check_if_rent(r[1], r[4]):
            rent = self._srvRental.add_rental(r[0], r[1], r[2], r[3], r[4])
            return rent
        else:
            print('The book cannot be rented')

    def _update_client(self, params):
        id = int(input('id: '))
        if self._srvClient.find(id) != -1:
            name = input('name: ')
            client = self._srvClient.update_client(id, name)
            return client
        else:
            print('The given id does not exist')

    def test_init_clients(self):
        surname_list = ['Pop', 'Ionescu', 'Popescu', 'Moldovan', 'Oltean', 'Stan', 'Rus', 'Rusu', 'Toma', 'Petrescu']
        forename_list = ['Ana', 'Dana', 'Dan', 'Sara', 'Vlad', 'Maria', 'Ion', 'Luca', 'Radu', 'Mihai']
        for i in range(1, 11):
            name = surname_list[randrange(9)] + " " + forename_list[randrange(9)]
            self._srvClient.add_client(i, name)
        return self._srvClient

    def test_init_rentals(self):
        book_id = ['ISBN 989-02-56458-03-6', 'ISBN 988-12-05458-36-1', 'ISBN 978-45-56898-02-4',
                   'ISBN 986-25-45646-41-2']
        for i in range(1, 11):
            rented_date = datetime.date(randrange(2018, 2020), randrange(1, 11), randrange(1, 29))
            returned_date = datetime.date(randrange(2018, 2020), randrange(1, 11), randrange(1, 29))
            id_book = choice(book_id)
            id_client = randint(2, 10)
            done = False
            while not done:
                if rented_date > returned_date:
                    rented_date = datetime.date(randrange(2018, 2020), randrange(1, 11), randrange(1, 29))
                    returned_date = datetime.date(randrange(2018, 2020), randrange(1, 11), randrange(1, 29))
                else:
                    done = True
            self._srvRental.add_rental(i, id_book, id_client, rented_date, returned_date)
        return self._srvRental

    def test_init_books(self):
        book_list = ['ISBN 989-02-56458-03-6', 'ISBN 988-12-05458-36-1', 'ISBN 978-45-56898-02-4',
                     'ISBN 986-25-45646-41-2', 'ISBN 987-80-45645-10-5', 'ISBN 977-65-31458-96-7',
                     'ISBN 997-60-52558-33-3', 'ISBN 998-78-05458-63-8', 'ISBN 976-78-65458-66-9',
                     'ISBN 975-55-64458-93-0']
        books = ['Maitreyi de Mircea Eliade', 'Ion de Liviu Rebreanu', 'Morometii de Marin Preda',
                 'Cel mai iubit dintre pamanteni de Marin Preda', 'Nostalgia de Mircea Cartarescu',
                 'Nunta in cer de Mircea Eliade', 'Adam si Eva de Liviu Rebreanu', 'Mara de Ioan Slavici',
                 'Act venetian de Camil Petrescu', 'Arta conversatiei de Ileana Vulpescu']

        for i in range(10):
            book = choice(books)
            books.remove(book)
            token = book.split('de', 1)
            title = token[0]
            author = token[1]
            book_id = choice(book_list)
            book_list.remove(book_id)
            self._srvBook.add_book(book_id, title, author)
        return self._srvBook

    def _update_book(self, params):
        book_id = input('id: ')
        if self._srvBook.find(book_id) != -1:
            title = input('title: ')
            author = input('author: ')
            self._srvBook.update_book(book_id, title, author)
        else:
            print('The given id cannot be found')

    def _statistic1(self, params):
        print("Most rented book. The list of book sorted in descending order by number of times they were rented")
        data = self._srvRental.most_rented_books()
        if len(data) == 0:
            print('No rentals')
        else:
            for book in data:
                print(book)

    def _statistic2(self, params):
        print("Most rented author. The list of authors sorted in descending order by the number of times their books "
              "were rented")
        data = self._srvRental.most_author()
        if len(data) == 0:
            print('No rentals')
        else:
            for author in data:
                print(author)

    def _statistic3(self, params):
        print("Most active clients. The list of clients sorted in descending order by the number of book rental days "
              "they have ")
        data = self._srvRental.most_clients()
        if len(data) == 0:
            print('No rentals')
        else:
            for client in data:
                print(client)

    def _search_client_name(self, params):
        name = input('Client Name: ')
        result = self._srvClient.search_client_name(name)
        if len(result) == 0:
            print('No matches!')
            return
        for c in result:
            print(c)

    def _search_by_title(self, params):
        title = input('Title: ')
        result = self._srvBook.search_title(title)
        if len(result) == 0:
            print('No matches!')
            return
        for r in result:
            print(r)

    def _search_by_author(self, params):
        author = input('Author: ')
        result = self._srvBook.search_author(author)
        if len(result) == 0:
            print('No matches!')
            return
        for r in result:
            print(r)

    def _search_book_id(self, params):
        book_id = input('Book id: ')
        result = self._srvBook.search_id(book_id)
        if len(result) == 0:
            print('No matches!')
            return
        for r in result:
            print(r)

    def _search_client_id(self, params):
        client_id = int(input('Client ID: '))
        result = self._srvClient.search_id(client_id)
        if len(result) == 0:
            print('No matches!')
            return
        for r in result:
            print(r)

    def _undo_ui(self, params):
        self._undo_service.undo()

    def _redo_ui(self, params):
        self._undo_service.redo()
