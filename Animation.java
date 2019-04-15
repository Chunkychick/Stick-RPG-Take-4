public abstract class Animation{

  private int frame = 0;
  private int numOfFrames = 4;
  private int lastFrame = numOfFrames-1;

  private int animationDelay = 2;
  private int animationCount = 0;

  public abstract void Sprite(String picture);

  public int getFrame(){
    return this.frame;
  }

  public void setFrame(int newFrame){
    this.frame = newFrame;
  }

  public void nextFrame(){
    if (this.frame == lastFrame){
      this.frame = 0;
    } else{
      this.frame ++;
    }
  }

  public boolean waiting(){
    if (this.animationCount < this.animationDelay){
      this.animationCount ++;
      return true;
    } else{
      this.animationCount = 0;
      return false;
    }
  }

  public void animate(String fileName){
    this.nextFrame();

    this.Sprite(fileName + Integer.toString(this.frame) + ".jpg");

  }

}
