package br.com.tick.smashrotation.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialization {
	
	public Serialization() {
		// TODO Auto-generated constructor stub
	}
	
	
	// Serialize!
	public static ObjectOutputStream createDataOutputStream(final String fileName, final String folderPath) throws IOException {

		ObjectOutputStream saver = null;

		try {

			final File folder = new File(folderPath);

			if (folder.exists() || folder.mkdirs()) {

				saver = new ObjectOutputStream(new FileOutputStream(new File(folder, fileName)));
			}

		} catch (IOException e) {
			throw e;
		}

		return saver;
	}
	
	// Unserialize!
	public static ObjectInputStream createDataInputStream(final String fileName, final String folderPath) throws IOException {

		ObjectInputStream  loader = null;

		try {

			final File folder = new File(folderPath);

			if (folder.exists() || folder.mkdirs()) {

				final File dataFile = new File(folder, fileName);

				if(dataFile.exists()) {
					loader = new ObjectInputStream(new FileInputStream(dataFile));
				}
			}

		} catch (IOException e) {
			throw e;
		}

		return loader;
	}

}
