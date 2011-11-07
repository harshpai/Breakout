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
	public void inti(){
		addMouseListeners();		
	}

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		
		setupBricks();
		
		showLabel("Click to Serve!",Color.BLACK);
		
		//Wait for the user to serve
		waitForClick();
				
		remove(label);
		
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
	
/**
 * Display the specified message at the center of the screen
 * @param message message to be displayed
 * */
	private void showLabel(String message,Color color){
		label = new GLabel(message);
		label.setFont("SansSerif-28");
		label.setColor(color)
		add(label,(getWidth()-label.getWidth())/2,(getHeight()+label.getAscent())/2);		
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
	private void buildLayer(int row){
				
			double y= BRICK_Y_OFFSET + row*(BRICK_HEIGHT+BRICK_SEP);
			
			/*
			 * Calculates the x coordinate of the first brick in
			 * the layer such that the layer is centered on the screen
			 */
			double setupWidth = (NBRICKS_PER_ROW*BRICK_WIDTH)+((NBRICKS_PER_ROW-1)*BRICK_SEP);
			double xOffset = (getWidth()-setupWidth)/2;
						
			for (int i=0;i<NBRICKS_PER_ROW;i++){
				double x = xOffset+i*(BRICK_WIDTH+BRICK_SEP);
				buildBrick(x,y,row);
			}
		}
		
/**
 * Builds a colored brick
 * @param x x coordinate of the brick
 * @param y y coordinate of the bricks layer
 * @param row row number of the brick - used to color brick
 * */	
	private void buildBrick(double x,double y,int row){
			GRect brick =new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
			brick.setFilled(true);
			switch (row/2){
				case 0:
					brick.setFillColor(Color.RED);
					break;
				case 1:
					brick.setFillColor(Color.ORANGE);
					break;
				case 2:
					brick.setFillColor(Color.YELLOW);
					break;
				case 3:
					brick.setFillColor(Color.GREEN);
					break;
				case 4:
					brick.setFillColor(Color.CYAN);
					break;
				default:
					brick.setFillColor(Color.BLACK);
					break;
			}
						
			add(brick);
			
		}
	
/**Displays messages to the user*/	
	private GLabel label ;
}
