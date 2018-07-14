package com.Fleury;

import java.io.FileWriter;
import java.io.IOException;

public class Split {
	public static void print(String name, String type, String contact, FileWriter writer) throws IOException {
		writer.write(split(name, type, contact));
	}

	private static String split(String name, String type, String contact) {
		StringBuilder sb = new StringBuilder();
		char sep = '*';
		int index = name.indexOf('-');
		sb.append(name.substring(0, index) + sep);
		sb.append(type + sep);
		String tel = "";
		String website = "";
		String email = "";
		String facebook = "";

		String[] temp = contact.split("\n");
		sb.append(temp[0] + sep);
		for (int i = 2; i < temp.length; i++) {
			try {
				char keyword = temp[i].charAt(0);
				String string = temp[i];
				switch (keyword) {
				case 'T':
					tel = string.substring(4);
					break;
				case 'f':
					facebook = string.substring(9);
					break;
				case 'm':
					email = string.substring(7);
					break;
				default:
					website = string;
				}
			} catch (Exception e) {
				System.out.println("print skip");
			}
		}

		sb.append("Montreal" + sep);
		sb.append("QC" + sep);
		if (temp[1].length() > 18) {
			sb.append(temp[1].substring(18) + sep);
		} else {
			sb.append("" + sep);
		}
		sb.append(tel + sep);
		sb.append(website + sep);
		sb.append(email + sep);
		sb.append(facebook + sep);
		sb.append('\n');

		return sb.toString();
	}
}
