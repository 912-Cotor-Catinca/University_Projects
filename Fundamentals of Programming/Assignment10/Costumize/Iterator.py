

class OrderIterator:
    def __init__(self, list=None):
        if list is None:
            list = []
        self._list = list
        self._position = 0

    def __iter__(self):
        self._position = 0
        return self

    def __len__(self):
        return len(self._list)

    def __next__(self):
        # Stop iteration when other elements are not available
        if self._position == len(self._list):
            raise StopIteration()
        # Move to the next element
        self._position += 1
        value = self._list[self._position - 1]
        return value

    def __getitem__(self, index):
        return self._list[index]

    def __setitem__(self, index, value):
        self._list[index] = value

    def __delitem__(self, index):
        del self._list[index]

    def append(self, item):
        return self._list.append(item)

    def remove(self, item):
        return self._list.remove(item)


"""
Comb sort algorithm
"""


class Sort:
    @staticmethod
    def sort(list, comp):
        n = len(list)
        gap = n - 1
        swapped = True
        while gap != 1 or swapped is True:
            gap = Sort.next_gap(gap)
            swapped = False
            for i in range(0, n - gap):
                if comp(list[i], list[i + gap]):
                    list[i], list[i+gap] = list[i+gap], list[i]
                    swapped = True
        return list

    @staticmethod
    def next_gap(gap):
        gap = (gap * 10) / 13
        if gap < 1:
            return 1
        return int(gap)

    @staticmethod
    def filter(list, comp):
        result = []
        n = len(list)
        for i in range(n):
            if comp(list[i]):
                result.append(list[i])
        return result

