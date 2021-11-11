import java.util.HashMap;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class JavaFXTemplate extends Application {
	
	
	private TextField portNumber, ipAddress, bettingAmount, results, currentWinnings;
	private Button connectToServer, exitBetting, startGame, startNewGame, exitGame, exit;
    private Text resultsPmpt, currentWinningsPmpt, playerPmpt, bankerPmpt, bettingAmountPmpt, bettingTypePmpt, portNumberPmpt, ipAddressPmpt;
	private HashMap<String, Scene> sceneMap;
	private HashMap<String, String> cardImages;
	private HBox portAndIP, connectAndExit, bettingAmountAndRadio, resultsAndButtons, playerCards, bankerCards, bothPlayers;
	private VBox textAndBut, radioAndField, startAndExit, bettingAmountAndField, vBoxBettingScene, startNewGameAndExit, resultsAndCurrentWinnings, ipPromAndText, portPromAndText, resultsPrmp, currentWinningsPrmp, info, pCards, bCards, gScene;
	private Client clientConnection;
	private RadioButton player, banker, draw;
	private ToggleGroup radioButtons;
	PauseTransition pauseForNaturalWinner = new PauseTransition(Duration.seconds(3));
	PauseTransition pauseForWinner = new PauseTransition(Duration.seconds(3));
	PauseTransition pauseAfterDealingcards = new PauseTransition(Duration.seconds(3));
	PauseTransition pauseforPlayerDraw = new PauseTransition(Duration.seconds(3));
	PauseTransition pauseforBankerDraw = new PauseTransition(Duration.seconds(3));
	PauseTransition pauseforEnteringGame = new PauseTransition(Duration.seconds(3));
	private EventHandler<ActionEvent> startnewGameHandler, exitGameHandler, leaveServerhandler, startGameHandler, connectToServerHandler;
	private Image player1, player2, banker1, banker2;
	private ImageView player1View, player2View, banker1View, banker2View;
	public BaccaratInfo data;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	
		primaryStage.setTitle("Baccarat Client");

		
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("portAndIDScene",  createPortIDScene(primaryStage));
		sceneMap.put("gameScene",  createGameScene());
		sceneMap.put("bettingScene",  createBettingScene());
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		pauseforEnteringGame.setOnFinished(e->primaryStage.setScene(sceneMap.get("gameScene")));
		
		primaryStage.setScene(sceneMap.get("portAndIDScene"));

		primaryStage.show(); 
		
		
		
	}
	
	
	public Scene createPortIDScene(Stage primaryStage) {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		
		portNumber = new TextField();
		portNumber.setStyle("-fx-font-size: 2.0em;");
		portNumber.setFocusTraversable(false);
		
		ipAddress = new TextField();
		ipAddress.setStyle("-fx-font-size: 2.0em;");
		ipAddress.setFocusTraversable(false);
		
		connectToServer = new Button("Connect To Server");
		connectToServer.setStyle("-fx-font-size: 2.0em;");
		exit = new Button("Exit");
		exit.setStyle("-fx-font-size: 2.0em;");
		
		ipAddressPmpt = new Text("Please Enter IP Address:");
		ipAddressPmpt.setStyle("-fx-font-size: 2.0em;");
		ipAddressPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		portNumberPmpt = new Text("Please Enter Port Number:");
		portNumberPmpt.setStyle("-fx-font-size: 2.0em;");
		portNumberPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		ipPromAndText = new VBox(10, ipAddressPmpt, ipAddress);
		ipPromAndText.setAlignment(Pos.CENTER);
		portPromAndText = new VBox(10, portNumberPmpt, portNumber);
		portPromAndText.setAlignment(Pos.CENTER);

		
		connectAndExit = new HBox(30, connectToServer, exit);
		connectAndExit.setAlignment(Pos.CENTER);
		portAndIP = new HBox(30 ,portPromAndText, ipPromAndText);
		portAndIP.setAlignment(Pos.CENTER);
		textAndBut = new VBox(40, portAndIP, connectAndExit);
		textAndBut.setAlignment(Pos.CENTER);

		pane.setCenter(textAndBut);
		
		connectToServerHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				int portNum = Integer.parseInt(portNumber.getText());
				primaryStage.setScene(sceneMap.get("bettingScene"));
                clientConnection = new Client(data->{
                	Platform.runLater(()->{results.setText(data.toString());
                	});
                }, portNum, ipAddress.getText());

                clientConnection.start();
              };

		};
		
		
		connectToServer.setOnAction(connectToServerHandler);
		
		exit.setOnAction(e ->Platform.exit());
        
		
		
		Scene scene = new Scene(pane, 1000, 800);
		scene.getRoot().setStyle("-fx-background-color: #008000 ;" + "-fx-font-family: 'serif'");
		return scene;

		
	}
	
	public Scene createGameScene() {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		
		playerPmpt = new Text("Player");
		playerPmpt.setStyle("-fx-font-size: 2.0em;");
		playerPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		resultsPmpt = new Text("Results:");
		resultsPmpt.setStyle("-fx-font-size: 2.0em;");
		resultsPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		bankerPmpt = new Text("Banker");
		bankerPmpt.setStyle("-fx-font-size: 2.0em;");
		bankerPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		currentWinningsPmpt = new Text("Current Winnings:");
		currentWinningsPmpt.setStyle("-fx-font-size: 2.0em;");
		currentWinningsPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		results = new TextField();
		results.setStyle("-fx-font-size: 2.0em;");
		results.setFocusTraversable(false);
		results.setEditable(false);
		
		currentWinnings = new TextField();
		currentWinnings.setStyle("-fx-font-size: 2.0em;");
		currentWinnings.setFocusTraversable(false);
		currentWinnings.setEditable(false);
		
		startNewGame = new Button("Start New Game");
		startNewGame.setStyle("-fx-font-size: 2.0em;");
		
		exitGame = new Button("Exit Game");
		exitGame.setStyle("-fx-font-size: 2.0em;");
		
		startNewGameAndExit = new VBox(20,startNewGame, exitGame);
		startNewGameAndExit.setAlignment(Pos.BOTTOM_CENTER);
		resultsAndCurrentWinnings = new VBox();
		resultsPrmp = new VBox(10, resultsPmpt, results);
		currentWinningsPrmp = new VBox(10, currentWinningsPmpt, currentWinnings);
		info = new VBox(20,resultsPrmp, currentWinningsPrmp);
		
		resultsAndButtons = new HBox(300, info, startNewGameAndExit);
		
		player1 = new Image("club1.jpg");
		player2 = new Image("diamond2.jpg");
		
		banker1 = new Image("heart4.jpg");
		banker2 = new Image("spade13.jpg");
		
		player1View = new ImageView(player1);
		player2View = new ImageView(player2);
		
		banker1View =  new ImageView(banker1);
		banker2View = new ImageView(banker2);
		
		playerCards = new HBox(10, player1View, player2View);
		pCards = new VBox(20, playerPmpt, playerCards);
		pCards.setAlignment(Pos.CENTER);

		playerCards = new HBox(10, banker1View, banker2View);
		bCards = new VBox(20, bankerPmpt, playerCards);
		bCards.setAlignment(Pos.CENTER);
		
		bothPlayers = new HBox(50, pCards, bCards);
		
		gScene = new VBox(50, bothPlayers, resultsAndButtons);
		
		pane.setCenter(gScene);
		
		exitGame.setOnAction(e ->Platform.exit());
		
		
		
		
		
	
		Scene scene = new Scene(pane, 1000, 900);
		scene.getRoot().setStyle("-fx-background-color: #008000;" + "-fx-font-family: 'serif'");
		return scene;
		
	}
	
	public Scene createBettingScene() {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		
		bettingAmount = new TextField();
		bettingAmount.setStyle("-fx-font-size: 2.0em;");
		bettingAmount.setFocusTraversable(false);
		
		bettingAmountPmpt = new Text("Betting Amount:");
		bettingAmountPmpt.setStyle("-fx-font-size: 2.0em;");
		bettingAmountPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		bettingTypePmpt = new Text("Betting Type:");
		bettingTypePmpt.setStyle("-fx-font-size: 2.0em;");
		bettingTypePmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		startGame = new Button("Start Game");
		startGame.setStyle("-fx-font-size: 2.0em;");
		
		exitBetting = new Button("Exit");
		exitBetting.setStyle("-fx-font-size: 2.0em;");
		
		radioButtons = new ToggleGroup();
		player = new RadioButton("Player");
		player.setStyle("-fx-font-size: 2.0em;" + "-fx-text-fill: white;");
		banker = new RadioButton("Banker");
		banker.setStyle("-fx-font-size: 2.0em;" + "-fx-text-fill: white;");
		draw = new RadioButton("Draw");
		draw.setStyle("-fx-font-size: 2.0em;" + "-fx-text-fill: white;");
		
		player.setToggleGroup(radioButtons);
		banker.setToggleGroup(radioButtons);
		draw.setToggleGroup(radioButtons);
		
		radioAndField = new VBox(20, bettingTypePmpt, player, banker, draw);		
		bettingAmountAndField = new VBox(20,bettingAmountPmpt, bettingAmount);
		
		startAndExit = new VBox(30, startGame, exitBetting);
		startAndExit.setAlignment(Pos.CENTER);
		bettingAmountAndRadio = new HBox(100, radioAndField, bettingAmountAndField);
		bettingAmountAndRadio.setAlignment(Pos.CENTER);
		vBoxBettingScene = new VBox(100, bettingAmountAndRadio, startAndExit);
		vBoxBettingScene.setAlignment(Pos.CENTER);
		pane.setCenter(vBoxBettingScene);
	
		
		startGameHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				RadioButton selectedType = (RadioButton)radioButtons.getSelectedToggle();
				data.bettingType = selectedType.getText();
				data.bettingAmount = Double.parseDouble(bettingAmount.getText());
				clientConnection.send(data);
				pauseforEnteringGame.play();
				
			}
		};
		
		
		
		startGame.setOnAction(startGameHandler);
		exitBetting.setOnAction(e ->Platform.exit());

		
		
		Scene scene = new Scene(pane, 1000, 900);
		scene.getRoot().setStyle("-fx-background-color: #008000 ;" + "-fx-font-family: 'serif'");
		return scene;
		
	}

}
