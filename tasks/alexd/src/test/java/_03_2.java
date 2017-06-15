import course.advalgos.ChangeMachine;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alex
 */
public class _03_2 {
    
      @Test public void
    case0() {
        
            int[] change = ChangeMachine.giveChange (0, new int[] { 1, 3, 7, 13, 29, 50 });
            
            assertArrayEquals ( new int[] { 0, 0, 0, 0, 0, 0 }, change);
        }
    
      @Test public void
    case1() {
        
            int[] change = ChangeMachine.giveChange (1, new int[] { 1, 3, 7, 13, 29, 50 });
            
            assertArrayEquals ( new int[] { 1, 0, 0, 0, 0, 0 }, change);
        }
    
      @Test public void
    case2() {
        
            int[] change = ChangeMachine.giveChange (2, new int[] { 1, 3, 7, 13, 29, 50 });
            
            assertArrayEquals ( new int[] { 2, 0, 0, 0, 0, 0 }, change);
        }
    
      @Test public void
    case9() {
        
            int[] change = ChangeMachine.giveChange (9, new int[] { 1, 3, 7, 13, 29, 50 });
            
            assertArrayEquals ( new int[] { 0, 3, 0, 0, 0, 0 }, change);
        }
    
      @Test public void
    case10() {
        
            int[] change = ChangeMachine.giveChange (10, new int[] { 1, 3, 7, 13, 29, 50 });
            
            assertArrayEquals ( new int[] { 0, 1, 1, 0, 0, 0 }, change);
        }
    
      @Test public void
    case58() {
        
            int[] change = ChangeMachine.giveChange (58, new int[] { 1, 3, 7, 13, 29, 50 });
            
            assertArrayEquals ( new int[] { 0, 0, 0, 0, 2, 0 }, change);
        }
    
      @Test public void
    case60() {
        
            int[] change = ChangeMachine.giveChange (60, new int[] { 1, 3, 7, 13, 29, 50 });
            
            assertArrayEquals ( new int[] { 0, 1, 1, 0, 0, 1 }, change);
        }
}
