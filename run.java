import java.util.*;
import java.io.*; 
 
public class run{
	
	public static ArrayList<double[]> data = new ArrayList<double[]>();	
	public static ArrayList<Double> magnitude = new ArrayList<Double>();
	public static double previousMagnitude = 0.0;
	public static double currentMagnitude = 0.0;
	public static int steps = 0;
	public static double runningThreshold = 0.0;
	public static double threshold = 4;
	public static ArrayList<Double> xList = new ArrayList<Double>();
	public static ArrayList<Double> yList = new ArrayList<Double>();
	public static ArrayList<Double> zList = new ArrayList<Double>();
	
    public static void main(String []args)throws FileNotFoundException{
		double time = 0.0; 		//get the time
		double x = 0.0;			//get x
		double y = 0.0; 			//get y
		double z = 0.0;
		
		double threshold = 0;	//set threshold
		double currentPace = 0.0;
		double previousPace = 0.0;
		
		readTextFile("data.txt", xList, yList);
		for(int i = 0; i < xList.size(); i++){
			
			calculateMagnitude(xList.get(i), yList.get(i));
			
		}

		System.out.print(steps);
		
		//if the feature is run: do run function
		//first collect data for 30 seconds
		//then calculate the rate of steps
		//delete array
		//every 10 seconds calculate array
		//if rate is significantly slower trigger sound
		
		String type = "run";
		if(type == "run"){
			
			calculateMagnitude(x,y);
			if(time == 30.0){
				currentPace = steps/time;
				System.out.print("Current pace" + currentPace);
				magnitude.clear();
				data.clear();
				previousPace = steps/time;
			}
			if(time > 30 && (time%10 == 0)){
				currentPace = steps/10;				
				magnitude.clear();
				data.clear();
				
				if(Math.abs(currentPace - previousPace) > runningThreshold){
					System.out.println("RUN FASTER");
				}
				
				
				
			}
		}
    }



	public static void readTextFile(String txtfile, ArrayList<Double> xlist, ArrayList<Double> ylist)throws FileNotFoundException {
		 FileInputStream fstream = new FileInputStream(txtfile);
	    DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String data;
		 try{
		    while ((data = br.readLine()) != null)   {
		      String[] tmp = data.split(",");    //Split space
				System.out.println(tmp[0]);
				xlist.add(Double.parseDouble(tmp[0]));
				ylist.add(Double.parseDouble(tmp[1]));

		    }
		 }
		 catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e){			
		}

	}
	
	
	 
	 public static void addData(double x, double y, double time){
	 	data.add(new double[]{x,y,time});	
		
	 }
	 

	 
	 
	/*public static void run(double x, double y){
	 
	 //while its still running
	 //keep adding data to the arraylist data
	 //calculate magnitude to find the number of steps
	 //at 30 secounds (t = 30) calculate the rate of steps
	 //every 10 seconds after (calculate the rate) and compare to initial rate
	 
	 
	 
	 
	 	ArrayList<Double> magnitude = new ArrayList<Double>();
		double magn = calculateMagnitude(x, y);
		magnitude.add(magn);
		System.out.println(magn);
		//getxy
		//send to calculate magnitude
		//get magnitude and add to magnitude
	 
	 
	 };*/
	 
	 
	 
	 public static void calculateMagnitude(double x, double y){
	 	double m = 0.0;
		m = Math.sqrt((Math.pow(x,2) + Math.pow(y,2)));
	 	magnitude.add(m);
		
		currentMagnitude = m;
		if(Math.abs(currentMagnitude - previousMagnitude) < threshold){

			steps++;		
		}
		
		previousMagnitude = m;
		
	 }
	 
	 
	 

	 

	
	 
} 


