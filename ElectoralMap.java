import java.io.File;
import java.util.Scanner;
import java.lang.Exception;
import java.io.FileNotFoundException;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class ElectoralMap
{
    private HashMap<String, Subregion> regions = new HashMap<>();
    private String[] candidates;
    
    private class Subregion
    {
        private String name;
        private int[] votes;
        private ArrayList<double[]> xCoords = new ArrayList<>();
        private ArrayList<double[]> yCoords = new ArrayList<>();
        private String color;

        public Subregion(String n, int[] v)
        {
            name = n;
            votes = v;
            color = winner();
        }
        public String winner()
        {
            if (votes[0] > votes[1] && votes[0] > votes[2])
            {
                return "RED";
            }
            else if (votes[1] > votes[0] && votes[1] > votes[2])
            {
                return "BLUE";
            }
            else if (votes[2] > votes[0] && votes[2] > votes[1])
            {
                return "GREEN";
            }
            else
            {
                return "WHITE"; //if no voting data
            }
        }
        public void addCoords(double[] xC, double[] yC)
        {
            xCoords.add(xC);
            yCoords.add(yC);
        }
        public String getName()
        {
            return name;
        }
        public String getColor()
        {
            return color;
        }
    }
    
    public void visualize(String regionName, int year) throws FileNotFoundException
    {
        scanVotingData(regionName, year);
        
        File fObj = new File("./input/" + regionName + ".txt");
        Scanner sObj = new Scanner(fObj);
        
        double yMin = sObj.nextDouble();
        double xMin = sObj.nextDouble();
        double yMax = sObj.nextDouble();
        double xMax = sObj.nextDouble();
        double h = xMax - xMin;
        double w = yMax - yMin;
        
        sObj.reset();
        sObj.nextLine();
        sObj.nextLine();
        sObj.nextLine();
        String curReg;
        
        while(sObj.hasNextLine())
        {
            curReg = sObj.nextLine();
            sObj.nextLine();
            String state = sObj.nextLine();
            int length = sObj.nextInt();
            //System.out.println(length);
            double[] xData = new double[length];
            double[] yData = new double[length];
            int ind = 0;
            
            while (sObj.hasNextLine() && ind < length)
            {
                //String[] temp = sObj.nextLine().split("   ");
                
                //System.out.println(temp[0]);
                xData[ind] = sObj.nextDouble();
                yData[ind] = sObj.nextDouble();
                ind++;
            }
            if (regions.get(curReg).getColor().equals("RED"))
            {
                StdDraw.setPenColor(StdDraw.RED);
            }
            else if (regions.get(curReg).getColor().equals("BLUE"))
            {
                StdDraw.setPenColor(StdDraw.BLUE);
            }
            else if (regions.get(curReg).getColor().equals("GREEN"))
            {
                StdDraw.setPenColor(StdDraw.GREEN);
            }
            else
            {
                StdDraw.setPenColor(StdDraw.WHITE);
            }
            
            regions.get(curReg).addCoords(xData, yData);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.polygon(xData, yData);
            
        }
        
        sObj.close();
        StdDraw.show();
    }
    public void scanVotingData(String rName, int y) throws FileNotFoundException
    {
        File fileObj = new File("./input/" + rName + y + ".txt");
        Scanner scanObj = new Scanner(fileObj);
        
        String[] firstR = scanObj.nextLine().split(",");
        candidates = new String[firstR.length-1];
        
        for (int i = 1; i < firstR.length; i++)
        {
            candidates[i-1] = firstR[i];
        }
        
        while (scanObj.hasNextLine())
        {
            String[] line = scanObj.nextLine().split(",");
            int[] vData = new int[line.length-1];
            
            for (int i = 1; i < line.length; i++)
            {
                vData[i-1] = Integer.parseInt(line[i]);
            }
            
            regions.put(line[0], new Subregion(line[0], vData));
        }
        
        scanObj.close();
    }
}
