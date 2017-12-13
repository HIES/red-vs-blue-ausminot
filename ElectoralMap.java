import java.io.File;
import java.util.Scanner;
import java.lang.Exception;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class ElectoralMap
{
    private HashMap<String, ArrayList<Subregion>> regions;
    
    private class Subregion
    {
        private String name;
        private int[] votes;
        private double[] xCoords;
        private double[] yCoords;
        private Color cVal;
        
        public Subregion(String n, int[] v, double[] xC, double[] yC, Color cV)
        {
            name = n;
            votes = v;
            xCoords = xC;
            yCoords = yC;
            cVal = cV;
            
            
        }
        

    }
    
    public void visualize(String regionName, int year) throws Exception
    {
        regions = new HashMap<>();
        ArrayList<Subregion> subs = new ArrayList<>();
        
        
        EmptyMap.visualize(regionName);
        
        //regions.put(regionName, 
        
        
    }
    
    
}
