
/**
 * Name: Isayiah Lim 
 * Period: 1
 * Project: Breast Cancer Classify
 * Date last updated: 9/26
 *
 * This class handles reading and writing test data from a file.
 *
 */
 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * InputHandler processes all input files and also prints the accuracy of results.
 */
public class InputHandler
{
     /**
     * Returns a two dimensional int array corresponding to a csv file (defined by filename) of
     * ints.
     * @throws IOException 
     */
    public static int[][] populateData(String filename)
    {
    	//declares the file, filereader, and bufferedreader
    	File f;
    	FileReader fr;
    	BufferedReader br = null;
    	//arraylist storing each line
    	ArrayList<String> dataStrings= new ArrayList<String>();
    	try
        {
        	//initializes all the vars
    		f = new File(filename);
        	fr = new FileReader(f);
        	br = new BufferedReader(fr);
        	
        	//reads the first line
        	String line = br.readLine();
        	//adds every line to the arraylist
            while(line != null)
            {
            	dataStrings.add(line);
            	line = br.readLine();
            }
            //sets the new 2-d array to the length number of rows, and the according num of columns
            int[][] data = new int[dataStrings.size()][dataStrings.get(0).split(",").length];
            //makes each string in the arraylist into an array in the 2d
            for(int i = 0; i < dataStrings.size(); i++)
            {
            	String[] read = dataStrings.get(i).split(",");
            	for(int j = 0; j < read.length; j++)
            		data[i][j] = Integer.parseInt(read[j]);
            }
            fr.close();
            return data;
        			
        }catch(Exception E)
        {
        	E.printStackTrace();
        }
		return null;
    }

}
