import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Breakout extends GraphicsProgram {



    private Ball ball;
    private Paddle paddle;
    private int lives;
    private int score;
    private GLabel livesLabel;
    private GLabel scoreLabel;

    private int numBricksInRow;

    private Color[] rowColors = {Color.RED, Color.RED, Color.ORANGE, Color.ORANGE, Color.YELLOW, Color.YELLOW,
            Color.GREEN, Color.GREEN, Color.CYAN, Color.CYAN};

    @Override
    public void init(){

        numBricksInRow = (int) (getWidth() / (Brick.WIDTH + 5.0));

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < numBricksInRow; col++) {

                double brickX = 10 + col * (Brick.WIDTH + 5);
                double brickY = Brick.HEIGHT + row * (Brick.HEIGHT + 5);

                Brick brick = new Brick(brickX, brickY, rowColors[row], row);
                add(brick);
            }
        }

        ball = new Ball(getWidth()/2, 350, 10, this.getGCanvas());
        add(ball);

        paddle = new Paddle(230, 430, 50 ,10);
        add(paddle);
        lives = 3;
        score = 0;
        //show how many lives I have
        livesLabel = new GLabel("Lives " + lives);
        add(livesLabel, 10,455);
        //show the Score
        scoreLabel = new GLabel("Score " + score);
        add(scoreLabel, 60,455);
    }

    @Override
    public void run(){
        addMouseListeners();
        waitForClick();
        gameLoop();

    }

    @Override
    public void mouseMoved(MouseEvent me){
        // make sure that the paddle doesn't go offscreen
        if((me.getX() < getWidth() - paddle.getWidth()/2)&&(me.getX() > paddle.getWidth() / 2)){
            paddle.setLocation(me.getX() - paddle.getWidth()/2, paddle.getY());
        }
    }

    private void gameLoop(){
        while(true){
            // move the ball
            ball.handleMove();

            // handle collisions
            handleCollisions();

            // handle losing the ball
            if(ball.lost){
                handleLoss();
            }

            pause(5);
        }
    }


    private void handleCollisions(){
        // obj can store what we hit
        GObject obj = null;

        // check to see if the ball is about to hit something

        if(obj == null){
            // check the top right corner
            obj = this.getElementAt(ball.getX()+ball.getWidth(), ball.getY());
        }

        if(obj == null){
            // check the top left corner
            obj = this.getElementAt(ball.getX(), ball.getY());
        }

        //check the bottom right corner for collision
        if (obj == null) {
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY() + ball.getHeight());
        }
        //check the bottom left corner for collision
        if (obj == null) {
            obj = this.getElementAt(ball.getX(), ball.getY() + ball.getHeight());
        }

        // see if we hit something
        if(obj != null){

            // lets see what we hit!
            if(obj instanceof Paddle){

                if(ball.getX() < (paddle.getX() + (paddle.getWidth() * .2))){
                    // did I hit the left side of the paddle?
                    ball.bounceLeft();
                } else if(ball.getX() > (paddle.getX() + (paddle.getWidth() * .8))) {
                    // did I hit the right side of the paddle?
                    ball.bounceRight();
                } else {
                    // did I hit the middle of the paddle?
                    ball.bounce();
                }

            }

            //track how many times a ball has hit a brick and add to score

            if(obj instanceof Brick){
                // bounce the ball
                //add score if hit brick
                score += 1;
                scoreLabel.setLabel("Score " + score);
                ball.bounce();
                ((Brick) obj).setFillColor(((Brick) obj).getFillColor().darker());
                // destroy the brick
                ((Brick) obj).hits();
                if(((Brick) obj).hits==0){
                    this.remove(obj);
                }
            }

        }

        // if by the end of the method obj is still null, we hit nothing
    }
    // make somewhere to store lives and give it a label.
    // subtract 1 from lives

    private void handleLoss(){
        ball.lost = false;

        lives -= 1;
        //updates out "lives" Label
        livesLabel.setLabel("Lives " + lives);

        if(lives == 0)
        {
            // Check if lives is 0 and exit game.
           loss();
        }
            reset();


    }
    //make a loss
    private void loss(){
        Dialog.showMessage("You Lost");
        System.exit(0);
    }
    private void reset(){
        ball.setLocation(getWidth()/2, 350);
        paddle.setLocation(230, 430);
        waitForClick();
    }

    public static void main(String[] args) {
        new Breakout().start();
    }
/*
 1) what do i do about lives/
 2) All of the bricks take same # of hits/
 3) how do i know how many lives i have/
 4) how do i know how many points i have/
 5) what happens if i run out of lives/
 6) how can it tell that a brick is hit/

 powerups in some bricks
 multiple levels
 an animation for a broken brick
 */
}