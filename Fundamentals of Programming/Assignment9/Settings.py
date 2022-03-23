class Settings:
    def __init__(self, file_name='settings.properties'):
        self._file_name = file_name

    def _load(self):
        f = open(self._file_name, 'rt')
        lines = f.readlines()
        f.close()
        dictionary = {}
        for line in lines:
            line = line.split('=')
            dictionary[line[0].strip()] = line[1].strip().strip('\n')
        return dictionary

    @property
    def type_of_repo(self):
        return self._load()['repository'].strip().strip('\n')

    @property
    def book_repo(self):
        return self._load()['books'].strip().strip('\n')

    @property
    def clients_repo(self):
        return self._load()['clients'].strip().strip('\n')

    @property
    def rentals_repo(self):
        return self._load()['rentals'].strip().strip('\n')

