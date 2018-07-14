package com.QuartierCanal;

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
		String email = "";
		String code = "";
		String address = "";

		String[] temp = contact.split("\n");
		for (int i = 0; i < temp.length; i++) {
			try {
				String keyword = temp[i].substring(0, 3);
				String string = temp[i];
				switch (keyword) {
				case "Adr":
					address = string.substring(9);
				case "Cod":
					code = string.substring(13);
				case "Tel":
					tel = string.substring(11);
					break;
				case "Cou":
					email = string.substring(10);
				case "Fax":
					fax = string.substring(5);
					break;
				default:
					website = string.substring(15);
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
		sb.append(email + sep);
		sb.append('\n');

		return sb.toString();
	}
}
