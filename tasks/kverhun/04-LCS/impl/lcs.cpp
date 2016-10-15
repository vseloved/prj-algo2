#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

#include "lcs.h"

namespace
{
    using TMatrix = std::vector<std::vector<size_t>>;
    TMatrix _ConstructMatrix(size_t i_dim1, size_t i_dim2)
    {
        TMatrix matrix(i_dim1);
        for (auto& row : matrix)
            row.resize(i_dim2);
        return matrix;
    }

    void _OutputMatrix(const TMatrix& i_matrix)
    {
        for (const auto& row : i_matrix)
        {
            for (const auto& elem : row)
            {
                std::cout << elem << '\t';
            }
            std::cout << std::endl;
        }
    }

    TMatrix _GetLcsDP(const std::string& i_w1, const std::string& i_w2)
    {
        auto lcs_matrix = _ConstructMatrix(i_w1.size() + 1, i_w2.size() + 1);

        auto sz1 = i_w1.size();
        auto sz2 = i_w2.size();

        for (size_t i = 0; i <= sz1; ++i)
            lcs_matrix[i][0] = 0;
        for (size_t i = 0; i <= sz2; ++i)
            lcs_matrix[0][i] = 0;

        for (size_t i = 1; i <= sz1; ++i)
            for (size_t j = 1; j <= sz2; ++j)
            {
                std::vector<size_t> lengths;
                lengths.push_back(lcs_matrix[i - 1][j]);
                lengths.push_back(lcs_matrix[i][j - 1]);
                if (i_w1[i - 1] == i_w2[j - 1])
                    lengths.push_back(lcs_matrix[i - 1][j - 1] + 1);

                lcs_matrix[i][j] = *std::max_element(lengths.begin(), lengths.end());
            }

        return lcs_matrix;
    }

    std::string _GetLCS(const std::string& i_w1, const std::string& i_w2)
    {
        auto lcs_matrix = _GetLcsDP(i_w1, i_w2);

        std::string lcs;

        size_t sz1 = i_w1.size();
        size_t sz2 = i_w2.size();
        size_t i = sz1, j = sz2;
        while (i > 0 && j > 0)
        {
            if (lcs_matrix[i - 1][j - 1] + 1 == lcs_matrix[i][j]) // same letter
            {
                lcs.push_back(i_w1[i - 1]);
                --i;
                --j;
            }
            else if (lcs_matrix[i - 1][j] == lcs_matrix[i][j]) // go from where bigger length came
                --i;
            else
                --j;
        }
        std::reverse(lcs.begin(), lcs.end());
        return lcs;
    }
}

namespace Tests
{
    class TestLCS
    {
    public:
        TestLCS(const std::string& i_w1, const std::string& i_w2, const std::string& i_lcs_expected)
            : m_w1(i_w1)
            , m_w2(i_w2)
            , m_lcs_expected(i_lcs_expected)
        { }

        bool Run() const
        {
            auto lcs = _GetLCS(m_w1, m_w2);
            bool result = m_lcs_expected == lcs;
            return result;
        }

    private:
        const std::string m_w1;
        const std::string m_w2;
 
        const std::string m_lcs_expected;
    };
}


int main(int i_argc, char** i_argv)
{
    if (i_argc == 1)
    {
        std::string w1, w2;
        std::cin >> w1 >> w2;

        auto lcs = _GetLCS(w1, w2);
        std::cout << lcs << std::endl;
    }
    else if (i_argc == 2 && std::string(i_argv[1]) == "--test")
    {
        Tests::TestLCS test_lcs_1("abc", "ac", "ac");
        Tests::TestLCS test_lcs_2("fyord", "world", "ord");
        Tests::TestLCS test_lcs_3("abcd", "abcd", "abcd");
        Tests::TestLCS test_lcs_4("fyord", "worfld", "ord");
        for (auto& test : { test_lcs_1, test_lcs_2, test_lcs_3, test_lcs_4 })
        {
            bool result = test.Run();
            std::cout << "Test: " << (result ? "OK" : "FAILED") << std::endl;
        }
    }

    return 0;
}

std::string LCS::GetLCS(const std::string& i_word1, const std::string& i_word2)
{
    return ::_GetLCS(i_word1, i_word2);
}