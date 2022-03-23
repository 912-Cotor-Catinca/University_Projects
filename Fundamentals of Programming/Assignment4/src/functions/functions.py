"""
Functions that implement program features. They should call each other, or other functions from the domain
"""
from domain.entity import get_day, get_type, get_money
import operator


def split_command(command):
    """
    Split command string into command words an params
    :param command:
    :return: command word and command params
    """
    command = command.strip()
    tokens = command.split(' ', 1)
    cmd_word = tokens[0].strip()
    cmd_params = tokens[1].strip() if len(tokens) == 2 else ''
    return cmd_word, cmd_params


def add_expense(expense_list, history, expense):
    """
    Add a new expense in the list
    :param history: stack of lists of expenses
    :param expense_list: The list of expenses
    :param expense: The expense which will be added
    :return: A new list with more expenses
    """
    history.append(expense_list.copy())
    expense_list.append(expense)


def delete_expense(expense_list, history, delete_list):
    history.append(expense_list.copy())
    for expense in delete_list:
        expense_list.remove(expense)


def remove_expense(expense_list, history, start_day=0, end_day=0, type=""):
    """
    Remove all expenses from a given category or given day / interval of days.
    :param history: stack of lists of expenses
    :param expense_list:The list with expenses
    :param start_day: The start day
    :param end_day: The end day. If it is given a single day then end_day == start_day
    :param type: The category which will be deleted
    :return: A new list with expenses
    """
    list_type = ['housekeeping', 'food', 'transport', 'clothing', 'internet', 'others']
    delete_list = []
    if type == "":
        for expense in expense_list:
            if start_day <= get_day(expense) <= end_day:
                delete_list.append(expense)
    elif start_day == 0 and end_day == 0:
        if type in list_type:
            for expense in expense_list:
                if get_type(expense) == type:
                    delete_list.append(expense)
        else:
            raise ValueError('That category does not exist')
    delete_expense(expense_list, history, delete_list)


def sum_category(expense_list, type=""):
    """
    Calculate the sum of all the expenses from a category
    :param expense_list: the expense's list
    :param type: The given category(string)
    :return: The sum of that category
    """
    s = 0
    list_type = ['housekeeping', 'food', 'transport', 'clothing', 'internet', 'others']
    if type in list_type:
        for expense in expense_list:
            if get_type(expense) == type:
                s += get_money(expense)
    return s


def day_list(expense_list):
    list_day = []
    for expense in expense_list:
        list_day.insert(get_day(expense), get_day(expense))
    return list_day


def sum_list(expense_list):
    """
    Return a list with amount of expenses for each day in a month
    :param expense_list: The expense list
    :return: A new list where new_list[i] = the total amount of expenses in the day i
    """
    new_list = [None] * 22
    list_day = day_list(expense_list)
    for i in range(0, len(list_day)):
        j = i + 1
        s = 0
        if j < len(list_day):
            if list_day[i] != list_day[j]:
                for expense in expense_list:
                    if get_day(expense) == list_day[i]:
                        s += get_money(expense)
                new_list.insert(list_day[i], s)
        elif j == len(list_day):
            for expense in expense_list:
                if get_day(expense) == list_day[i]:
                    s += get_money(expense)
            new_list.insert(list_day[i], s)

    return new_list


def max_day(expense_list):
    """
    Returns the day with the maximum expenses
    :param expense_list:
    :return:
    """
    sum_max = 0
    day_max = 0
    list_day = day_list(expense_list)
    for day in list_day:
        s = 0
        for expense in expense_list:
            if get_day(expense) == day:
                s += get_money(expense)
        if s > sum_max:
            sum_max = s
            day_max = day
    return day_max


def sort_day(expense_list):
    """
    Sort the list by amount of money spent in a day
    :param expense_list: The list with expenses
    :return: A new dictionary sorted by value; the key is the day and value is the money
    """
    new_list = sum_list(expense_list)
    sort_dict = {}
    j = 0
    for i in range(0, len(new_list)):
        if new_list[i] is not None:
            if j == 0:
                sort_dict[i] = new_list[i]

                j += 1
            else:
                sort_dict[i] = new_list[i]
                j += 1
    sort_list = sorted(sort_dict.items(), key=lambda item: item[1])

    return sort_list


def sort_category(expense_list, type=""):
    """
    Sort expenses with a given category by the amount of money spent
    :param expense_list:
    :param type: The category
    :return: A new list sorted
    """
    list_type = ['housekeeping', 'food', 'transport', 'clothing', 'internet', 'others']
    new_list = []
    if not type == "" and type in list_type:
        for expense in expense_list:
            if get_type(expense) == type:
                new_list.append(expense)

    new_list.sort(key=lambda item: item.get("money"))
    return new_list


def filter_expense(expense_list, history, type="", operator1="", value=0):
    """
    Keeps only the expenses with a given category or with a given property
    :param expense_list:
    :param history: stack of lists of expenses
    :param type: The category
    :param operator1: can be < or = or >
    :param value: The amount of money
    :return: A new list with expenses
    """
    operator_list = {'<': operator.lt, '=': operator.eq, '>': operator.gt}
    list_type = ['housekeeping', 'food', 'transport', 'clothing', 'internet', 'others']
    filter_list = []
    if operator1 == "":
        if type in list_type:
            for expense in expense_list:
                if get_type(expense) != type:
                    filter_list.append(expense)
        else:
            raise ValueError('That category does not exist')
    else:
        if operator1 in operator_list:
            for expense in expense_list:
                if get_type(expense) == type and not operator_list[operator1](get_money(expense), value):
                    filter_list.append(expense)
    delete_expense(expense_list, history, filter_list)


def undo_operation(expenses_list, history):
    expenses_list.clear()
    prev_list = history.pop()
    for expense in prev_list:
        expenses_list.append(expense)
