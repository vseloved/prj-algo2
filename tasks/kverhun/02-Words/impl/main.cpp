#include <iostream>
#include <fstream>
#include <set>
#include <string>
#include <vector>

namespace
{
  std::set<std::string> _ReadDictionaryFromFile(const std::string& file_name)
  {
    std::set<std::string> dictionary;

    std::ifstream file(file_name);
    std::string line;
    while (std::getline(file, line))
    {
      dictionary.insert(line);
    }

    return dictionary;
  }

  std::string _ReadStringFromFile(const std::string& i_file_name)
    {
    std::ifstream file(i_file_name);
    std::string string_read;
    file >> string_read;
    return string_read;
    }

  std::vector<size_t> _Split(const std::string& i_input, const std::set<std::string>& i_dictionary)
  {
    std::vector<size_t> lengths;

    size_t current_offset = 0;
    size_t current_count = 1;
   
    while (current_count + current_offset <= i_input.size())
    {
      auto str_to_check = i_input.substr(current_offset, current_count);
      if (i_dictionary.count(str_to_check) > 0)
      {
        lengths.push_back(current_count);
        current_offset = current_offset + current_count;
        current_count = 1;
      }
      else
      {
        ++current_count;
      }
    }

    return lengths;
  }

  std::vector<std::string> _ConvertToWords(const std::string& i_string, const std::vector<size_t>& i_lenghts)
  {
    std::vector<std::string> words;

    size_t current_offset = 0;
    for (auto lng : i_lenghts)
    {
      words.push_back(i_string.substr(current_offset, lng));
      current_offset += lng;
    }

    return words;
  }

}


int main(int i_argc, char** i_argv)
{
  if (i_argc < 3)
    return 1;

  system("pause");

  std::string dictionary_file_name(i_argv[1]);
  auto dictionary = _ReadDictionaryFromFile(dictionary_file_name);

  std::string str_to_split_file(i_argv[2]);
  std::string str_to_split = _ReadStringFromFile(str_to_split_file);
  auto lengths = _Split(str_to_split, dictionary);

  auto words = _ConvertToWords(str_to_split, lengths);

  for (const auto& word : words)
    std::cout << word << ' ';

  return 0;
}