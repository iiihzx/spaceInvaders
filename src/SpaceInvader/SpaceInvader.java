import java.util.ArrayList;
import java.util.Timer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import java.io.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.TimerTask;
import javax.swing.*;
import java.util.Timer;

import static java.lang.StrictMath.random;
import static javafx.scene.paint.Color.*;

public class SpaceInvader extends Application {
    Scene scene2;
    Timer timer;
    boolean right=true;
    public static final double Player_speed =3.0;
    public static final double Player_bullet_speed =6.0;
    public static double enemy_speed = 1.0;
    public static final double enemy_vertical_speed =0.2;
    public static final double enemy_bullet1_speed =4.0;
    public static final double enemy_bullet2_speed=5.0;
    public static final double enemy_bullet3_speed=6.0;
    double time=200/enemy_speed;
    double leftmost;
    double rightmost;
    Group pane1;
    boolean st=true;
    public int score =0;
    public int kill=0;
    public int lives=3;
    double alien_wid=100;
    double alien_hgt=70;
    Label Llevel;
    Label Llevel2;
    Label Llevel3;
    Label Llives;
    Label Llives2;
    Label Llives3;
    Label Lscore;
    Label win;
    Label lose;
    Image alien1=new Image("images/enemy1.png");
    Image alien2=new Image("images/enemy2.png");
    Image alien3=new Image("images/enemy3.png");
    Image player1=new Image("images/player.png");
    Image bullet1=new Image("images/bullet1.png");
    Image bullet2=new Image("images/bullet2.png");
    Image bullet3=new Image("images/bullet3.png");
    Image bullet4=new Image("images/player_bullet.png");
    String sound1 = getClass().getClassLoader().getResource("shoot.wav").toString();
    AudioClip shoot = new AudioClip(sound1);
    String sound2 = getClass().getClassLoader().getResource("explosion.wav").toString();
    AudioClip explode = new AudioClip(sound2);
    String sound3 = getClass().getClassLoader().getResource("invaderkilled.wav").toString();
    AudioClip killed = new AudioClip(sound3);
    ship player=new ship();
    ImageView playerv=new ImageView(player1);
    alien[] aliens=new alien[50];
    ImageView[] enemies = new ImageView[50];
    ImageView[] bulletview=new ImageView[50];
    ImageView[] pbullets=new ImageView[20];

    private void populate(Group pane) {
        lives=3;
        kill=0;
        for (int i = 0; i < 50; i++) {
            int row = 50 / 10;
            int col = 50 % 10;
            alien newa = new alien();
            aliens[i] = newa;
        }
        for (int i = 0; i < 50; i++) {
            if (i < 20) {
                enemies[i] = new ImageView(alien1);
                bulletview[i] = new ImageView(bullet1);
            } else if (i < 40) {
                enemies[i] = new ImageView(alien2);
                bulletview[i] = new ImageView(bullet2);
            } else if (i < 50) {
                enemies[i] = new ImageView(alien3);
                bulletview[i] = new ImageView(bullet3);
            }
            int row = i / 10;
            int col = i % 10;
            enemies[i].setX(200 + alien_wid * col);
            enemies[i].setY(row * alien_hgt);
            enemies[i].setFitWidth(alien_wid);
            enemies[i].setFitHeight(alien_hgt);
            enemies[i].setPreserveRatio(true);
            bulletview[i].setY(-100.0);
            bulletview[i].setX(-100.0);
            pane.getChildren().add(enemies[i]);
            pane.getChildren().add(bulletview[i]);
        }
        for (int i =0; i<20 ;i++){
            pbullets[i] = new ImageView(bullet4);
            pbullets[i].setY(-100.0);
            pbullets[i].setX(-100.0);
            pane.getChildren().add(pbullets[i]);

        }        pane.getChildren().add(playerv);
        playerv.setX(700);
        playerv.setY(720);
        playerv.setFitHeight(100);
        playerv.setFitWidth(100);
        playerv.setPreserveRatio(true);
    }

    int level=1;
    // We don't need a main method, since JavaFX runs from start() below.
    private void keylistener (KeyEvent e){
        if(e.getCode() == KeyCode.DIGIT1){
            level=1;
            enemy_speed=1.0;
            System.out.println("level"+level);
        }else if(e.getCode()==KeyCode.DIGIT2){
            level=2;
            enemy_speed=2.0;
            System.out.println("level"+level);
        }else if(e.getCode()==KeyCode.DIGIT3){
            level=3;
            enemy_speed = 3.0;
            System.out.println("level"+level);
        }else if (e.getCode() == KeyCode.D) {
            double x = playerv.getX();
            x += 10;
            if(x<1400) {
                playerv.setX(x);
            }
        } else if (e.getCode() == KeyCode.A) {
            double x = playerv.getX();
            x -= 10;
            if(x>0) {
                playerv.setX(x);
            }
        } else if (e.getCode() == KeyCode.SPACE) { // shoot
            for(ImageView i: pbullets){
                if(i.getY()<0){
                    shoot.play();
                    i.setX(playerv.getX());
                    i.setY(playerv.getY());
                    break;
                }
            }
        } else if (e.getCode()==KeyCode.R){
            restart();
        } else if(e.getCode()==KeyCode.Q){
            System.exit(0);
        }
    }


    @Override
    public void start(Stage stage) {
        score=0;
        Image image = new Image("images/logo.png", 1600, 600, true, true);
        ImageView imageView = new ImageView(image);
        final double rem = new Text("").getLayoutBounds().getHeight();
        stage.setTitle("spaceinvader");
        GridPane pane = new GridPane();
        pane.getChildren().add(imageView);
        Button button1 = new Button("Start");
        Button button2 = new Button("Quit");
        Button button3 = new Button("Quit");
        Scene scene1 = new Scene(pane, 1600, 1200);
        scene1.setOnKeyPressed(e -> keylistener(e));
        Label startlabel=new Label("Tab Start then can choose to start game from 1/2/3 level.");
        Label usernameLabel = new Label("A or D, move the ship left or right");
        Label fire=new Label("SPACE -FIRE");
        Label studentname=new Label("20715162 z55han");
        pane.add(startlabel, 0,5,100,1);
        pane.add(usernameLabel,0,6);
        pane.add(fire, 0, 7);
        pane.add(studentname, 0, 8);
        pane.add(button1, 0, 2, 4, 1);
        pane.add(button3, 0, 3, 4, 1);
        Group pane1=new Group();
        Lscore=new Label("Score: 0");
        Llives=new Label("Lives: 3");
        Llives2=new Label("Lives: 2");
        Llives3=new Label("Lives: 1");
        Llevel=new Label("Level: 1");
        Llevel2=new Label("Level: 2");
        Llevel3=new Label("Level: 3");
        win=new Label("You win!!!!Type 1,2 or 3 to choose level, then tab R to restart, or type Q to quit");
        lose=new Label("You lose!!! Type 1,2 or 3 to choose level, then tab R to restart, or type Q to quit");
        win.setTextFill(WHITE);
        lose.setTextFill(WHITE);
        Llevel.setTextFill(WHITE);
        Llevel2.setTextFill(WHITE);
        Llevel3.setTextFill(WHITE);
        Lscore.setTextFill(WHITE);
        Llives.setTextFill(WHITE);
        Llives2.setTextFill(WHITE);
        Llives3.setTextFill(WHITE);
        // pane1.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        //StackPane sp = new StackPane();
        //pane1.add(sp,1600,1200);
        Scene scene2 = new Scene(pane1, 1600, 1200,BLACK);
        populate(pane1);
        scene2.setOnKeyPressed(e -> keylistener(e));
        pane1.getChildren().add(Llevel);
        pane1.getChildren().add(Llevel2);
        pane1.getChildren().add(Llevel3);
        pane1.getChildren().add(Llives2);
        pane1.getChildren().add(Llives3);
        if(level==1){
            Llevel.setLayoutX(400);
            Llevel.setLayoutY(0);
            Llevel2.setLayoutY(-100);
            Llevel3.setLayoutY(-100);
        } else if (level==2){
            Llevel2.setLayoutX(400);
            Llevel2.setLayoutY(0);
            Llevel.setLayoutY(-100);
            Llevel3.setLayoutY(-100);
        } else if (level==3){
            Llevel3.setLayoutX(400);
            Llevel3.setLayoutY(0);
            Llevel2.setLayoutY(-100);
            Llevel.setLayoutY(-100);
        }
        pane1.getChildren().add(win);
        pane1.getChildren().add(lose);
        win.setLayoutX(-100);
        win.setLayoutY(-100);
        lose.setLayoutX(-100);
        lose.setLayoutY(-100);
        pane1.getChildren().add(Lscore);
        Lscore.setLayoutX(600);
        Lscore.setLayoutY(0);
        pane1.getChildren().add(Llives);
        Llives.setLayoutX(800);
        Llives.setLayoutY(0);
        Llives2.setLayoutX(-800);
        Llives2.setLayoutY(-10);
        Llives3.setLayoutX(-800);
        Llives3.setLayoutY(-10);
        // pane1.getChildren().add(button1);
        Scene scene3 = new Scene(new StackPane(button2), 1600, 1200);
        Scene scene4 = new Scene(new StackPane(button2), 1600, 1200);
        Scene scene5 = new Scene(new StackPane(button2), 1600, 1200);
        Scene scene6 = new Scene(new StackPane(button2), 1600, 1200);
        button1.setOnAction(event -> {
            stage.setTitle("spaceinvader");
            timer = new Timer();
            TimerTask task = new TimerTask()  {
                @Override
                public void run() {
                    if(st) {
                        handle_animation(stage);
                    }
                }
            };
            final int FPS = 30;
            timer.schedule(task, 0, 1000/30);
            stage.setScene(scene2);
        });
        button2.setOnAction(event -> {
            java.lang.System.exit(0);
        });
        button3.setOnAction(event -> {
            java.lang.System.exit(0);
        });
        // show starting scene
        stage.setTitle("Space Invaders");
        stage.setScene(scene1);
        stage.show();


    }

    void handle_animation(Stage stage) {
        ++time;
        if (enemies[49].getY() > 650 && kill <50) {
            losenext();
            // System.out.println("jjjj");
        }  else if (kill==50){
            winnext();
        }else {
            if (time % (900 / enemy_speed) < (450 / enemy_speed)) {
                for (ImageView i : enemies) {
                    if (i.getY() >= 0) {
                        i.setX(i.getX() + enemy_speed);
                        i.setY(i.getY() + enemy_vertical_speed);
                    }
                }
            } else {
                for (ImageView i : enemies) {
                    if (i.getY() >= 0) {
                        i.setX(i.getX() - enemy_speed);
                        i.setY(i.getY() + enemy_vertical_speed);
                    }


                }
            }
            if (time / enemy_speed % 20 > 18) {
                System.out.println("bullet");
                int acc = 0;
                while (true) {
                    System.out.println("here");
                    System.out.println(acc);
                    if (acc >= 1 || acc >= (50 - kill)) {
                        System.out.println("here1");
                        break;
                    } else {
                        System.out.println("here2");
                        double res = random();
                        res *= 50;
                        int j = (int) res;
                        System.out.println(bulletview[j].getX());
                        if (bulletview[j].getX() < 0 || bulletview[j].getY() > 700) {

                            double yy = enemies[j].getY() + alien_hgt;
                            double xx = enemies[j].getX();
                            bulletview[j].setX(xx);
                            bulletview[j].setY(yy);
                        }
                        acc++;
                    }
                }
            }
            for (int i = 0; i < 50; i++) {
                if (bulletview[i].getX() > 0) {
                    if (i < 20) {
                        bulletview[i].setY(bulletview[i].getY() + enemy_bullet1_speed);
                    } else if (i < 40) {
                        bulletview[i].setY(bulletview[i].getY() + enemy_bullet2_speed);
                    } else if (i < 50) {
                        bulletview[i].setY(bulletview[i].getY() + enemy_bullet3_speed);
                    }
                }
            }
        }
        for (ImageView i : pbullets) {
            //   System.out.println("hereh????");
            if (i.getY() > 0) {
                i.setY(i.getY() - Player_bullet_speed);
            } else {
                i.setY(-100);
            }

        }
        collide1(stage);
        for (ImageView i : pbullets) {
            if(i.getY()>0) {
                collide2(i);
                // System.out.println("x"+i.getX()+"y"+i.getY());
            }
        }
        if (kill==50){
            System.out.println("terminate!!!!!!");
            //clean();
        }
    }

    void collide1(Stage stage){
        for(ImageView i:bulletview){
            if((i.getX()<(playerv.getFitWidth()+playerv.getX()) &&
                    (i.getX()+i.getFitWidth())>playerv.getX())&&
                    (i.getY()<(playerv.getFitHeight()+playerv.getY()) &&
                            (i.getY()+i.getFitHeight())>playerv.getY())){
                //  System.out.println("be shooooooooooooooot");
                i.setY(-100);
                if(lives>1){
                    lives--;
                    if(lives==2){
                        //System.out.println("live2");
                        Llives.setLayoutX(-400);
                        Llives2.setLayoutX(800);
                        Llives2.setLayoutY(0);

                    } else if (lives==1){
                       // System.out.println("live1");
                        Llives2.setLayoutX(-400);
                        Llives3.setLayoutX(800);
                        Llives3.setLayoutY(0);
                    }
                   // System.out.println("ss");
                } else{
                    //System.out.println("die");
                    losenext();
                }
                System.out.println(player.getLife()+"life");
            }
        }
    }

    void collide2( ImageView i){
        if (i.getX() > 0) {
            for (int j=0; j<50; j++) {
                if((i.getX()<(enemies[j].getFitWidth()+enemies[j].getX()) &&
                        (i.getX()+i.getFitWidth())>enemies[j].getX())&&
                        (i.getY()<(enemies[j].getFitHeight()+enemies[j].getY()) &&
                                (i.getY()+i.getFitHeight())>enemies[j].getY())) {
                    // System.out.println("shooot ya");
                    enemies[j].setX(-100);
                    enemies[j].setY(-100);
                    i.setY(-100);
                    i.setX(-100);
                    kill++;
                    score += 50;
                    killed.play();
                    //enemy_speed;
                }
            }
        }
    }

    void losenext(){
        clean();
        st=false;
        lose.setLayoutX(400);
        lose.setLayoutY(400);

    }
     void winnext(){
        if(level==1){
            Llevel.setLayoutY(-100);
            Llives2.setLayoutY(0);
            Llives2.setLayoutX(400);
        } else if(level==2){
             Llevel2.setLayoutY(-100);
             Llives3.setLayoutY(0);
             Llives3.setLayoutX(400);
         }
        if(level<3) {
            level++;
            restart();
        } else {
           win.setLayoutX(400);
           win.setLayoutY(400);
        }
     }
    void clean(){
        for(ImageView i:enemies){
            i.setX(-100);
            i.setY(-100);
        }
        for(ImageView i:pbullets){
            i.setX(-100);
            i.setY(-100);
        }
        for(ImageView i:bulletview){
            i.setX(-100);
            i.setY(-100);
        }
        Llives3.setLayoutX(-100);
        Llives3.setLayoutY(-100);
        Llevel.setLayoutX(-100);
        Llevel.setLayoutX(-100);
        Llevel2.setLayoutX(-100);
        Llevel3.setLayoutX(-100);
        Lscore.setLayoutX(-100);
        Lscore.setLayoutX(-100);
        playerv.setY(-100);
    }

    void restart(){
        win.setLayoutY(-1000);
        lose.setLayoutY(-1000);
        if(level==1){
            Llevel.setLayoutY(0);
            Llevel.setLayoutX(400);
            enemy_speed=1.0;
        }else if (level==2){
            enemy_speed=2.0;
            Llevel2.setLayoutY(0);
            Llevel2.setLayoutX(400);
        }else if (level==3){
            enemy_speed=3.0;
            Llevel3.setLayoutY(0);
            Llevel3.setLayoutX(400);
        }
        Llives.setLayoutY(0);
        Llives.setLayoutX(400);
        Lscore.setLayoutX(600);
        Lscore.setLayoutY(0);
        lose.setLayoutX(-100);
        lives=3;
        kill=0;
        time=225/enemy_speed;
        for (int i = 0; i < 50; i++) {
            int row = i / 10;
            int col = i % 10;
            enemies[i].setX(200 + alien_wid * col);
            enemies[i].setY(row * alien_hgt);
            bulletview[i].setY(-100.0);
            bulletview[i].setX(-100.0);
        }
        for (int i =0; i<20 ;i++){
            //pbullets[i] = new ImageView(bullet4);
            pbullets[i].setY(-100.0);
            pbullets[i].setX(-100.0);

        }
        playerv.setX(700);
        playerv.setY(720);
        st=true;
    }
}