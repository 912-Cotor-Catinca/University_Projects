"""
    13. Determine the n-th element of the sequence 1,2,3,2,5,2,3,7,2,3,2,5,... obtained from the sequence of
        natural numbers by replacing composed numbers with their prime divisors, without memorizing the elements of
        the sequence.
"""


def desc(index):
    if index <= 3:
        return index
    i = 3
    number = 4
    while i < index:
        d = 2
        cnt = 0
        n = number
        while n > 1:
            exp = 0
            while n % d == 0:
                n /= d
                exp += 1
            if not exp == 0:
                cnt += 1
                if cnt + i == index:
                    return d
            if d == 2:
                d = 3
            else:
                d += 2
        i += cnt
        number += 1


if __name__ == '__main__':
    index = input("number:")
    print(desc(int(index)))
