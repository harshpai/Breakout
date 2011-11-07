/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
/** Enables mouse listeners in the game*/	
	public void init(){
				
	}

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		
		addMouseListeners();
		/*Initial setup of the game: bricks and paddle*/
		setupBricks();
		setupPaddle();
		
		/*Display a message to the user to start the game*/
		showLabel("Click to Serve!",Color.BLACK);
		
		//Wait for the user to serve
		waitForClick();

		//The game begins : Remove messages from the screen
		remove(label);
		
		//The game ends: Display appropriate win or loss message 
		if(hasWon()){
			showLabel("You win!!!",Color.GREEN);
		}
		else{
			showLabel("You lose",Color.RED);
		}			
		
	}

/**Start playing*/	
	private boolean hasWon(){
		//TODO: Implement play
		return true;
	}
	
/**Displays paddle at the center bottom of the screen*/
	private void setupPaddle(){
		
		//Calculate paddle position
		double x = (WIDTH-PADDLE_WIDTH)/2;
		double y = HEIGHT -PADDLE_HEIGHT-PADDLE_Y_OFFSET;
		
		paddle=createFilledRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT, Color.BLACK);
		
		add(paddle);
		
		
	}
	
	
	public void mouseMoved(MouseEvent e){
		
		if(e.getX()+PADDLE_WIDTH/2<(WIDTH-PADDLE_WIDTH)){
			double lastX = paddle.getLocation().getX();
			paddle.move(e.getX()-lastX-PADDLE_WIDTH/2,0);
			remove(label);
			showLabel("pos "+lastX, Color.BLACK);
		}
	}
	
/**
 * Display the specified message at the center of the screen
 * @param message message to be displayed
 * */
	private void showLabel(String message,Color color){
		label = new GLabel(message);
		label.setFont("SansSerif-28");
		label.setColor(color);
		add(label,(WIDTH-label.getWidth())/2,(HEIGHT+label.getAscent())/2);		
	}
	
	
/** Provides the initial brick setup for the Breakout game */
	private void setupBricks(){
		
		for (int i=0;i<NBRICK_ROWS;i++){
			buildLayer(i);
		}
		
	}
	
	
/**
 * Builds a Layer one brick at a time
 * @param row number of the brick row
 * */	
	private void buildLayer(int numRow){
		
			/*Calculate the y coordinate of the row*/	
			double y= BRICK_Y_OFFSET + numRow*(BRICK_HEIGHT+BRICK_SEP);
			
			/*
			 * Calculates the x coordinate of the first brick in
			 * the layer such that the layer is centered on the screen
			 */
			double rowWidth = (NBRICKS_PER_ROW*BRICK_WIDTH)+((NBRICKS_PER_ROW-1)*BRICK_SEP);
			double xOffset = (WIDTH-rowWidth)/2;
				
			
			/*Calculate the brick color*/
			Color color = getRowColor(numRow);			
		
			//Create bricks in a row
			for (int i=0;i<NBRICKS_PER_ROW;i++){
				
				//The bricks layer has an offset on the left side
				double x = xOffset+i*(BRICK_WIDTH+BRICK_SEP);
				
				//Adds the brick to the canvas
				add(createFilledRect(x,y,BRICK_WIDTH,BRICK_HEIGHT,color));
				
			}
		}
		
/**
 * Returns color of the brick row based on row number
 * @param numRow number of the brick row
 * @return color color of the brick row
 * */	
	private Color getRowColor(int numRow){
		switch (numRow/2){
		case 0:
			return Color.RED;			
		case 1:
			return Color.ORANGE;
		case 2:
			return Color.YELLOW;
		case 3:
			return Color.GREEN;
		case 4:
			return Color.CYAN;			
		default:
			return Color.BLACK;
		}
	}
	
/**
 * Creates a filled rectangle with specified dimensions
 * @param x x coordinate of the rectangle
 * @param y y coordinate of the rectangle
 * @param width width of the rectangle
 * @param height height of the rectangle
 * @param color fill color of the rectangle
 * @return rect rectangle with specified attributes
 * */	
	private GRect createFilledRect(double x,double y, double width, double height, Color color){
			
			GRect rect =new GRect(x, y, width, height);
			rect.setFilled(true);
			rect.setFillColor(color);						
			return rect;
			
		}
	
/**Displays messages to the user*/	
	private GLabel label ;
	
/**Paddle rectangle*/
	private GRect paddle;
}
