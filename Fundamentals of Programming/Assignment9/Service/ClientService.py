from Domain.Client import Client
from Domain.Undo import UndoRedoActionAddC, UndoRedoActionDeleteC,  UndoRedoActionUpdateC


class ClientService:
    def __init__(self, client_repo, client_valid):
        self._client_repo = client_repo
        self._client_valid = client_valid

    def add_client(self, client_id, name):
        """
        Instantiates a Client object with a given [client_book], a[name] and adds it to the repo
        :param client_id: The Client's id
        :param name: Client's title
        :return:
        """
        client = Client(client_id, name)
        if self._client_repo.find(client.client_id) != -1:
            raise ValueError('Client already exists!')
        else:
            self._client_valid.validate(client)
            self._client_repo.add(client)
        return client

    def find(self, id):
        """
        Find by id a book
        :param id: Book's id
        :return: the index if there exist or -1 otherwise
        """
        for i in range(len(self._client_repo)):
            if self._client_repo[i].client_id == id:
                return i
        return -1

    def update_client(self, id, name):
        """
       Updates the name of an instance of a Client object with a given id
       :param id: Client's id
       :param name: Client's name
       :return:
       """
        for c in self._client_repo.get_all():
            if c.client_id == id:
                old_name = c.name
        new_client = self._client_repo.update(id, name)
        return new_client, old_name

    def display(self):
        """
        Return a shallow copy of the list
        :return: the list of clients
        """
        return self._client_repo.get_all()

    def delete_client(self, id):
        """
        Deletes a Client object with the given [id]
        :param id: the client's id to be deleted
        :return:
        """
        client = self._client_repo.delete(id)
        return client

    def search_client_name(self, name):
        result = []
        for c in self._client_repo.get_all():
            if name.lower() in c.name.lower():
                result.append(c)
        return result

    def search_id(self, client_id):
        result = []
        id = str(client_id)
        for c in self._client_repo.get_all():
            client = str(c.client_id)
            if id.lower() in client.lower():
                result.append(c)
        return result


class ClientServiceUndo(ClientService):
    def __init__(self, client_repo, client_valid, undoRepo):
        ClientService.__init__(self, client_repo, client_valid)
        self._undoRepo = undoRepo

    def add_client(self, client_id, name):
        client = ClientService.add_client(self, client_id, name)
        actor = self._client_repo
        obj = client
        undoAction = UndoRedoActionAddC(actor, obj)
        self._undoRepo.push(undoAction)

    def delete_client(self, id):
        client = ClientService.delete_client(self, id)
        actor = self._client_repo
        obj = client
        undoAction = UndoRedoActionDeleteC(actor, obj)
        self._undoRepo.push(undoAction)

    def update_client(self, id, name):
        client, old_name = ClientService.update_client(self, id, name)
        actor = self._client_repo
        obj = client
        undoAction = UndoRedoActionUpdateC(actor, obj, name, old_name)
        self._undoRepo.push(undoAction)
