package com.JeanTalon;

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
		String fax = "";
		String website = "";
		String code = "";
		String address = "";

		String[] temp = contact.split("\n");
		String[] temp2 = temp[0].split(" ");
		for (int i = 0; i < temp2.length; i++) {
			if (i < temp2.length - 2) {
				address += temp2[i] + " ";
			} else {
				code += temp2[i] + " ";
			}
		}
		for (int i = 1; i < temp.length; i++) {
			try {
				char keyword = temp[i].charAt(0);
				String string = temp[i];
				switch (keyword) {
				case 'T':
					tel = string.substring(3);
					break;
				case 'F':
					fax = string.substring(3);
					break;
				default:
					website = string;
				}
			} catch (Exception e) {
				System.out.println("print skip");
			}
		}
		sb.append(address + sep);
		sb.append("Montreal" + sep);
		sb.append("QC" + sep);
		sb.append(code + sep);
		sb.append(tel + sep);
		sb.append(fax + sep);
		sb.append(website + sep);
		sb.append('\n');

		return sb.toString();
	}
}
