from DataReader import DataReader
import functools


def memoize(obj):
    cache = obj.cache = {}

    @functools.wraps(obj)
    def memoizer(*args, **kwargs):
        if args not in cache:
            cache[args] = obj(*args, **kwargs)
        return cache[args]
    return memoizer


class Solution:
    # @param s, a string
    # @param dict, a set of string
    # @return a list of strings
    def wordBreak(self, s, dict):

        # lazy way for the cache decorator
        @memoize
        def _wordBreak(s):
            # print(s)
            results = []
            for word in dict:
                if s == word:
                    results.append(word)
                elif s.startswith(word):
                    # print('got', word)
                    for result in _wordBreak(s[len(word):]):
                        results.append(word+' '+result)

            return results

        return _wordBreak(s)

def weight(s):
    weight = 0
    for word in s:
        weight += 10 ** len(word)
    return weight


if __name__ == "__main__":
    d = DataReader('../../../dict_en.txt')
    tokens = d.readTokens()
    wraps = Solution().wordBreak("thisisatest", tokens)
    print(sorted(wraps, key=weight))
