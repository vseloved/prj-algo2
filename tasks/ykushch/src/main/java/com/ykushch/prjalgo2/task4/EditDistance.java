package com.ykushch.prjalgo2.task4;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditDistance {

    private static final Logger log = LoggerFactory.getLogger(EditDistance.class);

    public static int computeMinEditDistance(String src, String dest, boolean shouldPrintMorphing) {
        char[] str1 = src.toCharArray();
        char[] str2 = dest.toCharArray();
        int editArr[][] = new int[str1.length + 1][str2.length + 1];

        for (int i = 0; i < editArr[0].length; i++) {
            editArr[0][i] = i;
        }

        for (int i = 0; i < editArr.length; i++) {
            editArr[i][0] = i;
        }

        for (int i = 1; i <= str1.length; i++) {
            for (int j = 1; j <= str2.length; j++) {
                if (str1[i - 1] == str2[j - 1]) {
                    editArr[i][j] = editArr[i - 1][j - 1];
                } else {
                    editArr[i][j] = NumberUtils.min(editArr[i - 1][j - 1], editArr[i - 1][j], editArr[i][j - 1]) + 1;
                }
            }
        }

        if(shouldPrintMorphing) {
            printMorphing(editArr, src, dest);
        }
        return editArr[str1.length][str2.length];
    }

    private static void printMorphing(int arr[][], String src, String dest) {
        char[] str1 = src.toCharArray();
        char[] str2 = dest.toCharArray();

        int i = arr.length - 1;
        int j = arr[0].length - 1;

        while (true) {
            if (i == 0 || j == 0) {
                if(j > 0) {
                    log.info(String.format("Insert letter1 %s", str2[j - 1]));
                    j--;
                    continue;
                }
                break;
            }

            if (str1[i - 1] == str2[j - 1]) {
                i--;
                j--;
            } else if (arr[i][j] == arr[i - 1][j - 1] + 1) {
                log.info(String.format("Change letter %s to %s", str1[i - 1], str2[j - 1]));
                i--;
                j--;
            } else if (arr[i][j] == arr[i - 1][j] + 1) {
                log.info(String.format("Delete letter in string1: %s", str1[i - 1]));
                i--;
            } else if (arr[i][j] == arr[i][j - 1] + 1) {
                log.info(String.format("Insert letter in string1: %s", str2[j - 1]));
                j--;
            } else {
                throw new IllegalArgumentException("Invalid data was obtained. Nothing to produce.");
            }

        }
    }
}
