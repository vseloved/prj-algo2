package course.advalgos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author alex
 */
public class ShortestPath {
    
      public static int
    shortestPath (List<List<Integer>> connections, int start, int end) {
        
            if (start == end)
                return 0;
        
            Set<Integer> visitedList = new HashSet<>();
            List<Integer> activeList = new ArrayList<>();
            List<Integer> newActiveList = new ArrayList<>();
            
            visitedList.add (start);
            activeList.add (start);
            
            int hopCount = 1;
            
            while (! activeList.isEmpty ())
            {   
                for (int vertex : activeList)
                {
                    List<Integer> children = connections.get (vertex);
                    
                    for (int child : children)
                    {
                        if (child == end)
                            return hopCount;
                        
                        if (visitedList.contains (child))
                            continue;
                        
                        newActiveList.add (child);
                    }
                }
                
                activeList = newActiveList;
                newActiveList = new ArrayList<>();
                hopCount ++;
            }
            
            return -1;
        }
}
