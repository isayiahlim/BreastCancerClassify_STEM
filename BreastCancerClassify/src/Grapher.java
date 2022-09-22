
// ===============================================================================================
//
//                                         DO NOT MODIFY
//
// ===============================================================================================

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Grapher class creates a JPanel displaying a graph of pairwise distances. The upper left
 * corner represents pairwise distances between benign clumps, and the bottom right corner
 * represents distances between malignant clumps.
 *
 * Darker colors represent closer distances, and lighter colors represent farther distances. If
 * calculateDistance is correctly implemented, the top left and bottom right corners should be dark,
 * and the other two areas should be light.
 */
public class Grapher extends JPanel
{
    private static final long serialVersionUID = 1L;

    private int[][] trainData;

    public void setData(int[][] data)
    {
        Arrays.sort(data, new java.util.Comparator<int[]>()
        {
            @Override
            public int compare(int[] a, int[] b)
            {
                return Double.compare(a[a.length - 1], b[b.length - 1]);
            }
        });
        trainData = data;
    }

    /**
     * Called by the runtime system whenever the panel needs painting. Prints axes titles and pixel
     * array.
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // Print Axes Titles
        Graphics2D g2 = (Graphics2D) g;
        Font font = g.getFont().deriveFont(20.0f);
        g.setFont(font);

        g2.translate(getWidth() / 2, getHeight() / 2);
        g2.rotate(-Math.PI / 2);
        g2.drawString("<--------- Malignant                 Benign ------->", -300, -320);
        g2.rotate(Math.PI / 2);

        g2.translate(-getWidth() / 2, -getHeight() / 2);
        g2.drawString("<--------- Benign                        Malignant ------->", 70, 40);

        // Find max distances to scale them from 0 to 1
        double maxDist = 0;
        for (int i = 0; i < trainData.length; i++)
        {
            for (int j = 0; j < trainData.length; j++)
            {
                double dist = BreastCancerClassify.calculateDistance(trainData[i], trainData[j]);
                if (dist > maxDist)
                {
                    maxDist = dist;
                }
            }
        }

        // Output x,y pixels based on scaled distances.
        for (int i = 0; i < trainData.length; i++)
        {
            for (int j = 0; j < trainData.length; j++)
            {
                double dist = BreastCancerClassify.calculateDistance(trainData[i], trainData[j]);
                int val = (int) (dist * 255 / maxDist);
                g.setColor(new Color(val, val, val));
                g.fillRect(i + 50, j + 50, 1, 1);
            }
        }
    }

    /**
     * Creates a graph of pairwise distances between data and itself.
     */
    public static void createGraph(int[][] data)
    {
        JFrame frame = new JFrame("Distances between data instances");
        frame.setSize(data.length + 100, data.length + 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new Grapher();
        ((Grapher) panel).setData(data);
        panel.setBackground(Color.WHITE);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
