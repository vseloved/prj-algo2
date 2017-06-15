import course.advalgos.ShortestPath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Aleksandr Dubinsky
 */
public class _03_1 {
    
      @Test public void
    graph1() {
        
            List<List<Integer>> graph = new ArrayList<> ();
            graph.add (Arrays.asList (1, 2, 3)); // 0
            graph.add (Arrays.asList ()); // 1
            graph.add (Arrays.asList (3)); // 2
            graph.add (Arrays.asList ()); // 3
            
            int shortestPath = ShortestPath.shortestPath (graph, 0, 3);
            
            assertEquals (1, shortestPath);
        }
    
}
