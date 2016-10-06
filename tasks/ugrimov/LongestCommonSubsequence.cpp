#include <iostream>

int getMaxCommonLength(int** matrix, std::string& word1, std::string& word2, int size1, int size2);
void resetMatrix(int** matrix, int size1, int size2);
std::string getCommonSequence(int** matrix, std::string& word1, std::string& word2);
void printMatrix(int** matrix, int sizeI, int sizeJ);
int max(int a, int b);
int max(int a, int b, int c);
int max(int a, int b, int c, int d);

int main() {

	std::string word1 = "worfld";
	std::string word2 = "fyord";

	int size1 = (int) (word1.size() + 1), size2 = (int) (word2.size() + 1);

	int** matrix = new int*[size1];
	for (int i = 0; i < size1; i++) {
		matrix[i]= new int[size2];
	}
	resetMatrix(matrix, size1, size2);


	std::cout<<getMaxCommonLength(matrix, word1, word2, size1, size2)<<std::endl;
	printMatrix(matrix, size1, size2);

	std::string reversed = getCommonSequence(matrix, word1, word2);
	std::reverse(reversed.begin(), reversed.end());

	std::cout<<reversed;
	return 0;
}

int getMaxCommonLength(int** matrix, std::string& word1, std::string& word2, int size1, int size2) {
	int seq = 0;
	for (int i = 1; i < size1; i++) {
		for (int j = 1; j < size2; j++) {

			if (word1[i - 1] == word2[j - 1]) {
				matrix[i][j] = matrix[i - 1][j - 1] + 1;
			} else {
				matrix[i][j] = max(matrix[i][j-1], matrix[i-1][j], matrix[i-1][j-1]);
			}

			if(matrix[i][j] > seq){
				seq = matrix[i][j];
			}
		}
	}
	return seq;
}

std::string getCommonSequence(int** matrix, std::string& word1, std::string& word2) {
	std::string output = "";
	int size1 = (int) (word1.size() + 1), size2 = (int) (word2.size() + 1);
	int i = size1 - 1, j = size2 - 1;
	while (i != 0 && j != 0) {

		int x = matrix[i][j], a = matrix[i - 1][j - 1],
		b = matrix[i - 1][j], c = matrix[i][j - 1];
		int maxAll = max(a, b, c, x);

		if (maxAll == x && x != a && x != b) {
			output.push_back(word1[i - 1]);
			i--;
			j--;
		} else if (maxAll == a) {
			j--;
		} else if (maxAll == b) {
			i--;
		}
	}
	return output;
}

void resetMatrix(int** matrix, int size1, int size2) {
	for (int i = 0; i < size1; i++) {
		for (int j = 0; j < size2; j++) {
			matrix[i][j] = 0;
		}
	}
}

void printMatrix(int** matrix, int sizeI, int sizeJ) {
	for (int i = 0; i < sizeI; i++) {
		for (int j = 0; j < sizeJ; j++) {
			std::cout<<matrix[i][j]<<" ";
		}
		std::cout<<std::endl;
	}
}

int max(int a, int b) {
	return a >= b ? a : b;
}

int max(int a, int b, int c) {
	return max(max(a, b), c);
}

int max(int a, int b, int c, int d) {
	return max(max(a, b, c), d);
}












