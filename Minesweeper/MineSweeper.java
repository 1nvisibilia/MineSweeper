// MineSweeper class

import java.io.*; 
import javax.swing.*;

public class MineSweeper
{
   // Object fields and class fields
   // The digital gameboard
   private Tile[][] gameboard;
   // Size of the gameboard
   private int length;
   private int width;
   // The total number of mines and safe tiles in a gameboard.
   private int minesLeft;
   private int safeTilesLeft;
   // Char representation of a mine (only for non-graphic gameboard)
   private final char MINE = 'X';
   // Integer representation of winning
   private final int WINNING = 0;
   // Constants for game difficulties and size and number of mines for each difficulty.
   private static final int EASY = 1, MEDIUM = 2, HARD = 3;
   private static final int EASY_LENGTH = 10, MEDIUM_LENGTH = 16, HARD_LENGTH = 20;
   private static final int EASY_WIDTH = 12, MEDIUM_WIDTH = 20, HARD_WIDTH = 24;
   private static final int EASY_MINES = 10, MEDIUM_MINES = 40, HARD_MINES = 80;
   
   // Constructing a game by difficulty.
   public MineSweeper (int difficulty)
   {
      if (difficulty == EASY)
      {
         constructGameboard(EASY_LENGTH, EASY_WIDTH, EASY_MINES);
      }
      else if (difficulty == MEDIUM)
      {
         constructGameboard(MEDIUM_LENGTH, MEDIUM_WIDTH, MEDIUM_MINES);
      }
      else if (difficulty == HARD)
      {
         constructGameboard(HARD_LENGTH, HARD_WIDTH, HARD_MINES);
      }
      else
      {
         System.out.println("Invalid Parameter Input.");
      }
   }
    
    // Construct a game by a custom text file with the correct format.
   public MineSweeper (String textFile)
   {
      try
      {
         minesLeft = 0;
         safeTilesLeft = 0;
         char currentTile;
         BufferedReader in = new BufferedReader (new FileReader (textFile));
         length = Integer.parseInt(in.readLine());
         width = Integer.parseInt(in.readLine());
         gameboard = new Tile[length][width];
         
         for (int i = 0; i < length; i ++)
         {
            for (int j = 0; j < width; j ++)
            {
               currentTile = (char) in.read();
               if (currentTile == MINE)
               {
                  gameboard[i][j] = new Mine();
                  minesLeft ++;
               }
               else
               {
                  gameboard[i][j] = new SafeTile(false, currentTile - '0');
                  safeTilesLeft ++;
               }
            }
            in.readLine();
         }
         in.close();
      }
      catch (IOException iox)
      {
         System.out.println("Error reading textfile.");
      }
   }
   
   // Mutators and Accessors
   public int getLength()
   {
      return length;
   }
   
   public int getWidth()
   {
      return width;
   }
   
   public int getSafeTilesLeft()
   {
      return safeTilesLeft;
   }
   
   public int getMinesLeft()
   {
      return minesLeft;
   }
   
   public void setGameboard (Tile[][] getGameboard)
   {
      this.gameboard = gameboard;
   }
   
   public Tile[][] getGameboard ()
   {
      return gameboard;
   }
   
   // Construct a default difficulty game.
   private void constructGameboard (int length, int width, int numMines)
   {
      // Assign values to basic object fields.
      int counter = 0;
      int row, column;
      gameboard = new Tile[length][width];
      this.length = length;
      this.width = width;
      minesLeft = numMines;
      safeTilesLeft = length * width - numMines;
      // Place a specific number of mines in the game board randomly.
      while (counter < numMines)
      {
         row = (int)(Math.random() * length);
         column = (int)(Math.random() * width);
         if (gameboard[row][column] == null)
         {
            gameboard[row][column] = new Mine();
            counter ++;
         }
      }
      
      // Assign values to 'minesAround' field for all the non-mine tiles.
      for (int i = 0; i < length;  i ++)
      {
         for (int j = 0; j < width; j ++)
         {
            if (gameboard[i][j] == null)
            {
               gameboard[i][j] = new SafeTile (false, 0);
               for (int k = -1; k < 2; k ++)
               {
                  for (int l = -1; l < 2; l ++)
                  {
                     if ((k != 0 || l != 0) && i + k >= 0 && i + k < length && j + l >= 0 && j + l < width && gameboard[i + k][j + l] instanceof Mine)
                     {
                        ((SafeTile)gameboard[i][j]).setMinesAround(((SafeTile)gameboard[i][j]).getMinesAround() + 1);
                     }
                  }
               }
            }
         }
      }
   }
   
   // Uncover a selected tile
   public boolean uncoverTile (int row, int column)
   {
      // Check the tile is valid in the array; the game, otherwise, outputs error message.
      try
      {
         // If tile is already discovered, return false(game continue) and the user will re-enter the location.
         if (gameboard[row][column].getDiscovered())
         {
            System.out.println("The tile [" + row + "][" + column + "] is already uncoverd. Please choose another tile.");
            return false;
         }
         else
         {
            // If the tile is a mine, return true(game end) and show the uncovered gameboard.
            if (gameboard[row][column] instanceof Mine)
            {
               gameboard[row][column].setDiscovered(true);
               gameboard[row][column].getTileButton().setIcon(new ImageIcon ("Graphics/exposedMine.jpg"));
               loseGame(row, column);
               return true;
            }
            else
            {
               gameboard[row][column].setDiscovered(true);
               safeTilesLeft --;
               MineSweeperGUI.reduceTile("" + safeTilesLeft);
               // If all the non-mine tiles are discovered, return true(game end condition) and output winning message.
               if (safeTilesLeft == WINNING)
               {
                  winGame();
                  return true;
               }
               // Starts the recursion method, which calls this method and the 'expandSafeTiles' methods recursively.
               else if (((SafeTile)gameboard[row][column]).getMinesAround() == 0)
               {
                  expandSafeTiles(row, column);
               }
               return false;
            }
         }
      }
      catch (ArrayIndexOutOfBoundsException ariobx)
      {
         System.out.println("Input is out of bound of the game board, please try again.");
         MineSweeperGUI.constructGameGUI(this);
         return false;
      }
   }
   
   // expand all the safe tiles
   public void expandSafeTiles (int row, int column)
   {
      // For each black tile(who has a value of 0 for mines around), actions are performed on the adjacent 8 tiles.
      for (int i = -1; i < 2; i ++)
      {
         for (int j = -1; j < 2; j ++)
         {
            {
               if ((i != 0 || j != 0) && row + i >= 0 && row + i < length && column + j >= 0 && column + j < width)
               {
                  // Automatically flips all the unflipped tiles around the blank tile.
                  if (gameboard[row + i][column + j].getDiscovered() == false)
                  {
                     uncoverTile(row + i, column + j);
                  }
               }
            }
         }
      }
   }
   
   // Game ends (player loses)
   public void loseGame (int row, int column)
   {
      MineSweeperGUI.reduceTile("---");
      MineSweeperGUI.constructEndingWindow(false);
      System.out.println("You have uncovered the mine at [" + row + "][" + column + "]. You lost.");
      uncoverAllTiles();
      System.out.println(this);
   }
   
   // Game ends (player wins)
   public void winGame ()
   {
      uncoverAllTiles();
      MineSweeperGUI.constructEndingWindow(true);
      System.out.println("Congradulation! You clears all the tiles!");
      System.out.println(this);
   }
   
   // uncover all tiles 
   private void uncoverAllTiles()
   {
      for (int i = 0; i < length; i ++)
      {
         for (int j = 0; j < width; j ++)
         {
            gameboard[i][j].setDiscovered(true);
         }
      }
   }
   // toString method (only for non-graphic gameboard)
   public String toString ()
   {
      String writableBoard = "";
      for (int i = 0; i < length; i ++)
      {
         for (int j = 0; j < width; j ++)
         {
            writableBoard = writableBoard + gameboard[i][j] + " ";
         }
         writableBoard = writableBoard + "\n";
      }
      return writableBoard;
   }
}