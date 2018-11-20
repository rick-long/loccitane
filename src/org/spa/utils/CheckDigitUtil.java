package org.spa.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by Ivy on 2016/01/16.
 */
public class CheckDigitUtil {
	private static final Map<Character, Integer> CHAR_KEY_MAP = new HashMap<Character, Integer>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('A', 10);
			put('B', 11);
			put('C', 12);
			put('D', 13);
			put('E', 14);
			put('F', 15);
			put('G', 16);
			put('H', 17);
			put('I', 18);
			put('J', 19);
			put('K', 20);
			put('L', 21);
			put('M', 22);
			put('N', 23);
			put('O', 24);
			put('P', 25);
			put('Q', 26);
			put('R', 27);
			put('S', 28);
			put('T', 29);
			put('U', 30);
			put('V', 31);
			put('W', 32);
			put('X', 33);
			put('Y', 34);
			put('Z', 35);
			put('0', 36);
			put('1', 37);
			put('2', 38);
			put('3', 39);
			put('4', 40);
			put('5', 41);
			put('6', 42);
			put('7', 43);
			put('8', 44);
			put('9', 45);
		}
	};

	private static final Map<Integer, Integer> POSITION_MAP = new HashMap<Integer, Integer>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(0, 7);
			put(1, 0);
			put(2, 1);
			put(3, 2);
			put(4, 3);
			put(5, 4);
			put(6, 5);
			put(7, 6);
			put(8, 7);
			put(9, 0);
		}
	};

	public static CheckDigit getCheckDigit(String sn) {
		CheckDigit checkDigit = new CheckDigit();
		char[] ca = sn.toCharArray();
		int count = 0;
		for (int i = 2; i < ca.length; i++) {
			int key = CHAR_KEY_MAP.get(ca[i]);
			if (i % 2 != 0) {
				key *= 2;
			}
			count += key / 10 + key % 10;
		}
		int checkDigit2 = count * 9 % 10;
		checkDigit.setCheckDigit2(String.valueOf(checkDigit2));
		checkDigit.setCheckDigit1(String.valueOf(ca[POSITION_MAP.get(checkDigit2)]));
		return checkDigit;
	}
}