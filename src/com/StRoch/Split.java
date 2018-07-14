package com.StRoch;

import java.io.FileWriter;
import java.io.IOException;

public class Split {
	public static void print(String info, String type, String media, String tel, FileWriter writer) throws IOException {
		writer.write(split(info, type, media, tel));
	}

	private static String split(String info, String type, String media, String tel) {
		StringBuilder sb = new StringBuilder();
		char sep = '*';

		String[] temp = info.split("\n");
		sb.append(temp[0] + sep);
		sb.append(type + sep);
		sb.append(temp[1] + sep);
		sb.append("Quebec City" + sep);
		sb.append("QC" + sep);
		sb.append(temp[3] + sep);
		sb.append(tel + sep);
		String website = " ";
		String facebook = " ";
		String twitter = " ";
		String insta = " ";
		
		String[] temp2 = media.split("\n");
		for (int i = 0; i < temp2.length; i++) {
			int index = temp2[i].indexOf("facebook");
			if (index != -1) {
				facebook = temp2[i];
			} else {
				index = temp2[i].indexOf("twitter");
				if (index != -1) {
					twitter = temp2[i];
				} else {
					index = temp2[i].indexOf("instagram");
					if (index != -1) {
						insta = temp2[i];
					} else {
						index = temp2[i].indexOf("maps");
						if (index == -1) {
							website = temp2[i];
						}
					}
				}
			}
		}
		sb.append(website + sep);
		sb.append(" " + sep);
		sb.append(facebook + sep);
		sb.append(twitter + sep);
		sb.append(insta + sep);
		sb.append('\n');

		return sb.toString();
	}
}
