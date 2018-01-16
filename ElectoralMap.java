import java.io.File;
import java.util.Scanner;
import java.lang.Exception;
import java.io.FileNotFoundException;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class ElectoralMap
{
    private static HashMap<String, HashMap<String, ArrayList<Subregion>>> outer = new HashMap<>();

    public static class Subregion
    {
        private String name;
        private int[] votes;
        private double[] xCoords;
        private double[] yCoords;
        private Color c = StdDraw.WHITE;
        private double[] minXY;
        private double[] maxXY;

        public Subregion(String n, double[] x, double[] y)
        {
            name = n;
            xCoords = x;
            yCoords = y;
        }
        public String getName()
        {
            return name;
        }
        public Color getColor()
        {
            return c;
        }
        public void changeName(String nN)
        {
            name = nN;
        }
        public void changeColor(Color nC)
        {
            c = nC;
        }
        public void changeVote(int[] nV)
        {
            votes = nV;
        }
    }

    public static void main(String rN, int y) throws Exception
    {
        scanGeoData(rN);
        scanVotingData(y);
        visualize(y);
    }
    public static void visualize(int y) throws Exception
    {
        for (String reg : outer.keySet())
        {
            for (String subreg : outer.get(reg).keySet())
            {
                for (Subregion s : outer.get(reg).get(subreg))
                {
                    double[] xSub = s.xCoords;
                    double[] ySub = s.yCoords;
                    Color cSub = s.c;
                    StdDraw.setPenColor(cSub);
                    StdDraw.filledPolygon(xSub, ySub);
                }
            }
        }

        StdDraw.show();
    }
    public static void scanGeoData(String r) throws Exception
    {
        StdDraw.enableDoubleBuffering();

        File inputF = new File("./input/" + r + ".txt");
        Scanner inputObj = new Scanner(inputF);

        double[] minXY = new double[2];
        double[] maxXY = new double[2];
        String[] l1 = inputObj.nextLine().split("   ");
        String[] l2 = inputObj.nextLine().split("   ");
        int numSubs = Integer.parseInt(inputObj.nextLine());
        for (int i = 0; i < l1.length; i++)
        {
            minXY[i] = Double.parseDouble(l1[i]);
            maxXY[i] = Double.parseDouble(l2[i]);
        }

        int c = 0;
        int length = 0;
        boolean front = true;
        double[] xC;
        double[] yC;
        int subC = 0;
        int xH = (int)(maxXY[0]-minXY[0]);
        int yH = (int)(maxXY[1]-minXY[1]);
        StdDraw.setCanvasSize(512*(xH/yH), 512);
        StdDraw.setXscale(minXY[0], maxXY[0]);
        StdDraw.setYscale(minXY[1], maxXY[1]);

        while (inputObj.hasNextLine() && subC < numSubs)
        {
            ArrayList<Subregion> subregs = new ArrayList<>();

            inputObj.nextLine();
            String subName = inputObj.nextLine();
            String rName = inputObj.nextLine();
            length = Integer.parseInt(inputObj.nextLine());

            if(subName.contains("county") || subName.contains("Parish") || subName.contains("city"))
            {
                int ind = subName.length(); 

                if(subName.indexOf("city") > subName.indexOf("Parish") && subName.indexOf("city") > subName.indexOf("county"))
                {
                    ind = subName.indexOf("city");
                }
                else if(subName.indexOf("county") > subName.indexOf("Parish") && subName.indexOf("county") > subName.indexOf("city"))
                {
                    ind = subName.indexOf("county");
                }
                else
                {
                    ind = subName.indexOf("Parish");
                }

                subName = subName.substring(0, ind-1);
            }

            xC = new double[length];
            yC = new double[length];
            c = 0;
            while (c < length)
            {
                String[] splitCoord = inputObj.nextLine().split("   ");
                xC[c] = Double.parseDouble(splitCoord[0]);
                yC[c] = Double.parseDouble(splitCoord[1]);

                c++;
            }

            subC++;
            Subregion curReg = new Subregion(subName, xC, yC);
            Subregion curOuterReg = new Subregion(rName, xC, yC);
            if (outer.containsKey(rName))
            {
                if (outer.get(rName).containsKey(subName))
                {
                    outer.get(rName).get(subName).add(curReg);
                    outer.get(rName).put(subName, outer.get(rName).get(subName));
                }
                else
                {
                    ArrayList<Subregion> temp = new ArrayList<>();
                    temp.add(curReg);
                    outer.get(rName).put(subName, temp);
                }
            }
            else
            {
                HashMap<String, ArrayList<Subregion>> inner = new HashMap<>();
                ArrayList<Subregion> subRegs = new ArrayList<>();
                subRegs.add(curReg);
                inner.put(subName, subRegs);
                outer.put(rName, inner);
            }
        }

        StdDraw.show();
        inputObj.close();
    }
    public static void scanVotingData(int y) throws Exception
    {
        for (String reg : outer.keySet())
        {
            int[] rVotes = new int[3];
            File inputF = new File("./input/" + reg + y + ".txt");
            Scanner inputObj = new Scanner(inputF);
            inputObj.nextLine();
            while (inputObj.hasNextLine())
            {
                String[] tempV = inputObj.nextLine().split(",");
                rVotes[0] = Integer.parseInt(tempV[1]);
                rVotes[1] = Integer.parseInt(tempV[2]);
                rVotes[2] = Integer.parseInt(tempV[3]);
                
                if (outer.get(reg).containsKey(tempV[0]))
                {
                    ArrayList<Subregion> temp = outer.get(reg).get(tempV[0]);
                    
                    if(rVotes[0] > rVotes[1] && rVotes[0] > rVotes[2])
                    {
                        for(int i = 0; i < temp.size(); i++)
                        {
                            temp.get(i).changeColor(StdDraw.RED);
                        }
                    }
                    else if(rVotes[1] > rVotes[0] && rVotes[1] > rVotes[2])
                    {
                        for(int i = 0; i < temp.size(); i++)
                        {
                            temp.get(i).changeColor(StdDraw.BLUE);
                        }
                    }
                    else
                    {
                        for(int i = 0; i < temp.size(); i++)
                        {
                            temp.get(i).changeColor(StdDraw.GREEN);
                        }
                    }
                }
            }
            
            inputObj.close();
        }
    }
}
