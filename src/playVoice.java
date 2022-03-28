import java.net.MalformedURLException;
import java.net.URL;
import java.applet.*;

public class playVoice {
	private URL file;
	public playVoice(String path) {
		try {
			file = new URL("file:" + path);
		} catch (MalformedURLException ex) {
			System.err.println(ex);
		}
		Applet.newAudioClip(file).play();
	}
}
