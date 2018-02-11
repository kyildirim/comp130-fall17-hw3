/*|Student Name: 
 *|Student Number: 
 *|COMP 130/131 Homework #3				
 *|Fine A Car! - A Crossroads Simulation	
 */

import acm.program.GraphicsProgram;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GRect;
import java.awt.Color;
import java.awt.Font;

//Ignore SuppressWarnings tag, it is irrelevant to the course.
@SuppressWarnings("serial")
public class FineACar extends GraphicsProgram {

	//Do not change these variables.
	/**
	 * Light objects.
	 */
	GOval vLight, hLight;
	//Your code starts here.

	
	
	//Your code ends here.
	/*
	 * DO NOT change anything below this line!
	 */
	
	/** 
	 * Initialization method. This method is guaranteed to run before run().
	 */
	public void init(){
		
		//Initialize the screen size.
		this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		//Set the background color. 
		this.setBackground(new Color(0,128,0));
		
		//Construct the roads.
		constructRoads();
		
		//Place the lights.
		placeLights();
		
	}
	
	/**
	 * This method constructs the lanes and the intersection.
	 */
	public void constructRoads(){
		
		//Road and crossing objects.
		GRect vRoad, hRoad, crossing;
		
		//Vertical and horizontal road creation.
		vRoad = createRoad(SCREEN_WIDTH/2, ROAD_THICKNESS, "v");
		hRoad = createRoad(SCREEN_HEIGHT/2, ROAD_THICKNESS, "h");
		add(vRoad);
		add(hRoad);
		
		//Square crossing section.
		crossing = new GRect(SCREEN_WIDTH/2-ROAD_THICKNESS/2, SCREEN_HEIGHT/2-ROAD_THICKNESS/2, ROAD_THICKNESS, ROAD_THICKNESS);
		crossing.setColor(new Color(255,255,255));
		add(crossing);
		
		//Lane separator lines.
		for(int i = 0; i<SCREEN_WIDTH/2-ROAD_THICKNESS/2; i+=25){
			GLine line = new GLine(i, SCREEN_HEIGHT/2, i+15, SCREEN_HEIGHT/2);
			line.setColor(Color.WHITE);
			add(line);
		}
		for(int i = SCREEN_WIDTH; i>SCREEN_WIDTH/2+ROAD_THICKNESS/2; i-=25){
			GLine line = new GLine(i, SCREEN_HEIGHT/2, i-15, SCREEN_HEIGHT/2);
			line.setColor(Color.WHITE);
			add(line);
		}
		for(int i = 0; i<SCREEN_HEIGHT/2-ROAD_THICKNESS/2; i+=25){
			GLine line = new GLine(SCREEN_WIDTH/2, i, SCREEN_WIDTH/2, i+15);
			line.setColor(Color.WHITE);
			add(line);
		}
		for(int i = SCREEN_HEIGHT; i>SCREEN_HEIGHT/2+ROAD_THICKNESS/2; i-=25){
			GLine line = new GLine(SCREEN_WIDTH/2, i, SCREEN_WIDTH/2, i-15);
			line.setColor(Color.WHITE);
			add(line);
		}
		
	}
	
	/**
	 * This method creates and places the light objects.
	 * @see See vLight and hLight for the light objects.
	 */
	public void placeLights(){
		
		//Create and place the vertical light.
		vLight = new GOval(25, 25);
		vLight.setFilled(true);
		vLight.setFillColor(Color.RED);
		add(vLight, SCREEN_WIDTH/2-75-3, SCREEN_HEIGHT/2-100);
		
		//Create and place the horizontal light.
		hLight = new GOval(25, 25);
		hLight.setFilled(true);
		hLight.setFillColor(Color.RED);
		add(hLight, SCREEN_WIDTH/2+75, SCREEN_HEIGHT/2-75-3);
		
	}
	
	/**
	 * @param center Center of the road to be built.
	 * @param width Thickness of the road.
	 * @param dir Direction of the road. Allowed values: "v", "h"
	 * @throws RuntimeException on invalid orientation.
	 * @return Created road as a GRect.
	 * @see constructRoads()
	 */
	public GRect createRoad(int center, int width, String dir){
		
		//Use dir to determine the road orientation.
		if(dir.equals("v")){
			
			//Create the road object.
			GRect road = new GRect(center-width/2, 0, width, SCREEN_HEIGHT);
			
			//Set border color.
			road.setColor(new Color(0,0,0,0));
			
			//Set fill color.
			road.setFilled(true);
			road.setFillColor(new Color(50,50,50));
			
			//Return the created road object.
			return road;
			
		}else if (dir.equals("h")){
			
			//Create the road object.
			GRect road = new GRect(0, center-width/2, SCREEN_WIDTH, width);
			
			//Set border color.
			road.setColor(new Color(0,0,0,0));
			
			//Set fill color.
			road.setFilled(true);
			road.setFillColor(new Color(50,50,50));
			
			//Return the created road object.
			return road;
			
		}else{
			
			//Ignore throw keyword, it is irrelevant to the course.
			throw new RuntimeException("Invalid argument 'orientation' = '" + dir + "' @ drawRoad.");
			
		}
	}
	

	/**
	 * Width of the screen.
	 */
	public static final int SCREEN_WIDTH = 800;
	
	/**
	 * Height of the screen.
	 */
	public static final int SCREEN_HEIGHT = 600;
	
	/**
	 * Thickness of the roads constructed on the screen.
	 */
	public static final int ROAD_THICKNESS = 100;
	
	/**
	 * Width of the cars.
	 */
	public static final int CAR_WIDTH = 30;
	
	/**
	 * Lenght of the cars.
	 */
	public static final int CAR_LENGHT = 70;
	
	/**
	 * Color for the blue car.
	 */
	public static final Color CAR_COLOR_BLUE = new Color(0,0,192);
	
	/**
	 * Color for the red car.
	 */
	public static final Color CAR_COLOR_RED = new Color(192,0,0);
	
	/**
	 * Minimum speed allowed for the cars.
	 */
	public static final int MIN_SPEED = 5;
	
	/**
	 * Maximum speed allowed for the cars.
	 */
	public static final int MAX_SPEED = 30;
	
	/**
	 * Speed limit for the cars
	 */
	public static final int SPEED_LIMIT = 20;
	
	/**
	 * Unit fine used to calculate speeding fines.
	 */
	public static final int SPEED_FINE = 10;
	
	/**
	 * Static fine for passing a red light.
	 */
	public static final int RED_LIGHT_FINE = 100;
	
	/**
	 * Pause time for the animation.
	 */
	public static final int PAUSE_TIME = 50;
	
	/**
	 * Minimum time for a traffic light to change.
	 */
	public static final int MIN_LIGHT_TIME = 1000;
	
	/**
	 * Maximum time for a traffic light to change. 
	 */
	public static final int MAX_LIGHT_TIME = 5000;
	
	/**
	 * Font for use in labels.
	 */
	public static final Font LABEL_FONT = new Font("Courier", Font.PLAIN, 15);
	
	/**
	 * Margin of fine labels for the Y-axis.
	 */
	public static final int LABEL_Y_MARGIN = 20;
	
	/**
	 * Margin of fine labels for the X-axis.
	 */
	public static final int LABEL_X_MARGIN = 10;

}
