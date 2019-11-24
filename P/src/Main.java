import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.omg.CORBA.BAD_CONTEXT;

import java.awt.*;
import java.awt.font.ImageGraphicAttribute;


/**
 * @author Amirreza Vedadiyan
 */
public class Main extends Application {

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 500;
    public static int scoreOne = 0;
    public static int scoreTwo = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox(20);
        root.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));

        //Result Part
        HBox results = new HBox(10);
        results.setAlignment(Pos.TOP_CENTER);
        Label playerOneName = new Label("Player 1:");
        Label playerOneScore = new Label(String.valueOf(scoreOne));
        Label splitter = new Label("  ");
        Label playerTwoName = new Label("Player 2:");
        Label playerTwoScore = new Label(String.valueOf(scoreTwo));
        setLabelStyle(playerOneName); setLabelStyle(playerOneScore);
        setLabelStyle(splitter);
        setLabelStyle(playerTwoName); setLabelStyle(playerTwoScore);
        results.getChildren().addAll(playerOneName, playerOneScore, splitter, playerTwoName, playerTwoScore);

        //Field part
        Pane field = new Pane();
        field.setPrefHeight(WINDOW_HEIGHT - 80); field.setPrefWidth(WINDOW_WIDTH);
        BackgroundImage myBI = new BackgroundImage(new Image("Images/table.png", WINDOW_WIDTH, WINDOW_HEIGHT - 80, false, true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        field.setBackground(new Background(myBI));
        Text start = new Text(WINDOW_WIDTH / 2 - 110, WINDOW_HEIGHT / 2 - 40, "Press Enter to Start");
        start.setFont(new Font("Helvetica", 30)); start.setFill(Color.rgb(255,0,157));
        Rectangle handleOne = new Rectangle(10, 50, 10, 100);
        handleOne.setFill(Color.RED);
        Rectangle handleTwo = new Rectangle(WINDOW_WIDTH - 20, 150,10,100);
        handleTwo.setFill(Color.RED);
        Circle ball = new Circle(WINDOW_WIDTH / 2,WINDOW_HEIGHT / 2 - 50 , 15);
        ball.setFill(Color.WHITE);
        field.getChildren().addAll(handleOne,handleTwo,ball,start);


        root.getChildren().addAll(results, field);


        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pong");
        primaryStage.setResizable(false);
        primaryStage.show();


        //Animation
        Ball ballClass = new Ball();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //Move
                ball.setCenterX(ball.getCenterX() + ballClass.getVx());
                ball.setCenterY(ball.getCenterY() + ballClass.getVy());
                //Check Hit Wall's or Cross Wall's
                if(ball.getCenterY() > (WINDOW_HEIGHT - results.getHeight() - 55) && ballClass.getVy() >= 0){
                    ballClass.setVy(-ballClass.getVy());
                }
                if(ball.getCenterY() < (WINDOW_HEIGHT - field.getHeight() - 55) && ballClass.getVy() <= 0){
                    ballClass.setVy(-ballClass.getVy());
                }
                if(ball.getCenterX() >= WINDOW_WIDTH  && ballClass.getVx() >= 0){
                    scoreOne++;
                    ball.setCenterY(WINDOW_HEIGHT / 2 - 50); ball.setCenterX(WINDOW_WIDTH / 2);
                    playerOneScore.setText(String.valueOf(scoreOne));
                }
                if(ball.getCenterX() <= 15 && ballClass.getVx() <= 0){
                    scoreTwo++;
                    ball.setCenterY(WINDOW_HEIGHT / 2 - 50); ball.setCenterX(WINDOW_WIDTH / 2);
                    playerTwoScore.setText(String.valueOf(scoreTwo));
                }
                //Check Hit Handle's
                if(Math.abs((ball.getCenterX() - handleOne.getX())) < 20 && Math.abs((ball.getCenterY() - handleOne.getY() - 50)) < 50 && ballClass.getVx() <= 0){
                    ballClass.setVx(-ballClass.getVx());
                }
                if(Math.abs((ball.getCenterX() - handleTwo.getX())) < 10 && Math.abs((ball.getCenterY() - handleTwo.getY() - 50)) < 50 && ballClass.getVx() >= 0){
                    ballClass.setVx(-ballClass.getVx());
                }
                //Scaling
                if(ball.getCenterX() < WINDOW_WIDTH / 2 && ballClass.getVx() > 0 && ball.getRadius() < 20){
                    ball.setRadius(ball.getRadius() + 0.1);
                }
                if(ball.getCenterX() < WINDOW_WIDTH / 2 && ballClass.getVx() < 0 && ball.getRadius() > 10){
                    ball.setRadius(ball.getRadius() - 0.1);
                }
                if(ball.getCenterX() > WINDOW_WIDTH / 2 && ballClass.getVx() > 0 && ball.getRadius() > 10){
                    ball.setRadius(ball.getRadius() - 0.1);
                }
                if(ball.getCenterX() > WINDOW_WIDTH / 2 && ballClass.getVx() < 0 && ball.getRadius() < 20){
                    ball.setRadius(ball.getRadius() + 0.1);
                }

            }
        };

        //Event Handle
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case W:
                        if(handleOne.getY() >= (WINDOW_HEIGHT - field.getHeight() - 70))
                            handleOne.setY(handleOne.getY() - 15);
                        break;
                    case S:
                        if(handleOne.getY() <= WINDOW_HEIGHT - results.getHeight() - 140)
                            handleOne.setY(handleOne.getY() + 15);
                        break;
                    case UP:
                        if(handleTwo.getY() >= (WINDOW_HEIGHT - field.getHeight() - 70))
                            handleTwo.setY(handleTwo.getY() - 15);
                        break;
                    case DOWN:
                        if(handleTwo.getY() <= WINDOW_HEIGHT - results.getHeight() - 140)
                            handleTwo.setY(handleTwo.getY() + 15);
                        break;
                    case ENTER:
                        animationTimer.start();
                        start.setText("");
                        break;
                }
            }
        });

//        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if(event.getCode() == KeyCode.ENTER){
//                    animationTimer.start();
//                }
//            }
//        });

    }

    public static void setLabelStyle(Label label) {
        label.setPrefHeight(WINDOW_HEIGHT / 10);
        label.setStyle("-fx-font-size:25px; -fx-text-fill: #00a653");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
