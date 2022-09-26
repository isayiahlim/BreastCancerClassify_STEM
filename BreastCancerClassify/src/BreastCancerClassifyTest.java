import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;

/**
 * Name: Isayiah Lim
 * Period: 2
 * Project: BreatCancerClassify
 * Date last updated: 9/26/22
 * 
 * Use this class for your personal jUnit test suite.
 *
 */

public class BreastCancerClassifyTest 
{
	@Test
	public void calculateDistanceTest()
	{
		int[] input1 = {0,0,0,0};
		int[] input2 = {0,0,0,0};
        double result = BreastCancerClassify.calculateDistance(input1, input2);
        assertEquals(0.0, result, 0);
	}
	
}
