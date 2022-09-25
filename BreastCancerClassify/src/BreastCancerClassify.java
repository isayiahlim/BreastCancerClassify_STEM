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
		//distance formula: adds the square of each first-second value
		for(int i = 1; i < first.length-1; i++)
		{
			total += (first[i]-second[i])*(first[i]-second[i]);
		}
		//returns square root
		return Math.sqrt(total);
	}
	
	/**
	 * getAllDistances creates an array of doubles with the distances
	 * to each training instance. The double[] returned should have the 
	 * same number of instances as trainData. 
	 */
	public static double[] getAllDistances(int[][] trainData, int[] testInstance)
	{
		//new array storing all the calculated distances
		double[] allDistances = new double[trainData[0].length];
		//for each value in a trainData row
		for(int i = 0; i < trainData[0].length; i++) 
		{
			//for each array in traindata, calculates the distance between that and instance
			for(int j = 0; j < trainData.length; j++)
				allDistances[j] = calculateDistance(trainData[j], testInstance);
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
		//int array with the indexes of the K closest entries
		int[] kClosestIndexes = new int[K];
		//array equal to parameter array, can be modified
		double[] newAllD = allDistances;
		//variables representing smallest value/index
		int index = -1;
		double smallest = 1000000;
		
		//fills the kClosestIndexes array
		for(int i = 0; i < K; i++)
		{
			//checks each distance - if the number is smaller, stores index and value
			for(int j = 0; j < newAllD.length; j++)
			{
				if(newAllD[j] < smallest)
				{	
					index = j;
					smallest = newAllD[j];
				}
			}
			//stores the smallest in the array
			kClosestIndexes[i] = index;
			//sets the previous smallest to a high value so it doesn't get chosen again
			newAllD[index] = 1000000;
			//resets vars
			index = -1;
			smallest = 1000000;
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
		//counters for total benign/malignant entries
		int benign = 0, malignant = 0;
		//runs through the kClosestIndexes array
		for(int i = 0; i < K; i++)
		{
			//looks at the last variable in the stored index's row
			int classification = trainData[kClosestIndexes[i]][trainData[0].length-1];
			//counts whether benign or malignant
			if(classification == BENIGN)
				benign++;
			else if(classification == MALIGNANT)
				malignant++;
		}
		//returns malignant if >= benign
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
		//int array with same length as the tested data
		int[] myResults = new int[testData.length];
		//for each row in testData, classifies it using KNN, then stores the results
		for(int i = 0; i < testData.length; i++)
		{
			myResults[i] = classify(trainData, 
					findKClosestEntries(getAllDistances(trainData, testData[i])));
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
		//counter for total number of correct
		double correct = 0;
		//compares each result to the test data
		for(int i = 0; i < myResults.length; i++)
		{
			//avoid index out of bounds errors
			if(testData.length >= myResults.length)
			{
				if(myResults[i] == testData[i][testData[0].length-1])
					correct ++;
			}
		}
		//puts the string in the right format
		return String.format("%.2f", (correct/testData.length)*100) + "%";
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
