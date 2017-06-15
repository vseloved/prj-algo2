package course.advalgos;

/**
 *
 * @author Aleksandr Dubinsky
 */
public class ChangeMachine {
    
    private static enum Pointer { LEFT, UP };
    
      public static int[]
    giveChange (int amount, int[] denominations) {
        
            assert denominations[0] == 1;
            
            if (amount == 0)
                return new int [denominations.length];
        
            Pointer[][] pointers = new Pointer [amount+1][denominations.length];
            int[][] counts = new int[amount+1][denominations.length];
            
            // counts[0] = {0} implicitly
            
            counts[1][0] = 1;
            pointers[1][0] = Pointer.UP;
            
            for (int i = 2; i <= amount; i++)
            {
                for (int j = 0; j < denominations.length; j++)
                {
                    int denomination = denominations [j];
                    
                    if (i - denomination < 0)
                    {
                        assert j != 0;
                        
                        pointers[i][j] = Pointer.LEFT;
                        counts[i][j] = counts[i][j-1];
                    }
                    else if (j != 0 && counts[i][j-1] <= counts[i-denomination][j] + 1)
                    {
                        pointers[i][j] = Pointer.LEFT;
                        counts[i][j] = counts[i][j-1];
                    }
                    else
                    {
                        pointers[i][j] = Pointer.UP;
                        counts[i][j] = counts[i-denomination][j] + 1;
                    }
                }
            }
            
            return walk (amount, pointers, denominations);
        }
    
      private static int[]
    walk (int totalAmount, Pointer[][] pointers, int[] denominations) {
        
            int amount = totalAmount;
            int denominationIndex = denominations.length - 1;
            
            int[] denominationCounts = new int [denominations.length];

            while (amount > 0)
            {
                Pointer pointer = pointers [amount][denominationIndex];
                
                if (pointer == Pointer.UP)
                {
                    denominationCounts[denominationIndex] ++;
                    amount -= denominations [denominationIndex];
                }
                else
                {
                    denominationIndex --;
                }
            }
            
            return denominationCounts;
        }
            
}
