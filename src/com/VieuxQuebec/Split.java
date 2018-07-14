package com.VieuxQuebec;

import java.io.FileWriter;
import java.io.IOException;

public class Split {
	public static void print(String name, String type, String address, String website, String phone, String contact,
			FileWriter writer) throws IOException {
		writer.write(split(name, type, address, website, phone, contact));
	}

	private static String split(String name, String type, String address, String website, String phone,
			String contact) {
		StringBuilder sb = new StringBuilder();
		char sep = '*';
		sb.append(name + sep);
		sb.append(type + sep);
		address = address.replace('\n', ' ');
		String[] temp2 = address.split(" ");
		address = "";
		String code = "";
		for (int i = 0; i < temp2.length; i++) {
			if (i < temp2.length - 2) {
				address += temp2[i] + " ";
			} else {
				code += temp2[i] + " ";
			}
		}
		sb.append(address + sep);
		sb.append("Quebec City" + sep);
		sb.append("QC" + sep);
		sb.append(code + sep);
		sb.append(phone + sep);
		sb.append(" " + sep);
		sb.append(website + sep);
		sb.append(" " + sep);
		String facebook = " ";
		String twitter = " ";
		String insta = " ";
		String[] temp = contact.split("\n");
		for (int i = 0; i < temp.length; i++) {
			int index = temp[i].indexOf("facebook");
			if (index != -1) {
				facebook = temp[i];
			} else {
				index = temp[i].indexOf("twitter");
				if (index != -1) {
					twitter = temp[i];
				} else {
					index = temp[i].indexOf("instagram");
					if (index != -1) {
						insta = temp[i];
					}
				}
			}
		}
		sb.append(facebook + sep);
		sb.append(twitter + sep);
		sb.append(insta + sep);
		sb.append('\n');

		return sb.toString();
	}
}
