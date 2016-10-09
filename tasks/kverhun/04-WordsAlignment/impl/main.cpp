#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

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
            for (const auto& element : row)
            {
                std::cout << element << '\t';
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

    TMatrix _GetAlignmentDP(const std::string& i_w1, const std::string& i_w2)
    {
        auto dp_matrix = _ConstructMatrix(i_w1.size() + 1, i_w2.size() + 1);
        
        size_t sz1 = i_w1.size();
        size_t sz2 = i_w2.size();
        
        dp_matrix[0][0] = 0;
        for (size_t i = 1; i <= sz1; ++i)
            dp_matrix[i][0] = 1;
        for (size_t j = 1; j <= sz2; ++j)
            dp_matrix[0][j] = 1;

        for (size_t i = 0; i < sz1; ++i)
        {
            for (size_t j = 0; j < sz2; ++j)
            {
                std::vector<size_t> distances;

                if (i_w1[i] == i_w2[j])
                    distances.push_back(dp_matrix[i][j]);
                else
                    distances.push_back(dp_matrix[i][j] + 1);

                distances.push_back(dp_matrix[i + 1][j] + 1);
                distances.push_back(dp_matrix[i][j + 1] + 1);

                dp_matrix[i + 1][j + 1] = *std::min_element(distances.begin(), distances.end());
            }
        }

        return dp_matrix;
    }
    
    std::pair<std::string, std::string> _AlingWords(const std::string& i_w1, const std::string& i_w2)
    {
        auto dp_matrix = _GetAlignmentDP(i_w1, i_w2);

        _OutputMatrix(dp_matrix);

        std::string w1_aligned, w2_aligned;
        
        auto fill_words = [&]()
        {
            size_t row = dp_matrix.size() - 1;
            size_t col = dp_matrix.front().size() - 1;

            while (row > 0 && col > 0)
            {
                auto min_distance = std::min(dp_matrix[row - 1][col - 1], std::min(dp_matrix[row - 1][col], dp_matrix[row][col - 1]));

                if (min_distance == dp_matrix[row-1][col-1])
                {
                    w1_aligned.push_back(i_w1[row - 1]);
                    w2_aligned.push_back(i_w2[col - 1]);
                    --row;
                    --col;
                }
                else if (min_distance == dp_matrix[row][col-1])
                {
                    w1_aligned.push_back('_');
                    w2_aligned.push_back(i_w2[col - 1]);
                    --col;
                }
                else if (min_distance == dp_matrix[row-1][col])
                {
                    w1_aligned.push_back(i_w1[row - 1]);
                    w2_aligned.push_back('_');
                    --row;
                }
            }
            
            if (row == 0)
            {
                while (col > 0)
                {
                    w1_aligned.push_back('_');
                    w2_aligned.push_back(i_w2[col - 1]);
                    --col;
                }
            }
            else if (col == 0)
            {
                while (row > 0)
                {
                    w1_aligned.push_back(i_w1[row - 1]);
                    w2_aligned.push_back('_');
                    --row;
                }
            }
            
        };
        fill_words();
        std::reverse(w1_aligned.begin(), w1_aligned.end());
        std::reverse(w2_aligned.begin(), w2_aligned.end());
        return std::make_pair(w1_aligned, w2_aligned);
    }

    std::string _GetLCS(const std::string& i_w1, const std::string& i_w2)
    {
        auto lcs_matrix = _GetLcsDP(i_w1, i_w2);

        std::string lcs;

        size_t sz1 = i_w1.size();
        size_t sz2 = i_w2.size();
        for (size_t i = 0; i < sz1; ++i)
        {
            for (size_t j = 0; j < sz2; ++j)
            {
                if (i_w1[i] == i_w2[j] && lcs_matrix[i][j] + 1 == lcs_matrix[i + 1][j + 1])
                    lcs.push_back(i_w1[i]);
            }
        }

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

    class TestWordsAlignment
    {
    public:
        TestWordsAlignment(const std::string& i_w1,
             const std::string& i_w2,
             const std::string& i_w1_aligned_expected, 
             const std::string& i_w2_alinged_expected)
            : m_w1(i_w1)
            , m_w2(i_w2)
            , m_w1_expected(i_w1_aligned_expected)
            , m_w2_expected(i_w2_alinged_expected)
        { }

        bool Run() const
        {
            auto aligned_words = _AlingWords(m_w1, m_w2);
            bool first_ok = (aligned_words.first == m_w1_expected);
            bool second_ok = (aligned_words.second == m_w2_expected);

            std::cout << "Input:" << std::endl;
            std::cout << m_w1 << std::endl << m_w2 << std::endl;
            std::cout << "Output:" << std::endl;
            std::cout << aligned_words.first << std::endl << aligned_words.second << std::endl;
            std::cout << "Expected result:" << std::endl;
            std::cout << m_w1_expected << std::endl << m_w2_expected << std::endl;

            return first_ok && second_ok;
        }

    private:
        const std::string m_w1;
        const std::string m_w2;
        const std::string m_w1_expected;
        const std::string m_w2_expected;
    };
}

int main(int i_argc, char** i_argv)
{
    if (i_argc == 1)
    {
        std::string w1, w2;
        std::cin >> w1 >> w2;

        auto aligned_words = _AlingWords(w1, w2);
        std::cout << aligned_words.first << std::endl << aligned_words.second << std::endl;

    }
    else if (i_argc == 2 && std::string(i_argv[1]) == "--test")
    {
        Tests::TestLCS test_lcs_1("abc", "ac", "ac");
        Tests::TestLCS test_lcs_2("fyord", "world", "ord");
        Tests::TestLCS test_lcs_3("abcd", "abcd", "abcd");
        for (auto& test : { test_lcs_1, test_lcs_2, test_lcs_3 })
        {
            bool result = test.Run();
            std::cout << "Test: " << (result ? "OK" : "FAILED") << std::endl;
        }

        Tests::TestWordsAlignment test1("abc", "ac", "abc", "a_c");
        Tests::TestWordsAlignment test2("fyord", "world", "fyor_d", "_world");
        Tests::TestWordsAlignment test3("abcd", "abcd", "abcd", "abcd");
        
        // taken from https://web.stanford.edu/class/cs124/lec/med.pdf
        //  changed to equivalent alignment in terms of edit distance
        Tests::TestWordsAlignment test4(
            "AGGCTATCACCTGACCTCCAGGCCGATGCCC", "TAGCTATCACGACCGCGGTCGATTTGCCCGAC",
            "_AGGCTATCACCTGACCTCCAGGCCGAT__GCCC___", "TAG_CTATCAC__GACCGC__GGTCGATTTGCCCGAC");

        for (auto& test : { test1, test2, test3, test4 })
        {
            bool result = test.Run();
            std::cout << "Test: " << (result ? "OK" : "FAILED") << std::endl;
        }
    }

    return 0;
}