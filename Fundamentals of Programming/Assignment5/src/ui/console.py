"""
    UI class.

    Calls between program modules
    ui -> service -> entity
    ui -> entity
"""
from random import randint, choice

from services.service import ExpenseList
from domain.entity import Expense


class UI:
    def __init__(self):
        self._expense = ExpenseList()
        self.init_test()
        self._commands = {'add': self.add_expense, 'list': self.display_list, 'filter': self.filter, 'undo': self.undo}

    def init_test(self):
        type_list = ["food", "internet", "housekeeping", "clothing", "others"]
        for i in range(10):
            _day = randint(1, 30)
            _amount = randint(0, 1000)
            _type = choice(type_list)
            self._expense.add(Expense(_day, _amount, _type))
        return self._expense

    @property
    def expense_list(self):
        return self._expense

    @staticmethod
    def print_menu():
        """
        Print out the menu
        """
        print("Expense:")
        print("\tTo add an expense, type add")
        print("\tTo display the list of expenses, type list")
        print("\tTo filter the list so that it contains only expenses above a certain value read from the console, type filter")
        print("\tTo undo the last operation that modified program data, type undo")

    def start(self):
        self.print_menu()
        done = False
        while not done:
            _command = input("Enter your command: ")
            try:
                if _command in self._commands:
                    self._commands[_command]()
                elif _command == 'x':
                    done = True
                else:
                    print("Bad command!")
            except ValueError as ve:
                print(str(ve))

    def read_expense(self):
        day = None
        amount = None
        type = None
        try:
            day = int(input('day: '))
            amount = int(input('amount: '))
            type = input('type: ')
        except ValueError:
            print('Invalid values! Please try again')
        return[day, amount, type]

    def add_expense(self):
        done = False
        while not done:
            e = self.read_expense()
            self._expense.add(Expense(e[0], e[1], e[2]))
            cmd = input()
            if cmd == 's' or cmd == 'S':
                done = True

    def display_list(self):
        for expense in self._expense.display():
            print(expense)

    def filter(self):
        value = int(input('Value: '))
        self._expense.filter(value)

    def undo(self):
        if self._expense.history == 10:
            raise ValueError('No more steps!')
        self._expense.undo_expense()


expense = ExpenseList()
ui = UI()
ui.start()
