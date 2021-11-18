import java.util.ArrayList;
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
	
	
	private TextField portNumber, ipAddress, bettingAmount;
	private TextField currentWinnings = new TextField();
	public ListView<String> results = new ListView<String>();
	private Button connectToServer, exitBetting, startGame, startNewGame, exitGame, exit, tempButton;
    private Text resultsPmpt, currentWinningsPmpt, playerPmpt, bankerPmpt, bettingAmountPmpt, bettingTypePmpt, portNumberPmpt, ipAddressPmpt;
	private HashMap<String, Scene> sceneMap;
	private HashMap<String, String> cardImages;
	private HBox portAndIP, connectAndExit, bettingAmountAndRadio, resultsAndButtons, playerCards, bankerCards, bothPlayers;
	private VBox textAndBut, radioAndField, startAndExit, bettingAmountAndField, vBoxBettingScene, startNewGameAndExit, resultsAndCurrentWinnings, ipPromAndText, portPromAndText, resultsPrmp, currentWinningsPrmp, info, pCards, bCards, gScene;
	private Client clientConnection;
	private RadioButton player, banker, draw;
	private ToggleGroup radioButtons;
	PauseTransition pauseBeforeNaturalWinner = new PauseTransition(Duration.seconds(3));
	PauseTransition pauseBeforeResult = new PauseTransition(Duration.seconds(3));
	PauseTransition pauseBeforeDealingcards = new PauseTransition(Duration.seconds(3));
	PauseTransition pauseBeforePlayerDraw = new PauseTransition(Duration.seconds(3));
	PauseTransition pauseBeforeBankerDraw = new PauseTransition(Duration.seconds(3));
	PauseTransition pauseforEnteringGame = new PauseTransition(Duration.seconds(3));
	private EventHandler<ActionEvent> startnewGameHandler, exitGameHandler, leaveServerhandler, startGameHandler, connectToServerHandler, temp;
	private Image player1, player2, banker1, banker2, player3, banker3;
	private ImageView player1View, player2View, banker1View, banker2View, player3View, banker3View;
	public BaccaratInfo data = new BaccaratInfo();


	public static void main(String[] args) {
		launch(args);
	}
	
//	private void checkClientConnection(String str) {
//		if (clientConnection == null) {
//			System.out.println(str + "client connection is null");
//		} else {
//			System.out.println(str + "client connection is not null");
//		}
//	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
	
		primaryStage.setTitle("Baccarat Client");
        
        data.playerHand = new ArrayList<String>();
        
        data.playerHand.add("card_back.jpeg");
        data.playerHand.add("card_back.jpeg");
        data.playerHand.add("card_back.jpeg");
        
        data.bankerHand = new ArrayList<String>();
        
        data.bankerHand.add("card_back.jpeg");
        data.bankerHand.add("card_back.jpeg");
        data.bankerHand.add("card_back.jpeg");
       
		
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("portAndIDScene",  createPortIDScene(primaryStage));
		sceneMap.put("gameScene",  createGameScene(primaryStage));
		sceneMap.put("bettingScene",  createBettingScene(primaryStage));
		
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
		portNumber.setStyle("-fx-font-size: 1.5em;");
		portNumber.setFocusTraversable(false);
		
		ipAddress = new TextField();
		ipAddress.setStyle("-fx-font-size: 1.5em;");
		ipAddress.setFocusTraversable(false);
		
		connectToServer = new Button("Connect To Server");
		connectToServer.setStyle("-fx-font-size: 1.5em;");
		exit = new Button("Exit");
		exit.setStyle("-fx-font-size: 1.5em;");
		
		ipAddressPmpt = new Text("Please Enter IP Address:");
		ipAddressPmpt.setStyle("-fx-font-size: 1.5em;");
		ipAddressPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		portNumberPmpt = new Text("Please Enter Port Number:");
		portNumberPmpt.setStyle("-fx-font-size: 1.5em;");
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
                clientConnection = new Client(d->{
                	Platform.runLater(()->{
                	   data = (BaccaratInfo)d;
                	   System.out.println(data.currentWinnings);
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
	
	public Scene createGameScene(Stage primaryStage) {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		
		playerPmpt = new Text("Player");
		playerPmpt.setStyle("-fx-font-size: 2.0em;");
		playerPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		resultsPmpt = new Text("Results:");
		resultsPmpt.setStyle("-fx-font-size: 1.5em;");
		resultsPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		bankerPmpt = new Text("Banker");
		bankerPmpt.setStyle("-fx-font-size: 2.0em;");
		bankerPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		currentWinningsPmpt = new Text("Current Winnings:");
		currentWinningsPmpt.setStyle("-fx-font-size: 1.5em;");
		currentWinningsPmpt.setFill(javafx.scene.paint.Color.WHITE);
		

		results.setStyle("-fx-font-size: 1.5em;");
		results.setFocusTraversable(false);
		
		currentWinnings.setStyle("-fx-font-size: 1.5em;");
		currentWinnings.setFocusTraversable(false);
		currentWinnings.setEditable(false);
		
		startNewGame = new Button("Start New Game");
		startNewGame.setStyle("-fx-font-size: 1.5em;");
		
		exitGame = new Button("Exit Game");
		exitGame.setStyle("-fx-font-size: 1.5em;");
		
		tempButton = new Button("temp button");
		tempButton.setStyle("-fx-font-size: 1.5em;");
		
		startNewGameAndExit = new VBox(20,startNewGame, exitGame, tempButton);
		startNewGameAndExit.setAlignment(Pos.BOTTOM_CENTER);
		resultsAndCurrentWinnings = new VBox();
		resultsPrmp = new VBox(10, resultsPmpt, results);
		currentWinningsPrmp = new VBox(10, currentWinningsPmpt, currentWinnings);
		info = new VBox(20,resultsPrmp, currentWinningsPrmp);
		
		resultsAndButtons = new HBox(300, info, startNewGameAndExit);
		resultsAndButtons.setAlignment(Pos.CENTER);
		
		player1 = new Image(data.playerHand.get(0));
		player2 = new Image(data.playerHand.get(1));
		player3 = new Image("card_back.jpeg");
		
		
		banker1 = new Image(data.bankerHand.get(0));
		banker2 = new Image(data.bankerHand.get(1));
		banker3 = new Image("card_back.jpeg");
		
		player1View = new ImageView(player1);
		player2View = new ImageView(player2);
		player3View = new ImageView(player3);
		
		banker1View =  new ImageView(banker1);
		banker2View = new ImageView(banker2);
		banker3View = new ImageView(banker3);
		
		player1View.setFitHeight(200);
		player1View.setFitWidth(175);
		
		player2View.setFitHeight(200);
		player2View.setFitWidth(175);
		
		player3View.setFitWidth(200);
		player3View.setFitWidth(175);
		
		banker1View.setFitHeight(200);
	    banker1View.setFitWidth(175);
	    
		banker2View.setFitHeight(200);
		banker2View.setFitWidth(175);
		
		banker3View.setFitWidth(200);
		banker3View.setFitWidth(175);
		
		
		playerCards = new HBox(10, player1View, player2View, player3View);
		pCards = new VBox(20, playerPmpt, playerCards);
		pCards.setAlignment(Pos.CENTER);

		bankerCards = new HBox(10, banker1View, banker2View, banker3View);
		bCards = new VBox(20, bankerPmpt, bankerCards);
		bCards.setAlignment(Pos.CENTER);
		
		bothPlayers = new HBox(50, pCards, bCards);
		bothPlayers.setAlignment(Pos.CENTER);
		
		gScene = new VBox(50, bothPlayers, resultsAndButtons);
		
		pane.setCenter(gScene);
		
		
	///////scene finished displaying here
		
		
//		// wait 3 seconds
//		pauseBeforeDealingcards.play();
//		
//		// at the end of 3 seconds, update cards
////		pauseBeforeDealingcards.setOnFinished(e->{
////			player1 = new Image(data.playerHand.get(0));
////			player2 = new Image(data.playerHand.get(1));
////			banker1 = new Image(data.bankerHand.get(0));
////			banker2 = new Image(data.bankerHand.get(1));
////		});
//		
//		pauseBeforeNaturalWinner.play();
//		
//		pauseBeforeNaturalWinner.setOnFinished(e->{
//			
////			String naturalWinMessage = "Player data.gameResult + " won a natural win! Congrats!!"
////			results.getItems().add(naturalWinMessage);
//		});
//		
//		pauseBeforePlayerDraw.play();
//		
//		pauseBeforePlayerDraw.setOnFinished(e->{
//			// do things
//		});
//		
//		pauseBeforeBankerDraw.play();
//		
//		pauseBeforeBankerDraw.setOnFinished(e->{
//			// do things
//		});
//		
//		
		//pauseBeforeResult.play();
		
//		pauseBeforeResult.setOnFinished(e->{
//			currentWinnings.setText(Double.toString(data.currentWinnings));
//			results.getItems().add(data.gameResult);
//		});
		
//		// then put another handler for reseting the game
		
		
		exitGame.setOnAction(e ->Platform.exit());
		
	
		Scene scene = new Scene(pane, 1200, 900);
		scene.getRoot().setStyle("-fx-background-color: #008000;" + "-fx-font-family: 'serif'");
		return scene;
		
	}
	
	public void updateGameScene() {
		
		results.getItems().add(data.gameResult);
		
		currentWinnings.setText(Double.toString(data.currentWinnings));
		
		player1 = new Image(data.playerHand.get(0));
		player2 = new Image(data.playerHand.get(1));
		player3 = new Image("card_back.jpeg");
		
		
		banker1 = new Image(data.bankerHand.get(0));
		banker2 = new Image(data.bankerHand.get(1));
		banker3 = new Image("card_back.jpeg");
		
		player1View.setImage(player1);
		player2View.setImage(player2);
		player3View.setImage(player3);
		
		banker1View.setImage(banker1);
		banker2View.setImage(banker2);
		banker3View.setImage(banker3);
		
	}
	
	public Scene createBettingScene(Stage primaryStage) {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		
		bettingAmount = new TextField();
		bettingAmount.setStyle("-fx-font-size: 1.5em;");
		bettingAmount.setFocusTraversable(false);
		
		bettingAmountPmpt = new Text("Betting Amount:");
		bettingAmountPmpt.setStyle("-fx-font-size: 1.5em;");
		bettingAmountPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		bettingTypePmpt = new Text("Betting Type:");
		bettingTypePmpt.setStyle("-fx-font-size: 1.5em;");
		bettingTypePmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		startGame = new Button("Start Game");
		startGame.setStyle("-fx-font-size: 1.5em;");
		
		exitBetting = new Button("Exit");
		exitBetting.setStyle("-fx-font-size: 1.5em;");
		
		radioButtons = new ToggleGroup();
		player = new RadioButton("Player");
		player.setStyle("-fx-font-size: 1.5em;" + "-fx-text-fill: white;");
		banker = new RadioButton("Banker");
		banker.setStyle("-fx-font-size: 1.5em;" + "-fx-text-fill: white;");
		draw = new RadioButton("Draw");
		draw.setStyle("-fx-font-size: 1.5em;" + "-fx-text-fill: white;");
		
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
				data = new BaccaratInfo();
				RadioButton selectedType = (RadioButton)radioButtons.getSelectedToggle();
				data.bettingType = selectedType.getText();
				data.bettingAmount = Integer.parseInt(bettingAmount.getText());
				clientConnection.send(data);
				data = clientConnection.getBaccaratInfo();
				updateGameScene();
				primaryStage.setScene(sceneMap.get("gameScene"));
				//pauseforEnteringGame.play();
				
			}
		};
		
		
		
		startGame.setOnAction(startGameHandler);
		exitBetting.setOnAction(e ->Platform.exit());

		
		
		Scene scene = new Scene(pane, 1000, 900);
		scene.getRoot().setStyle("-fx-background-color: #008000 ;" + "-fx-font-family: 'serif'");
		return scene;
		
	}

}
