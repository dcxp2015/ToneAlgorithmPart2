
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

//get array of acceleration data points
//calculate the velocity xy and magnitude and write to velocity array
//calculate the average velocity
//compare to previous velocity
// array for ax ay time

int *velocityX;
int *prevVelocity;
double accelerationX[] = { 0.0, 2.3, 3.4, 4.5 };
double *ax;
double accelerationY[] = { 0.0, 2.3, 3.4, 4.5 };
double *ay;
double accelerationZ[] = { 0.0, 2.3, 3.4, 4.5 };
double *az;
double time[] = { 0.0, 0.2, 0.3, 0.4 };
double *t;
double avg;
double prevAvg;
double accelerationM[4];
double *aM;

void calculateMagnitude(){
	int i;
	double dt, magn, prevX, prevY, prevZ;
	ax = accelerationX;
	ay = accelerationY;
	az = accelerationZ;
	aM = accelerationM;
	t = time;
	prevX = 0;
	prevY = 0;
	prevZ = 0;
	for (i = 1; i < 3; i++){
		dt = *(t + i) - *(t);
		magn = sqrt(*(ax + i)**(ax + i) + *(ay + i)**(ay + i) + *(ay + i)**(ay + i));
		/*vX = ((*(ax + i)+prevX) / 2)*dt + prevX;
		vY = ((*(ay + i) + prevY) / 2)*dt + prevY;
		vZ = ((*(az + i) + prevZ) / 2)*dt + prevY;*/
		
		//magn = sqrt(vX*vX + vY*vY+vZ*vZ);
		/*prevX = vX;
		prevY = vY;
		prevZ = vZ;*/
		accelerationM[i] = magn;
	}
}

void averageAcceleration(){
	int i;
	avg =0;
	for (i = 1; i < 3; i++){
		avg+=*(aM+i);
	}
	avg = avg / 4;	//change to specific size
	printf("%f\n", avg);
}

void checker(){
	prevAvg = 0;
	if (avg < prevAvg && (avg/prevAvg)<0.7){
		printf("run faster");
	}
	prevAvg = avg;

}

int main(int argc, char** argv){
	calculateMagnitude();
	averageAcceleration();
	checker();

	getchar();
	return 0;
}











