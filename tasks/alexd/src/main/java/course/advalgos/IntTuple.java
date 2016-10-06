package course.advalgos;

/**
 *
 * @author Alex
 */
public class IntTuple
{   
      public
    IntTuple(int a, int b) {
        
            this.a = a;
            this.b = b;
        }
    
//      public static IntTuple 
//    of(int a, int b) {
//        
//            return new IntTuple(a,b);
//        }
    
    public final int a;
    public final int b;
    
    @Override public boolean equals(Object other) { return (other instanceof IntTuple) && (a == ((IntTuple)other).a) && (b == ((IntTuple)other).b); }
    @Override public int hashCode() { return (((a<<16) + b) ^ 0x87d6cab9) * 31; }
    @Override public String toString() { return a + ", " + b; }
}
