import java.util.HashMap;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class JavaFXTemplate extends Application {
	
	
	private TextField portNumber, ipAddress, biddingAmount;
	private Button connectToServer, exit, StartGame, StartNewGame, ExitGame;
    private Text resultsPmpt, currentWinningsPmpt, playerPmpt, bankerPmpt, biddingAmountPmpt, 
    biddingTypePmpt, portNumberPmpt, ipAddressPmpt;
	private HashMap<String, Scene> sceneMap;
	private HashMap<Integer, String> cardImages;
	private HBox VBoxPortAndIP, VBoxConnectAndExit, biddingAmountAndRadio, ResultsAndButtons;
	private VBox textAndBut, startAndExit, vBoxForBiddingScene, startNewGameAndExit;
	private Client clientConnection;
	private RadioButton player, banker, draw;
	private ToggleGroup radioButtons;
	ListView<String> results;
	ListView<String> currentWinnings;
	PauseTransition pauseForWinner = new PauseTransition(Duration.seconds(3));
	PauseTransition pauseForCardDisplay = new PauseTransition(Duration.seconds(3));
	private EventHandler<ActionEvent> startnewGameHandler, exitGameHandler,
	leaveServerhandler, startGamehandler, radioButtonHandler, 
	connectToServerHandler;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	
		primaryStage.setTitle("Baccarat Client");
		
		this.connectToServer = new Button("Connect To Server");
		
		// when player presses a button, you would send info from textfields and create an instance
		// of bainfo class and pass innto the send funciton
		
		this.connectToServer.setOnAction(e-> {primaryStage.setScene(sceneMap.get("biddingScene"));
											primaryStage.setTitle("This is a client");
											clientConnection = new Client(data->{
							Platform.runLater(()->{results.getItems().add(data.toString());
											});
							});
							
											clientConnection.start();
		});
		
		
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("portAndIDScene",  createPortIDScene());
		sceneMap.put("gameScene",  createGameScene());
		sceneMap.put("biddingScene",  createBiddingScene());
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
	}
	
	
	public Scene createPortIDScene() {
		
		
		
	}
	
	public Scene createGameScene() {
		
		
		
	}
	
	public Scene createBiddingScene() {
		
		
		
	}
	
	public void makeCards() {
		
	}

}
