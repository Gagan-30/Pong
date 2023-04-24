package pong;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Gagan
 */
public class Pong extends Application 
{
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int PLAYER_HEIGHT = 100;
	private static final int PLAYER_WIDTH = 15;
	private static final double BALL_R = 15;
	private int ballYSpeed = 1;
	private int ballXSpeed = 1;
	private double playerOneYPos = HEIGHT / 2;
	private double playerTwoYPos = HEIGHT / 2;
	private double ballXPos = WIDTH / 2;
	private double ballYPos = HEIGHT / 2;
	private int scoreP1 = 0;
	private int scoreP2 = 0;
	private boolean gameStarted;
	private final int playerOneXPos = 0;
	private final double playerTwoXPos = WIDTH - PLAYER_WIDTH;
		
        @Override
	public void start(Stage Primarystage)
        {
		Primarystage.setTitle("P O N G");
		//background size
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		//JavaFX Timeline = free form animation defined by KeyFrames and their duration 
		Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		//number of cycles in animation INDEFINITE = repeat indefinitely
		tl.setCycleCount(Timeline.INDEFINITE);
		
		//mouse control (move and click)
		canvas.setOnMouseMoved(e ->  playerOneYPos  = e.getY());
		canvas.setOnMouseClicked(e ->  gameStarted = true);
		Primarystage.setScene(new Scene(new StackPane(canvas)));
		Primarystage.show();
		tl.play();
	}

	private void run(GraphicsContext gc) 
        {
		//set background color
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		
		//set text
		gc.setFill(Color.WHITE);
		gc.setFont(Font.font(25));
		
		if(gameStarted) 
                {
			//set ball movement
			ballXPos+=ballXSpeed;
			ballYPos+=ballYSpeed;
			
			//simple computer opponent who is following the ball
			if(ballXPos < WIDTH - WIDTH  / 4) 
                        {
				playerTwoYPos = ballYPos - PLAYER_HEIGHT / 2;
			}  else 
                        {
				playerTwoYPos =  ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2 ?playerTwoYPos += 1: playerTwoYPos - 1;
			}
			//draw the ball
			gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
			
		} 
                else
                {
			//set start text
			gc.setStroke(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.strokeText("Click", WIDTH / 2, HEIGHT / 2);
			
			//reset the ball position 
			ballXPos = WIDTH / 2;
			ballYPos = HEIGHT / 2;
			
			//reset the ball speed & direction
			ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
			ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
		}
		
		//ball stays in the canvas
		if(ballYPos > HEIGHT || ballYPos < 0) ballYSpeed *=-1;
		
		//Computer Point
		if(ballXPos < playerOneXPos - PLAYER_WIDTH) 
                {
			scoreP2++;
			gameStarted = false;
		}
		
		//Player Point
		if(ballXPos > playerTwoXPos + PLAYER_WIDTH) 
                {  
			scoreP1++;
			gameStarted = false;
		}
	
		//increase the speed after the ball hits the player
		if( ((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT) || 
			((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) 
                {
			ballYSpeed += 1 * Math.signum(ballYSpeed);
			ballXSpeed += 1 * Math.signum(ballXSpeed);
			ballXSpeed *= -1;
			ballYSpeed *= -1;
		}
		
		//draw score
		gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, WIDTH / 2, 100);
		//draw player 1 & 2
		gc.fillRect(playerTwoXPos, playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
		gc.fillRect(playerOneXPos, playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
	}
	
		// start the application
		public static void main(String[] args) 
                {
		launch(args);
		}
}
