/**
 * Created by neal on 11/28/14.
 */
public class MakeK
{
    long privateKey;
    public long k(long z)
    {
        PrimeGen prime = new PrimeGen();
        long k = 0;
        for (long start = (z/2) + 2; start < z * 2; start++)
        {
            if (prime.isPrime(start))
            {
                k = start;
                if ((z % k) == 0)
                {
                    continue;
                }
                break;
            }
        }
        for (int begin = 1; begin < z * 3; begin++)
        {
            privateKey = begin;
            if ((k * privateKey)%z == 1)
                return k;
        }
        return 0;
    }
}