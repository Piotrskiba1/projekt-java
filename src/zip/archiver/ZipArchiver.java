package zip.archiver;

import java.io.IOException;

public interface ZipArchiver {

	void archive(String source, String target) throws IOException;

	void setComment(String msg);

	public enum COMPRESSION_LEVEL {
		_0(0), _1(1), _2(2), _3(3), _4(4), _5(5), _6(6), _7(7), _8(8), _9(9);

		private COMPRESSION_LEVEL(int lvl) {
			this.lvl = lvl;
		}

		int lvl;
	}
}
