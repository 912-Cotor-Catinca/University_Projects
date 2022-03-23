#pragma once
#include "RealEstate.h"
#include "Repository.h"

typedef struct {
    // the matrix of repositories
    Repo** matrix_undo; // it contains the repository's states after all operations performed; each line contains a repo list 
    int index;
    int capacity;
    int available_undos;
}Undo;

/// <summary>
/// Undo Constructor
/// </summary>
/// <returns></returns>
Undo* createUndo(); 
/// <summary>
/// Undos the last operation.
/// </summary>
/// <param name="undo"></param>
/// <param name="r"></param>
/// <returns>Returns 1 if the undo can be done(index >= 0), -1 otherwise</returns>
int undo(Undo* undo, Repo* r);
/// <summary>
/// Redos the last operation.
/// </summary>
/// <param name="redo"></param>
/// <param name="r"></param>
/// <returns>Returns 1 if the redo can be done(index >= 0), -1 otherwise</returns>
int redo(Undo* redo, Repo* r);

///Gets the current index of the matrix of repositories.
int GetIndex(Undo* undo);
///Gets the capacity of the matrix of repositories.
int GetCap(Undo* undo);
///Gets the available undos or redos of the matrix of repositories.
int GetLen(Undo* undo);

///Adds an operation to the matrix of repositories.
void MAtrixAdd(Undo* undo, Repo* r);

///Resize the capacity of the matrix
void resize_mat(Undo* undo);

///The undo Destructor
void destroy_undo(Undo* undo);