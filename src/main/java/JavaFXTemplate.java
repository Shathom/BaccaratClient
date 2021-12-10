import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
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
	private Button connectToServer, exitBetting, startGame, startNewGame, exitGame, exit;
    private Text resultsPmpt, currentWinningsPmpt, playerPmpt, bankerPmpt, bettingAmountPmpt, bettingTypePmpt, portNumberPmpt, ipAddressPmpt;
	private HashMap<String, Scene> sceneMap;
	private HBox portAndIP, connectAndExit, bettingAmountAndRadio, resultsAndButtons, playerCards, bankerCards, bothPlayers;
	private VBox textAndBut, radioAndField, startAndExit, bettingAmountAndField, vBoxBettingScene, startNewGameAndExit, resultsAndCurrentWinnings, ipPromAndText, portPromAndText, resultsPrmp, currentWinningsPrmp, info, pCards, bCards, gScene;
	private Client clientConnection;
	private RadioButton player, banker, draw;
	private ToggleGroup radioButtons;
	PauseTransition pauseBeforeNaturalWinner = new PauseTransition(Duration.seconds(6));
	PauseTransition pauseBeforeNoNaturalWinner = new PauseTransition(Duration.seconds(6));
	PauseTransition pauseBeforeUltimateResult = new PauseTransition(Duration.seconds(13));
	PauseTransition pauseBeforeDealingcards = new PauseTransition(Duration.seconds(3));
	PauseTransition pauseBeforePlayerDraw = new PauseTransition(Duration.seconds(7));
	PauseTransition pauseBeforeBankerDraw = new PauseTransition(Duration.seconds(9));
	private EventHandler<ActionEvent> startNewGameHandler, leaveServerhandler, startGameHandler, connectToServerHandler;
	private Image player1, player2, banker1, banker2, player3, banker3;
	private ImageView player1View, player2View, banker1View, banker2View, player3View, banker3View;
	public BaccaratInfo data = new BaccaratInfo();


	public static void main(String[] args) {
		launch(args);
	}

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
		
		pauseBeforeDealingcards.setOnFinished(e->updateGameScene());
		
		primaryStage.setScene(sceneMap.get("portAndIDScene"));

		primaryStage.show(); 
		
		exit.setOnAction(e->primaryStage.close());
		exitGame.setOnAction(e->primaryStage.close());
		exitBetting.setOnAction(e->primaryStage.close());

	}

	
	public Scene createPortIDScene(Stage primaryStage) {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		
		portNumber = new TextField();
		portNumber.setStyle("-fx-font-size: 1.5em;" + "-fx-background-radius: 20;");
		portNumber.setFocusTraversable(false);
		
		ipAddress = new TextField();
		ipAddress.setStyle("-fx-font-size: 1.5em;" + "-fx-background-radius: 20;");
		ipAddress.setFocusTraversable(false);
		
		connectToServer = new Button("Connect To Server");
		connectToServer.setStyle("-fx-font-size: 1.5em;" +"-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22), linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%), linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9),rgba(255,255,255,0));" + "-fx-background-radius: 30;" + "-fx-background-insets: 0,1,2,3,0;" + "-fx-text-fill: #654b00;" + "-fx-font-weight: bold;");

		exit = new Button("Exit");
		exit.setStyle("-fx-font-size: 1.5em;" +"-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22), linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%), linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9),rgba(255,255,255,0));" + "-fx-background-radius: 30;" + "-fx-background-insets: 0,1,2,3,0;" + "-fx-text-fill: #654b00;" + "-fx-font-weight: bold;");

		
		ipAddressPmpt = new Text("Please Enter IP Address:");
		ipAddressPmpt.setStyle("-fx-font-size: 1.5em;" + "-fx-font-weight: bold;");
		ipAddressPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		portNumberPmpt = new Text("Please Enter Port Number:");
		portNumberPmpt.setStyle("-fx-font-size: 1.5em;" + "-fx-font-weight: bold;");
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
        
		Scene scene = new Scene(pane, 1000, 800);
		scene.getRoot().setStyle("-fx-background-color: #008000 ;" + "-fx-font-family: 'serif'");
		return scene;

		
	}
	
	private void updateGameScene() {
		
		
		pauseBeforeDealingcards.play();
		
		pauseBeforeDealingcards.setOnFinished(e->{
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
			
		});
		
			if (data.naturalWin) {
				pauseBeforeNaturalWinner.setOnFinished(e->{
				if(data.bettingType.equals(data.gameResult)) {
					String handTotals = "Player Total: " + data.playerHandTotal + " Banker Total: " + data.bankerHandTotal;
					String congratsMessage = "Congradulations! You bet " + data.bettingType + "! " + " You have a natural win!!!";
					results.getItems().add(handTotals);
					results.getItems().add(congratsMessage);
					currentWinnings.setText("Current Winnings: $" + Double.toString(data.currentWinnings) + "    " + "Total Winnings: $" + Double.toString(data.totalWinnings));
				} else {
					String handTotals = "Player Total: " + data.playerHandTotal + " Banker Total: " + data.bankerHandTotal;
					String sorryMessage = data.gameResult + " has a natural win\n " + "Sorry, you bet " + data.bettingType + "!" + " You lost your bet :(";
					results.getItems().add(handTotals);
					results.getItems().add(sorryMessage);
					currentWinnings.setText("Current Winnings: $" + Double.toString(data.currentWinnings) + "    " + "Total Winnings: $" + Double.toString(data.totalWinnings));
				}
				
			  });
				pauseBeforeNaturalWinner.play();
				
			} else {
				
				pauseBeforeNoNaturalWinner.setOnFinished(p->{
					
				String noNaturalMessage = "There was no natural winner";
				results.getItems().add(noNaturalMessage);
					
				
				if (data.playerDraw) {
					pauseBeforePlayerDraw.setOnFinished(e->{
						String anotherCardPlayer = "Player gets another card";
						results.getItems().add(anotherCardPlayer);
						player3 = new Image(data.playerHand.get(2));
						player3View.setImage(player3);
					
					});
					pauseBeforePlayerDraw.play();
				}
				if (data.bankerDraw) {
					pauseBeforeBankerDraw.setOnFinished(e->{
						String anotherCardBanker = "Banker gets another card";
						results.getItems().add(anotherCardBanker);
						banker3 = new Image(data.bankerHand.get(2));
						banker3View.setImage(banker3);
					});
					pauseBeforeBankerDraw.play();
				}
				pauseBeforeUltimateResult.setOnFinished(e->{
				if(data.bettingType.equals(data.gameResult)) {
					String handTotals = "Player Total: " + data.playerHandTotal + " Banker Total: " + data.bankerHandTotal;
					String congratsMessage = "Congradulations! You bet " + data.bettingType + "! " + "You won!!!";
					results.getItems().add(handTotals);
					results.getItems().add(congratsMessage);
					currentWinnings.setText("Current Winnings: $" + Double.toString(data.currentWinnings) + "    " + "Total Winnings: $" + Double.toString(data.totalWinnings));
				} else {
					String handTotals = "Player Total: " + data.playerHandTotal + " Banker Total: " + data.bankerHandTotal;
					String sorryMessage = data.gameResult + " won\n " + "Sorry, you bet " + data.bettingType + "!" + " You lost your bet :(";
					results.getItems().add(handTotals);
					results.getItems().add(sorryMessage);
					currentWinnings.setText("Current Winnings: $" + Double.toString(data.currentWinnings) + "    " + "Total Winnings: $" + Double.toString(data.totalWinnings));
				}
				
				});
				pauseBeforeUltimateResult.play();
			});
		   pauseBeforeNoNaturalWinner.play();	
		}			
	}
	
	public Scene createGameScene(Stage primaryStage) {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		
		playerPmpt = new Text("Player");
		playerPmpt.setStyle("-fx-font-size: 2.0em;" + "-fx-font-weight: bold;");
		playerPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		resultsPmpt = new Text("Results:");
		resultsPmpt.setStyle("-fx-font-size: 1.5em;" + "-fx-font-weight: bold;");
		resultsPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		bankerPmpt = new Text("Banker");
		bankerPmpt.setStyle("-fx-font-size: 2.0em;" + "-fx-font-weight: bold;");
		bankerPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		currentWinningsPmpt = new Text("Winnings:");
		currentWinningsPmpt.setStyle("-fx-font-size: 1.5em;" + "-fx-font-weight: bold;");
		currentWinningsPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		results.setPrefWidth(100);
		results.setPrefHeight(1000);
		
		results.setStyle("-fx-font-size: 1.5em;");
		results.setFocusTraversable(false);
		
		currentWinnings.setStyle("-fx-font-size: 1.5em;" + "-fx-background-radius: 20;");
		currentWinnings.setFocusTraversable(false);
		currentWinnings.setEditable(false);
		
		startNewGame = new Button("Start New Game");
		startNewGame.setStyle("-fx-font-size: 1.5em;" +"-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22), linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%), linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9),rgba(255,255,255,0));" + "-fx-background-radius: 30;" + "-fx-background-insets: 0,1,2,3,0;" + "-fx-text-fill: #654b00;" + "-fx-font-weight: bold;");
		
		exitGame = new Button("Exit Game");
		exitGame.setStyle("-fx-font-size: 1.5em;" +"-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22), linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%), linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9),rgba(255,255,255,0));" + "-fx-background-radius: 30;" + "-fx-background-insets: 0,1,2,3,0;" + "-fx-text-fill: #654b00;" + "-fx-font-weight: bold;");

	
		
		startNewGameAndExit = new VBox(20,startNewGame, exitGame);
		startNewGameAndExit.setAlignment(Pos.CENTER);
		resultsAndCurrentWinnings = new VBox();
		resultsPrmp = new VBox(10, resultsPmpt, results);
		currentWinningsPrmp = new VBox(10, currentWinningsPmpt, currentWinnings);
		info = new VBox(70,resultsPrmp, currentWinningsPrmp);
		info.setPrefSize(500, 500);
		
		resultsAndButtons = new HBox(300, info, startNewGameAndExit);
		resultsAndButtons.setAlignment(Pos.CENTER);
		
		player1 = new Image("card_back.jpeg");
		player2 = new Image("card_back.jpeg");
		player3 = new Image("card_back.jpeg");
		
		
		banker1 = new Image("card_back.jpeg");
		banker2 = new Image("card_back.jpeg");
		banker3 = new Image("card_back.jpeg");
		
		player1View = new ImageView(player1);
		player2View = new ImageView(player2);
		player3View = new ImageView(player3);
		
		banker1View =  new ImageView(banker1);
		banker2View = new ImageView(banker2);
		banker3View = new ImageView(banker3);
		
		player1View.setFitHeight(250);
		player1View.setFitWidth(175);
		
		player2View.setFitHeight(250);
		player2View.setFitWidth(175);
		
		player3View.setFitWidth(250);
		player3View.setFitWidth(175);
		
		banker1View.setFitHeight(250);
	    banker1View.setFitWidth(175);
	    
		banker2View.setFitHeight(250);
		banker2View.setFitWidth(175);
		
		banker3View.setFitWidth(250);
		banker3View.setFitWidth(175);
		
		
		playerCards = new HBox(20, player1View, player2View, player3View);
		
		pCards = new VBox(20, playerPmpt, playerCards);
		pCards.setAlignment(Pos.CENTER);

		bankerCards = new HBox(20, banker1View, banker2View, banker3View);
		bCards = new VBox(20, bankerPmpt, bankerCards);
		bCards.setAlignment(Pos.CENTER);
		
		bothPlayers = new HBox(60, pCards, bCards);
		bothPlayers.setAlignment(Pos.CENTER);
		
		gScene = new VBox(50, bothPlayers, resultsAndButtons);
		
		pane.setCenter(gScene);
		
		
		startNewGameHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				primaryStage.setScene(sceneMap.get("bettingScene"));
				results.getItems().clear();
				currentWinnings.setText("");
				
				player1View.setImage(new Image("card_back.jpeg"));
				player2View.setImage(new Image("card_back.jpeg"));
				player3View.setImage(new Image("card_back.jpeg"));
				
				banker1View.setImage(new Image("card_back.jpeg"));
				banker2View.setImage(new Image("card_back.jpeg"));
				banker3View.setImage(new Image("card_back.jpeg"));	
				
			}
		};
		
		
		startNewGame.setOnAction(startNewGameHandler);
		
	   
		Scene scene = new Scene(pane, 1300, 800);
	
		scene.getRoot().setStyle("-fx-background-color: #008000;" + "-fx-font-family: 'serif'");
		return scene;
		
	}
	
	public Scene createBettingScene(Stage primaryStage) {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(60));
		
		bettingAmount = new TextField();
		bettingAmount.setStyle("-fx-font-size: 1.5em;" + "-fx-background-radius: 20;");
		bettingAmount.setFocusTraversable(false);
		
		bettingAmountPmpt = new Text("Betting Amount:");
		bettingAmountPmpt.setStyle("-fx-font-size: 1.5em;" + "-fx-font-weight: bold;");
		bettingAmountPmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		bettingTypePmpt = new Text("Betting Type:");
		bettingTypePmpt.setStyle("-fx-font-size: 1.5em;" + "-fx-font-weight: bold;");
		bettingTypePmpt.setFill(javafx.scene.paint.Color.WHITE);
		
		startGame = new Button("Start Game");
		startGame.setStyle("-fx-font-size: 1.5em;" +"-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22), linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%), linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9),rgba(255,255,255,0));" + "-fx-background-radius: 30;" + "-fx-background-insets: 0,1,2,3,0;" + "-fx-text-fill: #654b00;" + "-fx-font-weight: bold;");

		exitBetting = new Button("Exit");
		exitBetting.setStyle("-fx-font-size: 1.5em;" +"-fx-background-color: linear-gradient(#ffd65b, #e68400),linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22), linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%), linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9),rgba(255,255,255,0));" + "-fx-background-radius: 30;" + "-fx-background-insets: 0,1,2,3,0;" + "-fx-text-fill: #654b00;" + "-fx-font-weight: bold;");

		
		radioButtons = new ToggleGroup();
		player = new RadioButton("Player");
		player.setStyle("-fx-font-size: 1.5em;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;");
		banker = new RadioButton("Banker");
		banker.setStyle("-fx-font-size: 1.5em;" + "-fx-text-fill: white;" +"-fx-font-weight: bold;");
		draw = new RadioButton("Draw");
		draw.setStyle("-fx-font-size: 1.5em;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;");
		
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
				primaryStage.setScene(sceneMap.get("gameScene"));
				updateGameScene();
	
			}
		};
		
		startGame.setOnAction(startGameHandler);

		
		Scene scene = new Scene(pane, 1000, 900);
		scene.getRoot().setStyle("-fx-background-color: #008000 ;" + "-fx-font-family: 'serif'");
		return scene;
		
	}

}

//-fx-control-inner-background: blue;
