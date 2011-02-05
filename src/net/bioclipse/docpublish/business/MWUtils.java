package net.bioclipse.docpublish.business;

import java.io.*;

public class MWUtils {
	public static String readFileInWorkspace(String filePath) {
		String fullPath = null;
		String result = null;
		try {
			fullPath = filePathToFullPath(filePath);
			result = readFileAsString(fullPath);
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return result;
	}

	static String filePathToFullPath(String filePath) {
		String fullPath = null;
		String dirSeparator = null;
		String eclipseWorkspacePath = null;

		if (System.getProperty("os.name").startsWith("Windows")) {
			dirSeparator = "\\";
		} else {
			dirSeparator = "/";
		}

		try {
			eclipseWorkspacePath = System.getProperty("user.dir");
			fullPath = eclipseWorkspacePath + dirSeparator + filePath;
			System.out.println("filePath: " + filePath);
			System.out.println("fullPath: " + fullPath);
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return fullPath;
	}

	private static String readFileAsString(String filePath)
			throws java.io.IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}
}
