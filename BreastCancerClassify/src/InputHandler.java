
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
import java.util.Arrays;

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
    public static int[][] populateData(String filename) throws IOException
    {
    	File f;
    	FileReader fr;
    	BufferedReader br = null;
    	ArrayList<String> dataStrings= new ArrayList<String>();
    	try
        {
        	f = new File(filename);
        	fr = new FileReader(f);
        	br = new BufferedReader(fr);
        			
        }catch(Exception E)
        {
        	E.printStackTrace();
        }
    	String line = br.readLine();
        while(line != null)
        {
        	dataStrings.add(line);
        	line = br.readLine();
        }
        int[][] data = new int[dataStrings.size()][dataStrings.get(0).length()];
        for(int i = 0; i < dataStrings.size(); i++)
        {
        	String[] read = dataStrings.get(i).split(",");
        	for(int j = 0; j < read.length; j++)
        		data[i][j] = Integer.parseInt(read[j]);
        }
        return data;
    }

}
