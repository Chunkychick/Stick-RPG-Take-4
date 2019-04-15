import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;

public class Objects extends Animation{
	public static ArrayList<Objects> objectlist = new ArrayList<Objects>();
	private double x;
	private double y;
	private double MoveSpeed;
	private double staticMoveSpeed;
	private Image face;
	private int sizeX;
	private int sizeY;
	private boolean Touching = false;
	private String name = "";
	private int HP = 1;
	private int hpLimit = 4;
	private int damageDelay = 20;
	private int damageDelayCounter = 0;
	private String description;

	//right end of object
	public double Right(){
		double farx = x + sizeX - 1;
		return (farx);
	}
	//bottom end of object
	public double Bottom(){
		double fary = y + sizeY - 1;
		return (fary);
	}
	//current x of object
	public double Left(){
		return (x);
	}
	//current y of object
	public double Top(){
		return (y);
	}
	public double CenterX(){
		return(x+sizeX/2);
	}
	public double CenterY(){
		return(y+sizeY/2);
	}
	//changes the y of object
	public void NewY(double y){
		this.y = y;
	}
	//changes the x of object
	public void NewX(double x){
		this.x = x;
	}
	//returns the image of the object
	public Image sprite(){
		return (face);
	}

	public double MoveSpeed(){
		return (MoveSpeed);
	}
	public void SetMoveSpeed(double speed){
		this.MoveSpeed = speed;
		if (speed > this.staticMoveSpeed)
			this.staticMoveSpeed = speed;
	}
	public void OldMoveSpeed(){
		this.MoveSpeed = staticMoveSpeed;
	}

	public double StaticMoveSpeed(){
		return(this.staticMoveSpeed);
	}

	public void Sprite(String picture){
		this.face = new Image("file:res/" + picture);
	}
	public boolean Touching(){
		return (Touching);
	}
	public void isTouching(boolean touch){
		this.Touching = touch;
	}

	public String Name(){
		return (name);
  }

	public int CurrentHP(){
		return(HP);
	}

	public void SetHp(int newhp){
		if (newhp < hpLimit)
			this.HP = newhp;
		else
			this.HP = hpLimit;
	}

	public void SetMaxHP(int hp){
		this.hpLimit = hp;
	}

	public int GetMaxHP(){
		return(this.hpLimit);
	}

	public String Description(){
		return(this.description);
	}


	//creates the object
	//square this objects

	public Objects(double startx, double starty, double speed, String image, int xsize, int ysize, String name, double life){
		this.face = new Image("file:res/" + image);
		this.x = startx;
		this.y = starty;
		this.MoveSpeed = speed;
		this.staticMoveSpeed = speed;
		this.sizeX = xsize;
		this.sizeY = ysize;
		this.name = name;
		this.HP = (int)life;
		this.hpLimit = (int)life;
		objectlist.add(this);
	}

	//square objects

	//health bar

	public Objects(String image){
		this.face = new Image("file:res/" + image);
		this.MoveSpeed = 6;
	}

	public Objects(String image, int xsize, int ysize, String description){
		this.face = new Image("file:res/" + image);
		this.description = description;
		this.sizeX = xsize;
		this.sizeY = ysize;
	}


	public double[] Move(double moveX, double moveY) {
		double[] movement = new double[2];
		// MAKE SURE ONLY X OR Y IS INPUTED. NOT BOTH AT ONCE
		//   ^ need to fix this eventually
		//System.out.println(moveX + " " + moveY);
		for (Objects item : objectlist) {
			if (item == this) // don't compare against self
				continue;

			if (this.Right() + moveX < item.Left()){
				continue;
			}
			if (this.Left() + moveX > item.Right()){
				continue;
			}
			if (this.Bottom() + moveY < item.Top()){
				continue;
			}
			if (this.Top() + moveY > item.Bottom()){
				continue;
			}
			// calculate amount that can be moved, return to caller
			// also calculate if touching other objects

			if (moveX > 0){
				moveX = Math.min(moveX, item.Left() - this.Right()-1);
				if (moveX == 0){}

			}
			else if (moveX < 0){
				moveX = -Math.min(-moveX, this.Left() - item.Right()-1);
				if (moveX == 0){}

			}

			if (moveY > 0) {
				moveY = Math.min(moveY, item.Top() - this.Bottom()-1);
				if (moveY == 0){}

			}
			else if (moveY < 0){
				moveY = -Math.min(-moveY, this.Top() - item.Bottom()-1);
				if (moveY == 0){}

			}
		}

		//System.out.println("this " + moveX + ", " + moveY);
		//System.out.println(moveY);
		this.x += moveX;
		this.y += moveY;
		movement[0] = moveX;
		movement[1] = moveY;
		return (movement);

	}

}
