// Mine Class

public class Mine extends Tile
{
   // Field
   public static final boolean DISCOVERED = false;
   
   // Constructor
   public Mine ()
   {
      super(DISCOVERED);
   }
   
   // This method checks if a mine is discovered or not, then return a boolean variable regarding the result.
   public boolean mineDiscovered ()
   {
      if (discovered)
      {
         return true;
      }
      else
      {
         return false;
      }
   }
   
   // toString method (only for the non-graphic gameboard)
   public String toString ()
   {
      if (discovered)
      {
         return MINE + "";
      }
      else
      {
         return COVERED + "";
      }
   }
}