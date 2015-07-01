package visual;

import game.GoldMine;
import game.Team;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import java.util.*;
import javafx.animation.FadeTransition;
import javafx.scene.image.ImageView;
import javafx.animation.TranslateTransition;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;

public class Run extends Application {

    int cicle;
    boolean GameTurn=true;
    public static int SLEEP_TIME = 350;
    FlowPane method_pane = new FlowPane();
    public Game G = new Game();

    @Override
    public void start(Stage MapStage) {
        cicle = 0;
        MapStage.setResizable(false);
        MapStage.setTitle("JAVA Final Project RC ");
        new Thread(() -> {
            for (int k = 0;GameTurn; k++) {
                Platform.runLater(() -> {
                    FlowPane hubs;
                    BorderPane BankInfo = new BorderPane();
                    //BorderPane Top = new BorderPane();
                    G.update(cicle);
                    Label Bank_info_A = new Label("Bank A = " + G.getAllBankAmount(Team.A));
                    Label Bank_info_B = new Label("Bank B = " + G.getAllBankAmount(Team.B));
                    Label soldier_info_A = new Label("Soldier A = " + G.soldier_A.getHealth());
                    Label soldier_info_B = new Label("Soldier B = " + G.soldier_B.getHealth());
                    Label King_info_A = new Label("King Health A = "+G.getKing(Team.A).getHealth());
                    Label King_info_B = new Label("King Health B = "+G.getKing(Team.B).getHealth());
                    Label Left_space = new Label(" ");
                    Left_space.setMinWidth(20);
                    BankInfo.setBottom(new FlowPane(Bank_info_A, Bank_info_B));
                    BankInfo.setTop(new FlowPane(King_info_A,soldier_info_A, King_info_B,soldier_info_B));
                    BankInfo.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #0077ff, #0077ff)");
                    Bank_info_A.setMinWidth(300);
                    Bank_info_B.setMinWidth(300);
                    King_info_A.setMinWidth(150);
                    King_info_B.setMinWidth(150);
                    soldier_info_A.setMinWidth(150);
                    soldier_info_B.setMinWidth(150);
                    hubs = hubsUpgrade();
                    BankInfo.setCenter(hubs);
                    BankInfo.setLeft(new BorderPane(Left_space));
                    Scene scene = new Scene(BankInfo, G.getScreenSize(), G.getScreenSize());
                    MapStage.setScene(scene);

                    cicle++;
                    MapStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            System.exit(0);
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
                    MapStage.show();

                });
                try {
                    Thread.sleep(SLEEP_TIME);
                   
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                GameTurn=endOfGame();
            }
        }).start();
    }

    private FlowPane hubsUpgrade() {
        FlowPane hubs = new FlowPane();
        try {
            for (int i = 0; i < G.getRow(); i++) {
                for (int j = 0; j < G.getColumn(); j++) {
                    hubs.getChildren().add(G.getHubView(i, j));
                }
            }
        } catch (Exception e) {
            System.out.println("exp on RUN>>hubsUpgrade()");
            e.printStackTrace();
        }
        
        return hubs;
    }
    public boolean endOfGame(){
        return (G.getKing(Team.A).getHealth()>0 && G.getKing(Team.B).getHealth()>0);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
