package de.ng.nizada.gamecore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesUtil {

	public static Properties load(File file) {
		return load(file, new Properties());
	}

	public static Properties load(File file, Properties properties) {
		try {
			Path path = file.toPath();

			if (Files.notExists(path.getParent()))
				Files.createFile(Files.createDirectories(path.getParent()));
			if (Files.notExists(path))
				Files.createFile(path);

			FileInputStream fileInputStream = new FileInputStream(file);
			properties.load(fileInputStream);
			fileInputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return properties;
	}

	public static Properties save(File file, Properties properties) {
		return save(file, properties, null);
	}

	public static Properties save(File file, Properties properties, String comments) {
		try {
			Path path = file.toPath();

			if (Files.notExists(path))
				Files.createFile(Files.createDirectories(path.getParent()));

			FileOutputStream fileOutputStream = new FileOutputStream(file);
			properties.store(fileOutputStream, comments);
			fileOutputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return properties;
	}
}