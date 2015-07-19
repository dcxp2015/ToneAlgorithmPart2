import java.util.*;
import java.io.*;

public class run{


	public static ArrayList<Double> xList = new ArrayList<Double>();
	public static ArrayList<Double> yList = new ArrayList<Double>();
	public static ArrayList<Double> tList = new ArrayList<Double>();
	public static ArrayList<Double> magn  = new ArrayList<Double>();
	public static double currentMagnitude = 0;
	public static double previousMagnitude = 0;
	public static double currentPace = 0;
	public static double previousPace = 0;
	public static int steps = 0;

	public static void main(String []args)throws FileNotFoundException{
		double time = 0;
		readTextFile("data.txt");
		double threshold = getThreshold();
		for(int i = 0; i < xList.size(); i++){
			detectSteps(xList.get(i), yList.get(i), i, threshold);
		}
		System.out.println(steps);



		String type = "run";
		if(type == "run"){
			if(time == 30.0){
				currentPace = steps/time;
				System.out.print("Current pace" + currentPace);
				magn.clear();

				previousPace = steps/time;
			}
			if(time > 30 && (time%10 == 0)){
				currentPace = steps/10;				
				magn.clear();

				
				if(currentPace - previousPace < 0){
					System.out.println("RUN FASTER");
				}		
			}
		}

	}


	public static void readTextFile(String txtfile)throws FileNotFoundException {
		FileInputStream fstream = new FileInputStream(txtfile);
	    DataInputStream in = new DataInputStream(fstream);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String data;
		 try{
		    while ((data = br.readLine()) != null)   {
		      String[] tmp = data.split("\\t");    //Split space
				//System.out.println(tmp[0]);

				xList.add(Double.parseDouble(tmp[0]));
				yList.add(Double.parseDouble(tmp[1]));
				tList.add(Double.parseDouble(tmp[2]));
				magn.add(Math.sqrt(Math.pow(Double.parseDouble(tmp[0]),2) + Math.pow(Double.parseDouble(tmp[1]),2)));
		    }
		 }
		 catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e){			
		}
	}	


	public static ArrayList<Double> copy(ArrayList<Double> lst){
		ArrayList<Double> copy = new ArrayList<Double>();
		for(int i = 0; i < lst.size(); i++){
			copy.add(lst.get(i));
		}
		return copy;
	}



	public static double getThreshold(){
		ArrayList<Double> sortedmagn = copy(magn);
		Collections.sort(sortedmagn);
		double threshold = 0;
		for(int i = 0; i < sortedmagn.size()/2; i++){
			threshold+=sortedmagn.get(i);		
		}
		threshold = threshold/sortedmagn.size()/2;

		return threshold;
	}

	
	public static void detectSteps(double x, double y, int i, double threshold){
		double	m = Math.sqrt((Math.pow(x,2) + Math.pow(y,2)));
	
		currentMagnitude = m;
		magn.add(currentMagnitude);
		if(i!=magn.size()-1){
			if((currentMagnitude < threshold)&&(previousMagnitude-currentMagnitude > 0) && (currentMagnitude-magn.get(i+1) < 0)){
				steps++;
			}
		}

		previousMagnitude = m;
	 }

}