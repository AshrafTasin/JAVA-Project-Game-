package game;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class StartMenu {

    private Pane mainPane;
    private Scene mainScene;
    public static Stage mainStage;
    public static GameMenu gameMenu;

    AudioInputStream audio;
    Clip clip;


    public StartMenu() throws Exception {

        mainPane = new Pane();
        mainPane.setPrefSize(800, 600);

        InputStream imagefile = Files.newInputStream(Paths.get("E:\\images\\a.png"));
        Image background = new Image(imagefile);
        imagefile.close();

        ImageView showBackground = new ImageView(background);
        showBackground.setFitWidth(800);
        showBackground.setFitHeight(600);

        gameMenu = new GameMenu();
        gameMenu.setVisible(true);

        mainPane.getChildren().addAll(showBackground, gameMenu);
        mainScene = new Scene(mainPane);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setTitle("What Goes UP!");

        audio = AudioSystem.getAudioInputStream(new File("E:\\Elements\\aa.wav").getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audio);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public class GameMenu extends Parent {

        public GameMenu() {

            VBox menu0 = new VBox(10);
            VBox menu1 = new VBox(10);
            VBox menu2 = new VBox(10);
            VBox menu3 = new VBox(10);

            menu0.setTranslateX(500);
            menu0.setTranslateY(200);

            menu1.setTranslateX(500);
            menu1.setTranslateY(200);

            menu2.setTranslateX(500);
            menu2.setTranslateY(200);

            menu3.setTranslateX(500);
            menu3.setTranslateY(200);

            final int offset = 400;
            menu1.setTranslateX(offset);

            MenuButton start = new MenuButton("START");
            start.setOnMouseClicked(event -> {

                getChildren().add(menu2);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.1), menu0);
                tt.setToX(menu0.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.1), menu2);
                tt1.setToX(menu0.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(event1 -> {
                    getChildren().remove(menu0);
                });

            });

            MenuButton lvel1 = new MenuButton("LEVEL UNO");
            lvel1.setOnMouseClicked(event -> {

                Game gameStarts = new Game();
                gameStarts.newGame(mainStage);
                clip.stop();

            });

            MenuButton lvel2 = new MenuButton("LEVEL DOS");
            lvel2.setOnMouseClicked(event -> {

                Game2 gameStarts = new Game2();
                gameStarts.newGame(mainStage);
                clip.stop();

            });

            MenuButton back1 = new MenuButton("BACK");
            back1.setOnMouseClicked(event -> {

                getChildren().add(menu0);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.1), menu2);
                tt.setToX(menu2.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.1), menu0);
                tt1.setToX(menu2.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(event1 -> {
                    getChildren().remove(menu2);
                });

            });


            MenuButton btnExit = new MenuButton("EXIT TO DESKTOP");
            btnExit.setOnMouseClicked(event -> {

                System.exit(0);
            });

            MenuButton score = new MenuButton("HOW TO PLAY");
            score.setOnMouseClicked(event -> {

                getChildren().add(menu1);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.1), menu0);
                tt.setToX(menu0.getTranslateX() - offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.1), menu1);
                tt1.setToX(menu0.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(event1 -> {
                    getChildren().remove(menu0);
                });
            });

            MenuButton highscore = new MenuButton("SPACE+A=FORWARD");
            MenuButton highscore1 = new MenuButton("SPACE+D=GO BACK");
            MenuButton highscore2 = new MenuButton("SPACE=JUMP");
            MenuButton highscore3 = new MenuButton("P=PAUSE");
            MenuButton highscore4 = new MenuButton("Q=RESUME");

            MenuButton back = new MenuButton("BACK");
            back.setOnMouseClicked(event -> {

                getChildren().add(menu0);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(0.1), menu1);
                tt.setToX(menu1.getTranslateX() + offset);

                TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.1), menu0);
                tt1.setToX(menu1.getTranslateX());

                tt.play();
                tt1.play();

                tt.setOnFinished(event1 -> {
                    getChildren().remove(menu1);
                });

            });


            menu0.getChildren().addAll(start, score, btnExit);
            menu1.getChildren().addAll(highscore,highscore1,highscore2,highscore3,highscore4,back);
            menu2.getChildren().addAll(lvel1,lvel2,back1);

            Rectangle bg = new Rectangle(800, 600);
            bg.setFill(Color.GREY);
            bg.setOpacity(0.0);

            getChildren().addAll(bg, menu0);

        }
    }


    public static class MenuButton extends StackPane {

        private Text text;

        public MenuButton(String name) {
            text = new Text(name);
            text.setFont(text.getFont().font(20));
            text.setFill(Color.WHITE);

            Rectangle bg = new Rectangle(200, 30);
            bg.setOpacity(0.6);
            bg.setFill(Color.RED);


            setAlignment(Pos.CENTER);
            setRotate(0.5);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {

                bg.setTranslateX(10);
                text.setTranslateX(10);
                bg.setFill(Color.WHITE);
                text.setFill(Color.BLACK);

            });

            setOnMouseExited(event -> {

                bg.setTranslateX(0);
                text.setTranslateX(0);
                bg.setFill(Color.RED);
                text.setFill(Color.WHITE);

            });

        }
    }


    public Stage getMainStage(){

        return mainStage;
    }

}
