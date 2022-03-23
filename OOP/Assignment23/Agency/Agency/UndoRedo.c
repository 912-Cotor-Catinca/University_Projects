#include "UndoRedo.h"
#include <stdlib.h>

Undo* createUndo()
{
	Undo* undo = (Undo*)malloc(sizeof(Undo));

	if (undo == NULL)
	{
		return NULL;
	}

	undo->available_undos = 0;
	undo->capacity = 100;
	undo->index = -1;

	undo->matrix_undo = (Repo**)malloc(sizeof(Repo*) * undo->capacity);
	for (int i = 0; i < undo->capacity; ++i)
		undo->matrix_undo[i] = create_repo();

	if (undo->matrix_undo == NULL)
		return NULL;

	return undo;
}

int undo(Undo* undo, Repo* r)
{
	if (GetIndex(undo) <= 0)
		return -1;
	undo->index -= 1;
	DeepCopyArray(r->estates, undo->matrix_undo[GetIndex(undo)]->estates); // copies in the repository the old repository before the previous operation
	r->estates->lenght = len(undo->matrix_undo[GetIndex(undo)]->estates); // updates the current lenght
	return 1;
}

int redo(Undo* redo, Repo* r)
{
	if (GetIndex(redo) == GetLen(redo) - 1)
		return -1;

	redo->index += 1;
	DeepCopyArray(r->estates, redo->matrix_undo[GetIndex(redo)]->estates); // copies in the repository the new repository after the previous operation
	r->estates->lenght = len(redo->matrix_undo[GetIndex(redo)]->estates); //updates the current lenght
	return 1;
}

void resize_mat(Undo* undo)
{
	undo->capacity = undo->capacity * 2;
	Repo** tmp = (Repo**)malloc(sizeof(Repo*) * undo->capacity);
	for (int i = 0; i < GetCap(undo); ++i)
		tmp[i] = create_repo();

	for (int i = 0; i < GetLen(undo); ++i)
	{
		tmp[i] =  undo->matrix_undo[i];
		tmp[i]->estates->lenght = undo->matrix_undo[i]->estates->lenght;
		tmp[i]->estates->capacity = undo->matrix_undo[i]->estates->capacity;
	}

	for (int i = 0; i < undo->capacity; ++i)
		destroy_repo(undo->matrix_undo[i]);
	free(undo->matrix_undo);
	undo->matrix_undo = tmp;
}

void MAtrixAdd(Undo* undo, Repo* r)
{
	if (undo->capacity == undo->available_undos)
		resize_mat(undo);
	while (undo->index + 1 < undo->available_undos)
		undo->available_undos -= 1;
	copy_repo(undo->matrix_undo[GetLen(undo)], r); // copy in the matrix on the last position the current repository after any operation(add, delete, update)
	undo->matrix_undo[undo->available_undos]->estates->lenght = r->estates->lenght; //updates the current lenght
	undo->matrix_undo[undo->available_undos]->estates->capacity = r->estates->capacity; //updates the capacity
	undo->index += 1;
	undo->available_undos += 1;
}

int GetIndex(Undo* undo)
{
	return undo->index;
}

int GetCap(Undo* undo)
{
	return undo->capacity;
}

int GetLen(Undo* undo)
{
	return undo->available_undos;
}

void destroy_undo(Undo* undo)
{
	for (int i = 0; i < GetCap(undo); ++i)
	{
		destroy(undo->matrix_undo[i]->estates);
		free(undo->matrix_undo[i]);
		undo->matrix_undo[i] = NULL;
	}
		
	free(undo->matrix_undo);
	undo->matrix_undo = NULL;
	free(undo);

}
