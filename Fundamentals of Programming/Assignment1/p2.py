"""
    6.Determine a calendar date (as year, month, day) starting from two integer numbers representing
      the year and the day number inside that year.
"""


def is_leap_year(year):
    """
    Check if is a leap year(with 366 days)
    leap years are multiples of 4 and 400 but not of 100: 1900 is not a leap year
    :param year: The year
    :return: 1 if is a leap year, 0 if is not
    """
    leap = None
    if not year % 4 == 0:
        leap = 0
    elif not year % 100 == 0:
        leap = 1
    elif not year % 400 == 0:
        leap = 0
    else:
        leap = 1

    return leap


def number_of_days(year, number):
    """
    Determine the calendar date
    :param year: The year
    :param number: The day number inside that year
    :return: The date
    """
    leap = is_leap_year(year)

    list_days = []
    list = [1, 3, 5, 7, 8, 10, 12]
    for month in range(1, 13, 1):
        if month in list:
            list_days.insert(month, 31)
        elif month == 2:
            list_days.insert(month, 28 + leap)
        else:
            list_days.insert(month, 30)
    i = 0
    nr = number
    while nr > list_days[i]:
        nr -= list_days[i]
        i += 1

    list_months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
                   "November", "December"]
    print(nr, list_months[i])


if __name__ == '__main__':
    year = input("year: ")
    number = input("number: ")
    number_of_days(int(year), int(number))
