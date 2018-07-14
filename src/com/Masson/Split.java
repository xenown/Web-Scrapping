package com.Masson;

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
		String tel = "";
		String website = "";
		String fax = "";
		String email = "";
		String facebook = "";

		String[] temp = contact.split("\n");
		sb.append(temp[0] + sep);
		for (int i = 1; i < temp.length; i++) {
			char keyword = temp[i].charAt(0);
			String string = temp[i];
			switch (keyword) {
			case 't':
				tel = string.substring(4);
				break;
			case 'f':
				facebook = string.substring(9);
				break;
			case 'm':
				email = string.substring(7);
				break;
			case 'F':
				fax = string.substring(8);
				break;
			default:
				website = string;
			}
		}

		sb.append("Montreal" + sep);
		sb.append("QC" + sep);
		sb.append("" + sep);
		sb.append(tel + sep);
		sb.append(fax + sep);
		sb.append(website + sep);
		sb.append(email + sep);
		sb.append(facebook + sep);
		sb.append('\n');

		return sb.toString();
	}
}
