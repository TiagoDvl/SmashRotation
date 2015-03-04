package br.com.tick.smashrotation.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialization {
	
	private static String FILE_NAME = "SMASH_ROTATION";
	
	public Serialization() {
		// TODO Auto-generated constructor stub
	}
	
	// Serialize!
	public static ObjectOutputStream createDataOutputStream(final String folderPath) throws IOException {

		ObjectOutputStream saver = null;

		try {

			final File file = new File(folderPath);

			if (file.exists() || file.mkdirs()) {

				saver = new ObjectOutputStream(new FileOutputStream(new File(file, FILE_NAME)));
			}

		} catch (IOException e) {
			throw e;
		}

		return saver;
	}
	
	// Unserialize!
	public static ObjectInputStream createDataInputStream(final String folderPath) throws IOException {

		ObjectInputStream  loader = null;

		try {

			final File folder = new File(folderPath);

			if (folder.exists() || folder.mkdirs()) {

				final File dataFile = new File(folder, FILE_NAME);

				if(dataFile.exists()) {
					loader = new ObjectInputStream(new FileInputStream(dataFile));
				}
			}

		} catch (IOException e) {
			throw e;
		}

		return loader;
	}
	
	/*
	public static boolean persistData(final List<Category> data) throws IOException {

		boolean saved = false;

		synchronized (SYNC_OBJECT) {

			ObjectOutputStream objectOutputStream = Serialization.createDataOutputStream(Serialization.getFileName(data.get(0)), Serialization.getFolderPath());
			if (objectOutputStream != null) {
				final ObjectOutputStream saver = Serialization.createDataOutputStream(Serialization.getFileName(data.get(0)), Serialization.getFolderPath());

				try {
					if (saver != null) {
						saver.writeObject(data);
						saved = true;
					} else {
						WrapperLog.error("Could not save on persistData. Saved = false", RssReaderDAO.class, "http://pastie.org/10000155persistData");
						saved = false;
					}
				} catch (IOException e) {
					saved = false;
				} finally {
					if (saver != null) {
						saver.close();
					}
				}
				objectOutputStream.close();
				return saved;
			} else {
				return saved;
			}
		}
	}
	*/

}
