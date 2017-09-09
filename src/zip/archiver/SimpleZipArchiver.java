package zip.archiver;

import zip.archiver.ZipArchiver;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Klasa implementujaca ZipArchiver, odpowiada za archiwizacjê
 */
public class SimpleZipArchiver implements ZipArchiver {

	private static final int BUFFER = 2048;

	private FileOutputStream fileOutputStream;
	private ZipOutputStream zipOutputStream;
	private BufferedInputStream bufferedInputStream;
	private String basePath;
	private String message;
	private COMPRESSION_LEVEL compressionLevel;

	/**
	 * Konstruktor domyœlny, ustawia stopieñ kompesji na 5
	 */
	public SimpleZipArchiver() {
		this.compressionLevel = COMPRESSION_LEVEL._5;
	}

	/**
	 * Konstruktor z parametrem
	 * @param compressionLevel stopieñ kompresji
	 */
	public SimpleZipArchiver(COMPRESSION_LEVEL compressionLevel) {
		this.compressionLevel = compressionLevel;
	}
	/**
	 * Archiwizacja pliku
	 * @param sourcePath œcie¿ka do kompresji
	 * @param targetPath œcie¿ka docelowa zapisu pliku
	 */
	public void archive(String sourcePath, String targetPath) throws IOException {
		File source = new File(sourcePath);
		File target = new File(targetPath);

		if (!target.exists()) {
			target.createNewFile();
		}
		basePath = source.getAbsolutePath();

		fileOutputStream = new FileOutputStream(target);
		zipOutputStream = new ZipOutputStream(new BufferedOutputStream(fileOutputStream));
		zipOutputStream.setLevel(compressionLevel.lvl);
		setMethod();

		if (message != null) {
			zipOutputStream.setComment(message);
		}
		if (source.isDirectory()) {
			archiveDir(source);
		} else {
			archiveFile(source);
		}

		zipOutputStream.close();
	}

	/**
	 * Ustawia komentarz
	 * @param msg komentarz
	 */
	@Override
	public void setComment(String msg) {
		this.message = msg;
	}
	
	/**
	 * Metoda kompresji
	 */
	private void setMethod() {
		if (this.compressionLevel != COMPRESSION_LEVEL._0) {
			zipOutputStream.setMethod(ZipOutputStream.DEFLATED);
		} else {
			zipOutputStream.setMethod(ZipOutputStream.STORED);
		}
	}

	/**
	 * Archiwizacjia katalogi
	 * @param file katalog
	 * @throws IOException
	 */
	private void archiveDir(File file) throws IOException {
		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				archiveDir(f);
			} else {
				archiveFile(f);
			}
		}
	}

	/**
	 * Archiwizacja pliku
	 * @param file plik
	 * @throws IOException
	 */
	private void archiveFile(File file) throws IOException {
		String targetFilePath = file.getAbsolutePath().replace(basePath, "");
		byte[] data = new byte[BUFFER];

		FileInputStream fi = new FileInputStream(file);
		bufferedInputStream = new BufferedInputStream(fi, BUFFER);
		ZipEntry entry = new ZipEntry(targetFilePath);
		zipOutputStream.putNextEntry(entry);

		int count;
		while ((count = bufferedInputStream.read(data, 0, BUFFER)) != -1) {
			zipOutputStream.write(data, 0, count);
		}

		bufferedInputStream.close();
	}
}
