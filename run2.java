import java.util.*;
import java.io.*;

public class run2{


	public static ArrayList<Double> xList = new ArrayList<Double>();
	public static ArrayList<Double> yList = new ArrayList<Double>();
	public static ArrayList<Double> zList = new ArrayList<Double>();
	public static ArrayList<Double> tList = new ArrayList<Double>();
	public static ArrayList<Double> magn  = new ArrayList<Double>();
	public static ArrayList<Double> peak  = new ArrayList<Double>();
	public static ArrayList<Double> velocityX = new ArrayList<Double>();
	public static ArrayList<Double> velocityY = new ArrayList<Double>();
	public static ArrayList<Double> velocityZ = new ArrayList<Double>();
	public static ArrayList<Double> velocityM = new ArrayList<Double>();
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
		//System.out.println(steps);
		//getMax();
		//calculateVelocity();
		checker();
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
		      String[] tmp = data.split(",");    //Split space
				//System.out.println(tmp[0]);
				tList.add(Double.parseDouble(tmp[0]));
				xList.add(Double.parseDouble(tmp[1]));
				yList.add(Double.parseDouble(tmp[2]));
				zList.add(Double.parseDouble(tmp[3]));

				magn.add(Math.sqrt(Math.pow(Double.parseDouble(tmp[1]),2) + Math.pow(Double.parseDouble(tmp[2]),2) + Math.pow(Double.parseDouble(tmp[3]),2)      ));
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
				//System.out.println(i);
				steps++;
			}
		}

		previousMagnitude = m;
	 }


	 //wtf am i doing????
	 //check to see max/min of acceeration
	 //calculate average

	 public static void getMax(){
	 	//ArrayList<Double> peak = new ArrayList<Double>();
	 	for(int i = 1; i < magn.size()-1; i++){
	 		if(magn.get(i)-magn.get(i-1) > 0 && magn.get(i)-magn.get(i+1) > 0){
	 			peak.add(magn.get(i));
	 			System.out.println(magn.get(i));
	 		}
	 	}
	 	//System.out.println(peak.size());
	 	// Collections.sort(peak);
	 	// for(int i = peak.size()/2; i < peak.size(); i++){
	 	// 	System.out.println()

	 	// }



	 }

	public static void calculateVelocity(){

		double vM = 0;
		velocityX.add(0.0);
		velocityY.add(0.0);
		velocityZ.add(0.0);
		for(int i = 1; i < xList.size()-2; i++){
			double dt = tList.get(i)-tList.get(i-1);
			//System.out.println(dt);
			double vX = (xList.get(i)+xList.get(i-1)/2)*dt + velocityX.get(i-1);
			//System.out.println(vX);
			//System.out.println((yList.get(i) + yList.get(i-1)/2)*dt);
			double vY = (yList.get(i)+yList.get(i-1)/2)*dt + velocityY.get(i-1);//yList.get(i)*dt;
			//System.out.println(vY);
			double vZ = (zList.get(i)+zList.get(i-1)/2)*dt + velocityZ.get(i-1);
			vM = Math.sqrt(Math.pow(vX,2) + Math.pow(vY,2)+Math.pow(vZ,2));
			velocityX.add(vX);
			//System.out.println(vY);
			velocityY.add(vY);
			velocityZ.add(vZ);
			velocityM.add(vM);
			//System.out.println(vM);
			



			//System.out.println(vX);
			//System.out.println(vM);
			
//(acceleration[i] + acceleration[i-1])/2 * interval + velocity[i-1]			
		}
	}

	//get current batch of data, calculate velocities
	//compare avg velocity to previous velocity
	//if previous velocity is greater than the current velocity by a certain threshld
	//trigger sound byte
	


	public static void checker(){
		double currAvg = 0;
		double prevAvg = 0;
		double pprevAvg = 0;
		double max = 0;
		double min = 0;
		double prevMax = 0;
		ArrayList<Double> second = new ArrayList<Double>();
		int prevI = 0;
		for(int i = 1; i < tList.size()-3; i++){
			currAvg+=magn.get(i);
			
			second.add(magn.get(i)); 


			//System.out.println(currAvg);
			// if(Math.ceil(tList.get(i-1)) == 29 && Math.ceil(tList.get(i)) == 30 && Math.ceil(tList.get(i+1)) == 30){
			// 	currAvg=currAvg/i;
			// 	prevAvg = currAvg;
			// 	currAvg= 0;
			// 	prevI = i;
			// }
			// System.out.println(Math.ceil(tList.get(i)));
			
			if(Math.floor(tList.get(i))%1 == 0 && (Math.floor(tList.get(i-1)) == Math.floor(tList.get(i))-1)){// &&  (Math.ceil(tList.get(i-1)) == Math.ceil(tList.get(i))+1)){
				//System.out.println(Math.floor(tList.get(i)));
				Collections.sort(second);
				max = second.get(second.size()-1);
				min = second.get(0);
				//System.out.println(max);
				//System.out.println(min);
				
				/*System.out.println(Math.floor(tList.get(i)));
				System.out.println(currAvg);
				System.out.println(i);
				System.out.println(prevI);
				System.out.println(i-prevI);*/
				
				currAvg=currAvg/(i-prevI);
				
				/*System.out.println(currAvg);
				System.out.println(prevAvg);
				System.out.println(currAvg);
				System.out.println(prevAvg);*/
				
				//System.out.println(currAvg-prevAvg);
				
				
				if(currAvg < prevAvg && (currAvg/prevAvg) < 0.7){
				//if((currAvg-prevAvg) < 0){
					//System.out.println(currAvg);
					//System.out.println(prevAvg);
					//System.out.println(tList.get(i));
					System.out.println(Math.floor(tList.get(i)));
					System.out.println("run faster");
					
				}	
				pprevAvg  = prevAvg;
				prevI = i;
				prevAvg = currAvg;
				currAvg = 0;
				prevMax = max;
				second.clear();
				
				//System.out.println
				
				
			}

		}


	}





}