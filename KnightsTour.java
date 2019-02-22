import java.util.*;
public class KnightsTour{

    private KnightSquare[][] board;
    private int knight_counter = 0;

  public static boolean attacks(int row1, int col1, int row2, int col2){
    return (1==(col1 - col2) && 2 == Math.abs(row1 - row2)) || (1==(row1 - row2) && 2 == Math.abs(col1 - col2));
  }
  public KnightsTour(int rows, int cols){
    board = new KnightSquare[rows][cols];
    for(int i = 0; i < rows; i ++){
      for(int j = 0; j < cols; j ++){
        board[i][j]=new KnightSquare(i,j);
        if(Math.abs(rows - i)==2 || i==1){
          board[i][j].setSpacesLeft(6);
        }
        if(Math.abs(cols - j)==2 || j==1){
          board[i][j].setSpacesLeft(6);
        }
        if(Math.abs(rows - i)==1 || i==0){
          board[i][j].setSpacesLeft(4);
          if(Math.abs(cols - j)==1 || j==0){
            board[i][j].setSpacesLeft(2);
          }
          if(Math.abs(cols - j)==2 || j==1){
            board[i][j].setSpacesLeft(3);
          }

        }
        if(Math.abs(cols - j)==1 || j==0){
          board[i][j].setSpacesLeft(4);
          if(Math.abs(rows - i)==1 || i==0){
            board[i][j].setSpacesLeft(2);
          }
          if(Math.abs(rows - i)==2 || i==1){
            board[i][j].setSpacesLeft(3);
          }

        }
      }
    }
  }
  private boolean addKnight(int row, int col){
    if (row<0 || row>=board.length || col <0 || col>=board[0].length) {
      return false;
    }
    if(board[row][col].order==0){
      knight_counter++;
      board[row][col].setorder(1*knight_counter);
      for(int i = 0; i < row + 4; i ++){
        for(int j = 0; j < board[0].length; j ++){
          if(attacks(i,j,row,col)&& !(i<0 || i>=board.length || j <0 || j>=board[0].length)){
            if(board[i][j].order==0){
              board[i][j].setSpacesLeft(board[i][j].spaces_left-1);
            }
          }
        }
      }
      return true;
    }
    else{
      return false;
    }
  }
  private boolean removeKnight(int row, int col){
    if(board[row][col].order>0){
      knight_counter--;
      board[row][col].order = 0;
      for(int i = 0; i < row + 4; i ++){
        for(int j = 0; j < board[0].length; j ++){
          if(attacks(i,j,row,col)&& !(i<0 || i>=board.length || j <0 || j>=board[0].length)){
            if(board[i][j].spaces_left>0){
              board[i][j].setSpacesLeft(board[i][j].spaces_left+1);
            }
          }
        }
      }
      return true;
    }
    else{
      return false;
    }
  }
  public String toString(){
    String to_return = "";
    for(int i = 0; i < board.length; i ++){
      for(int j = 0; j < board[0].length; j ++){
          to_return += board[i][j].order;
          if(board[i][j].order<10){
            to_return += " ";
          }
          to_return += "|";
      }
      to_return = to_return+"\n";
    }
    return to_return;
  }

public boolean compare(int[] opt1, int[] opt2, int lastkx, int lastky){
  if(lastkx+opt1[0]>board.length-1 || lastkx+opt1[0]<0 || lastky+opt1[1]>board[0].length-1 || lastky+opt1[1]<0){
    return false;
  }
  if(lastkx+opt2[0]>board.length-1 || lastkx+opt2[0]<0 || lastky+opt2[1]>board[0].length-1 || lastky+opt2[1]<0){
    return true;
  }
  if(board[lastkx+opt1[0]][lastky+opt1[1]].order>0){
    return false;
  }
  if(board[lastkx+opt2[0]][lastky+opt2[1]].order>0){
    return true;
  }
  return (board[lastkx+opt1[0]][lastky+opt1[1]].spaces_left<board[lastkx+opt2[0]][lastky+opt2[1]].spaces_left);
}

public int[][] sort(int[][] options, int lastkx,int lastky){
  for(int i=1;i<options.length;i++){
    for(int j=0;j<i;j++){
      if(compare(options[i],options[j],lastkx,lastky)){
        int[] temp=options[i];
        options[i]=options[j];
        options[j]=temp;
      }
    }
  }
  return options;
}
public boolean solve(int lastkx, int lastky){
  //System.out.println(toString());
  if(knight_counter == board.length * board[0].length){
    return true;
  }

  else{
    int[][] options = {{2,1},{2,-1},{-2,-1},{-2,1},{1,2},{-1,2},{1,-2},{-1,-2}};
    options=sort(options,lastkx,lastky);
    for(int i=0;i<options.length;i++){
      if(addKnight(lastkx + options[i][0], lastky + options[i][1])){
        if(solve(lastkx + options[i][0], lastky + options[i][1])){
          return true;
        }
        removeKnight(lastkx + options[i][0], lastky + options[i][1]);
      }
    }
    return false;
    }
}


  //private boolean backtrack(){
    //   int last_q_index = -1;
    //   if(queen_counter !=0){
    //     for(int i = 0; i < board.length; i ++){
    //
    //       if(board[queen_counter - 1][i] == -1){
    //         last_q_index = i;
    //       }
    //
    //     }
    //     removeQueen(queen_counter-1,last_q_index);
    //   }
    //   else{
    //     return false;
    //   }
    //   boolean didnt_do_stuff = true;
    //
    //   for(int i = last_q_index + 1; i < board.length; i ++){
    //     if(board[queen_counter][i]==0){
    //       addQueen(queen_counter,i);
    //       didnt_do_stuff = false;
    //       return true;
    //     }
    //   }
    //   if(didnt_do_stuff){
    //     return backtrack();
    //   }
    //   if(last_q_index == -1){
    //     return false;
    //   }
    //   else{
    //     return false;
    //   }  }
  //
  // public int countSolutions(){
  //   int counter =0;
  //   if(solve()){
  //     counter++;
  //   };
  //   while(backtrack()){
  //     if(solve()){
  //       counter++;
  //       //System.out.println(counter);
  //     }
  //   }
  //
  //   return counter;
  // }

 public static void main(String args[]){
    KnightsTour nb = new KnightsTour(6,5);
    // note that the way I wrote solve, you must add the start knight and then run solve
    nb.addKnight(0,0);
    nb.solve(0,0);
    System.out.println(nb.toString());

  }
}
