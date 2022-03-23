class UndoException(Exception):
    def __init__(self, msg):
        self._msg = msg

    def __str__(self):
        return str(self._msg)


class UndoRedoAction:
    def __init__(self, actor, obj):
        self._actor = actor
        self._obj = obj

    def execute_action(self):
        pass

    def execute_rev_action(self):
        pass


'''
Book redo/undo
'''


class UndoRedoActionAdd(UndoRedoAction):
    def __init__(self, actor, obj):
        UndoRedoAction.__init__(self, actor, obj)

    def execute_action(self):
        self._actor.delete(self._obj.book_id)

    def execute_rev_action(self):
        self._actor.add(self._obj)


class UndoRedoActionDelete(UndoRedoAction):
    def __init__(self, actor, obj):
        UndoRedoAction.__init__(self, actor, obj)

    def execute_action(self):
        self._actor.add(self._obj)

    def execute_rev_action(self):
        self._actor.delete(self._obj.book_id)


class UndoRedoActionUpdate(UndoRedoAction):
    def __init__(self, actor, obj, new_title, new_author, old_title, old_author):
        UndoRedoAction.__init__(self, actor, obj)
        self._old_author = old_author
        self._old_title = old_title
        self._new_title = new_title
        self._new_author = new_author

    def execute_action(self):
        self._actor.update(self._obj.book_id, self._old_title, self._old_author)

    def execute_rev_action(self):
        self._actor.update(self._obj.book_id, self._new_title, self._new_author)


'''
Client undo/redo
'''


class UndoRedoActionAddC(UndoRedoAction):
    def __init__(self, actor, obj):
        UndoRedoAction.__init__(self, actor, obj)

    def execute_action(self):
        self._actor.delete(self._obj.client_id)

    def execute_rev_action(self):
        self._actor.add(self._obj)


class UndoRedoActionDeleteC(UndoRedoAction):
    def __init__(self, actor, obj):
        UndoRedoAction.__init__(self, actor, obj)

    def execute_action(self):
        self._actor.add(self._obj)

    def execute_rev_action(self):
        self._actor.delete(self._obj.client_id)


class UndoRedoActionUpdateC(UndoRedoAction):
    def __init__(self, actor, obj, new_name, old_name):
        UndoRedoAction.__init__(self, actor, obj)
        self._old_name = old_name
        self._new_name = new_name

    def execute_action(self):
        self._actor.update(self._obj.client_id, self._old_name)

    def execute_rev_action(self):
        self._actor.update(self._obj.client_id, self._new_name)


'''
Rental undo/redo
'''


class UndoRedoActionAddR(UndoRedoAction):
    def __init__(self, actor, obj):
        UndoRedoAction.__init__(self, actor, obj)

    def execute_action(self):
        self._actor.delete(self._obj.rental_id)

    def execute_rev_action(self):
        self._actor.add(self._obj)


class UndoRedoActionDeleteR(UndoRedoAction):
    def __init__(self, actor, obj):
        UndoRedoAction.__init__(self, actor, obj)

    def execute_action(self):
        self._actor.add(self._obj)

    def execute_rev_action(self):
        self._actor.delete(self._obj.rental_id)


class UndoRedoActionDeleteClientRental(UndoRedoAction):
    def __init__(self, actor, obj, actor_object, rentals):
        self._rentals = rentals
        self._actor_object = actor_object
        UndoRedoAction.__init__(self, actor, obj)

    def execute_action(self):
        self._actor_object.add(self._obj)
        for i in range(0, len(self._rentals)):
            if self._rentals[i].client == self._obj:
                self._actor.add(self._rentals[i])

    def execute_rev_action(self):
        self._actor_object.delete(self._obj.client_id)
        for i in range(0, len(self._rentals)):
            if self._rentals[i].client == self._obj:
                self._actor.delete(self._rentals[i].rental_id)


class UndoRedoActionDeleteBookRental(UndoRedoAction):
    def __init__(self, actor, obj, actor_obj, rentals):
        UndoRedoAction.__init__(self, actor, obj)
        self._rentals = rentals
        self._actor_obj = actor_obj

    def execute_action(self):
        self._actor_obj.add(self._obj)
        for i in range(0, len(self._rentals)):
            if self._rentals[i].get_book == self._obj:
                self._actor.add(self._rentals[i])

    def execute_rev_action(self):
        self._actor_obj.delete(self._obj.book_id)
        for i in range(0, len(self._rentals)):
            if self._rentals[i].get_book == self._obj:
                self._actor.delete(self._rentals[i].rental_id)


class UndoRedoActionUpdateR(UndoRedoAction):
    def __init__(self, actor, obj, new_date, old_date):
        UndoRedoAction.__init__(self, actor, obj)
        self._old_date = old_date
        self._new_date = new_date

    def execute_action(self):
        self._actor.update_return(self._obj.rental_id, self._old_date)

    def execute_rev_action(self):
        self._actor.update_return(self._obj.rental_id, self._new_date)
