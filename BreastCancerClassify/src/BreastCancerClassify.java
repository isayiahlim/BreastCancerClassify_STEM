/**
 * Name: Isayiah Lim
 * Period: 1
 * Project: Breast Cancer Classifications: We will use a pool of data to develop an algorithm
 * to classify tumors as malignant or benign, then analyze athe responses for correctness
 * Date last updated: 
 * 
 * BreastCancerClassify contains the core implementation of the 
 * kNearestNeighbors algorithm to classify cell clumps as malignant
 * or benign. 
 * 
 * It is suggested that you work on the methods in the following order:
 * 	1) calculateDistance - once you finish this, you should see a
 * 	   graph of distances appear!
 * 	2) getAllDistances
 * 	3) findKClosestEntries
 * 	4) classify
 *  5) kNearestNeighbors (use your helpers correctly!)
 *  6) getAccuracy
 */
public class BreastCancerClassify {
	
	public static final Integer K = 5;
    public static final Integer BENIGN = 2;
    public static final Integer MALIGNANT = 4;
	
	/**
	 * calculateDistance computes the distance between the two data
	 * parameters. 
	 */
	public static double calculateDistance(int[] first, int[] second)
	{
		double total = 0;
		for(int i = 1; i < first.length-1; i++)
		{
			total += (first[i]-second[i])*(first[i]-second[i]);
		}
		return Math.sqrt(total);
	}
	
	/**
	 * getAllDistances creates an array of doubles with the distances
	 * to each training instance. The double[] returned should have the 
	 * same number of instances as trainData. 
	 */
	public static double[] getAllDistances(int[][] trainData, int[] testInstance)
	{
		double[] allDistances = new double[trainData.length];
		for(int i = 0; i < trainData.length; i++) 
		{
			allDistances[i] = calculateDistance(trainData[i], testInstance);
		}
		return allDistances;
	}
	
	/**
	 * findKClosestEntries finds and returns the indexes of the 
	 * K closest distances in allDistances. Return an array of size K, 
	 * that is filled with the indexes of the closest distances (not
	 * the distances themselves). 
	 * 
	 * Be careful! This method can be tricky.
	 */
	public static int[] findKClosestEntries(double[] allDistances)
	{
		int[] kClosestIndexes = new int[K];
		for(int i = 0; i < K; i++)
		{
			int index = 0;
			double smallest = allDistances[0];
			for(int j = 0; j < allDistances.length; j++)
			{
				if(i > 0)
				{
					if(j != kClosestIndexes[i-1])
					{
						if(allDistances[j] < smallest && j != index)
						{
							index = j;
							smallest = allDistances[j];
						}
					}
				}
				else
				{
					if(allDistances[j] < smallest && j != index)
					{
						index = j;
						smallest = allDistances[j];
					}
				}
					
			}
			kClosestIndexes[i] = index;
		}
		return kClosestIndexes;
	}
	
	/**
	 * classify makes a decision as to whether an instance of testing 
	 * data is BENIGN or MALIGNANT. The function makes this decision based
	 * on the K closest train data instances (whose indexes are stored in 
	 * kClosestIndexes). If more than half of the closest instances are 
	 * malignant, classify the growth as malignant. Otherwise classify
	 * as benign.
	 * 
	 * Return one of the global integer constants defined in this function. 
	 */
	public static int classify(int[][] trainData, int[] kClosestIndexes)
	{
		int benign = 0, malignant = 0;
		for(int i = 0; i < kClosestIndexes.length; i++)
		{
			int closestI = kClosestIndexes[i];
			int classification = trainData[closestI][trainData.length-1];
			if(classification == BENIGN)
				benign++;
			else if(classification == MALIGNANT)
				malignant++;
		}
		if(benign > malignant)
			return BENIGN;
		return MALIGNANT;
	}
	
	/**
	 * kNearestNeighbors classifies all the data instances in testData as 
	 * BENIGN or MALIGNANT using the helper functions you wrote and the kNN 
	 * algorithm.
	 * 
	 * For each instance of your test data, use your helper methods to find the
	 * K closest points, and classify your result based on that!
	 * @param trainData: all training instances
	 * @param testData: all testing instances
	 * @return: int array of classifications (BENIGN or MALIGNANT)
	 */
	public static int[] kNearestNeighbors(int[][] trainData, int[][] testData){
		int[] myResults = new int[K];
		for(int i = 0; i < K; i++)
		{
			if(testData.length >= K)
			{
				double[] allD = getAllDistances(trainData, testData[i]);
				int[] closestD = findKClosestEntries(allD);
				myResults[i] = classify(trainData, closestD);
			}
		}
		return myResults;
	}

	/**
	 * getAccuracy returns a String representing the classification accuracy.
	 *
	 * The retun String should be rounded to two decimal places followed by the % symbol.
	 * Examples:
	 * If 4 out of 5 outcomes were correctly predicted, the returned String should be: "80.00%"
	 * If 3 out of 9 outcomes were correctly predicted, the returned String should be: "33.33%"
	 * If 6 out of 9 outcomes were correctly predicted, the returned String should be: "66.67%"
	 * Look up Java's String Formatter to learn how to round a double to two-decimal places.
	 *
	 * This method should work for any data set, given that the classification outcome is always
	 * listed in the last column of the data set.
	 * @param: myResults: The predicted classifcations produced by your KNN model
	 * @param: testData: The original data that contains the true classifications for the test data
	 */
	public static String getAccuracy(int[] myResults, int[][] testData) {
		double correct = 0;
		for(int i = 0; i < myResults.length; i++)
		{
			if(testData.length >= myResults.length)
			{
				if(myResults[i] == testData[i][testData.length-1])
					correct ++;
			}
		}
		String returnString = String.format("%.2f%n", correct/testData.length);
		return returnString+"%";
	}
	
	
	//DO NOT MODIFY THE MAIN METHOD
	public static void main(String[] args) {

		int[][] trainData = InputHandler.populateData("./datasets/train_data.csv");
		int[][] testData = InputHandler.populateData("./datasets/test_data.csv");
		
		//Display the distances between instances of the train data. 
		//Points in the upper left corner (both benign) or in the bottom
		//right (both malignant) should be darker. 
		Grapher.createGraph(trainData);

		int[] myResults = kNearestNeighbors(trainData, testData);

		System.out.println("Model Accuracy: " + getAccuracy(myResults, testData));
	}

}
