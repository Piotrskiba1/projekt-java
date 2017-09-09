package zip;

import java.io.IOException;

import zip.archiver.SimpleZipArchiver;
import zip.archiver.ZipArchiver;
import zip.unarchiver.UnZip;
import zip.unarchiver.Unarchiver;

/**
 *	Klasa implementuj¹ca Interfejsy ZipArchiver i Unarchiver
 */
public class ZipApp implements ZipArchiver, Unarchiver {

	ZipArchiver zipArchiver = new SimpleZipArchiver();
	Unarchiver unarchiver = new UnZip();

	@Override
	public void archive(String source, String target) throws IOException {
		zipArchiver.archive(source, target);
	}

	@Override
	public void setComment(String msg) {
		zipArchiver.setComment(msg);
	}

	@Override
	public void unarchive(String source, String target) throws IOException {
		unarchiver.unarchive(source, target);
	}

}
