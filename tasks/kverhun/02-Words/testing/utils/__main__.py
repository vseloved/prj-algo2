import sys
import random

def generate_test(dictionary, words_number):
    words = random.sample(dictionary, words_number)
    test_string = ''.join(words)
    return words, test_string


def main(argv):
    print(argv)

    dictionary_file_name = argv[0]
    words_sample_size = int(argv[1])
    test_string_filename = argv[2]
    answer_string_filename = argv[3]

    with open(dictionary_file_name) as f:
        content = f.read()

    dictionary = content.split('\n')
    words, test_str = generate_test(dictionary, words_sample_size)

    with open(test_string_filename, 'w+') as f:
        f.write(test_str)

    answer_string = ' '.join(words)
    with open(answer_string_filename, 'w+') as f:
        f.write(answer_string)


if __name__ == '__main__':
    main(sys.argv[1:])