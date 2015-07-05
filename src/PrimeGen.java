/**
 * Created by Neal on 11/28/2014.
 */
public class PrimeGen
{
    public boolean isPrime(long numChecked)
    {
        long divisor = 2;
        while (divisor <= Math.sqrt(numChecked))
        {
            if ((numChecked % divisor) == 0)
            {
                return false;
            }
            divisor++;
        }
        return true;
    }
}
