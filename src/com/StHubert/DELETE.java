package com.StHubert;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DELETE {
	public static void main(String[] args) throws IOException {
		File file = new File ("StHubert.txt");
		FileWriter write = new FileWriter("StHubert2.txt");
		
		Scanner scan = new Scanner(file);
		String line;
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			System.out.println(line);
			StringBuilder sb = new StringBuilder();
			String[] temp = line.split("\t");
			int i = temp[1].indexOf('(');
			temp[1] = temp[1].substring(0, i);
			for (int j = 0; j < temp.length; j++) {
				sb.append(temp[j]);
				sb.append('*');
			}
			sb.append('\n');
			write.write(sb.toString());
		}
		write.close();
		scan.close();
	}
}
