package mainWorldTask1Advanced;
import java.io.IOException;
//import java.util.Scanner;
//import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

public class MainWorldTask1Advanced {

	double position_correction = 2.4; //Corrects the y-Position of all calculated values

	public static void main(String[] args) throws IOException {
		MainWorldTask1Advanced start = new MainWorldTask1Advanced();
		start.startUp();
	}

	public void startUp() throws IOException{
		PrintStream ps = new PrintStream("Task1_rnd.world");
		includes(ps);
		init(ps);
		floriInit(ps);
		wallInits(ps);
		obstacleInit(potCalculation(), ps);
		ps.close();
	}

	public ArrayList<Pot> potCalculation(){
		ArrayList<Pot> arr = new ArrayList<Pot>();

		double y = 0;
		final double pi = 3.141592;
		final double mu = 7.5;
		double width = 0.80;		//Width between row boarders 0.75 [m] + 2*0.025[m](obstacle dimension)
		double numRows = 12;		//Number of rows that will be created
		double distance = 0.1;		//Space between each obstacle in [m]

		for (double row = 1; row <=(numRows*width); row=row+width){
			for (double x = 0; x <= 15; x=x+distance){
				y = (Math.exp(-0.5*((x-mu)*(x-mu)))/Math.sqrt(2*pi) + 0.5)*2;

				arr.add(new Pot(x+2,y+row));
			}
		}

		return arr;
	}

	public void obstacleInit(ArrayList<Pot> arr, PrintStream ps) throws IOException{

		//Calculated pot inits
		for(int i = 0; i<arr.size(); i++){
			int rnd = randomNumber();
			ps.println("\nobstacle"+rnd);
			ps.println("(");
			ps.println("\tname \"obstacle"+ i +"\"");
			ps.println("\tpose ["+ arr.get(i).getX() +" "+ (arr.get(i).getY()-position_correction)+ " 0 0]");
			ps.println(")");
		}
	}

	public int randomNumber(){
		double rnd = Math.random();

		if (rnd < 0.2){
			return 1;
		}
		else if(rnd < 0.4){
			return 2;
		}
		else if(rnd < 0.6){
			return 3;
		}
		else if(rnd < 0.8){
			return 4;
		}
		else if(rnd < 1){
			return 5;
		}
		else{
			return 1;
		}
	}

	public void floriInit(PrintStream ps) throws IOException{
		//Floribot init
		ps.println("\nfloribot");
		ps.println("(");
		ps.println("\tname \"flori\"");
		ps.println("\tpose [0 "+(2.4-position_correction)+" 0 0]");
		ps.println(")");	
	}

	public void wallInits(PrintStream ps) throws IOException{
		//Wall inits
		ps.println("\nwall_vertical");
		ps.println("(");
		ps.println("\tname \"wall_vertical_1\"");
		ps.println("\tpose [-1 "+(6-position_correction)+" 0 0]");
		ps.println(")");

		ps.println("\nwall_vertical");
		ps.println("(");
		ps.println("\tname \"wall_vertical_2\"");
		ps.println("\tpose [20 "+(6-position_correction)+" 0 0]");
		ps.println(")");

		ps.println("\nwall_horizontal");
		ps.println("(");
		ps.println("\tname \"wall_horizontal_1\"");
		ps.println("\tpose [9.5 "+(0-position_correction)+" 0 90]");
		ps.println(")");

		ps.println("\nwall_horizontal");
		ps.println("(");
		ps.println("\tname \"wall_horizontal_2\"");
		ps.println("\tpose [9.5 "+(12-position_correction)+" 0 90]");
		ps.println(")");	
	}

	public void includes(PrintStream ps) throws IOException{
		//Includes
		ps.println("include \"floribot.inc\"");
	}

	public void obstaclesModel(PrintStream ps, double radius, int number) throws IOException{

		//Define obstacle model
		ps.println("\ndefine obstacle"+number+" model");
		ps.println("(");
		ps.println("\t# a obstacle is rectangular");
		ps.println("\tsize ["+radius+" "+radius+" 0.2]");
		ps.println("\tcolor \"blue\"");
		ps.println(")");
	}

	public void init(PrintStream ps) throws IOException{
		//Define Window
		ps.println("\nwindow");
		ps.println("(");
		ps.println("\tsize [400 400]");
		ps.println("\n\t# camera options");
		ps.println("\tcenter [0 0]");
		ps.println("\trotate [0 0]");
		ps.println("\tscale 100.0");
		ps.println("\n\t# perspective camera options");
		ps.println("\tpcam_loc [0 -4 2]");
		ps.println("\tpcam_angle [70 0]");
		ps.println("\n\t# GUI options");
		ps.println("\tshow_data 0");
		ps.println("\tshow_flags 1");
		ps.println("\tshow_blocks 1");
		ps.println("\tshow_clock 1");
		ps.println("\tshow_footprints 0");
		ps.println("\tshow_grid 1");
		ps.println("\tshow_trailrise 0");
		ps.println("\tshow_trailarrows 0");
		ps.println("\tshow_trailfast 0");
		ps.println("\tshow_occupancy 0");
		ps.println("\tshow_tree 0");
		ps.println("\tpcam_on 0");
		ps.println("\tscreenshots 0");
		ps.println(")");

		//Define wall model
		ps.println("\ndefine wall_vertical model");
		ps.println("(");
		ps.println("\t# a wall is rectangular");
		ps.println("\n\tpolygons 1");
		ps.println("\tpolygon[0].points 4");
		ps.println("\tpolygon[0].points[0] [0 0]");
		ps.println("\tpolygon[0].points[1] [1 0]");
		ps.println("\tpolygon[0].points[2] [1 0.01]");
		ps.println("\tpolygon[0].points[3] [0 0.01]");
		ps.println("\tsize [0.2 12 0.5]");
		ps.println("\tcolor \"black\"");
		ps.println(")");

		//Define wall model
		ps.println("\ndefine wall_horizontal model");
		ps.println("(");
		ps.println("\t# a wall is rectangular");
		ps.println("\n\tpolygons 1");
		ps.println("\tpolygon[0].points 4");
		ps.println("\tpolygon[0].points[0] [0 0]");
		ps.println("\tpolygon[0].points[1] [1 0]");
		ps.println("\tpolygon[0].points[2] [1 0.01]");
		ps.println("\tpolygon[0].points[3] [0 0.01]");
		ps.println("\tsize [0.2 21 0.5]");
		ps.println("\tcolor \"black\"");
		ps.println(")");

		//Define five different obstacle models with increasing radius
		//Starting at 0.03 m + 0.02 m on every loop up to a maximum of 5 different loop runtime
		int k = 1;
		for (double i = 0.03; i <= 0.13; i=i+0.02){
			obstaclesModel(ps, i, k);
			k++;
		}
	}
}
