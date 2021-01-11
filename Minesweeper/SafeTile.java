// SafeTile class

public class SafeTile extends Tile
{
   // Field
   protected int minesAround;
   
   // Constructor
   public SafeTile (boolean discovered, int minesAround)
   {
      super (discovered);
      this.minesAround = minesAround;
   }
   
   // Mutator and accessor
   public void setMinesAround (int mines)
   {
      minesAround = mines;
   }
   
   public int getMinesAround ()
   {
      return minesAround;
   }
   
   // toString method (only for the non-graphic gameboard)
   public String toString ()
   {
      if (discovered)
      {
         return minesAround + "";
      }
      else
      {
         return COVERED + "";
      }
   }
}