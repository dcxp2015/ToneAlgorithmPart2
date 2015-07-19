import java.util.*;
import java.io.*;
import java.util.Scanner;
//Algorithm for Bench and Squad (since the two have similar motions)
public class BenchPress
{
   public static int xpos = 0; 
   public static int ypos = 0; 
   public static final double threshold = 1.5; 
   public static ArrayList<double[]> data = new ArrayList<double[]>();
   public static ArrayList<Double> xList = new ArrayList<Double>();
   public static ArrayList<Double> yList = new ArrayList<Double>();
   public static ArrayList<Double> timeList = new ArrayList<Double>(); 
   
   public static void main(String []args)throws FileNotFoundException
   {
      readTextFile("data1.txt", xList, yList, timeList);
      //displayData(data); 
      //System.out.println(data.get(19)[2]); 
      int a = callibrateStart(data); 
      System.out.println(stop(data)); 
      //System.out.println(data.size());
      //System.out.println(a); 
      //int x = callibrate(data); 
      System.out.println(positionx(data));
      System.out.println(positiony(data));  
      System.out.println((data.get(501)[2] - data.get(500)[2])*data.get(501)[0]); 
      //if(stop(data)) 
      //System.out.println("Just..... DO IT - Shia LaBeouf");
   }
   public static void readTextFile(String txtfile, ArrayList<Double> xlist, ArrayList<Double> ylist, ArrayList<Double> timelist)throws FileNotFoundException 
   {
      FileInputStream fstream = new FileInputStream(txtfile);
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String data;
      try
      {
         while ((data = br.readLine()) != null)   
         {
            String[] tmp = data.split(" ");    //Split space
         	//System.out.println(tmp[0]);
            timelist.add(Double.parseDouble(tmp[0])); 
            xlist.add(Double.parseDouble(tmp[1]));
            ylist.add(Double.parseDouble(tmp[2]));
         }
      }
      catch (FileNotFoundException e) 
      {
         e.printStackTrace();
      }
      catch(IOException e)
      {			
      }
      for(int i= 0; i < xlist.size(); i++)
      {
         addData(xlist, ylist, timelist, i);
      }
   
   }
   public static void addData(ArrayList<Double> x,ArrayList<Double> y,ArrayList<Double> t,int a)
   {
      data.add(new double[]{x.get(a),y.get(a),t.get(a)});
   }
   public static void displayData(ArrayList<double[]> data) 
   {
      for(int i = 0; i < data.size(); i++)
      {
         for(int j = 0; j < data.get(i).length; j++)
         {
            System.out.println(data.get(i)[j]);
         }
      } 
   }
   public static boolean stop(ArrayList<double[]> a)
   {
      for(int i = 0; i < a.size(); i++)
      {
         if(a.get(i)[0] < .5 && a.get(i)[0] > -.5 && a.get(i)[1] < .5 && a.get(i)[1] > -.5)
            return true; 
      }
      return false; 
   }
   public static double positionx(ArrayList<double[]> data)
   {
      double firstriemmansumx = 0; 
      double secondriemmansumx = 0; 
      ArrayList<Double> xsum1 = new ArrayList<Double>(); 
      for(int i = 1; i < data.size(); i++)
      {
         firstriemmansumx += data.get(i)[0] * (data.get(i)[2]-data.get(i-1)[2]); 
         xsum1.add(firstriemmansumx); 
      }
      for(int c = 1; c < xsum1.size(); c++)
      {
         secondriemmansumx += xsum1.get(c) * (data.get(c)[2]-data.get(c-1)[2]);
      }
      return secondriemmansumx; 
   }
   public static double positiony(ArrayList<double[]> data)
   {
      double firstriemmansumy = 0; 
      double secondriemmansumy = 0; 
      ArrayList<Double> ysum1 = new ArrayList<Double>(); 
      for(int i = 1; i < data.size(); i++)
      {
         firstriemmansumy += data.get(i)[1] * (data.get(i)[2]-data.get(i-1)[2]); 
         ysum1.add(firstriemmansumy); 
      }
      for(int c = 1; c < ysum1.size(); c++)
      {
         secondriemmansumy += ysum1.get(c) * (data.get(c)[2]-data.get(c-1)[2]);
      }
      return secondriemmansumy;    
   }
   public static boolean rightSpot(double rightx, double righty, ArrayList<double[]> data)
   {
      double diffy = Math.abs(positiony(data) - righty); 
      double diffx = Math.abs(positionx(data) - rightx); 
      return(diffy < threshold && diffx < threshold); 
   }
   public static int callibrateStart(ArrayList<double[]> data)
   {
      int index = 0; 
      boolean found = false;
      for(int i = 0; i < data.size() && !found; i++)
         if(data.get(i)[0] > 0 || data.get(i)[1] > 0){
            index = i;
            found = true; 
         }
      return index;    
   }
}