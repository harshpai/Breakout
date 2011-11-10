/*
 * File: Breakout.java
 * -------------------
 * Name:Harsh Pai
 * Section Leader:Mehran Sahami
 *
 * This file implements the game of Breakout.
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
    
/** Color of brick layers */    
    private static final int RED_ROW = 0;
    private static final int ORANGE_ROW = 1;
    private static final int YELLOW_ROW = 2;
    private static final int GREEN_ROW = 3;
    private static final int CYAN_ROW = 4;
    
/** Score for breaking a red brick */    
    private static final int MAXSCORE = 5;
    
/** Number of brick layers with same color */    
    private static final int ROWS_PER_COLOR =2;
    
/** Initial velocities of the ball */
    private static final double VMAX = 2.0;
    private static final double VMIN = 1.0;

/** Animation cycle delay */
    private static final int DELAY = 10;
    
/* Method: run() */
/** Runs the Breakout program. */
    public void run() {        
        
        addMouseListeners();  
        
        setup();
        
        play();

    }

    
/** Initial setup of the game: bricks and paddle */    
    private void setup(){
        
        setupBricks();
        
        setupPaddle();
    }
    
/** Start playing */    
    private void play(){

        boolean hasWon = false;
        
        //Start turns
        for (int i=0 ;i<NTURNS;i++){
        	
            /* Extension: Display a message to the user to start the game */
            showLabel("Click to serve!",Color.BLACK);
            
            // Wait for the user to serve
            waitForClick();

            // The game begins. 
            // Remove messages from the screen.
            remove(label);
            
            if(playTurn()){
         
            	hasWon  = true;
                break;
            }    
        }
        
        // Extension: Show result to the player
        showResult(hasWon);

    }
   
/** Displays win or loss to the player */    
    private void showResult(boolean hasWon){
    	
        // Show messages indicating win or loss
        if(hasWon){
        	
            // Player wins game ends
            showLabel("You win!",Color.RED);  
        }
        else{
        	
            // Player has finished all turns, 
        	// display 'You lose' message
            showLabel("You lose",Color.RED);         
        }
    }
    
/**  
 * Plays a single turn of the game
 * @return returns true if player wins,
 * returns false otherwise
 * */    
    private boolean playTurn(){

        createBall();
        
        // Initialize x velocity of the ball
        vx = rgen.nextDouble(VMIN,VMAX);
        if(rgen.nextBoolean(0.5)) vx=-vx;
        
        // Extension: Add kicker
        // For every new ball set paddleHits to 0
        paddleHits=0;
        
        while (bounce()){
        
            // Extension: Add kicker
        	// Double x velocity on 7th paddle bounce
            checkKicker();
        	
            // Check if all bricks are destroyed 
            if(checkForCollision())
            {
                remove(ball);
                return true;
            }   
            
            pause(DELAY);
            ball.move(vx, vy);
        }
        
        return false;
    }
    
    
/** Collision with another object reverses y velocity
 * and collision with brick also removes the brick
 * @return returns true if all bricks have been removed, 
 * returns false otherwise
 * */    
    private boolean checkForCollision(){
    	
    	// Get the colliding object
        GObject collider = getCollidingObject();
        
        /** Extension: play sound
         * Load file containing bounce sound */    
        AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
        
        // Ball bounces off the paddle
        if(collider == paddle){
        	
        	// Y coordinate of the bottom of ball
        	double ballLowerY = ball.getY()+2*BALL_RADIUS;
        	
        	// Y coordinate of the lower edge of paddle;
        	double paddleLowerY =paddle.getY()+PADDLE_HEIGHT ; 
        	
        	// Bounce the ball only if it hasn't passed the 
        	// lower edge of the paddle. This ensures that ball
        	// doesn't get glued to the paddle.
        	if(ballLowerY<paddleLowerY){
        		        		
        		double diff = paddle.getY()-ballLowerY;
        		vy=-vy ;
        		
        		// Assume that the ball will move an equal amount 
        		// in the same direction as it would have otherwise
        		ball.move(0, 2*diff);
        		
        		//Extension: play sound
        		bounceClip.play();
        	}
        	
        	//Extension: bounce ball in x direction if it hits the paddle edge
        	bouncePaddleX();
            
        }
        // Ball collides with a brick
        else if (collider != null && collider != scoreBoard){
            
            vy=-vy;
         
            // Remove brick and decrement brick counter
            remove(collider);
            NBRICKS_LEFT_IN_GAME--;
            
            // Extension: play sound
            bounceClip.play();
            
            // Extension: update score
            updateScore(collider);
                       
            // Game over: Player wins
            if(NBRICKS_LEFT_IN_GAME==0) return true;

        }
        
        return false;
    }
    
/** Bounces ball in x direction if collision 
 * occurs with the edge of the paddle */    
    private void bouncePaddleX(){
    	
    	double diff;
    	
    	// Consider paddle edge to be 10% paddle width on both sides
    	double leftPaddleX = paddle.getX()+(0.1*PADDLE_WIDTH);
    	double rightPaddleX = paddle.getX()+(0.9*PADDLE_WIDTH);
    	
    	// right and left coordinates of binding rectangle of ball
    	double leftBallX = ball.getX();
    	double rightBallX = ball.getX()+2*BALL_RADIUS;
    	
    	if(vx>0 && rightBallX < leftPaddleX) {
    		
    		vx=-vx;
    		
    		diff=rightBallX-paddle.getX();
    		    		
    		// Assume that the ball will move an equal amount 
    		// in the same direction as it would have otherwise
    		ball.move(100, 0);
    		waitForClick();
    	} 
    	else if(vx<0 && leftBallX>rightPaddleX){
    		
    		vx=-vx;
    		
    		diff=paddle.getX()+PADDLE_WIDTH-leftBallX;
    		
    		// Assume that the ball will move an equal amount 
    		// in the same direction as it would have otherwise
    		ball.move(100, 0);
    		waitForClick();
    	}
    		
    	
    }
    
/** 
 * Keeps score by updating label below the paddle
 * @param collider the brick that collided with ball
 *  */
    private void updateScore(GObject collider){
    	
    	if(collider.getColor() ==  Color.RED) currentScore += MAXSCORE - RED_ROW;
    	else if(collider.getColor() ==  Color.ORANGE) currentScore += MAXSCORE - ORANGE_ROW;
    	else if(collider.getColor() ==  Color.YELLOW) currentScore += MAXSCORE - YELLOW_ROW;
    	else if(collider.getColor() ==  Color.GREEN) currentScore += MAXSCORE - GREEN_ROW;
    	else if(collider.getColor() ==  Color.CYAN) currentScore += MAXSCORE - CYAN_ROW;
    	    
    	scoreBoard.setLabel("Score:"+currentScore);
    }
    
/** Checks the four corners of the rectangle binding the ball
 * for bricks and paddle
 * @return returns colliding object or null
 * */
    private GObject getCollidingObject(){
        
        //Coordinates of the top left corner of the bounding rectangle of the ball
        double x = ball.getX();
        double y = ball.getY();
        
        if(getElementAt(x,y)!=null){
            return getElementAt(ball.getX(),ball.getY());
        }
        else if (getElementAt(x+2*BALL_RADIUS,y)!=null){
            return getElementAt(x+2*BALL_RADIUS,ball.getY());
        }
        else if (getElementAt(x,y+2*BALL_RADIUS)!=null){
            return getElementAt(ball.getX(),y+2*BALL_RADIUS);
        }
        else if (getElementAt(x+2*BALL_RADIUS,y+2*BALL_RADIUS)!=null){
            return getElementAt(x+2*BALL_RADIUS,y+2*BALL_RADIUS);
        }
        else{
            return null;
        }
                
    }
    
    
/** Determine if collision with floor,ceiling or sides of the screen
 * update velocities and location as appropriate
 * @return returns false if the ball hits lower edge of screen, returns true otherwise
 * */
    private boolean bounce(){
        
        double diff;
        
        // Check for collision with floor
        if( ball.getY()>(HEIGHT-2*BALL_RADIUS)){
            
            remove(ball);
            return false;
            
        }
        // Check for collision with ceiling
        else if(ball.getY()<0){
            
            // Reverse ball's Y velocity
            vy=-vy;
            
            // Assume bounce will move an amount in the opposite direction
            // equal to the amount it would have traveled otherwise
            diff = ball.getY();
            ball.move(0,-2*diff);   
                        
        }        
        // Check for collision with right edge
        else if( ball.getX()>(WIDTH-2*BALL_RADIUS)){
            
            // Reverse ball's X velocity
            vx=-vx;
            
            // Assume bounce will move an amount in the opposite direction
            // equal to the amount it would have traveled otherwise
            diff = ball.getX()-(WIDTH - 2*BALL_RADIUS);
            ball.move(-2*diff,0);
                        
        }
        // Check for collision with left edge
        else if( ball.getX()<0){
            
            // Reverse ball's X velocity
            vx=-vx;
            
            // Assume bounce will move an amount in the opposite direction
            // equal to the amount it would have traveled otherwise
            diff = ball.getX();
            ball.move(-2*diff,0);
            
        }
        
        return true;
    }
    
/** Doubles the horizontal velocity on the
 * seventh time the ball hits the paddle
 *  */    
    private void checkKicker(){
        paddleHits++;
        if (paddleHits==7){
        	vx*=2;
        }
    }
    
/** Creates a ball at the center of the screen */
    private void createBall(){
    	
    	//x and y coordinate of the ball
    	double x = WIDTH/2-BALL_RADIUS;
    	double y = HEIGHT/2-BALL_RADIUS;
    	
        ball = new GOval(x,y,2*BALL_RADIUS,2*BALL_RADIUS);
        ball.setFilled(true);
        add(ball);
    }
    
/** Displays paddle at the bottom center of the screen */
    private void setupPaddle(){
        
        //Calculate paddle position
        double x = (WIDTH-PADDLE_WIDTH)/2;
        double y = HEIGHT -PADDLE_HEIGHT-PADDLE_Y_OFFSET;
        
        paddle=createFilledRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT, Color.BLACK);
        
        add(paddle);        
        
        // Extension: add score board below the paddle
        scoreBoard = new GLabel("Score:0");
        add(scoreBoard,x,y+2*PADDLE_HEIGHT);
    }
    
    
/** Called on mouse move to move the paddle*/
    public void mouseMoved(MouseEvent e){
        
        //Screen border coordinates
        double rightEdge = WIDTH-PADDLE_WIDTH/2;
        double leftEdge = PADDLE_WIDTH/2;
        
        //Check to keep the paddle from going out of screen
        if(e.getX() >= leftEdge && e.getX() <= rightEdge){
            double lastX = paddle.getLocation().getX();
            
            //moves the paddle such that the center of the paddle
            //tracks the mouse
            paddle.move(e.getX()-lastX-PADDLE_WIDTH/2,0);
        }
    }
    
/**
 * Display the specified message at the center of the screen
 * @param message Text to the Label
 * @param color Color of the message font
 * */
    private void showLabel(String message,Color color){
        label = new GLabel(message);
        label.setFont("SansSerif-35");
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
                
            
            /* Calculate the brick color */
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
        switch (numRow/ROWS_PER_COLOR){
        case RED_ROW:
            return Color.RED;            
        case ORANGE_ROW:
            return Color.ORANGE;
        case YELLOW_ROW:
            return Color.YELLOW;
        case GREEN_ROW:
            return Color.GREEN;
        case CYAN_ROW:
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
            rect.setColor(color);
            rect.setFilled(true);
            rect.setFillColor(color);                        
            return rect;
            
        }
    
/** Instance variable label is used to display messages to the user */    
    private GLabel label;
    
/** Instance variable score is used to display score to the user */
    private GLabel scoreBoard;
    
/** private instance variable paddle rectangle */
    private GRect paddle;
    
/** private instance variable ball */    
    private GOval ball;
    
/** Instance variable random generator to generate vx for the ball and otherwise */   
    private RandomGenerator rgen = RandomGenerator.getInstance();
    
/** Instance variable to keep track of the velocity of the ball */
    private double vx;
    private double vy = VMAX;
    
/** Instance variable Number of bricks remaining in the game */    
    private int NBRICKS_LEFT_IN_GAME = NBRICK_ROWS * NBRICKS_PER_ROW;
    
/** Instance variable to count the number of paddle hits  */    
    private int paddleHits;
    
/** Instance variable to track score of thr player */
    private int currentScore = 0;

} 