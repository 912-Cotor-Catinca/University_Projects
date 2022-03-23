from Repository.UndoRepo import UndoRepoException


class UndoError():
    def __init__(self, msg):
        self._msg = msg

    def __str__(self):
        return self._msg


class UndoService:
    def __init__(self, repoUndo):
        self._undoRepo = repoUndo

    def undo(self):
        if self._undoRepo.size() == 0:
            raise UndoRepoException('no undo')
        undoAction = self._undoRepo.peek()
        self._undoRepo.pop()
        undoAction.execute_action()

    def redo(self):
        if self._undoRepo.size() == 0:
            raise UndoRepoException('no redo')
        self._undoRepo.pull()
        undoAction = self._undoRepo.peek()
        undoAction.execute_rev_action()
