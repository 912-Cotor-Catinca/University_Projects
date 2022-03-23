"""
    Entity class should be coded here
"""


class ExpenseException(Exception):
    def __init__(self, msg):
        self._msg = msg


class Expense:
    def __init__(self, day, amount=0, type=""):
        """
        The constructor of Expense class
        :param day:
        :param amount:
        :param type:
        """

        if not isinstance(day, int):
            raise ExpenseException('Invalid value for day')
        if not isinstance(amount, int):
            raise ExpenseException('Invalid value for amount')
        if not isinstance(type, str):
            raise ExpenseException('Type is a string')

        self._day = day
        self._amount = amount
        self._type = type

        if not type in ["food", "internet", "housekeeping", "clothing", "others"]:
            raise ExpenseException('Not the correct category!')

    @property
    def days(self):
        return self._day

    @property
    def amount(self):
        return self._amount

    @property
    def type(self):
        return self._type

    def __str__(self):
        return 'day: ' + str(self._day) + ' amount: ' + str(self._amount) + ' type: ' + self._type


def test_expense():
    expense = Expense(3, 50, 'food')
    assert expense.days == 3 and expense.amount == 50 and expense.type == 'food'
    expense = Expense(10, 500, 'internet')
    assert expense.days == 10 and expense.amount == 500 and expense.type == 'internet'
    expense = Expense(6, 100, 'clothing')
    assert expense.days == 6 and expense.amount == 100 and expense.type == 'clothing'
    try:
        expense = Expense('asd', 'asd', 'rubbish') 
        assert False
    except ExpenseException:
        assert True


test_expense()
