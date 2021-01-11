import java.awt.*;  
import javax.swing.*;
import java.awt.event.*;

public class MineSweeperGUI extends JFrame
{
   // Class Fields
   private static JFrame welcomeWindow;
   private static JFrame selectModeWindow;
   private static JFrame mainWindow;
   private static JFrame customTextWindow;
   private static JFrame endingWindow;
   private static JLabel safeTilesLeft;
   
   // Class constants
   private static final int lengthErrorGUI = 37;
   private static final int widthErrorGUI = 14;
   private static final int TILE_SIZE = 24;
   private static final double BANNER_RATIO = 2.048;
   private static final int EASY_DIFFICULTY = 1;
   private static final int NORMAL_DIFFICULTY = 2;
   private static final int HARD_DIFFICULTY = 3;
   
   // This method contruct the welcome screen of the game.
   public static void constructWelcomeScreenGUI()
   {
      SwingUtilities.invokeLater(
         new Runnable()
         {
            public void run ()
            {   
               // Construct the background image JLabel.
               JLabel welcomeBgImg = new JLabel(new ImageIcon("Graphics/banner.png"));
               welcomeBgImg.setBounds(0, 0, 1024, 500);
               
               // Construct the playButton
               JButton playButton = new JButton("PLAY", new ImageIcon("Graphics/welcomeButton.png"));
               playButton.setHorizontalTextPosition(JButton.CENTER);
               playButton.setVerticalTextPosition(JButton.CENTER);
               playButton.setBounds(687, 86, 172, 86);
               
               // Construct the instructionButton
               JButton instructionButton = new JButton("INSTRUCTIONS", new ImageIcon("Graphics/welcomeButton.png"));
               instructionButton.setHorizontalTextPosition(JButton.CENTER);
               instructionButton.setVerticalTextPosition(JButton.CENTER);
               instructionButton.setBounds(687, 172, 172, 86);
            
               // Add the buttons and labels to a layeredPane.
               JLayeredPane welcomePane = new JLayeredPane();
               welcomePane.setBounds(0, 0, 1024, 500);
               welcomePane.add(welcomeBgImg, 1);
               welcomePane.add(playButton, 0);
               welcomePane.add(instructionButton, 0);
               
               // Add the layeredPane to JFrame after initializing the JFrame.
               welcomeWindow = new JFrame ("MineSweeper");
               welcomeWindow.add(welcomePane);
               welcomeWindow.setSize(1024 + widthErrorGUI, 500 + lengthErrorGUI);
               welcomeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               welcomeWindow.setLayout(null);
               welcomeWindow.setVisible(true);
               
               // Add listeners to the buttons.
               playButton.addActionListener(
                  new ActionListener ()
                  {
                     @Override
                     public void actionPerformed(ActionEvent e)
                     {
                        // Display the select difficulty window.
                        constructDifficultyWindow();
                     }
                  });
                  
               instructionButton.addActionListener(
                  new ActionListener ()
                  {
                     @Override
                     public void actionPerformed(ActionEvent e)
                     {
                        // Construct the instruction Image.
                        JLabel instructionPic = new JLabel(new ImageIcon("Graphics/instruction.png"));
                        instructionPic.setBounds(0, 0, 683, 352);
                        
                        // Construct the instruction window.
                        JFrame instructionWindow = new JFrame("Instruction");
                        instructionWindow.setVisible(true);
                        instructionWindow.setSize(683, 410);
                        instructionWindow.add(instructionPic);
                     }
                  });
            }
         });
   }
   
   // This methods display a window for user to enter the text file for a custom MineSweeper game.
   public static void displayCustomGameWindow()
   {
      // Construct JLabel: instruction for how to enter the txt file.
      JLabel customGameLabel = new JLabel("Enter the text file for custom game below and click \"Launch\"");
               
      // Construct JButton for submitting the txt file.
      JButton submitCustomGame = new JButton("Launch");
               
      // Construct TTextField for entering the txt file.
      JTextField inputbox = new JTextField("Enter the file name here");
               
      // Initialize the JFrame and add all components to it.
      customTextWindow = new JFrame("Custom Game");
      customTextWindow.setVisible(true);
      customTextWindow.setSize(360, 200);
      customTextWindow.add(customGameLabel, BorderLayout.NORTH);
      customTextWindow.add(submitCustomGame, BorderLayout.SOUTH);
      customTextWindow.add(inputbox, BorderLayout.CENTER);
               
      // Add listeners to the JButtons.
      submitCustomGame.addActionListener(
         new ActionListener ()
         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               // If the txt file is valid, construct the MineSweeper game. Otherwise, the window display an error message and
               // prompts the user the try again.
               String textFile = inputbox.getText();
               MineSweeper game = new MineSweeper(textFile);
               if (game.getGameboard() != null)
               {
                  constructGameGUI(game);
               }
               else
               {
                  customGameLabel.setText("File is not found. Check for spelling!");
                  customGameLabel.setForeground(Color.RED);
               }
            }
         });
   }
   
   // This method display the window for select gamemode(difficulty).
   public static void constructDifficultyWindow ()
   {
      // Initialize the select game mode window.
      selectModeWindow = new JFrame("Select Difficulty");
      selectModeWindow.setSize(172 + widthErrorGUI, 430 + lengthErrorGUI);
      selectModeWindow.setVisible(true);
      selectModeWindow.setLayout(null);
            
      // Initialize the JLabel.
      JLabel selectDifficulty = new JLabel(new ImageIcon("Graphics/selectDifficulty.png"));
      selectDifficulty.setBounds(0, 0, 172, 86);
            
      // Intialize all the JButton for different difficulties and their listeners.
      JButton easyMode = new JButton("Easy", new ImageIcon("Graphics/welcomeButton.png"));
      easyMode.setBounds(0, 86, 172, 86);
      easyMode.setHorizontalTextPosition(JButton.CENTER);
      easyMode.setVerticalTextPosition(JButton.CENTER);
      easyMode.addActionListener(
         new ActionListener ()
         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               // Construct a MineSweeper game with given difficulty.
               MineSweeper game = new MineSweeper(EASY_DIFFICULTY);
               constructGameGUI(game);
            }
         });
            
      JButton normalMode = new JButton("Normal", new ImageIcon("Graphics/welcomeButton.png"));
      normalMode.setBounds(0, 172, 172, 86);
      normalMode.setHorizontalTextPosition(JButton.CENTER);
      normalMode.setVerticalTextPosition(JButton.CENTER);
      normalMode.addActionListener(
         new ActionListener ()
         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               // Construct a MineSweeper game with given difficulty.
               MineSweeper game = new MineSweeper(NORMAL_DIFFICULTY);
               constructGameGUI(game);
            }
         });
            
      JButton hardMode = new JButton("Hard", new ImageIcon("Graphics/welcomeButton.png"));
      hardMode.setBounds(0, 258, 172, 86);
      hardMode.setHorizontalTextPosition(JButton.CENTER);
      hardMode.setVerticalTextPosition(JButton.CENTER);
      hardMode.addActionListener(
         new ActionListener ()
         {
            @Override
             public void actionPerformed(ActionEvent e)
            {
               // Construct a MineSweeper game with given difficulty.
               MineSweeper game = new MineSweeper(HARD_DIFFICULTY);
               constructGameGUI(game);
            }
         });
            
      JButton customMode = new JButton("Custom", new ImageIcon("Graphics/welcomeButton.png"));
      customMode.setBounds(0, 344, 172, 86);
      customMode.setHorizontalTextPosition(JButton.CENTER);
      customMode.setVerticalTextPosition(JButton.CENTER);
      customMode.addActionListener(
         new ActionListener ()
         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               displayCustomGameWindow();
            }
         });
               
      // Add all components to the JFrame selectModeWindow.
      selectModeWindow.add(selectDifficulty);
      selectModeWindow.add(easyMode);
      selectModeWindow.add(normalMode);
      selectModeWindow.add(hardMode);
      selectModeWindow.add(customMode);
   }
   
   // This method redirect calls the expandTiles method in MineSweeper whenever a tile (JButton) is pressed.
   public static void addButtonListener (MineSweeper game, JButton button, int row, int column)
   {
      button.addActionListener(
         new ActionListener ()
         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               System.out.println("The tile : [" + row + "][" + column + "] was clicked.");
               game.uncoverTile(row, column);
            }
         });
   }
   
   // This method updates the message on how many safe tiles are left.
   public static void reduceTile(String tilesLeft)
   {
      safeTilesLeft.setText("Safe Tile(s) Left: " + tilesLeft);
   }
   
   // This method constructc and displays the main gaming window for MineSweeper.
   public static void constructGameGUI (MineSweeper game)
   {
      int row = game.getLength();
      int column = game.getWidth();
      SwingUtilities.invokeLater(
         new Runnable()
         {
            public void run ()
            {
               // Close all unnecessary windows.
               welcomeWindow.setVisible(false);
               selectModeWindow.setVisible(false);
               if (customTextWindow != null)
               {
                  customTextWindow.setVisible(false);
               }
               
               // Initialize the JFrame.
               mainWindow = new JFrame("MineSweeper");
               mainWindow.setVisible(true);
               mainWindow.setBackground(Color.GRAY);
               mainWindow.setLayout(null);
               mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               
               // Initialize the JLayeredPane
               JLayeredPane gamingPane = new JLayeredPane();
               
               // Calculate the sizes of panels and frames.
               int pixelWidth = column * TILE_SIZE;
               int pixelLength = row * TILE_SIZE;
               int bannerHeight = (int) (pixelWidth / BANNER_RATIO);
               
               // Initialize the JPanel for buttons.
               JPanel buttonBoard = new JPanel(new GridLayout(row, column));
               buttonBoard.setBounds(0, bannerHeight, pixelWidth, pixelLength);
               buttonBoard.setBackground(Color.WHITE);
               gamingPane.add(buttonBoard, 1);
               
               // Construct a JPanel for the banner picture.
               JPanel banner = new JPanel();
               banner.setBounds(0, 0, pixelWidth, bannerHeight);
               banner.setBackground(Color.WHITE);
               gamingPane.add(banner, 1);
               
               // Add a JLabel for the banner.
               JLabel bannerPicture = new JLabel();
               ImageIcon originalImg = new ImageIcon("Graphics/banner.png");
               
               // Size the banner image for the JLabel.
               Image sizedImage = originalImg.getImage();
               sizedImage = sizedImage.getScaledInstance(pixelWidth, bannerHeight, java.awt.Image.SCALE_SMOOTH);
               originalImg = new ImageIcon(sizedImage);
              
               // Add the picture to JLabel
               bannerPicture.setIcon(originalImg);
               
               // Add the JLabel to JPanel.
               banner.add(bannerPicture);
               
               // Initialize the JButtons.
               JButton[][] buttons = new JButton[row][column];
               int xCord = 0;
               int yCord = 0;
               
               // Create a Label indicating the SafeTiles left.
               safeTilesLeft = new JLabel("Safe Tile(s) Left: " + game.getSafeTilesLeft());
               safeTilesLeft.setForeground(Color.BLACK);
               safeTilesLeft.setFont(safeTilesLeft.getFont().deriveFont(16.0f));
               safeTilesLeft.setBounds(6, 0, 200, 30);
               gamingPane.add(safeTilesLeft, 0);
               
               // Size the mainWindow JFrame and the gamingPane JLayeredPane.
               mainWindow.setSize(pixelWidth + widthErrorGUI, pixelLength + lengthErrorGUI + bannerHeight);
               gamingPane.setBounds(0, 0, pixelWidth + widthErrorGUI, pixelLength + lengthErrorGUI + bannerHeight);
               mainWindow.add(gamingPane);
               
               // Initializes and adds listeners for all JButtons.
               for (int i = 0; i < row; i ++)
               {
                  for (int j = 0; j < column; j ++)
                  {
                     buttons[i][j] = new JButton(new ImageIcon("Graphics/coveredTile.png"));
                     buttons[i][j].setBounds(xCord, yCord, TILE_SIZE, TILE_SIZE);
                     
                     addButtonListener(game, buttons[i][j], i, j);
                     
                     buttonBoard.add(buttons[i][j]);
                     game.getGameboard()[i][j].setTileButton(buttons[i][j]);
                     
                     yCord += TILE_SIZE;
                  }
                  yCord = 0;
                  xCord += TILE_SIZE;
               }
            }
         });
   }
   
   // This method displays the ending window after the game has ended.
   public static void constructEndingWindow (boolean endResult)
   {
      // Initialize the JFrame.
      endingWindow = new JFrame("Game Over!");
      endingWindow.setVisible(true);
      JLabel message;
      // Depends on the result of the game, modify the window and add JLabels with necessary texts.
      if (endResult)
      {
         message = new JLabel("Congratulation! All the mines are cleared. You won!");
         message.setForeground(Color.GREEN);
         endingWindow.setSize(500,130);
      }
      else
      {
         message = new JLabel("Oops! You just uncovered a mine. You lost.");
         message.setForeground(Color.RED);
         endingWindow.setSize(420,130);
      }
      message.setFont(message.getFont().deriveFont(19.0f));
      endingWindow.add(message, BorderLayout.CENTER);
   }
   
   public static void main(String[] args)
   {
      // Runs the MineSweeper game.
      constructWelcomeScreenGUI();
   }
}