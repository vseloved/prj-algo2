#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include <sstream>
#include <functional>
#include <limits>

namespace
{
  std::string _ReadStringFromFile(const std::string& i_filename)
  {
    std::ifstream file(i_filename);
    std::stringstream buffer;
    buffer << file.rdbuf();

    return buffer.str();
  }

  std::vector<std::string> _SplitStringByDelimiter(std::string i_string_to_split, const std::string& i_delimiter)
  {
    std::vector<std::string> result;
    size_t position = 0;
    while ((position = i_string_to_split.find(i_delimiter)) != std::string::npos)
    {
      result.push_back(i_string_to_split.substr(0, position));
      i_string_to_split.erase(0, position + i_delimiter.size());
    }
    result.push_back(i_string_to_split.substr(0, position));
    return result;
  }

  double _CalculateStringBadness(size_t i_str_lng, size_t i_max_string_lng)
  {
    double diff = static_cast<double>(i_max_string_lng - i_str_lng);
    return diff*diff*diff;
  }


  std::pair<double, std::vector<size_t>> _GetPartition(const std::vector<std::string>& i_words, size_t i_max_line_lng)
  {
    std::vector<std::vector<double>> badness_matrix(i_words.size());
    for (auto& badness_row : badness_matrix)
      badness_row.resize(i_words.size());

    std::vector<double> dp_vals(i_words.size() + 1);
    std::vector<size_t> argmins(i_words.size() + 1);

    dp_vals[i_words.size()] = 0;

    std::function<void(size_t)> DP = [&](size_t i_word_from)
    {
      if (i_word_from >= i_words.size())
        return;
      
      size_t last_index_to_check = i_words.size();
      
      size_t current_lng = 0;
      
      double best_cost = std::numeric_limits<double>::max();
      size_t best_lng = 1;
      for (size_t lng = 1; i_word_from + lng <= last_index_to_check && current_lng + i_words[i_word_from + lng - 1].size() + (lng > 1 ? 1 : 0) <= i_max_line_lng; ++lng)
      {
        current_lng += (i_words[i_word_from + lng - 1].size() + (lng > 1 ? 1 : 0));
        double current_cost = _CalculateStringBadness(current_lng, i_max_line_lng) + dp_vals[i_word_from + lng];
        if (current_cost < best_cost)
        {
          best_cost = current_cost;
          best_lng = lng;
        }
      }

      dp_vals[i_word_from] = best_cost;
      argmins[i_word_from] = best_lng;
    };

    for (size_t i = i_words.size() + 1; i > 0; --i)
      DP(i-1);
    
    std::vector<size_t> line_lengths;
    size_t index = 0;
    while (index < i_words.size())
    {
      line_lengths.push_back(argmins[index]);
      index += argmins[index];
    }
    
    return std::make_pair(dp_vals[0], line_lengths);
  }

  std::pair<double, std::vector<size_t>> _GetJustifiedStringPartition(const std::string& i_string_to_justify, size_t i_max_line_lng)
  {
    auto words_list = _SplitStringByDelimiter(i_string_to_justify, " ");
    return _GetPartition(words_list, i_max_line_lng);
  }
  
  std::vector<std::vector<std::string>> _GetLines(const std::vector<std::string>& i_words, const std::vector<size_t>& i_partition)
  {
    std::vector<std::vector<std::string>> lines;
    size_t current_index = 0;
    for (auto lng : i_partition)
    {
      lines.push_back({});
      for (size_t i = 0; i < lng; ++i)
        lines.back().push_back(i_words[current_index + i]);
      current_index += lng;
    }
    return lines;
  }

}

int main(int i_argc, char** i_argv)
{
  std::string string_to_justify = [i_argc, i_argv]() -> std::string
  {
    std::string filename(i_argv[1]);
    return _ReadStringFromFile(filename);
  }();

  size_t max_line_lng = [&]() ->size_t
  {
    std::string max_lng_string(i_argv[2]);
    std::stringstream stream;
    stream << max_lng_string;
    size_t max_lng;
    stream >> max_lng;
    return max_lng;
  }();

  auto result = _GetJustifiedStringPartition(string_to_justify, max_line_lng);
  auto lines = _GetLines(_SplitStringByDelimiter(string_to_justify, " "), result.second);

  std::cout << "cost: " << result.first << std::endl;
  for (const auto& line : lines)
  {
    size_t current_width = 0;
    for(size_t i = 0; i < line.size(); ++i)
    {
      std::cout << line[i] << (i + 1 < line.size() ? " " : "");
      current_width += line[i].size() + (i + 1 < line.size() ? 1 : 0);
    }
    std::cout << std::string(max_line_lng - current_width, '=');
    std::cout << std::endl;
  }

  return 0;
}