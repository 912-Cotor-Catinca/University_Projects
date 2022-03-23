"""
Domain file includes code for entity management
entity = number, transaction, expense etc.
"""


def create_expenses(day, money, type):
    """
    Create a family expense
    :param day: The day(integer) between 1 and 30
    :param money: The amount of money(integer) spent in that day
    :param type: The expense type one of: housekeeping, food, transport, clothing, internet, others
    :return: The expense
    Raise ValueError if the expense cannot be created with the params
    """
    list_type = ['housekeeping', 'food', 'transport', 'clothing', 'internet', 'others']
    if day < 1 or day > 30 or money < 1 or len(type) == 0 or not (type in list_type):
        raise ValueError('Cannot create expense with given params!')
    return {'day': day, 'money': money, 'type': type}


def get_day(expense):
    return expense['day']


def get_money(expense):
    return expense['money']


def get_type(expense):
    return expense['type']


def to_str(expense):
    return 'day: ' + str(get_day(expense)) + ' money: ' + str(get_money(expense)) + ' type: ' + get_type(expense)
