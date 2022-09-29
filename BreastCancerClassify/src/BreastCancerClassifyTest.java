import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
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
	/*
	 * Calculate Distance Tests
	 */
	@Test
	//tests what happens with the arrays are equal (and only have three values
	public void testCalculateDistance1()
	{
		int[] input1 = {0,0,0};
		int[] input2 = {0,0,0};
        double result = BreastCancerClassify.calculateDistance(input1, input2);
        assertEquals(0.0, result, 0);
	}
	@Test
	//tests what happens with only one value
	public void testCalculateDistance2()
	{
		int[] input1 = {0};
		int[] input2 = {2};
        double result = BreastCancerClassify.calculateDistance(input1, input2);
        assertEquals(0.0, result, 0);
	}
	@Test
	//tests what happens with only two values
	public void testCalculateDistance3()
	{
		int[] input1 = {0,0};
		int[] input2 = {2,82};
        double result = BreastCancerClassify.calculateDistance(input1, input2);
        assertEquals(0.0, result, 0);
	}
	@Test
	//tests what happens with a LOT of values
	public void testCalculateDistance4()
	{
		int[] input1 = new int[402];
		for(int i = 0; i < input1.length; i ++)
			input1[i] = 0;
		int[] input2 = new int[402];
		for(int i = 0; i < input2.length; i ++)
			input2[i] = 1;
        double result = BreastCancerClassify.calculateDistance(input1, input2);
        assertEquals(20.0, result, 0);
	}
	
	/*
	 * All Distances Tests
	 */
	@Test
	//tests a standard output
	public void testGetAllDistances1()
	{
		int[][] trainData = {{10,10,10,2}, {6,2,4,2}};
		int[] testInstance = {5,5,5,5};
		double [] results = BreastCancerClassify.getAllDistances(trainData, testInstance);
		assertEquals(7.07, results[0], 0.05);
		assertEquals(3.16, results[1], 0.05);
	}
	@Test
	//tests a short test instance
	public void testGetAllDistances2()
	{
		int[][] trainData = {{10,10,10,2}, {10,6,100,2}, {10,10,0,4}, {10,52,233,9}};
		int[] testInstance = {2,2};
		double [] results = BreastCancerClassify.getAllDistances(trainData, testInstance);
		for(double num : results)
			assertEquals(0, num, 0.05);
	}
	
	/*
	 * Find K Closest Entries Tests
	 */
	@Test
	//tests a normal allDistances
	public void testFindKClosestEntries1()
	{
		double[] allDistances = {15, 0, 1.1, 1.1, 5, 2.4, 15};
		int[] expected = {1,2,3,5,4};
		int[] results = BreastCancerClassify.findKClosestEntries(allDistances);
		for(int i = 0; i < results.length; i ++)
			assertEquals(expected[i], results[i], 0);
	}
	@Test
	//tests allDistances with the same values & 0s
	public void testFindKClosestEntries2()
	{
		double[] allDistances = {0, 0, 0, 0, 0};
		int[] expected = {0,1,2,3,4};
		int[] results = BreastCancerClassify.findKClosestEntries(allDistances);
		for(int i = 0; i < results.length; i ++)
			assertEquals(expected[i], results[i], 0);
	}
	@Test
	//tests allDistances with big values
	public void testFindKClosestEntries3()
	{
		double[] allDistances = {29102384, 32421, 582490, 2857910, 204350};
		int[] expected = {1,4,2,3,0};
		int[] results = BreastCancerClassify.findKClosestEntries(allDistances);
		for(int i = 0; i < results.length; i ++)
			assertEquals(expected[i], results[i], 0);
	}
	
	/*
	 * classify Tests
	 */
	@Test
	//tests a normal classify
	public void testClassify1()
	{
		int[][] trainData= {{3,3,2},{6,29,2},{6,456,2},{26,29,4},{6,29,2},{6,29,4}};
		int[] kClosestIndexes = {1,2,3};
		int result = BreastCancerClassify.classify(trainData, kClosestIndexes);
		assertEquals(2, result, 0);
	}
	@Test
	//tests classify where the malignant and benign are equal
	public void testClassify2()
	{
		int[][] trainData= {{3,3,2},{6,29,2},{6,456,0},{26,29,4},{6,29,2},{6,29,4}};
		int[] kClosestIndexes = {1,2,3};
		int result = BreastCancerClassify.classify(trainData, kClosestIndexes);
		assertEquals(4, result, 0);
	}
	@Test
	//tests just one kClosestIndex
	public void testClassify3()
	{
		int[][] trainData= {{3,3,2},{6,29,2},{6,456,2},{26,29,4},{6,29,2},{6,29,4}};
		int[] kClosestIndexes = {0};
		int result = BreastCancerClassify.classify(trainData, kClosestIndexes);
		assertEquals(2, result, 0);
	}
	@Test
	//tests an even count of indexes
	public void testClassify4()
	{
		int[][] trainData= {{3,3,2},{6,29,4},{6,456,2},{26,29,4},{6,29,2},{6,29,4}};
		int[] kClosestIndexes = {0,1};
		int result = BreastCancerClassify.classify(trainData, kClosestIndexes);
		assertEquals(4, result, 0);
	}
	
	/*
	 * K Nearest Neighbors Tests
	 */
	@Test
	//tests an empty testData
	public void testKNearestNeighbors1()
	{
		int[][] trainData= {{3,3,2},{6,29,2},{6,456,2},{26,29,4},{6,29,2},{6,29,4}};
		int[][] testData= {{}};
		
		int[] results = BreastCancerClassify.kNearestNeighbors(trainData, testData);
		int[] expected = {2};
		assertArrayEquals(expected, results);
	}
	@Test
	//tests standard output
	public void testKNearestNeighbors2()
	{
		int[][] trainData= {{3,3,2},{6,29,2},{6,456,2},{26,29,4},{6,29,2},{6,29,4}};
		int[][] testData= {{3,3,2},{6,29,2},{6,456,2},{26,29,4}};
		
		int[] results = BreastCancerClassify.kNearestNeighbors(trainData, testData);
		int[] expected = {2,2,2,2};
		assertArrayEquals(expected, results);
	}
	
	
	/*
	 * get Accuracy Tests
	 */
	@Test
	//tests a normal getAccuracy
	public void testGetAccuracy1()
	{
		int[] myResults= {2,2,2,2,4};
		int[][] testData = {{4,5,63,4,2}, {4,5,63,4,2}, {4,5,63,4,2}, {4,5,63,4,2}, {4,5,63,4,4}};
		String expected = "100.00%";
		String result = BreastCancerClassify.getAccuracy(myResults, testData);
		assertEquals(expected, result);
	}
	@Test
	//tests a completely wrong result
	public void testGetAccuracy2()
	{
		int[] myResults= {2,2,2,2,4};
		int[][] testData = {{4,5,63,4,4}, {4,5,63,4,4}, {4,5,63,4,4}, {4,5,63,4,4}, {4,5,63,4,2}};
		String expected = "0.00%";
		String result = BreastCancerClassify.getAccuracy(myResults, testData);
		assertEquals(expected, result);
	}
}
