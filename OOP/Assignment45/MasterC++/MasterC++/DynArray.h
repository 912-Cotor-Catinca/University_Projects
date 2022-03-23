#pragma once
#include "MasterC.h"
#include <string>


template<typename T>
class DynArray {
private:
	T* elems;
	int capacity;
	int length;

public:
	/// <summary>
	/// The Constructor
	/// </summary>
	/// <param name="capacity"></param>
	DynArray(int capacity);
	///The desctructor
	~DynArray();
	/// <summary>
	/// Get the current lenght
	/// </summary>
	/// <returns></returns>
	int getLength();
	/// <summary>
	/// Get the capacity
	/// </summary>
	/// <returns></returns>
	int getCapacity();
	/// <summary>
	/// Get the elements of the array
	/// </summary>
	/// <returns></returns>
	T* getElems();
	/// <summary>
	/// Adds a generic element to the dynamic array.
	/// </summary>
	/// <param name="e">The tutorial to be added</param>
	void add(T e);
	/// <summary>
	/// Removes one occurence of an element from a dynamic array.
	/// </summary>
	/// <param name="e">The tutorial to be deleted</param>
	/// <returns></returns>
	bool remove(T e);
	/// <summary>
	/// Updates an existing element in the dynamic array.
	/// </summary>
	/// <param name="e">The element to be updated</param>
	/// <param name="title">The new title</param>
	/// <param name="pres">The new presenter</param>
	/// <param name="duration">The new duration</param>
	/// <param name="likes">The new number of likes</param>
	void update(T e, std::string title, std::string pres, std::string duration, int likes);
	/// <summary>
	/// Resize the dynamic array
	/// </summary>
	void resize();

	T& operator[](int pos);

};

template<typename T>
DynArray<T>::DynArray(int capacity)
{
	this->capacity = capacity;
	this->length = 0;
	this->elems = new T[this->capacity];
}

template<typename T>
DynArray<T>::~DynArray()
{
	delete[] this->elems;
}

template<typename T>
void DynArray<T>::resize()
{
	int new_cap = this->capacity * 2;
	T* new_elems = new T[new_cap];

	this->capacity = new_cap;
	for (int i = 0; i < this->length; ++i)
		new_elems[i] = this->elems[i];
	delete[] this->elems;
	this->elems = new_elems;
}

template<typename T>
void DynArray<T>::add(T e)
{
	if (this->capacity == this->length)
		resize();

	this->elems[this->length] = e;
	this->length++;
}


template<typename T>
bool DynArray<T>::remove(T e)
{
	int pos = 0;
	bool found = false;
	while (pos < this->length && found == false)
	{
		if (this->elems[pos].getLink().compare(e.getLink()) == 0)
			found = true;
		else
			pos++;
	}

	if (found == true)
	{
		this->elems[pos] = this->elems[this->length - 1];
		this->length--;
		return true;
	}
	return false;
}

template<typename T>
void DynArray<T>::update(T e, std::string title, std::string pres, std::string duration, int likes)
{
	int pos = 0;
	bool found = false;
	while (pos < this->length && found == false)
	{
		if (this->elems[pos].getLink().compare(e.getLink()) == 0)
			found = true;
		else
			pos++;
	}

	if (found == false)
		return;
	else
	{
		this->elems[pos].setTitle(title);

		this->elems[pos].setLikes(likes);
		this->elems[pos].setPres(pres);
		this->elems[pos].setDuration(duration);
	}
}

template<typename T>
int DynArray<T>::getLength()
{
	return this->length;
}

template<typename T>
int DynArray<T>::getCapacity()
{
	return this->capacity;
}

template<typename T>
T* DynArray<T>::getElems()
{
	return this->elems;
}

template<typename T>
T& DynArray<T>::operator[](int pos)
{
	return this->elems[pos];
}