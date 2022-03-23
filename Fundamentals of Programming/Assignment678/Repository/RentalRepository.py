from datetime import date

from Domain.Rental import RentalException


class RentalRepoException(RentalException):
    def __init__(self, msg):
        super(RentalRepoException, self).__init__(msg)


class RentalRepo:
    def __init__(self):
        self._rental_list = []

    def __len__(self):
        return len(self._rental_list)

    def add(self, rental):
        """
        Adds a new instance of a Rental object to the list
        :param rental: the instance of the Rental object to be added
        :return:
        """
        if self.find(rental.rental_id) != -1:
            raise RentalRepoException('The given id does not exist')
        self._rental_list.append(rental)

    def update(self, id, rented_date, returned_date):
        done = False
        for rental in self._rental_list:
            if self.find(rental.rental_id) != -1 and rental.rental_id == id:
                rental.rented_date = rented_date
                rental.returned_date = returned_date
                done = True
        if not done:
            raise RentalRepoException('The given id cannot be found!')

    def update_return(self, id, returned_date1):
        """
        Updates the returned date of a returned book with the given id
        :param returned_date1:
        :param id: the id of the given book to be returned
        :return:
        """
        done = False
        # returned_date1 = date.today()
        for rental in self._rental_list:
            if self.find(rental.rental_id) != -1 and rental.rental_id == id:
                if rental.returned_date == date(1, 1, 1):
                    rental.returned_date = returned_date1
                    return rental
                done = True
        if not done:
            raise RentalRepoException('The book cannot be returned!')

    def delete(self, id):
        """
        Deletes a rental from the list
        :param id: an instance of a Rental object
        :return:
        """
        index = self.find(id)
        if index == -1:
            raise RentalRepoException('The given id does not exist')
        del self._rental_list[index]

    def find(self, id):
        """
        Search for a rental instance in the list with a matching given [id] attribute
        :param id: the rental's id(unique positive integer)
        :return: the index if the rental exist or -1 otherwise
        """
        for i in range(len(self._rental_list)):
            if self._rental_list[i].rental_id == id:
                return i
        return -1

    def get_all(self):
        return self._rental_list[:]

    def __getitem__(self, item):
        return self._rental_list[item]
