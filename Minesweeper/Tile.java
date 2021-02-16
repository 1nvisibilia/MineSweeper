// Tile class
import javax.swing.*;

abstract class Tile
{
   // Fields
   // the boolean variable discovered indicates if a tile is flipped or not.
   protected boolean discovered;
   protected final char COVERED = '?';
   protected final char MINE = 'X';
   protected JButton tileButton;
   
   // Constructor
   public Tile (boolean discovered)
   {
      this.discovered = discovered;
   }
   
   // Accessors and mutators
   public void setDiscovered (boolean discovered)
   {
      if (discovered && !this.discovered)
      {
         if (this instanceof Mine)
         {
            tileButton.setIcon(new ImageIcon("Graphics/hiddenMine.jpg"));
         }
         else
         {
            tileButton.setIcon(new ImageIcon("Graphics/" + ((SafeTile)this).getMinesAround() + ".jpg"));
         }
      }
      this.discovered = discovered;
   }
   
   public void setTileButton (JButton tileButton)
   {
      this.tileButton = tileButton;
   }
   
   public boolean getDiscovered ()
   {
      return discovered;
   }
   
   public JButton getTileButton ()
   {
      return tileButton;
   }
}