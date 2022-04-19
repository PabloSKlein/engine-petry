package com.unisinos.enginepetry.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Util {

	public static List<String> loadLinesBuffer(String pathFile, String charset) throws IOException {
		return loadLinesBuffer(pathFile, charset, true);
	}

	public static List<String> loadLinesBuffer(String pathFile, String charset, boolean whiteLines) throws IOException {
		return loadLinesBuffer(new File(pathFile), charset, whiteLines);
	}

	public static List<String> loadLinesBuffer(File file, String charset) throws IOException {
		return loadLinesBuffer(file, charset, true);
	}

	public static List<String> loadLinesBuffer(File file, String charset, boolean whiteLines) throws IOException {
		ArrayList<String> lines = new ArrayList<>();
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file), charset);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		while ((line = br.readLine()) != null) {
			if (whiteLines || !line.trim().equalsIgnoreCase("")) {
				lines.add(line);
			}
		}
		br.close();
		return lines;
	}
}
