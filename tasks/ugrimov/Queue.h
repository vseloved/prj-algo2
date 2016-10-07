//
// Created by Artem on 06.10.16.
//

#ifndef PROJECTOR_QUEUE_H
#define PROJECTOR_QUEUE_H

#include <vector>
#include <iostream>

using namespace std;

template <class T>
class Queue {

	vector<T> elements;

	bool hasParent(int i) {
		int parentIndex = i / 2;
		return parentIndex >= 0 && parentIndex < elements.size() - 1;
	}

	T hparent(int i) {
		return elements[parentIndex(i)];
	}

	int parentIndex(int i) {
		return i / 2;
	}

	bool hasLeft(int i) {
		int index = leftIndex(i);
		return index < elements.size();
	}

	bool hasRight(int i) {
		int index = rightIndex(i);
		return index < elements.size();
	}

	int leftIndex(int i) {
		return i * 2 + 1;
	}

	int rightIndex(int i) {
		return i * 2 + 2;
	}

	T hleft(int i) {
		return elements[leftIndex(i)];
	}

	T hright(int i) {
		return elements[rightIndex(i)];
	}

	void heapUp(int i) {
		//сравниваем с парентом и заменяем, если parent больше
		if (hasParent(i) && elements[i] < hparent(i)) {
			while (hasParent(i) && elements[i] < hparent(i)) {
				std::swap(elements[i], elements[parentIndex(i)]);//heap up
				i = parentIndex(i);
			}
		}
	}

	void heapDown() {
		//сравниваем с left && right
//		vector<T> newQueue(elements);
//		elements.clear();
//		for (int i = 0; i < newQueue.size(); i++) {
//			add(newQueue[i]);
//		}

		for (int i = 0; i < elements.size(); i++) {
			if (hasLeft(i) && elements[i] > hleft(i)) {
				std::swap(elements[i], elements[leftIndex(i)]);
				i = leftIndex(i);
			} else if (hasRight(i) && elements[i] > hleft(i)) {
				std::swap(elements[i], elements[rightIndex(i)]);
				i = rightIndex(i);
			}
		}
	}

public:
	//extract min - get first element
	T extract() {
		T result = elements.front();
		elements.erase(elements.begin());
		heapDown();
		return result;
	}

	void add(T element) {
		elements.push_back(element);
		heapUp(elements.size() - 1);
	}

	void print() {
		for (int i = 0; i < elements.size(); i++) {
			cout<<elements[i]<<" ";
		}
		cout<<endl;
	}
};


#endif //PROJECTOR_QUEUE_H
