package com.example.platformergame;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.animation.AnimationTimer;
import java.io.IOException;

public class PlatformGameApplication extends Application {
    private Rectangle obstacle;
    private Rectangle player;
    private Rectangle ground;
    private Rectangle background;
    private double groundLevel = 500;
    private double gravity = 1.0;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private boolean isOnGround = true; // Track if the player is on the ground
    private double playerSpeed = 5.0;

    private Pane root;  // Main layout for the game
    private AnimationTimer gameLoop; // The game loop timer
    @Override
    public void start(Stage stage) throws IOException {
        root = new Pane(); // initializes root game

        createBackground();
        createGround();
        createPlayer();
        createObstacle();

        root.getChildren().addAll(background, ground, player, obstacle);

        FXMLLoader fxmlLoader = new FXMLLoader(PlatformGameApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(root, 800, 600);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    isMovingLeft = true;
                    break;
                case RIGHT:
                    isMovingRight = true;
                    break;
                case SPACE:
                    if (isOnGround) {
                        jump();
                    }
                    break;
                default:
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    isMovingLeft = false;
                    break;
                case RIGHT:
                    isMovingRight = false;
                    break;
                case SPACE:
                    isOnGround = true;
                default:
                    break;
            }
        });


        stage.setScene(scene);
        stage.setTitle("Julian's Game!");
        stage.show();

        startGameLoop(); //initializes and starts game
    }

    private void createBackground() {
        background = new Rectangle(800, 600);
        background.setFill(Color.CYAN);
    }

    private void createGround() {
        ground = new Rectangle(800, 20);
        ground.setFill(Color.RED);
    }

    private void createPlayer() {
        player = new Rectangle(50, 50);
        player.setFill(Color.GREEN);
        player.setTranslateX(100);
        player.setTranslateY(groundLevel - player.getHeight());
    }

    private void createObstacle() {

        obstacle = new Rectangle(100, 100);
        obstacle.setFill(Color.BLACK);
        obstacle.setTranslateX(200);
        obstacle.setTranslateY(groundLevel);
    }





    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateGame();
                renderGame();
            }
        };
        gameLoop.start();
    }

    private void updateGame() {

        if (isMovingLeft) {
            player.setTranslateX(player.getTranslateX() - playerSpeed);
        }
        if (isMovingRight) {
            player.setTranslateX(player.getTranslateX() + playerSpeed);
        }

        if (!isOnGround) {
            player.setTranslateY(player.getTranslateY() + gravity);
        } else {
            gravity = 1;
        }

        checkCollisions();

    }

    private void renderGame() {

        background.setTranslateX(background.getTranslateX() - 1);

        if (background.getTranslateX() <= -background.getWidth()) {
            background.setTranslateX(0);
        }
    }

    private void jump() {
        isOnGround = false;
        gravity = -12.0;
    }

    private void checkCollisions() {

        if (player.getTranslateY() >= groundLevel - player.getHeight()) {
            player.setTranslateY(groundLevel - player.getHeight());
            isOnGround = true;
            verticalSpeed = 0;
        } else if (player.getTranslateX() + player.getWidth() > obstacle.getTranslateX() &&
                player.getTranslateX() < obstacle.getTranslateX() + obstacle.getWidth() &&
                player.getTranslateY() + player.getHeight() >= obstacle.getTranslateY() &&
                player.getTranslateY() + player.getHeight() <= obstacle.getTranslateY() + verticalSpeed) {
            player.setTranslateY(obstacle.getTranslateY() - player.getHeight());
            isOnGround = true;
            verticalSpeed = 0;
        }
        else {
            isOnGround = false;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}