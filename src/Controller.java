
import mraa.Pwm;

public class Controller {
	
	public static void main(String[] args) throws InterruptedException {
		
//		Modul8r m = new Modul8r("bloom.mp3", 44100);
//		FourierTransform3 f = new FourierTransform3();
		
		// initialize servo pins
		
		Pwm low = new Pwm(9);
		low.period_us(1500);
		low.enable(true);
		
		low.write(0);
		low.write(0.5);
		low.write(1.0);
		low.write(-0.5);
		
	}
}
