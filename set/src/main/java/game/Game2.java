package game;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.System.exit;

public class Game2 {

    private Pane gamePane = new Pane();
    private Scene gameScene;
    private Scene finalScene;
    private Scene pauseScene;
    private Pane finalPane = new Pane();
    private Pane pausePane = new Pane();
    private Stage gameStage;
    private Stage menuStage;
    private Stage pauseStage;

    private static final int GAME_WIDTH = 1280;
    private static final int GAME_HEIGHT = 800;
    private int levelWidth = 1280;
    private int levelHeight = 800;
    private int gohomeX,gohomeY;
    private static int score=0;

    private static Point2D vel = new Point2D(0, 0);

    private boolean A;
    private boolean D;
    private boolean space;
    private boolean paused=false;
    private static boolean canjump = true;

    private AnimationTimer loop;

    private ImageView[] fire;
    private ImageView[] stone;

    Random pos = new Random();

    private static ArrayList<Node> blocks = new ArrayList<>();

    AudioInputStream audio1,audio2,audio3;
    Clip clip1,clip2,clip3;

    private Node balls;

    private  static  BigJump jump1,jump2;
    private Portal[] timet;
    private  static double specialjump=0;

    public void newGame(Stage menuStage) {

        this.menuStage = menuStage;
        this.menuStage.hide();

        initStage();
        initUI();
        getFire();
        getStone();
        gameloop();

        gameStage.show();
        gameStage.setFullScreen(TRUE);

        try {
            audio1 = AudioSystem.getAudioInputStream(new File("E:\\Elements\\game.wav").getAbsoluteFile());
            clip1 = AudioSystem.getClip();
            clip1.open(audio1);
            clip1.loop(Clip.LOOP_CONTINUOUSLY);

            audio2 = AudioSystem.getAudioInputStream(new File("E:\\Elements\\end.wav").getAbsoluteFile());
            clip2 = AudioSystem.getClip();

            audio3 = AudioSystem.getAudioInputStream(new File("E:\\Elements\\clap.wav").getAbsoluteFile());
            clip3 = AudioSystem.getClip();

        }catch (Exception e){}

    }

    private void initStage() {

        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        finalScene = new Scene(finalPane,800, 600);
        pauseScene = new Scene(pausePane,800, 600);
        gameStage = new Stage();
        pauseStage = new Stage();
        gameStage.setScene(gameScene);
        pauseStage.setScene(pauseScene);

        Portal p1,p2,p3;
        p1=new Portal();p2=new Portal();p3=new Portal();

        Image background = null;
        try (InputStream imagefile = Files.newInputStream(Paths.get("E:\\images\\3.jpg"))) {
            background = new Image(imagefile);
        } catch (IOException ex) {
        }

        ImageView showBackground = new ImageView(background);
        showBackground.setFitWidth(80 * Level.LEVEL0[0].length()+100);
        gamePane.getChildren().add(showBackground);

        Image background2 = null;
        try (InputStream imagefile = Files.newInputStream(Paths.get("E:\\images\\exit.png"))) {
            background2 = new Image(imagefile);
        } catch (IOException ex) {
        }

        ImageView showBackground2 = new ImageView(background2);
        showBackground2.setFitWidth(225/4);
        showBackground2.setFitHeight(225/4);
        showBackground2.setX(gohomeX=10);
        showBackground2.setY(gohomeY=10+10+10+10+10+5+5);
        gamePane.getChildren().add(showBackground2);

        timet=new Portal[15];
        jump1=new BigJump();
        Image background3 = null;
        try (InputStream imagefile = Files.newInputStream(Paths.get("E:\\images\\p.gif"))) {
            background3 = new Image(imagefile);
        } catch (IOException ex) {
        }


        ImageView showBackground3 = new ImageView(background3);
        showBackground3.setFitWidth(40+30+10);
        showBackground3.setFitHeight(50);
        showBackground3.setX(p1.x=500+15+15+30);
        showBackground3.setY(p1.y=500+50);
        gamePane.getChildren().add(showBackground3);

        ImageView showBackground4 = new ImageView(background3);
        showBackground4.setFitWidth(40+30+10);
        showBackground4.setFitHeight(50);
        showBackground4.setX(p2.x=800+15+15+30+100-50-10-10);
        showBackground4.setY(p2.y=800+50+200-100+10+5+5);
        gamePane.getChildren().add(showBackground4);

        ImageView showBackground5 = new ImageView(background3);
        showBackground5.setFitWidth(40+30+10);
        showBackground5.setFitHeight(50);
        showBackground5.setX(p3.x=1300+300);
        showBackground5.setY(p3.y=550);
        gamePane.getChildren().add(showBackground5);

        timet[0]=p1;timet[1]=p2;timet[2]=p3;


        background3 = null;
        try (InputStream imagefile = Files.newInputStream(Paths.get("E:\\images\\icon1.png"))) {
            background3 = new Image(imagefile);
        } catch (IOException ex) {
        }


        showBackground3 = new ImageView(background3);
        showBackground3.setFitWidth(50);
        showBackground3.setFitHeight(50);
        showBackground3.setX(jump1.x= 600+400+200+200+100 );
        showBackground3.setY(jump1.y= 600+200+85+85);
        gamePane.getChildren().add(showBackground3);

    }

    private void initUI() {

        levelWidth = 80 * Level.LEVEL0[0].length();
        levelHeight = 1200;
        for (int i = 0; i < Level.LEVEL0.length; i++) {
            String line = Level.LEVEL0[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {

                    case '1':
                        Node platform = createRectangle(j * 60, i * 60, 60, 10,RandomColor2.color());
                        blocks.add(platform);
                        break;
                }
            }
        }

        balls = createRectangle(0, 400, 20, 20, Color.RED);
        balls.setTranslateX(0);
        balls.setTranslateY(600+100);

        balls.translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 640 && offset < levelWidth-(levelWidth/2)-410) {
                gamePane.setLayoutX(-(offset - 640));
            }
        });

        balls.translateYProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > 400 && offset < levelHeight - 400) {
                gamePane.setLayoutY(-(offset - 400));
            }
        });
    }

    private Node createCircle(int x, int y, int w, Color color) {
        Circle entity = new Circle(w);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        gamePane.getChildren().add(entity);
        return entity;

    }

    private Node createRectangle(int x, int y, int w, int h, Color color) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        gamePane.getChildren().add(entity);
        return entity;

    }

    private void key() {

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.D) {
                    D = true;
                } else if (event.getCode() == KeyCode.A) {
                    A = true;
                } else if (event.getCode() == KeyCode.SPACE) {

                    space = true;
                }else if(event.getCode()==KeyCode.P){
                    paused=true;
                }

            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.D) {

                    D = false;
                } else if (event.getCode() == KeyCode.A) {

                    A = false;
                } else if (event.getCode() == KeyCode.SPACE) {

                    space = false;
                } else if(event.getCode()== KeyCode.Q){
                    paused=false;
                }
            }
        });
    }

    private void gameloop() {

        loop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                if(!paused){
                    pauseStage.hide();
                    key();
                    update();
                    moveFire();
                    moveStone();
                    onScreen();
                    onScreenStone();
                    port();
                }

                else{
                    pauseMenu();
                    pauseStage.show();
                    pauseScene.setOnKeyPressed(new EventHandler<KeyEvent>(){

                        @Override
                        public void handle(KeyEvent event) {

                            if(event.getCode()==KeyCode.Q){
                                paused=false;
                            }
                        }
                    });
                }

            }
        };

        loop.start();
    }

    private void endgmae() {
        finalPane.setPrefSize(800,600);

        Image background=null;
        try (InputStream imagefile = Files.newInputStream(Paths.get("E:\\images\\e.jpg"))) {
            background = new Image(imagefile);
        }catch (Exception e){

        }

        ImageView showBackground = new ImageView(background);
        showBackground.setFitWidth(800);
        showBackground.setFitHeight(600);


        VBox v=new VBox(10);
        v.setTranslateX(300);
        v.setTranslateY(300);


        StartMenu.MenuButton btnExit = new StartMenu.MenuButton("GAME OVER!");
        StartMenu.MenuButton result = new StartMenu.MenuButton("Score : "+score);

        btnExit.setOnMouseClicked(event -> {
            gameStage.close();
            StartMenu.mainStage.show();

        });

        result.setOnMouseClicked(event -> {
            gameStage.close();
            StartMenu.mainStage.show();
        });

        v.getChildren().addAll(btnExit,result);
        Rectangle bg = new Rectangle(800, 600);
        bg.setFill(Color.GREY);
        bg.setOpacity(0.0);

        finalPane.getChildren().addAll(showBackground,bg,v);
    }

    private void endgmae2() {
        finalPane.setPrefSize(800,600);

        Image background=null;
        try (InputStream imagefile = Files.newInputStream(Paths.get("E:\\images\\e.jpg"))) {
            background = new Image(imagefile);
        }catch (Exception e){

        }

        ImageView showBackground = new ImageView(background);
        showBackground.setFitWidth(800);
        showBackground.setFitHeight(600);


        VBox v=new VBox(10);
        v.setTranslateX(300);
        v.setTranslateY(300);

        StartMenu.MenuButton btnExit = new StartMenu.MenuButton("Success!");
        StartMenu.MenuButton result = new StartMenu.MenuButton("Score : "+score);

        btnExit.setOnMouseClicked(event -> {
            gameStage.close();
            StartMenu.mainStage.show();

        });

        result.setOnMouseClicked(event -> {
            gameStage.close();
            StartMenu.mainStage.show();
        });

        v.getChildren().addAll(btnExit,result);
        Rectangle bg = new Rectangle(800, 600);
        bg.setFill(Color.GREY);
        bg.setOpacity(0.0);

        finalPane.getChildren().addAll(showBackground,bg,v);
    }

    private void update() {

        if (A && balls.getTranslateX() >= 5) {

            moveBallX(-5);
        }

        if (D && balls.getTranslateX() + 20 <= 1800) {

            moveBallX(5);
        }

        if (space && balls.getTranslateY() >= 5) {
            ++score;
            jumpPlayer();
        }

        if (vel.getY() < 10) {
            vel = vel.add(0, 0.85);
        }

        moveBallY((int) vel.getY());

    }

    private void getFire() {

        fire = new ImageView[25];
        for (int i = 0; i < fire.length; ++i) {

            fire[i] = new ImageView("file:///E:\\images\\f.png");
            fire[i].setFitHeight(40);
            fire[i].setFitWidth(40);
            setFire(fire[i]);
            gamePane.getChildren().add(fire[i]);
        }

    }

    private void setFire(ImageView imaage) {

        imaage.setLayoutX(-(pos.nextInt(370) + 800));
        imaage.setLayoutY((pos.nextInt(3200)));
    }

    private void moveFire() {

        for (int i = 0; i < fire.length; ++i) {
            fire[i].setLayoutX(fire[i].getLayoutX() + 8);

            if(Math.abs((fire[i].getLayoutX() - balls.getTranslateX())) <= 20 && Math.abs((fire[i].getLayoutY() - balls.getTranslateY())) <= 20){

                clip1.stop();

                try{
                    clip2.open(audio2);
                    clip2.loop(0);
                }catch (Exception e){}

                loop.stop();
                endgmae();
                gameStage.setScene(finalScene);
                gameStage.setFullScreen(FALSE);
            }

            if(balls.getTranslateX()-gohomeX<=15  && balls.getTranslateY()-gohomeY<=15)
            {
                clip1.stop();

                try{
                    clip3.open(audio3);
                    clip3.loop(0);
                }catch (Exception e){}

                loop.stop();
                endgmae2();
                gameStage.setScene(finalScene);
                gameStage.setFullScreen(FALSE);
            }


        }
    }

    private void onScreen() {

        for (int i = 0; i < fire.length; ++i) {

            if (fire[i].getLayoutX() > 80 * Level.LEVEL0[0].length()) {

                setFire(fire[i]);
            }
        }
    }

    private void getStone() {

        stone = new ImageView[50];
        for (int i = 0; i < stone.length; ++i) {

            stone[i] = new ImageView("file:///E:\\images\\r2.png");
            stone[i].setFitHeight(40);
            stone[i].setFitWidth(40);
            setFire(stone[i]);
            gamePane.getChildren().add(stone[i]);
        }

    }

    private void setStone(ImageView image) {

        image.setLayoutX((pos.nextInt(3200)));
        image.setLayoutY(-(pos.nextInt(3200)));
    }

    private void moveStone() {

        for (int i = 0; i < stone.length; ++i) {
            stone[i].setLayoutY(stone[i].getLayoutY() + 6);
            stone[i].setLayoutX(stone[i].getLayoutX() - 6);

            if(Math.abs((stone[i].getLayoutX() - balls.getTranslateX())) <= 20 && Math.abs((stone[i].getLayoutY() - balls.getTranslateY())) <= 20){

                clip1.stop();

                try{
                    clip2.open(audio2);
                    clip2.loop(0);
                }catch (Exception e){}

                loop.stop();
                endgmae();
                gameStage.setScene(finalScene);
                gameStage.setFullScreen(FALSE);
            }

            if(balls.getTranslateX()-gohomeX<=15  && balls.getTranslateY()-gohomeY<=15)
            {

                clip1.stop();

                try{
                    clip3.open(audio3);
                    clip3.loop(0);
                }catch (Exception e){}

                loop.stop();
                endgmae2();
                gameStage.setScene(finalScene);
                gameStage.setFullScreen(FALSE);
            }
        }
    }

    private void onScreenStone() {

        for (int i = 0; i < stone.length; ++i) {

            if (stone[i].getLayoutY() > 1250) {

                setStone(stone[i]);
            }
        }
    }

    private void moveBallX(int value) {

        boolean movingRight = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {


            for (Node platform : blocks) {

                if (balls.getBoundsInParent().intersects(platform.getBoundsInParent())) {

                    if (movingRight) {
                        if (balls.getTranslateX() + 20 == platform.getTranslateX()) {
                            return;
                        }
                    } else {
                        if (balls.getTranslateX() == platform.getTranslateX() + 10) {
                            return;
                        }
                    }
                }
            }
            balls.setTranslateX(balls.getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    private void moveBallY(int value) {

        boolean movingDown = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {

            for (Node platform : Game2.blocks) {

                if (balls.getBoundsInParent().intersects(platform.getBoundsInParent())) {

                    if (movingDown) {

                        if (balls.getTranslateY() + 20 == platform.getTranslateY()) {
                            canjump = true;
                            return;
                        }
                    } else {
                        if (balls.getTranslateY() == platform.getTranslateY() + 10) {
                            return;
                        }
                    }
                }
            }
            balls.setTranslateY(balls.getTranslateY() + (movingDown ? 1 : -1));
        }
    }

    private void jumpPlayer() {
        if (canjump) {
            vel = vel.add(0, -30-specialjump*1.00);
            canjump = false;
        }
    }

    private void pauseMenu(){

        pausePane.setPrefSize(800,60);

        Image background=null;
        try (InputStream imagefile = Files.newInputStream(Paths.get("E:\\images\\e.jpg"))) {
            background = new Image(imagefile);
        }catch (Exception e){

        }

        ImageView showBackground = new ImageView(background);
        showBackground.setFitWidth(800);
        showBackground.setFitHeight(600);


        VBox v=new VBox(10);
        v.setTranslateX(300);
        v.setTranslateY(300);


        StartMenu.MenuButton resume = new StartMenu.MenuButton("PRESS 'Q' TO RESUME");

        resume.setOnMouseClicked(event -> {
            gameStage.show();
            paused=false;
        });

        v.getChildren().addAll(resume);
        Rectangle bg = new Rectangle(800, 600);
        bg.setFill(Color.GREY);
        bg.setOpacity(0.0);

        pausePane.getChildren().addAll(showBackground,bg,v);
    }

    private void port() {

        boolean touch=false;
        int catched=-1,turn=-1;
        for(int i=0 ; i<=2 ; i++){

            if( timet[i].matchPort(balls.getTranslateX(),balls.getTranslateY())){
                touch=true;turn=catched=i;
            }
        }
        if(touch==true){

            while(turn==catched) {turn=pos.nextInt(3);}

            balls.setTranslateX(timet[turn].x+20+15);
            balls.setTranslateY(timet[turn].y);
        }

        if(jump1.Collides(balls.getTranslateX(),balls.getTranslateY())){

            specialjump=10;
        }

        else specialjump=0;
    }


}