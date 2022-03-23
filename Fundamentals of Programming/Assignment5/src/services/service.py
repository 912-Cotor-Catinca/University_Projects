"""
    Service class includes functionalities for implementing program features
"""
from domain.entity import Expense


class ExpenseList:
    def __init__(self):
        self._expense = []
        self._history = []

    def __len__(self):
        return len(self._expense)

    def add(self, expense):
        """
        Add an expense in expense list
        :param expense:
        :return:
        """
        self._history.append(self._expense.copy())
        self._expense.append(expense)

    def delete(self, delete_list):
        for expense in delete_list:
            self._expense.remove(expense)

    def filter(self, value):
        delete_list = []
        self._history.append(self._expense.copy())
        for i in self._expense:
            if i.amount <= value:
                delete_list.append(i)
        self.delete(delete_list)

    def display(self):
        list = []
        for expense in self._expense:
            list.append(expense)
        return list

    def undo_expense(self):
        self._expense.clear()
        prev_list = self._history.pop()
        for expense in prev_list:
            self._expense.append(expense)

    def history(self):
        return len(self._history)


def test_list():
    expense_list = ExpenseList()
    e = Expense(day=5, amount=400, type='internet')
    expense_list.add(e)
    expense_list.add(Expense(day=2, amount=200, type='clothing'))
    assert len(expense_list) == 2

    delete_list = [e]
    expense_list.delete(delete_list)
    assert len(expense_list) == 1

    expense_list.add(Expense(day=10, amount=100, type='food'))
    expense_list.add(Expense(day=15, amount=150, type='food'))

    expense_list.filter(140)

    assert len(expense_list) == 2
    expense_list.undo_expense()
    assert len(expense_list) == 3
    assert expense_list.history() == 4
    expense_list.undo_expense()
    expense_list.undo_expense()
    expense_list.undo_expense()
    assert expense_list.history() == 1



test_list()
