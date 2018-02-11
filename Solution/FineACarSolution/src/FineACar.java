import java.awt.Color;
import java.awt.Font;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

@SuppressWarnings("serial")
public class FineACar extends GraphicsProgram {

	GOval vLight, hLight;
	int vLightTime, hLightTime;
	GRect carRed, carBlue;
	int vRed, vBlue;
	int fineRed, fineBlue;
	GLabel redLabel, blueLabel;
	
	public void run(){
		
		//Initialize the labels.
		initializeLabels();
		
		//Initiate cars and their speeds.
		createCars();
		vRed=getRandomSpeed();
		vBlue=getRandomSpeed();
		
		//Initiate light times.
		vLightTime = getRandomTime();
		hLightTime = getRandomTime();
		
		//Main animation loop.
		while(true){
			//Light crossing flags.
			boolean redCrossed, blueCrossed;
			redCrossed = (carRed.getX()<SCREEN_WIDTH/2+ROAD_THICKNESS/2);
			blueCrossed = (carBlue.getY()>SCREEN_HEIGHT/2-ROAD_THICKNESS/2);
			
			//Car animations.
			carRed.move(-1*vRed, 0);
			carBlue.move(0, vBlue);
			pause(PAUSE_TIME);
			
			//Handle lights.
			vLightTime-=PAUSE_TIME;
			hLightTime-=PAUSE_TIME;
			if(vLightTime<=0){
				if(vLight.getFillColor()==Color.RED){
					vLight.setFillColor(Color.GREEN);
				}else{
					vLight.setFillColor(Color.RED);
				}
				vLightTime=getRandomTime();
			}
			if(hLightTime<=0){
				if(hLight.getFillColor()==Color.RED){
					hLight.setFillColor(Color.GREEN);
				}else{
					hLight.setFillColor(Color.RED);
				}
				hLightTime=getRandomTime();
			}
			
			//Fining due to speed limit.
			if(redCrossed!=(carRed.getX()<SCREEN_WIDTH/2+ROAD_THICKNESS/2)){
				if(vRed>SPEED_LIMIT){
					if(hLight.getFillColor()==Color.RED){
						fineRed += RED_LIGHT_FINE;
						display(redLabel, "+"+ ((vRed-SPEED_LIMIT)*SPEED_FINE+RED_LIGHT_FINE) + "$ Reason: Speeding & red light.");
					}else{
						fineRed += (vRed-SPEED_LIMIT)*SPEED_FINE;
						display(redLabel, "+"+ (vRed-SPEED_LIMIT)*SPEED_FINE + "$ Reason: Speeding.");
					}
				}else if(hLight.getFillColor()==Color.RED){
					fineRed += RED_LIGHT_FINE;
					display(redLabel, "+"+ RED_LIGHT_FINE + "$ Reason: Red light.");
				}else{
					display(redLabel, "");
				}
			}
			if(blueCrossed!=(carBlue.getY()>SCREEN_HEIGHT/2-ROAD_THICKNESS/2)){
				if(vBlue>SPEED_LIMIT){
					if(vLight.getFillColor()==Color.RED){
						fineBlue += RED_LIGHT_FINE;
						display(blueLabel, "+" + ((vBlue-SPEED_LIMIT)*SPEED_FINE+RED_LIGHT_FINE) + "$ Reason: Speeding & red light.");
					}else{
						fineBlue += (vBlue-SPEED_LIMIT)*SPEED_FINE;
						display(blueLabel, "+" + (vBlue-SPEED_LIMIT)*SPEED_FINE + "$ Reason: Speeding.");
					}
				}else if(vLight.getFillColor()==Color.RED){
					fineBlue += RED_LIGHT_FINE;
					display(blueLabel, "+" + RED_LIGHT_FINE + "$ Reason: Red light.");
				}else{
					display(blueLabel, "");
				}
			}

			//End on collision.
			if(carRed.getBounds().intersects(carBlue.getBounds()))break;
			
			//Repositioning on car world exit.
			if(carRed.getX()+CAR_LENGHT<0){
				repositionCar(carRed);
				vRed=getRandomSpeed();
				redCrossed=false;
			}else if(carBlue.getY()>SCREEN_HEIGHT){
				repositionCar(carBlue);
				vBlue=getRandomSpeed();
				blueCrossed=false;
			}
			
		}
		
		//Post collision final display.
		display(blueLabel, "Total fine: " + fineBlue + "$");
		display(redLabel, "Total fine: " + fineRed + "$");
	}
	
	public void initializeLabels(){
		redLabel=new GLabel("");
		redLabel.setFont(LABEL_FONT);
		redLabel.setColor(CAR_COLOR_RED);
		blueLabel=new GLabel("");
		blueLabel.setFont(LABEL_FONT);
		blueLabel.setColor(CAR_COLOR_BLUE);
		positionLabels();
		add(redLabel);
		add(blueLabel);
	}
	
	public void positionLabels(){
		redLabel.setLocation(SCREEN_WIDTH-redLabel.getWidth()-10,20);
		blueLabel.setLocation(10, 20);
	}
	
	public void createCars(){
		carRed=new GRect(CAR_LENGHT, CAR_WIDTH);
		carRed.setFilled(true);
		carRed.setFillColor(CAR_COLOR_RED);
		carBlue=new GRect(CAR_WIDTH, CAR_LENGHT);
		carBlue.setFilled(true);
		carBlue.setFillColor(CAR_COLOR_BLUE);
		repositionCar(carRed);
		repositionCar(carBlue);
		add(carRed);
		add(carBlue);
	}
	
	public void repositionCar(GRect car){
		if(car.getFillColor()==CAR_COLOR_RED){
			car.setLocation(SCREEN_WIDTH, SCREEN_HEIGHT/2-ROAD_THICKNESS/2+(ROAD_THICKNESS/2-CAR_WIDTH)/2);
		}else if(car.getFillColor()==CAR_COLOR_BLUE){
			car.setLocation(SCREEN_WIDTH/2-ROAD_THICKNESS/2+(ROAD_THICKNESS/2-CAR_WIDTH)/2, -1*CAR_LENGHT);
		}else{
			throw new RuntimeException("Given GRect is not recognized as a car.");
		}
	}
	
	public int getRandomSpeed(){
		return new RandomGenerator().nextInt(MIN_SPEED, MAX_SPEED);
	}
	
	public int getRandomTime(){
		return new RandomGenerator().nextInt(MIN_LIGHT_TIME, MAX_LIGHT_TIME)/PAUSE_TIME*PAUSE_TIME;
	}
	
	public void display(GLabel label, String str){
		label.setLabel(str);
		positionLabels();
	}
	
	/*
	 * DO NOT change anything below this line!
	 */
	
	public void init(){
		this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setBackground(new Color(0,128,0));
		constructRoads();
		placeLights();
	}
	
	public void constructRoads(){
		
		GRect vRoad, hRoad, crossing;
		vRoad = createRoad(SCREEN_WIDTH/2, ROAD_THICKNESS, "v");
		hRoad = createRoad(SCREEN_HEIGHT/2, ROAD_THICKNESS, "h");
		crossing = new GRect(SCREEN_WIDTH/2-ROAD_THICKNESS/2, SCREEN_HEIGHT/2-ROAD_THICKNESS/2, ROAD_THICKNESS, ROAD_THICKNESS);
		crossing.setColor(new Color(255,255,255));
		add(vRoad);
		add(hRoad);
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
	
	public void placeLights(){
		vLight = new GOval(25, 25);
		vLight.setFilled(true);
		vLight.setFillColor(Color.RED);
		add(vLight, SCREEN_WIDTH/2-75-3, SCREEN_HEIGHT/2-100);
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
		if(dir.equals("v")){
			GRect road = new GRect(center-width/2, 0, width, SCREEN_HEIGHT);
			road.setColor(new Color(0,0,0,0));
			road.setFilled(true);
			road.setFillColor(new Color(50,50,50));
			return road;
		}else if (dir.equals("h")){
			GRect road = new GRect(0, center-width/2, SCREEN_WIDTH, width);
			road.setColor(new Color(0,0,0,0));
			road.setFilled(true);
			road.setFillColor(new Color(50,50,50));
			return road;
		}else{
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
	
	//TODO Add margin vars.
}
