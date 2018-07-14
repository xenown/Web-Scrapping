package com.DistrictCentral;

import java.io.FileWriter;
import java.io.IOException;

public class Split {
	public static void print(String name, String type, String contact, FileWriter writer) throws IOException {
		writer.write(split(name, type, contact));
	}

	private static String split(String name, String type, String contact) {
		StringBuilder sb = new StringBuilder();
		char sep = '*';
		sb.append(name + sep);
		sb.append(type + sep);
		String[] temp = contact.split("\n");
		String address = " ";
		String tel = " ";
		for (int i = 0; i < temp.length; i++) {
			char keyword = temp[i].charAt(0);
			switch (keyword) {
			case 'A':
				address = temp[i].substring(10);
				break;
			case 'T':
				tel = temp[i].substring(12);
				break;
			}
		}
		sb.append(address + sep);
		sb.append("Montreal" + sep);
		sb.append("QC" + sep);
		sb.append(" " + sep);
		sb.append(tel + sep);
		sb.append('\n');

		return sb.toString();
	}
}
