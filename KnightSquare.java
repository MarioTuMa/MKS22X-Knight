public class KnightSquare{
  public int spaces_left = 0;
  public int order = 0;
  public int row = 0;
  public int col = 0;
  public KnightSquare(int rows, int cols){
        row=rows;
        col=cols;
  }
  public boolean setSpacesLeft(int n){
    spaces_left=n;
    return true;
  }
  public boolean setorder(int n){
    order=n;
    return true;
  }

}
