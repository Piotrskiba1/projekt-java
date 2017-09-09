package zip.unarchiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Klasa dekompresuj¹ca
 */
public class UnZip implements Unarchiver {

	/**
	 * Metoda dekompresujaca
	 * @param source œcie¿ka katalogu/pliku do dekompresji
	 * @param target œcie¿ka zapisu
	 */
	@Override
	public void unarchive(String source, String target) throws IOException {
		byte[] buffer = new byte[1024];

		try {

			File folder = new File(target);
			if (!folder.exists()) {
				folder.mkdir();
			}

			ZipInputStream zis = new ZipInputStream(new FileInputStream(source));
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(target + File.separator + fileName);

				System.out.println("file unzip : " + newFile.getAbsoluteFile());

				new File(newFile.getParent()).mkdirs();

				FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

			System.out.println("Done");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}