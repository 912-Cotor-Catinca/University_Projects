"""
    2. Given natural number n, determine the prime numbers p1 and p2 such that n = p1 + p2
"""


import math


def is_prime(n):
    """
    Check if the number is a prime number
    :param n: The number
    :return: True if the number is prime, and False if not
    """
    if n < 2:
        return False
    if n == 2:
        return True
    if n % 2 == 0:
        return False
    for d in range(3, int(math.sqrt(n)) + 1, 2):
        if n % d == 0:
            return False
    return True


def solve(n):
    if n < 2:
        print("Invalid input")
        return

    if n % 2 == 1:
        if is_prime(n - 2):
            print(2, n - 2)
            return
        else:
            print('There are no pairs that satisfy the require')
            return

    for i in range(2, int(n / 2) + 1, 1):
        if is_prime(i) and is_prime(n - i):
            print(i, n - i)
            # return


if __name__ == '__main__':
    n = input('Number: ')
    solve(int(n))
