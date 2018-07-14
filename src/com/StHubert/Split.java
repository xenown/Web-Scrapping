package com.StHubert;
import java.io.FileWriter;
import java.io.IOException;

public class Split {

	public static void print(String name, String type, String contact, FileWriter writer) throws IOException {
		writer.write(split(name, type, contact));
	}

	private static String split(String name, String type, String contact) {
		// List<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		char sep = '*';
		sb.append(name + sep);
		// list.add(name);
		// list.add(type);
		sb.append(type + sep);
		String address = "";
		String tel = "";
		String website = "";
		String fax = "";
		String email = "";

		String[] temp = contact.split("\n");
		for (int i = 0; i < temp.length; i++) {
			char keyword = temp[i].split(" ")[0].charAt(0);
			int index = temp[i].indexOf(" ");
			String string = temp[i].substring(index + 1);
			switch (keyword) {
			case 'A':
				address = string;
				break;
			case 'P':
				tel = string;
				break;
			case 'W':
				website = string;
				break;
			case 'e':
				email = string;
				break;
			case 'F':
				fax = string;
				break;
			}
		}

		// list.add(address);
		// list.add("Montreal");
		// list.add("QC");
		// list.add("");
		// list.add(tel);
		// list.add(fax);
		// list.add(website);
		// list.add(email);
		sb.append(address + sep);
		sb.append("Montreal" + sep);
		sb.append("QC" + sep);
		sb.append("" + sep);
		sb.append(tel + sep);
		sb.append(fax + sep);
		sb.append(website + sep);
		sb.append(email + sep);
		sb.append('\n');

		return sb.toString();
	}

}
