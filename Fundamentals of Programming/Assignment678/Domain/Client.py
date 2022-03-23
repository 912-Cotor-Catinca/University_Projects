"""
Implement the client class
"""


class ClientException(Exception):
    def __init__(self, msg):
        self._msg = msg

    def __str__(self):
        return self._msg


class ClientValidationError(ClientException):
    def __init__(self, error_list):
        self._errors = error_list

    def __str__(self):
        result = ''
        for er in self._errors:
            result += er
            result += '\n'
        return result


class ClientValidator:
    @staticmethod
    def is_valid_name(name):
        """
        Implement a person's name
        :param name:
        :return:
        """
        tokens = name.split(' ', 1)
        return len(tokens) > 1

    def validate(self, client):
        errors = []
        if not self.is_valid_name(client.name):
            errors.append('Invalid name')
        if client.client_id < 1:
            errors.append('Invalid client id')
        if len(errors) > 0:
            raise ClientValidationError(errors)


class Client:
    def __init__(self, client_id=0, name=''):
        if not isinstance(client_id, int):
            raise ClientException('Invalid value for client id')
        if not isinstance(name, str):
            raise ClientException('Invalid value for name')

        self._client_id = client_id
        self._name = name

    def id(self):
        return self._client_id

    def name_client(self):
        return self._name

    @property
    def name(self):
        return self._name

    @property
    def client_id(self):
        return self._client_id

    @name.setter
    def name(self, other):
        self._name = other

    def __str__(self):
        return str(self._client_id) + ' ' + self._name





