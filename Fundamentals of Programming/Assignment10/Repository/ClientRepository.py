from Domain.Client import ClientException
from Costumize.Iterator import OrderIterator


class ClientRepoException(ClientException):
    def __init__(self, msg):
        super(ClientRepoException, self).__init__(msg)


class ClientRepo:
    def __init__(self):
        self._client_list = OrderIterator()

    @property
    def client_list(self):
        return self._client_list

    def __len__(self):
        return len(self._client_list)

    def add(self, client):
        """
        Appends a new client in the client list
        :param client: an instance of the Client object
        :return:
        """
        if client in self._client_list:
            raise ClientRepoException('The given id already exist')
        self._client_list.append(client)

    def delete(self, id):
        """
        Delete a client from the list
        :param id: an instance of a Client Object
        :return:
        """
        for c in self._client_list:
            if c.client_id == id:
                client = c
                self._client_list.remove(client)
                return client
        raise ClientRepoException('The given id does not exist')

    def update(self, id, name):
        """
        Updates the name attribute of the given [id]
        :param id: the instance of a Client object from the list that is to be updated
        :param name: the new name of the client(string)
        :return:
        """
        done = False
        for client in self._client_list:
            if self.find(client.client_id) != -1 and client.client_id == id:
                client.name = name
                return client
                done = True
        if not done:
            raise ClientRepoException('The given id cannot be found!')

    def find(self, id):
        """
        Search for a client instance in the list with a matching given [id] attribute
        :param id: the client's id(unique positive integer)
        :return: the index or -1 if there is no client with the given id
        """
        for i in range(len(self._client_list)):
            if self._client_list[i].client_id == id:
                return i
        return -1

    def find_client(self, id):
        """
        Searches for a book in the list with a matching given [id] attribute
        :param id: book' s id(unique string)
        :return: the book instance or raise Exception if the book is already rented
        """
        for c in self._client_list:
            if c.client_id == id:
                return c

    def get_all(self):
        return self._client_list[:]

    def __getitem__(self, item):
        return self._client_list[item]
