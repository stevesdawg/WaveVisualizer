import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

public class FourierTransform3{
	
	private final double maxFreq = 8500;
	
	/**
	 * Generates a spectrum with three frequency bins - we only have three servos D:
	 * @return a 1 by 3 array.
	 * First element contains average amplitude of the low frequencies.
	 * Second element contains average amplitude of mid frequencies.
	 * Third element contains average amplitude of high frequencies.
	 */
	public double[] generateSpectrum(double[] audioFrameData, double lowCutoff, double midCutoff){
	    //data: input data, must be spaced equally in time.
	    //lowPass: The cutoff frequency at which 
	    //frequency: The frequency of the input data.
	
	    //The apache Fft (Fast Fourier Transform) accepts arrays that are powers of 2.
	    int maxPowerOf2 = 1;
	    while(maxPowerOf2 < audioFrameData.length)
	        maxPowerOf2 = 2 * maxPowerOf2;
	
	    //pad with zeros
	    double[] padded = new double[maxPowerOf2];
	    for(int i = 0; i < audioFrameData.length; i++)
	        padded[i] = audioFrameData[i];
	
	
	    FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
	    Complex[] fourierTransform = transformer.transform(padded, TransformType.FORWARD);
	
	    //invert back to time domain
	    Complex[] reverseFourier = transformer.transform(fourierTransform, TransformType.INVERSE);
	    
	    // calculate the accumulated sum of magnitudes of the frequency bins
	    double lowSum = 0;
	    double midSum = 0;
	    double highSum = 0;
	    
	    for(int i = 0; i < fourierTransform.length; i++) {
	        double theFreq = maxFreq * i / (double)fourierTransform.length;
	        if (theFreq <= lowCutoff) {
	        	lowSum += reverseFourier[i].getReal();
	        } else if (theFreq > lowCutoff && theFreq <= midCutoff) {
	        	midSum += reverseFourier[i].getReal();
	        } else if (theFreq > midCutoff && theFreq <= maxFreq) {
	        	highSum += reverseFourier[i].getReal();
	        }
	    }

	    //get the real part of the reverse 
	    double[] result = {lowSum, midSum, highSum};
	    return result;
	}
}