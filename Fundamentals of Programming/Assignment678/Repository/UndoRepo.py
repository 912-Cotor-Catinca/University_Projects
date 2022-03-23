from Domain.Undo import UndoException


class UndoRepoException(UndoException):
    def __init__(self, msg):
        super(UndoRepoException, self).__init__(msg)


class RepoUndo:
    def __init__(self):
        self._list = []
        self._index = -1

    def push(self, undoAction):
        self._list = self._list[:self._index + 1]
        self._list.append(undoAction)
        self._index += 1

    def pop(self):
        if self._index == -1:
            raise ValueError('no undo')
        self._index -= 1

    def peek(self):
        if self._index == len(self._list):
            raise UndoRepoException('no redo')
        return self._list[self._index]

    def pull(self):
        if self._index == len(self._list) or self._index == -1:
            raise UndoRepoException('no redo')
        self._index += 1

    def size(self):
        return self._index+1

    def full(self):
        return self._index == len(self._list) - 1
