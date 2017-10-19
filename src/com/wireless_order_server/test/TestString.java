package com.wireless_order_server.test;

public class TestString {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("12 -------->"+isNum("12"));
	}

	private static boolean isNum(String msg) {

		return msg.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");

	}

	
}
