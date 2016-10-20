/**
 * <b>Homework #4</b>
 * <p>
 * Given: 2 words. Morphing word operations: delete character, insert character and replace,
 * which allows transform one word into other.
 * <p>
 * Task:
 * <p>
 * Implement function, that will count the maximum length of whole sequence of characters in both words.
 * Return the list of character pairs, that will show the morph process.
 * Example:
 * <p>
 * Given two words "world" and "fyord". The maximum common subsequence is "ord" with length 3.
 * Optimal morphing is the following:
 * <table>
 * <tr>- f   // вставка буквы 'f'</tr>
 * <tr>w y   // замена буквы 'w' на 'y'</tr>
 * <tr>o o   // буквы совпадают, операций морфинга применять не нужно</tr>
 * <tr>r r</tr>
 * <tr>l -   // удаление буквы 'l'</tr>
 * <tr>d d</tr>
 * </p>
 * <p>
 */
package com.ykushch.prjalgo2.task4;