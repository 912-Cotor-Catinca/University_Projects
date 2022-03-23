from unittest import TestCase

from Costumize.Iterator import OrderIterator, Sort
from Repository.ClientRepository import ClientRepo


class TestIterator(TestCase):
    def setUp(self):
        self.list = OrderIterator()

    def tests(self):
        self.list.append('item1')
        self.list.append('item2')
        self.list.append('item3')
        self.assertEqual(len(self.list), 3)
        index = 0
        for i in self.list:
            self.list[index] = index + 1
            index += 1
        self.assertEqual(self.list[1], 2)
        self.list.append(3)
        self.assertEqual(len(self.list), 4)
        self.list.remove(3)
        self.assertEqual(len(self.list), 3)
        self.list.append(3)
        self.list.__delitem__(3)
        self.assertEqual(self.list[2], 3)


class TestSort(TestCase):
    def setUp(self):
        self.list1 = ClientRepo()
        self.list2 = []

    def test_sort(self):
        result1 = Sort.sort(self.list1, lambda i, j: i.client_id < j.client_id)
        self.list2.append(3)
        self.list2.append(100)
        self.list2.append(2)
        self.list2.append(4)
        result2 = Sort.sort(self.list2, lambda i, j: i < j)

    def test_filter(self):
        self.list2.append(3)
        self.list2.append(100)
        self.list2.append(2)
        self.list2.append(4)
        result = Sort.filter(self.list2, lambda i: i > 3)
        self.assertEqual(len(result), 2)