import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Modul8r {
	
	private FileInputStream in;
	private int frameSize;
	
	public Modul8r(String filename, int frameSize) throws FileNotFoundException {
		in = new FileInputStream(filename);
		this.frameSize = frameSize;
	}
	
	public boolean hasNextFrame() throws IOException {
		return (in.read(new byte[frameSize], 0, frameSize) >= frameSize);
	}
	
	public double[] getFrameData() throws IOException {
		byte[] buffer = new byte[frameSize];
		double[] frameData = new double[frameSize]; 
		in.read(buffer, 0, frameSize);
		for (int i = 0; i < frameSize; i++) {
			frameData[i] = buffer[i] / 128.0;
		}
		return frameData;
	}
}

