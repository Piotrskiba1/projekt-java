package zip.unarchiver;

import java.io.IOException;

public interface Unarchiver {

	void unarchive(String source, String target) throws IOException;

}
