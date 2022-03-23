#
# Write the implementation for A3 in this file
#

#
# domain section is here (domain = numbers, transactions, expenses, etc.)
# getters / setters
# No print or input statements in this section
# Specification for all non-trivial functions (trivial usually means a one-liner)
import operator
from datetime import date


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


# Functionalities section (functions that implement required features)
# No print or input statements in this section
# Specification for all non-trivial functions (trivial usually means a one-liner)
# Each function does one thing only
# Functions communicate using input parameters and their return values


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


def add_expense(expense_list, expense):
    """
    Add a new expense in the list
    :param expense_list: The list of expenses
    :param expense: The expense which will be added
    :return: A new list with more expenses
    """
    expense_list.append(expense)


def delete_expense(expense_list, delete_list):
    for expense in delete_list:
        expense_list.remove(expense)


def remove_expense(expense_list, start_day=0, end_day=0, type=""):
    """
    Remove all expenses from a given category or given day / interval of days.
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
    delete_expense(expense_list, delete_list)


# UI section
# (all functions that have input or print statements, or that CALL functions with print / input are  here).
# Ideally, this section should not contain any calculations relevant to program functionalities


def print_menu():
    print("The Family Expenses ! ")
    print("\tTo add a new expense today, use add <sum> <category> command")
    print("\tTo insert a new expense in a given day, use add <sum> <category> command")
    print("\tTo remove all expenses from a day, remove <day> command")
    print("\tTo remove all expenses between two dates, use remove <start day> to <end day> command")
    print("\tTo remove all expenses from a category, use remove <category> command")
    print("\tTo display all expenses, use list command")
    print("\tTo display all expenses from a category, use list <category> command")
    print("\tTo display all expenses from a category having an amount of monet with a given property, "
          "use list <category> [ < | = | > ] <value> command")


def start_ui():
    print_menu()
    expense_list = []
    test_init(expense_list)

    command_dict = {'add': add_expense_ui, 'insert': insert_expense_ui, 'list': display_list_ui, 'remove': remove_expense_ui}
    done = False
    while not done:
        try:
            command = input('command> ')
            cmd_word, cmd_params = split_command(command)
            if cmd_word in command_dict:
                command_dict[cmd_word](expense_list, cmd_params)
            elif cmd_word == 'exit':
                done = True
            else:
                print('Bad command')
        except ValueError as ve:
            print(str(ve))


def display_list_ui(expense_list, cmd_params):
    operator_list = {'<': operator.lt, '=': operator.eq, '>': operator.gt}
    list_type = ['housekeeping', 'food', 'transport', 'clothing', 'internet', 'others']
    tokens = cmd_params.split(' ')
    if cmd_params == "":
        for expense in expense_list:
            print(to_str(expense))
    elif len(tokens) == 1:
        for expense in expense_list:
            if get_type(expense) == tokens[0] and get_type(expense) in list_type:
                print(to_str(expense))
    elif len(tokens) == 3:
        if tokens[1] in operator_list:
            for expense in expense_list:
                if get_type(expense) == tokens[0] and operator_list[tokens[1]](get_money(expense), int(tokens[2])):
                    print(to_str(expense))


def insert_expense_ui(expense_list, cmd_params):
    tokens = cmd_params.split(' ')
    if len(tokens) != 3:
        raise ValueError('Invalid Params')
    for i in range(len(tokens)):
        tokens[i] = tokens[i].strip()

    expense = create_expenses(int(tokens[0]), int(tokens[1]), tokens[2])
    add_expense(expense_list, expense)


def add_expense_ui(expense_list, cmd_params):
    today = str(date.today().day)
    today += ' '
    today = today + cmd_params
    insert_expense_ui(expense_list, today)


def remove_expense_ui(expense_list, cmd_params):
    list_type = ['housekeeping', 'food', 'transport', 'clothing', 'internet', 'others']
    tokens = cmd_params.split(' ')
    for i in range(len(tokens)):
        tokens[i] = tokens[i].strip()
    if len(tokens) == 1:
        if tokens[0] in list_type:
            remove_expense(expense_list, type=tokens[0])
        else:
            remove_expense(expense_list, start_day=int(tokens[0]), end_day=int(tokens[0]))
    elif len(tokens) == 3:
        remove_expense(expense_list, start_day=int(tokens[0]), end_day=int(tokens[2]))


# Test functions go here
#
# Test functions:
#   - no print / input
#   - great friends with assert


def test_init(expenses_list):
    expenses_list.append(create_expenses(8, 100, 'food'))
    expenses_list.append(create_expenses(9, 1000, 'clothing'))
    expenses_list.append(create_expenses(10, 20, 'transport'))
    expenses_list.append(create_expenses(10, 150, 'food'))
    expenses_list.append(create_expenses(10, 200, 'clothing'))
    expenses_list.append(create_expenses(20, 50, 'housekeeping'))
    expenses_list.append(create_expenses(5, 65, 'others'))
    expenses_list.append(create_expenses(7, 70, 'internet'))
    expenses_list.append(create_expenses(15, 40, 'others'))
    expenses_list.append(create_expenses(25, 500, 'housekeeping'))


def insert_test():
    expense_list = []
    test_init(expense_list)
    insert_expense_ui(expense_list, "2 27 food")
    try:
        insert_expense_ui(expense_list, "42 20 food")  # this should raise an error because day 42 does not exist
        assert False
    except ValueError:
        assert True


def add_test():
    expense_list = []
    test_init(expense_list)
    add_expense_ui(expense_list, "23 food")

    try:
        add_expense_ui(expense_list, "23 4")  # this should raise an error because there are too many parameters
        assert False
    except ValueError:
        assert True


def delete_test():
    expense_list = []
    test_init(expense_list)
    remove_expense_ui(expense_list, "5")

    try:
        remove_expense_ui(expense_list, "drinks")  # this should raise an error because that category does not exist
        assert False
    except ValueError:
        assert True


def list_test():
    expense_list = []
    test_init(expense_list)
    display_list_ui(expense_list, "= 100")

    try:
        display_list_ui(expense_list, "food > 123sad")  # this should raise an error because the value is not integer
        assert False
    except ValueError:
        assert True


def tests():
    list_test()
    insert_test()
    delete_test()
    add_test()


# tests()


start_ui()
