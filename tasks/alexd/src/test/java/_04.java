
import course.advalgos.Needleman;
import org.junit.Test;

/**
 *
 * @author Aleksandr Dubinsky
 */
public class _04 {
    
      @Test public void
    longestCommonSubsequence() {
        
            int[] wordA = toIntArray ("world");
            int[] wordB = toIntArray ("fyord");
            
            String result 
                    = Needleman
                            .withNoStartEndGapPenalties()
                            .align (wordA, wordB)
                            .print (new String[] { "w", "o", "r", "l", "d" }, new String[] { "f", "y", "o", "r", "d" });
            
            System.out.println (result);
        }
    
      @Test public void
    alignment() {
        
            int[] wordA = toIntArray ("world");
            int[] wordB = toIntArray ("fyord");
            
            String result 
                    = Needleman
                            .align (wordA, wordB)
                            .print (new String[] { "w", "o", "r", "l", "d" }, new String[] { "f", "y", "o", "r", "d" });
            
            System.out.println (result);
        }
    
    
    
      private static int[]
    toIntArray(String string) {
        
            return toIntArray (string.toCharArray ());
        }
    
      private static int[]
    toIntArray(char[] chars) {
        
            int[] ints = new int [chars.length];
            for(int i = 0; i < chars.length; i++)
                ints[i] = (int) chars[i];
            return ints;
        }
}
