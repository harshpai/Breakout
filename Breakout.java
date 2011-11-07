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

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		setupBricks();
		
	}

	
	private void setupBricks(){
		
		for (int i=0;i<NBRICK_ROWS;i++){
			buildLayer(NBRICKS_PER_ROW,i);
		}
	}
	
	
	/**
	 * Builds a Layer one brick at a time
	 * @param bricks number of bricks in a layer
	 * @param row number of the brick row
	 * */	
		private void buildLayer(int bricks,int row){
				
			double y= BRICK_Y_OFFSET + row*(BRICK_HEIGHT+BRICK_SEP);
			
			/*
			 * Calculates the x coordinate of the first brick in
			 * the layer such that the layer is centered on the screen
			 */
			double x = (getWidth()-(bricks*BRICK_WIDTH)-((bricks+1)*BRICK_SEP))/2;
			
			for (int i=0;i<bricks;i++){
				buildBrick(x+i*(BRICK_WIDTH+BRICK_SEP),y,row);
			}
		}
		
	/**
	 * Builds a brick
	 * @param x x coordinate of the brick
	 * @param y y coordinate of the bricks layer
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
	
	
}
