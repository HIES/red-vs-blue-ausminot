import java.io.File;
import java.util.Scanner;
import java.lang.Exception;
import java.awt.Color;

public class EmptyMap
{
    
    public static void visualize(String region) throws Exception
    {
        
        File f = new File("./input/" + region + ".txt"); 
        Scanner inputObject = new Scanner(f);
        inputObject.useDelimiter("   ");
        String minXY = inputObject.nextLine();
        String maxXY = inputObject.nextLine();
        String[] minxyString = minXY.split("   ");
        String[] maxxyString = maxXY.split("   ");
        double[] minxyDouble = new double[minxyString.length];
        double[] maxxyDouble = new double[maxxyString.length];
        for(int i=0;i<minxyString.length;i++)
        {
            minxyDouble[i] = Double.parseDouble(minxyString[i]);
        }
        for(int i=0;i<maxxyString.length;i++)
        {
            maxxyDouble[i] = Double.parseDouble(maxxyString[i]);
        }
        StdDraw.setCanvasSize((int) (512*(maxxyDouble[0]-minxyDouble[0])/(maxxyDouble[1]-minxyDouble[1])), 512);
        StdDraw.setXscale(minxyDouble[0], maxxyDouble[0]);
        StdDraw.setYscale(minxyDouble[1], maxxyDouble[1]);
        
        int rowRegions = Integer.parseInt(inputObject.nextLine());
        int index = 0;
        while (index < rowRegions)
        {
            inputObject.nextLine();
            inputObject.nextLine();
            inputObject.nextLine();
            int rowSubregions = Integer.parseInt(inputObject.nextLine());
            double[] xPoints = new double[rowSubregions];
            double[] yPoints = new double[rowSubregions];
            
            for (int i = 0; i < rowSubregions; i++)
            {
                String[] coords = inputObject.nextLine().split("   ");
                xPoints[i] = Double.parseDouble(coords[0]);
                yPoints[i] = Double.parseDouble(coords[1]);
            }
            index++;
            
            StdDraw.polygon(xPoints, yPoints);
        }
        
        
        StdDraw.show();
        inputObject.close();
    }
}
