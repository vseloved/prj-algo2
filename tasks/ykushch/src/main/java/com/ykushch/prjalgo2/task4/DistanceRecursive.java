package com.ykushch.prjalgo2.task4;

import java.util.Arrays;
import java.util.Collections;

public class DistanceRecursive {
    public static int computeEditDistance(String srcString, String destString) {
        int srcStrLen = srcString.length();
        int destStrLen = destString.length();

        if (srcStrLen == 0) {
            // insert all
            return destStrLen;
        }

        if (destStrLen == 0) {
            // remove all
            return srcStrLen;
        }

        char srcCharEnd = srcString.charAt(srcStrLen - 1);
        char destCharEnd = destString.charAt(destStrLen - 1);

        if (srcCharEnd == destCharEnd) {
            return computeEditDistance(srcString.substring(0, srcStrLen - 1), destString.substring(0, destStrLen - 1));
        }

        int insertCnt = computeEditDistance(srcString, destString.substring(0, destStrLen - 1));
        int removeCnt = computeEditDistance(srcString.substring(0, srcStrLen - 1), destString);
        int replaceCnt = computeEditDistance(srcString.substring(0, srcStrLen - 1), destString.substring(0, destStrLen - 1));

        return 1 + Collections.min(Arrays.asList(insertCnt, removeCnt, replaceCnt));
    }

    public static String findLongestCommonSequence(String src, String dest) {
        int srcLen = src.length();
        int destLen = dest.length();

        if (srcLen == 0 || destLen == 0) {
            return "";
        }

        char srcLastChar = src.charAt(srcLen - 1);
        char destLastChar = dest.charAt(destLen - 1);

        if (areLastCharsEqual(srcLastChar, destLastChar)) {
            return findLongestCommonSequence(src.substring(0, srcLen - 1),
                    dest.substring(0, destLen - 1)) + src.charAt(srcLen - 1);
        } else {
            String x = findLongestCommonSequence(src, dest.substring(0, destLen - 1));
            String y = findLongestCommonSequence(src.substring(0, srcLen - 1), dest);

            return x.length() > y.length()
                    ? x
                    : y;
        }
    }

    private static boolean areLastCharsEqual(char srcLastChar, char destLastChar) {
        return srcLastChar == destLastChar;
    }
}
