/**
 * Created by Neal on 12/3/2014.
 */
public class FactorPrimes {
	private long max = Tester.max;
	PrimeGen primeDivisors = new PrimeGen();
	public long factorMax() {
		for (long start = 2; start < Math.sqrt(max); start++) {
			if (primeDivisors.isPrime(start)) {
				if (max%start == 0) {
					return start;
				}
			}
		}
		return 0;
	}
}