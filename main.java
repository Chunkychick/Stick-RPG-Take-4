import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;
import java.util.Random;

//so many imports /:
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class main extends Application {

	//dimensions of the game
	private final int width = 1280;
	private final int height = 768;

	int[][] PixelArray = new int[width][height];
	private final int maxX = 2000;
	private final int maxY = 2000;
	private final int fps = 30; // 55 is about the fastest atm //50 is the normal amount
	double prevTime = 0;
	int prefferedTime = (1000/fps);
	int sleepTime = 0;

  int walkDelay = 10;
  int walkDelayCurrent = 0;

	int number = 0;
	double pressTimer = 0;

  String pressed;
  String button;

	String lastKeyPressed;

	int loopNum = 0;

	Image grass = new Image("file:res/Grass.png");

	static Player player = new Player(400, 400, 16, "player_down_0.jpg", 28, 40, "player", 1);

	int halfX = width/2 - 28/2;
	int halfY = height/2 - 40/2;

	public void restart(Objects player){

		Objects.objectlist.clear();

		player.NewX(400);
		player.NewY(400);
		player.SetHp(4);
		Objects.objectlist.add(player);

	}

	public void start(Stage Window) {
		 boolean New = true;



     Window.setTitle("Game");
     System.out.println("test");

     Group root = new Group();
     Scene theWindow = new Scene(root);
     Window.setScene(theWindow);
     Window.setResizable(false);

     Canvas canvas = new Canvas(width, height);
     root.getChildren().add(canvas);

     GraphicsContext screen = canvas.getGraphicsContext2D();

     //array for keyboard input
     ArrayList<String> input = new ArrayList<String>();


     //keyboard input
	    theWindow.setOnKeyPressed(new EventHandler<KeyEvent>() {
         public void handle(KeyEvent e) {
             String code = e.getCode().toString();

                 // only add once... prevents duplicates
             if ( !input.contains(code) ){
                 input.add(code);
             		button = code;
								lastKeyPressed = code;
             }
         }});

	    theWindow.setOnKeyReleased(new EventHandler<KeyEvent>() {
         public void handle(KeyEvent e) {
             String code = e.getCode().toString();
             input.remove( code );
             button = "";
             if (code.equals("ESCAPE")){
             	pressed = "";

             }
     }});

       // main game loop
   new AnimationTimer(){
   	public void handle(long NanoSeconds){
   		//double seconds = NanoSeconds / 1000000000;
   		//System.out.println(time);

   		//making the player move while within the window
   		//left movement and collision detection
   		if ((input.contains("LEFT") || input.contains("A")) && !(player.waiting())) {
   			double moveX = Math.min(player.Left(), player.MoveSpeed());
   			player.Move(-moveX, 0);
   			player.animate("player_left_");
   		}
   		//right movement and collision detection
   		if ((input.contains("RIGHT") || input.contains("D")) && !(player.waiting())) {
   			double moveX = Math.min(maxX+halfX - player.Right(), player.MoveSpeed());
   			player.Move(moveX, 0);
   			player.animate("player_right_");
   		}
   		//up movement and collision detection
   		if ((input.contains("UP") || input.contains("W")) && !(player.waiting())) {
   			double moveY = Math.min(player.Top(), player.MoveSpeed());
   			player.Move(0, -moveY);
        player.animate("player_up_");
   		}
   		//down movement and collision detection
   		if ((input.contains("DOWN") || input.contains("S")) && !(player.waiting())) {
   			double moveY = Math.min(maxY+halfY- player.Bottom(), player.MoveSpeed());
   			player.Move(0, moveY);
        player.animate("player_down_");
   			//System.out.println("DOWN");
   		}

      if (input.size() == 0 && lastKeyPressed != null){
				if (lastKeyPressed.equals("A") || lastKeyPressed.equals("LEFT")){
					player.Sprite("player_left_0.jpg");
				} else if (lastKeyPressed.equals("D") || lastKeyPressed.equals("RIGHT")){
					player.Sprite("player_right_0.jpg");
				} else if (lastKeyPressed.equals("W") || lastKeyPressed.equals("UP")){
					player.Sprite("player_up_0.jpg");
				} else if (lastKeyPressed.equals("S") || lastKeyPressed.equals("DOWN")){
					player.Sprite("player_down_0.jpg");
				}

      }

   		//other buttons

   		//redrawing the screen after player moves
   		//screen.clearRect(0, 0, width, height);
   		//screen.drawImage(Background, 0, 0);


      screen.clearRect(0, 0, width, height);

			/*

			for (int i = 0; i < 3; i ++){
				for (int j = 0; j < 6; j++){
					screen.drawImage(grass, j*128, i*128);
				}
			}
      screen.drawImage(player.sprite(), player.Left(), player.Top());
			*/
			loopNum = 0;
      for(Objects O: Objects.objectlist){

				if ((player.Left() >= halfX && player.Left() <= maxX) || (player.Top() >= halfY && player.Top() <= maxY)){

					if (player.Left() >= halfX && player.Left() <= maxX && player.Top() >= halfY && player.Top() <= maxY){
						if (loopNum == 0){
							for (int i = 0; i < 20; i ++){
								for (int j = 0; j < 20; j++){
									screen.drawImage(grass, j*256  + halfX - player.Left(), i*256 + halfY - player.Top());
								}
							}
						}

						screen.drawImage(O.sprite(), O.Left() + halfX - player.Left(), O.Top() + halfY - player.Top());

					} else if (player.Left() >= halfX && player.Left() <= maxX){
						if (player.Top() >= maxY){
							if (loopNum == 0){
								for (int i = 0; i < 20; i ++){
									for (int j = 0; j < 20; j++){
										screen.drawImage(grass, j*256  + halfX - player.Left(), i*256 + halfY - maxY);
									}
								}
							}

							screen.drawImage(O.sprite(), O.Left() + halfX - player.Left(), O.Top() + halfY - maxY);
						}else{
							if (loopNum == 0){
								for (int i = 0; i < 20; i ++){
									for (int j = 0; j < 20; j++){
										screen.drawImage(grass, j*256  + halfX - player.Left(), i*256);
									}
								}
							}

							screen.drawImage(O.sprite(), O.Left() + halfX - player.Left(), O.Top());
						}

					}else if (player.Top() >= halfY && player.Top() <= maxY){
						if (player.Left() >= maxX){
							if (loopNum == 0){
								for (int i = 0; i < 20; i ++){
									for (int j = 0; j < 20; j++){
										screen.drawImage(grass, j*256 + halfX - maxX, i*256 + halfY - player.Top());
									}
								}
							}

							screen.drawImage(O.sprite(), O.Left() + halfX - maxX, O.Top() + halfY - player.Top());
						}else{
							if (loopNum == 0){
								for (int i = 0; i < 20; i ++){
									for (int j = 0; j < 20; j++){
										screen.drawImage(grass, j*256, i*256 + halfY - player.Top());
									}
								}
							}

							screen.drawImage(O.sprite(), O.Left(), O.Top() + halfY - player.Top());
						}
					}
				}else if ((player.Left() >= maxX) || (player.Top() >= maxY)){

					if (player.Left() >= maxX && player.Top() >= maxY){
						if (loopNum == 0){
							for (int i = 0; i < 20; i ++){
								for (int j = 0; j < 20; j++){
									screen.drawImage(grass, j*256 + halfX - maxX, i*256 + halfY - maxY);
								}
							}
						}
						screen.drawImage(O.sprite(), O.Left() + halfX - maxX, O.Top() + halfY - maxY);

					} else if (player.Left() >= maxX){

						if (loopNum == 0){
							for (int i = 0; i < 20; i ++){
								for (int j = 0; j < 20; j++){
									screen.drawImage(grass, j*256 + halfX - maxX, i*256);
								}
							}
						}
						screen.drawImage(O.sprite(), O.Left() + halfX - maxX, O.Top());

					} else if (player.Top() >= maxY){
						if (loopNum == 0){
							for (int i = 0; i < 20; i ++){
								for (int j = 0; j < 20; j++){
									screen.drawImage(grass, j*256, i*256 + halfY - maxY);
								}
							}
						}
						screen.drawImage(O.sprite(), O.Left(), O.Top() + halfY - maxY);

					}

				}else {
					if (loopNum == 0){
						for (int i = 0; i < 20; i ++){
							for (int j = 0; j < 20; j++){
								screen.drawImage(grass, j*256, i*256);
							}
						}
					}
					screen.drawImage(O.sprite(),O.Left(), O.Top());

				}

				loopNum ++;
			}

   		try {
   			double time = NanoSeconds / 1000000;
   		    double timetaken =  time - prevTime;
   		    prevTime = time;
   		    sleepTime = prefferedTime - (int)timetaken;
   		    pressTimer -= 0.1;

   		    if (!((int)timetaken > prefferedTime)){
   		    	Thread.sleep(sleepTime);
   		    	//System.out.print("true");
   		    }

   		} catch(InterruptedException ex) {
   			System.out.println("InterruptedException");
   		}


   		if (player.CurrentHP() <= 0){
   			restart(player);
   		}

   	}
   }.start();

   Window.show();
}

public static void run(String[] args){
	launch(args);
}

public static void main(String[] args) {

     launch(args);

     System.out.print(Objects.objectlist.size());


	}
}
