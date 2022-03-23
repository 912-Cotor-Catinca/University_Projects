"""
Assemble the program and start the user interface here
"""
import operator
from datetime import date

from domain.entity import to_str, get_type, get_money, create_expenses
from functions.functions import split_command, add_expense, remove_expense, sum_category, max_day, sort_category, \
    filter_expense, sort_day, undo_operation


def print_menu():
    print("The Family Expenses ! ")
    print("\tTo add a new expense today, use add <sum> <category> command")
    print("\tTo insert a new expense in a given day, use add <sum> <category> command")
    print("\tTo remove all expenses from a day, remove <day> command")
    print("\tTo remove all expenses between two dates, use remove <start day> to <end day> command")
    print("\tTo remove all expenses from a category, use remove <category> command")
    print("\tTo display all expenses, use list command")
    print("\tTo display all expenses from a category, use list <category> command")
    print("\tTo display all expenses from a category having an amount of money with a given property, "
          "use list <category> [ < | = | > ] <value> command")
    print("\tTo display the total expense for category, use sum <category> command")
    print("\tTo display the day with the maximum expenses, use max day command")
    print("\tTo  display the total daily expenses in ascending order by amount of money spent, use sort day command")
    print("\tTo display the daily expenses for category in ascending order by amount of money spent, "
          "use sort <category> command")
    print("\tTo keep only expenses in the category given, use filter <category> command")
    print("\tTo keep only expenses in category books with amount of money with a given property"
          " use filter <category> [ < | = | > ] <value> command")
    print("\tTo undo operations, use undo command")


def start_ui():
    print_menu()
    expense_list = []
    test_init(expense_list)
    history = list()

    command_dict = {'add': add_expense_ui, 'insert': insert_expense_ui, 'list': display_list_ui,
                    'remove': remove_expense_ui, 'sum': display_sum, 'max': display_max_day,
                    'sort': display_sort, 'filter': filter_expense_ui, 'undo': undo_ui}
    done = False
    while not done:
        try:
            command = input('command> ')
            cmd_word, cmd_params = split_command(command)
            if cmd_word == 'undo':
                undo_ui(expense_list, history)
            elif cmd_word in command_dict:
                command_dict[cmd_word](expense_list, cmd_params, history)
            elif cmd_word == 'exit':
                done = True
            else:
                print('Bad command')
        except ValueError as ve:
            print(str(ve))


def display_list_ui(expense_list, cmd_params, history):
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


def display_sum(expense_list, cmd_params, history):
    list_type = ['housekeeping', 'food', 'transport', 'clothing', 'internet', 'others']
    tokens = cmd_params.split(' ')

    if tokens[0] in list_type:
        print('The total sum of', tokens[0], 'is',
              sum_category(expense_list, type=tokens[0]))
    else:
        raise ValueError('The category does not exist!')


def display_sort(expense_list, cmd_params, history):
    list_type = ['housekeeping', 'food', 'transport', 'clothing', 'internet', 'others']
    tokens = cmd_params.split(' ')
    for i in range(len(tokens)):
        tokens[i] = tokens[i].strip()

    if len(tokens) == 1:
        if tokens[0] == 'day':
            new_list = sort_day(expense_list)
            for expense in new_list:
                print('day:', str(expense[0]), 'money', str(expense[1]))
        elif tokens[0] in list_type:
            new_list = sort_category(expense_list, type=tokens[0])
            for expense in new_list:
                print(to_str(expense))
        else:
            raise ValueError('Incorrect parameters!')
    else:
        raise ValueError('Too many parameters!')


def display_max_day(expense_list, cmd_params, history):
    if cmd_params == "":
        s = max_day(expense_list)
        if s != -1:
            print('The day with maximum expenses is: ' + str(s))
        else:
            raise ValueError('There is no day')
    else:
        raise ValueError('Unexpected parameter!')


def insert_expense_ui(expense_list, cmd_params, history):
    tokens = cmd_params.split(' ')
    if len(tokens) != 3:
        raise ValueError('Invalid Params')
    for i in range(len(tokens)):
        tokens[i] = tokens[i].strip()

    expense = create_expenses(int(tokens[0]), int(tokens[1]), tokens[2])
    add_expense(expense_list, history, expense)


def add_expense_ui(expense_list, cmd_params, history):
    today = str(date.today().day)
    today += ' '
    today = today + cmd_params
    insert_expense_ui(expense_list, today, history)


def filter_expense_ui(expense_list, cmd_params, history):
    list_type = ['housekeeping', 'food', 'transport', 'clothing', 'internet', 'others']
    operator_list = {'<': operator.lt, '=': operator.eq, '>': operator.gt}
    tokens = cmd_params.split(' ')
    for i in range(len(tokens)):
        tokens[i] = tokens[i].strip()

    if len(tokens) == 1:
        if tokens[0] in list_type:
            filter_expense(expense_list, history, type=tokens[0])
    elif len(tokens) == 3:
        if tokens[1] in operator_list:
            for expense in expense_list:
                if get_type(expense) == tokens[0] and operator_list[tokens[1]](get_money(expense), int(tokens[2])):
                    filter_expense(expense_list, history, type=tokens[0], operator1=tokens[1], value=int(tokens[2]))
        else:
            raise ValueError('Incorrect operator!')
    else:
        raise ValueError('Too many parameters!')


def remove_expense_ui(expense_list, cmd_params, history):
    list_type = ['housekeeping', 'food', 'transport', 'clothing', 'internet', 'others']
    tokens = cmd_params.split(' ')
    for i in range(len(tokens)):
        tokens[i] = tokens[i].strip()

    if len(tokens) == 1:
        if tokens[0] in list_type:
            remove_expense(expense_list, history, type=tokens[0])
        else:
            remove_expense(expense_list, history, start_day=int(tokens[0]), end_day=int(tokens[0]))
    elif len(tokens) == 3:
        remove_expense(expense_list, history, start_day=int(tokens[0]), end_day=int(tokens[2]))


def undo_ui(expense_list, history):
    if len(history) == 0:
        raise ValueError('No more operations to undo!')
    else:
        undo_operation(expense_list, history)


# ============================================== functions tests ======================================================


def test_init(expenses_list):
    expenses_list.append(create_expenses(5, 65, 'others'))
    expenses_list.append(create_expenses(7, 70, 'internet'))
    expenses_list.append(create_expenses(8, 100, 'food'))
    expenses_list.append(create_expenses(9, 1000, 'clothing'))
    expenses_list.append(create_expenses(10, 20, 'transport'))
    expenses_list.append(create_expenses(10, 150, 'food'))
    expenses_list.append(create_expenses(10, 200, 'clothing'))
    expenses_list.append(create_expenses(15, 40, 'others'))
    expenses_list.append(create_expenses(20, 50, 'food'))
    expenses_list.append(create_expenses(25, 500, 'housekeeping'))


def insert_test():
    expense_list = []
    test_init(expense_list)
    history = list()
    insert_expense_ui(expense_list, "2 27 food", history)
    try:
        insert_expense_ui(expense_list, "42 20 food", history)  # this should raise an error because day 42 does not exist
        assert False
    except ValueError:
        assert True


def add_test():
    expense_list = []
    test_init(expense_list)
    history = list()
    add_expense_ui(expense_list, "23 food", history)

    try:
        add_expense_ui(expense_list, "23 4", history)  # this should raise an error because there are incorrect parameters
        assert False
    except ValueError:
        assert True


def delete_test():
    expense_list = []
    test_init(expense_list)
    history = list()
    remove_expense_ui(expense_list, "5", history)

    try:
        remove_expense_ui(expense_list, "drinks", history)  # this should raise an error because that category does not exist
        assert False
    except ValueError:
        assert True


def list_test():
    expense_list = []
    test_init(expense_list)
    history = list()
    display_list_ui(expense_list, "= 100", history)

    try:
        display_list_ui(expense_list, "food > 123sad", history)  # this should raise an error because the value is not integer
        assert False
    except ValueError:
        assert True


def total_sum_test():
    expense_list = []
    test_init(expense_list)
    history = list()
    assert sum_category(expense_list, "internet") == 70

    try:
        display_sum(expense_list, "nothing", history)  # this should raise an error because the category doesn't exist
        assert False
    except ValueError:
        assert True


def max_day_test():
    expense_list = []
    test_init(expense_list)
    history = list()
    assert max_day(expense_list) == 9

    try:
        display_max_day(expense_list, "asdfghhgfd", history)
        assert False
    except ValueError:
        assert True


def filter_test():
    expense_list = []
    test_init(expense_list)
    history = list()
    filter_expense_ui(expense_list, "food", history)

    try:
        filter_expense_ui(expense_list, "internet == 80", history)  # this should raise an error because the
        # operator does not exist
        assert False
    except ValueError:
        assert True


def undo_test():
    expenses_list = []
    test_init(expenses_list)
    init_list = expenses_list.copy()
    history = list()

    remove_expense_ui(expenses_list, "9", history)
    len_after_operation = len(expenses_list)
    undo_operation(expenses_list, history)

    try:
        if len(expenses_list) != len_after_operation + 1:
            assert False
        else:
            for i in range(len(expenses_list)):
                if expenses_list[i] != init_list[i]:
                    assert False
    except ValueError:
        assert True


def tests():
    list_test()
    insert_test()
    delete_test()
    add_test()
    total_sum_test()
    max_day_test()
    filter_test()
    undo_test()


tests()
start_ui()
