#include <iostream>
#include <fstream>
#include <set>
#include <string>

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

}


int main(int i_argc, char** i_argv)
{
  if (i_argc < 2)
    return 1;

  std::string dictionary_file_name(i_argv[1]);
  auto dictionary = _ReadDictionaryFromFile(dictionary_file_name);

  for (const auto& str : dictionary)
    std::cout << str << std::endl;

  return 0;
}