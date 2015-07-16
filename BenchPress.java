import java.util.*;
import java.io.*;
import java.util.Scanner;

public class BenchPress
{
   public static int xpos = 0; 
   public static int ypos = 0; 
   public static ArrayList<double[]> data = new ArrayList<double[]>();
   public static void main(String[] args)
   {
      addData(0.0, 0.0, 0.0);
      addData(1.0, 0.0, 1.0);
      addData(4.0, 0.0, 2.0); 
      addData(6.0, 1.0, 3.0); 
      addData(5.5, -5.0, 5.0); 
      int x = callibrate(data); 
      System.out.println(positionx(data)); 
      System.out.println(positiony(data));
      //System.out.println(rightSpot(data));
      //if(stop(data)) 
      //System.out.println("Just..... DO IT - Shia LaBeouf");
   }
   public static void addData(double x, double y, double time)
   {
      data.add(new double[]{x,y,time});
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
      double totx = 0; 
      ArrayList<Double> xsum = new ArrayList<Double>(); 
      for(int i = 0; i < data.size(); i++)
      {
         xsum.add(data.get(i)[0]* data.get(i)[2]);
      }
      for(int i = 0; i < data.size(); i++)
      {
         totx = totx + xsum.get(i) * data.get(i)[2]; 
      }
      return totx; 
   }
   public static double positiony(ArrayList<double[]> data)
   {
      double toty = 0; 
      ArrayList<Double> ysum = new ArrayList<Double>(); 
      double time  = data.get(data.size() - 1)[2];
      for(int i = 0; i < data.size(); i++)
      {
         ysum.add(data.get(i)[1]* data.get(i)[2]);
      }
      for(int i = 0; i < data.size(); i++)
      {
         toty = toty + ysum.get(i) * data.get(i)[2]; 
      }
      return toty; 
   }
   public static boolean rightSpot(double rightx, double righty, ArrayList<double[]> data)
   {
      return(positiony(data) == righty && positionx(data) == rightx); 
   }
   public static int callibrate(ArrayList<double[]> data)
   {
      int index = 0; 
      for(int i = 0; i < data.size(); i++)
         if(data.get(i)[0] > 1 || data.get(i)[1] > 1)
            index = i; 
      return index; 
   }
}