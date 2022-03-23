#
# Write the implementation for A2 in this file
#

# Function section
# (write all non-UI functions in this section)
# There should be no print or input statements below this comment
# Each function should do one thing only
# Functions communicate using input parameters and their return values


def create_complex_nr(real_part, imag_part):
    """
    Create a complex number with the given params
    :param real_part: The real part(int)
    :param imag_part: The imaginary part(int)
    :return: Create the complex number
    """
    return [real_part, imag_part]


def get_ReZ(number):
    return number[0]


def get_ImZ(number):
    return number[1]


def to_str(number):
    """
    Build the string representation of a complex number
    :param number: The complex number
    :return: Its string representation
    """

    if get_ReZ(number) > 0 and get_ImZ(number) > 0:
        return str(get_ReZ(number)) + ' + ' + str(get_ImZ(number)) + 'i'
    elif get_ImZ(number) < 0 and get_ReZ(number) < 0:
        return '-' + str(abs(get_ReZ(number))) + ' - ' + str(abs(get_ImZ(number))) + 'i'
    elif get_ReZ(number) > 0 and get_ImZ(number) < 0:
        return str(get_ReZ(number)) + ' - ' + str(abs(get_ImZ(number))) + 'i'
    elif get_ReZ(number) < 0 and get_ImZ(number) > 0:
        return '-' + str(abs(get_ReZ(number))) + ' + ' + str(get_ImZ(number)) + 'i'
    elif get_ReZ(number) == 0:
        return str(get_ImZ(number)) + 'i'
    elif get_ImZ(number) == 0:
        return str(get_ReZ(number))


def add_number(complex_list, number):
    """
    Add number to the list
    """
    if find_by_number(complex_list, get_ReZ(number), get_ImZ(number)):
        raise ValueError('Duplicate number!')
    complex_list.append(number)


def find_by_number(complex_list, real_part, imag_part):
    """
    Find a number in complex_list with given real part and imaginary part
    :param complex_list: List of complex numbers
    :param real_part:The real part
    :param imag_part:The imaginary part
    :return:The number or None if number does not exist
    """
    for number in complex_list:
        if real_part == get_ReZ(number) and imag_part == get_ImZ(number):
            return number
    return None


def increasing_sequence(complex_list):
    """
    Determine the longest increasing real part sequence. By every step it checks if the current real part of
    the number is greater then the previous one and if so, the length of sequence is incremented and compared to
    the current longest one
    :param complex_list: The list of complex numbers
    :return: The start position and the length of the longest sequence
    """
    cnt = 1
    len_max = 0
    index = 0
    for i in range(1, len(complex_list)):
        if get_ReZ(complex_list[i]) > get_ReZ(complex_list[i-1]):
            cnt += 1
        else:
            cnt = 1
        if cnt > len_max:
            len_max = cnt
            index = i - len_max + 1
    return index, len_max


def get_modulo(number):
    return get_ReZ(number) * get_ReZ(number) + get_ImZ(number) * get_ImZ(number)


def modulo_sequence(complex_list):
    """
    Determine the longest sequence of numbers having the same modulus. By every step it checks if the modulo of the
    current number is greater then the modulo of previous one and increments the length of the current sequence. If it
    finds a longer length, then updates the maximum length
    :param complex_list: The list of complex numbers
    :return: The start position and the longest length of the sequence
    """
    len_max = 0
    cnt = 1
    index = 0
    for i in range(1, len(complex_list)):
        if get_modulo(complex_list[i]) == get_modulo(complex_list[i - 1]):
            cnt += 1
        else:
            cnt = 1
        if cnt > len_max:
            len_max = cnt
            index = i - len_max + 1
    return index, len_max


# UI section
# (write all functions that have input or print statements here).
# Ideally, this section should not contain any calculations relevant to program functionalities


def show_increasing_sequence(complex_list):
    """
    Prints the longest sequence of increasing real part of a number
    """
    source, lmax = increasing_sequence(complex_list)
    dest = source + lmax - 1
    for number in range(source, dest + 1):
        print(complex_list[number])


def show_same_modulo(complex_list):
    """
    Print the longest sequence of numbers with same modulus
    """
    index, lmax = modulo_sequence(complex_list)
    dest = index + lmax - 1
    for number in range(index, dest + 1):
        print(complex_list[number])


def read_number():
    number = None
    while not number:
        real_part = int(input('Enter real part: '))
        imag_part = int(input('Enter imaginary part: '))
        number = create_complex_nr(real_part, imag_part)
        if number is None:
            print('Invalid number parameters!')
    return number


def add_number_ui(complex_list):
    number = read_number()
    add_number(complex_list, number)


def print_number(complex_list):
    for number in complex_list:
        print(to_str(number))


def print_menu():
    print('\n0. Exit')
    print('1. Show all numbers: ')
    print('2. Add number: ')
    print('3. Sequence of increasing numbers: ')
    print('4. Sequence of numbers with same modulus: ')


def start():
    """
    print menu
    read a complex number input
    call functions that fulfills the requirements
    """
    complex_list = []
    initialization(complex_list)
    command_dict = {'1': print_number, '2': add_number_ui, '3': show_increasing_sequence, '4': show_same_modulo}
    done = False
    while not done:
        print_menu()
        command = input('Enter command: ')
        if command in command_dict:
            command_dict[command](complex_list)
        elif command == '0':
            print("Exit")
            done = True
        else:
            print('Invalid command')


def initialization(complex_list):
    complex_list.append(create_complex_nr(1, -2))
    complex_list.append(create_complex_nr(0, -2))
    complex_list.append(create_complex_nr(2, 2))
    complex_list.append(create_complex_nr(3, -1))
    complex_list.append(create_complex_nr(6, 0))
    complex_list.append(create_complex_nr(3, 2))
    complex_list.append(create_complex_nr(2, -3))
    complex_list.append(create_complex_nr(5, 1))
    complex_list.append(create_complex_nr(1, 4))
    complex_list.append(create_complex_nr(3, 3))


start()
