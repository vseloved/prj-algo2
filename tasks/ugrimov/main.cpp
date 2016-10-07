#include <iostream>
#include "CommonSubsequence.h"
#include "Queue.h"

using namespace std;

int main() {

	//CommonSubsequence subsequence("worfld", "fyord");
	Queue<int> queue;

	queue.add(10);
	queue.add(2);
	queue.add(4);
	queue.add(20);
	queue.add(1);
	queue.add(3);

	queue.print();

	cout<<queue.extract()<<endl;
	cout<<queue.extract()<<endl;
	cout<<queue.extract()<<endl;
	cout<<queue.extract()<<endl;

	queue.print();

	return 0;
}


















