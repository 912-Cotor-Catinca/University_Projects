#define  _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <assert.h>

float Power(int n, float x);
void LongestSequence(int n, int a[], int* lmax, int* pmax);
void Menu();
int Input();
void ReadArray(int a[], int* n);
void Tests();
void ReadNumbers(float* x, int* n);

int main()
{
	Menu();

	int array[101];
	int number;
	bool done = false;
	while (!done)
	{
		printf("\nEnter your choice: ");
		int choice = Input();
		if (choice == 1)
		{
			ReadArray(array, &number);

			//for (int i = 0; i < number; ++i)
			//	printf("%d ", array[i]);
		}
		else if (choice == 2)
		{
			float x;
			int n;
			ReadNumbers(&x, &n);
			printf("result is: %.2f", Power(n, x));
		}
		else if (choice == 3)
		{
			int lmax, pmax;
			LongestSequence(number, array, &lmax, &pmax);
			if (lmax == 0)
				printf("There is no such subsequence");
			else
			{
				printf("The longest subsequence is: ");
				for (int i = pmax + 1 - lmax; i <= pmax; ++i) // the first index is pmax - lmax + 1
					printf("%d ", array[i]);
			}

		}
		else if (choice == 4)
		{
			printf("Byee");
			done = true;
		}
		else
			printf("Wrong Input");
	}

	Tests();

	return 0;
}

void ReadNumbers(float* x, int* n)
{
	printf("x = ");
	scanf("%f", x);
	printf("n = ");
	scanf("%d", n);

}

float Power(int n, float x)
{
	/*
	This function determine the x^n where x is a real number, n-natural number
	This solution divides the problem into subproblems of size n/2 and call the subproblems recursively.
	x^n = x ^(n/2) * x^(n/2), for n even
	x^n = x ^(n/2) * x^(n/2)*x, for n odd
	O(n) - time complexity
	*/

	if (n == 0)
		return 1;
	else if (n % 2 == 0)
		return Power(n / 2, x) * Power(n / 2, x);
	else
		return Power(n / 2, x) * Power(n / 2, x) * x;
}

void LongestSequence(int n, int a[], int* lmax, int* pmax)
{
	/*
	The longest alternating subsequnce. 
	This algorithm increase the current lenght if a[i]*a[i+1] < 0(have opposite signs), otherwise it is set to 1.
	Then current lenght is compared to the maximum lenght, and if it is greater - the maximum lenght and the last index of the 
	subsequence are updated.
	*/
	int l = 0, l1 = 0;
	l = 1;
	for (int i = 1; i < n; ++i)
	{
		if (a[i] * a[i - 1] < 0 || (a[i] * a[i - 1] == 0 && a[i] == 0 && a[i-1] < 0) || (a[i] * a[i - 1] == 0 && a[i] < 0 && a[i - 1] == 0)) // have contrary signs
			l++;
		else
			l = 1;

		if (l >= l1)
		{
			l1 = l;
			*pmax = i; // the last index of the longest subsequence
		}

	}
	*lmax = l1;

}

void Menu()
{
	printf("Press 1 for reading a vector of numbers from the console.\n");
	printf("Press 2 for resolving first required functionality.\n");
	printf("Press 3 for resolving second required functionality.\n");
	printf("Press 4 for exiting the project.\n");

}

int Input()
{
	int n;
	scanf("%d", &n);
	return n;
}

void ReadArray(int a[], int* n)
{
	int number;
	printf("Number of elements: ");
	scanf("%d", &number);
	*n = number;
	printf("Elements: ");
	for (int i = 0; i < number; ++i)
	{
		scanf("%d", &a[i]);
	}

}

void Tests()
{
	// Testing the first requirement
	int x = 5, n = 3;
	assert(Power(n, x) == 125);
	assert(Power(0, 1) == 1);
	assert(Power(1, 2) == 2);
	assert(Power(2, -1) == 1);
	assert(Power(2, 0.25) == 0.062500);

	// Testing second requierement
	int a1[2] = { 1, 1 };
	int lmax, pmax;
	LongestSequence(2, a1, &lmax, &pmax);
	assert(pmax == 1);
	assert(lmax == 1);

	int a2[5] = { 1, -2, 1, -1, -4 };
	LongestSequence(5, a2, &lmax, &pmax);
	assert(pmax == 3);
	assert(lmax == 4);
	assert(pmax - lmax + 1 == 0); //first index

	int a3[10] = { 1, -2, 1, -1, -4, 1, -1, 1, 2, -1 };
	LongestSequence(10, a3, &lmax, &pmax);
	assert(pmax == 7);
	assert(lmax == 4);
	assert(pmax - lmax + 1 == 4); //first index
}
