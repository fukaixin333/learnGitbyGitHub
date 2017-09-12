package com.citic.server.test;

public class StateTest {
	public static void main(String[] args) {
		int a = 1;
		int b = 2;
		int c = 4;
		
		int[] keys = new int[] {3, 3, 2, 3, 3, 1, 2, 2, 2, 3};
		int value = 0;
		for (int i : keys) {
			if (i == 1) {
				value = value & (~a) | a;
			} else if (i == 2) {
				value = value & (~b) | b;
			} else {
				value = value & (~c) | c;
			}
		}
		
		if (value == a) {
			System.out.println("全是1");
		} else if (value == b) {
			System.out.println("全是2");
		} else if (value == c) {
			System.out.println("全是3");
		} else if (value == 3) {
			System.out.println("1和2");
		} else if (value == 5) {
			System.out.println("1和3");
		} else if (value == 6) {
			System.out.println("2和3");
		} else {
			System.out.println("1、2、3全都有");
		}
	}
}
