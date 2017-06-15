/*
 * Copyright Almson Corp.
 */
package course.advalgos;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import course.advalgos.Needleman.NeedlemanResult.Pointer;
import static course.advalgos.Needleman.NeedlemanResult.Pointer.SKIP_B;
//import net.almson.function.IntBiConsumer;
//import net.almson.util.ArrayUtils2;
//import net.almson.util.IntTuple;
//import org.apache.commons.lang3.NotImplementedException;
import static org.apache.commons.lang3.StringUtils.repeat;

/**
 * see: http://www.avatar.se/molbioinfo2001/dynprog/adv_dynamic.html
 * 
 * @author Aleksandr Dubinsky
 */
public final class Needleman 
{
    
    private Needleman() { throw new UnsupportedOperationException(); }
    
    
      @FunctionalInterface public interface 
    MatchMismatchScorer
    {
        double score (int a, int b, int indexA, int indexB, int lengthA, int lengthB);
        
        default boolean isMatch (int a, int b) { return a == b; }
    }
    
      @FunctionalInterface public interface 
    GapScorer
    {
        enum Direction { SKIP_A, SKIP_B }
        
        double score (Direction direction, int indexA, int indexB, int lengthA, int lengthB);
    }
    
      
      public static NeedlemanResult
    align (int[] sequence1, int[] sequence2){ return new Needleman.NeedlemanBuilder().align(sequence1, sequence2); } 
    
      public static NeedlemanBuilder
    withMatchScore (double matchScore) { return new Needleman.NeedlemanBuilder().withMatchScore (matchScore); }

      public static NeedlemanBuilder
    withMismatchPenalty (double mismatchPenalty) { return new Needleman.NeedlemanBuilder().withMismatchPenalty (mismatchPenalty); }

      public static NeedlemanBuilder
    withCustomMatchScorer (MatchMismatchScorer matchScorer) { return new Needleman.NeedlemanBuilder().withCustomMatchScorer(matchScorer); }

      public static NeedlemanBuilder
    withGapPenalty(double gapPenalty) { return new Needleman.NeedlemanBuilder().withGapPenalty (gapPenalty); }

      public static NeedlemanBuilder
    withGapPenalties(double gapInAPenalty, double gapInBPenalty) { return new Needleman.NeedlemanBuilder().withGapPenalties(gapInAPenalty, gapInBPenalty); }

      public static NeedlemanBuilder
    withNoStartEndGapPenalties () { return new Needleman.NeedlemanBuilder().withNoStartEndGapPenalties(); }
    
      public static NeedlemanBuilder
    withNoStartEndGapPenaltiesInB () { return new Needleman.NeedlemanBuilder().withNoStartEndGapPenaltiesInB(); }
        
      public static NeedlemanBuilder
    withNoStartEndGapPenaltiesInA () { return new Needleman.NeedlemanBuilder().withNoStartEndGapPenaltiesInA(); }

      public static NeedlemanBuilder
    withCustomGapScorer (GapScorer gapScorer) { return new Needleman.NeedlemanBuilder().withCustomGapScorer(gapScorer); }

    
    public static class NeedlemanBuilder
    {
        private NeedlemanBuilder(){ }
     
        
        private static final double DEFAULT_MATCH_SCORE = 2;
        private static final double DEFAULT_GAP_PENALTY = - 0;
        private static final double DEFAULT_MISMATCH_PENALTY = 1;
            
        private MatchMismatchScorer matchScorer = new StandardMatchScorer();
        private GapScorer gapScorer = new StandardGapScorer();
        
        
          private final static class 
        StandardMatchScorer implements MatchMismatchScorer
        {
            public double matchScore = DEFAULT_MATCH_SCORE;
            public double mismatchPenalty = DEFAULT_MISMATCH_PENALTY;
            
              @Override public double
            score (int a, int b, int i, int j, int aLength, int bLength) {
                    
                    return a == b ? matchScore : - mismatchPenalty;
                }
        }
        
          private final static class 
        StandardGapScorer implements GapScorer
        {   
            double gapInAPenalty = DEFAULT_GAP_PENALTY;
            double gapInBPenalty = DEFAULT_GAP_PENALTY;
            
            boolean noStartGapPenaltyA = false;
            boolean noEndGapPenaltyA = false;
            
            boolean noStartGapPenaltyB = false;
            boolean noEndGapPenaltyB = false;
            
              @Override public double
            score(GapScorer.Direction sequence, int aIdx, int bIdx, int aLength, int bLength) {
                
                    if (sequence == GapScorer.Direction.SKIP_A)
                    {
                        if (noStartGapPenaltyA && aIdx == 0)
                            return 0;
                        if (noEndGapPenaltyA && aIdx == aLength)
                            return 0;
                    }
                    if (sequence == GapScorer.Direction.SKIP_B)
                    {
                        if (noStartGapPenaltyB && bIdx == 0)
                            return 0;
                        if (noEndGapPenaltyB && bIdx == bLength)
                            return 0;
                    }
                    
                    if (sequence == GapScorer.Direction.SKIP_A) 
                        return - gapInAPenalty;
                    return - gapInBPenalty;
                }
        }
        
        
          public NeedlemanResult
        align (int[] sequence1, int[] sequence2) { return new NeedlemanImp().align(sequence1, sequence2, this); }
        
        
          public NeedlemanBuilder
        withMatchScore (double matchScore){
            
                if (! (matchScorer instanceof StandardMatchScorer))
                    throw new IllegalArgumentException ("The match scorer has been set to something custom.");
                
                ((StandardMatchScorer)matchScorer).matchScore = matchScore;
                
                return this;
            }
        
          public NeedlemanBuilder
        withMismatchPenalty (double mismatchPenalty){
            
                if (! (matchScorer instanceof StandardMatchScorer))
                    throw new IllegalArgumentException ("The match scorer has been set to something custom.");
            
                ((StandardMatchScorer)matchScorer).mismatchPenalty = mismatchPenalty;
                
                return this;
            }
        
          public NeedlemanBuilder
        withCustomMatchScorer (MatchMismatchScorer matchScorer){
                
                this.matchScorer = matchScorer;
                return this;
            }
        
        
          public NeedlemanBuilder
        withGapPenalty(double gapPenalty){
            
                if (! (gapScorer instanceof StandardGapScorer))
                    throw new IllegalArgumentException ("The gap scorer has been set to something custom.");
                
                ((StandardGapScorer)this.gapScorer).gapInAPenalty = gapPenalty;
                ((StandardGapScorer)this.gapScorer).gapInBPenalty = gapPenalty;
                        
                return this;
            }
        
          public NeedlemanBuilder
        withGapPenalties(double gapInAPenalty, double gapInBPenalty){
            
                if (! (gapScorer instanceof StandardGapScorer))
                    throw new IllegalArgumentException ("The gap scorer has been set to something custom.");
                
                ((StandardGapScorer)this.gapScorer).gapInAPenalty = gapInAPenalty;
                ((StandardGapScorer)this.gapScorer).gapInBPenalty = gapInBPenalty;
                        
                return this;
            }
        
          public NeedlemanBuilder
        withNoStartEndGapPenalties () {
            
                withNoStartEndGapPenaltiesInA();
                withNoStartEndGapPenaltiesInB();
                
                return this;
            }
        
          public NeedlemanBuilder
        withNoStartEndGapPenaltiesInA () {
            
                if (! (matchScorer instanceof StandardMatchScorer))
                    throw new IllegalArgumentException ("The match scorer has been set to something custom.");
                        
                ((StandardGapScorer)gapScorer).noStartGapPenaltyA = true;
                ((StandardGapScorer)gapScorer).noEndGapPenaltyA = true;
                
                return this;
            }
        
          public NeedlemanBuilder
        withNoStartEndGapPenaltiesInB () {
            
                if (! (matchScorer instanceof StandardMatchScorer))
                    throw new IllegalArgumentException ("The match scorer has been set to something custom.");
                
                ((StandardGapScorer)gapScorer).noStartGapPenaltyB = true;
                ((StandardGapScorer)gapScorer).noEndGapPenaltyB = true;
                
                return this;
            }
        
          public NeedlemanBuilder
        withCustomGapScorer (GapScorer gapScorer){
                    
                this.gapScorer = gapScorer;
                
                return this;
            }
    }
    
    
      private static class 
    NeedlemanImp
    {
        private NeedlemanImp() {}
        
          private static class
        Scores
        {
            Scores (int length) {
                
                    this.length = length;
                    this.scores = new double [length + 2];
                    this.ptr = 0;
                }
              
            final int length;
            final double[] scores;
            int ptr;
            
//            final static int MAX_LENGTH = ArrayUtils2.MAX_ARRAY_SIZE - 2;
            final static int MAX_LENGTH = Integer.MAX_VALUE - 8 - 2;
            
              void 
            put (double val) {
                
                    scores[ptr] = val;
                    ptr = (ptr + 1) % (length + 2);
                }

              double 
            getDiag() {
                
                    int index = (ptr + 0) % (length + 2);
                    return scores[index];
                }

              double 
            getAbove() {
                
                    int index = (ptr + 1) % (length + 2);
                    return scores[index];
                }

              double 
            getLeft() {
                
                    int index = (ptr + length+1) % (length+2); // ie, scoresPtr-1
                    return scores[index];
                }
        }
        
          private NeedlemanResult
        align (int[] a, int[] b, NeedlemanBuilder config) {
            
                if ((long)a.length * b.length > NeedlemanResult.MAX_N_BY_M)
                    throw new IllegalArgumentException ("sequences are too long. The product of their lengths must not be greater than " + NeedlemanResult.MAX_N_BY_M);
                if (a.length > Scores.MAX_LENGTH)
                    throw new IllegalArgumentException ("sequence1 is too long. Its length must not be greater than " + Scores.MAX_LENGTH);

                Scores scores = new Scores (a.length);

                NeedlemanResult pointersMap = new NeedlemanResult (a.length + 1, b.length + 1);
            
                {
                    final int bIdx = 0;
                    
                    scores.put (0);
                    
                    for (int aIdx = 1; aIdx < a.length + 1; aIdx++)
                    {
                        double left = scores.getLeft() + config.gapScorer.score (GapScorer.Direction.SKIP_B, aIdx, bIdx, a.length, b.length);

                        double max = left;
                        Pointer pointer = Pointer.SKIP_B;

                        pointersMap.setPointer (aIdx, bIdx, pointer);
                        scores.put (max);
                    }
                }
                
                for (int bIdx = 1; bIdx < b.length + 1; bIdx++)
                {
                    {
                        final int aIdx = 0;
                        
                        double above = scores.getAbove() + config.gapScorer.score(GapScorer.Direction.SKIP_A, aIdx, bIdx, a.length, b.length);

                        double max = above;
                        Pointer pointer = Pointer.SKIP_A;

                        pointersMap.setPointer (aIdx, bIdx, pointer);
                        scores.put (max);
                    }

                    for (int aIdx = 1; aIdx < a.length + 1; aIdx++)
                    {
                        double diag = scores.getDiag() + config.matchScorer.score (a[aIdx-1], b[bIdx-1], aIdx, bIdx, a.length, b.length);
                        double above = scores.getAbove() + config.gapScorer.score (GapScorer.Direction.SKIP_A, aIdx, bIdx, a.length, b.length);
                        double left = scores.getLeft() + config.gapScorer.score (GapScorer.Direction.SKIP_B, aIdx, bIdx, a.length, b.length);

                        double max = Math.max(Math.max(diag, above), left);
                        Pointer pointer;
                        if (max == diag)
                        {
                            if (config.matchScorer.isMatch (a[aIdx-1], b[bIdx-1]))
                                pointer = Pointer.MATCH;
                            else
                                pointer = Pointer.MISMATCH;
                        }
                        else if (max == above)
                        {
                            pointer = Pointer.SKIP_A;
                        }
                        else
                        {
                            pointer = Pointer.SKIP_B;
                        }
                                
                        pointersMap.setPointer (aIdx, bIdx, pointer);
                        scores.put (max);
                    }
                }
                return pointersMap;
            }
    }

      public static class 
    NeedlemanResult
    { 
        NeedlemanResult (int N, int M) {
            
                this.N = N;
                this.M = M;
                int pointersSize = Math.toIntExact ((long)N * M / POINTERS_PER_ELEMENT + 1);
                this.pointers = new long [pointersSize];
            }
        
        public final int N, M;
        private final long[] pointers; // NxM matrix of traceback pointers. 2-bit values packed into int32's.
        private final static int BITS_PER_POINTER = 2;
        private final static int POINTERS_PER_ELEMENT = Long.SIZE / BITS_PER_POINTER;
//        private final static long MAX_N_BY_M = (long) POINTERS_PER_ELEMENT * ArrayUtils2.MAX_ARRAY_SIZE - 1;
        private final static long MAX_N_BY_M = (long) POINTERS_PER_ELEMENT * (Integer.MAX_VALUE - 8) - 1;
        
        static enum Pointer { MATCH, MISMATCH, SKIP_A, SKIP_B }

          /* Test-private */ void
        setPointer (int x, int y, Pointer pointer) {
            
                int val = pointer.ordinal();
            
                assert ! (x < 0 || x >= N || y < 0 || y >= M);
                assert ! (val < 0 || 3 < val);

                long index = (long)y * N + x;
                int whole = Math.toIntExact (index / POINTERS_PER_ELEMENT);
                int frac = (int) (index % POINTERS_PER_ELEMENT);

                pointers[whole] = pointers[whole] | ((long)val << (frac*BITS_PER_POINTER));
            }

          /* Test-private */ Pointer
        getPointer (int x, int y){

                if (x < 0 || x >= N || y < 0 || y >= M)
                    throw new IllegalArgumentException();

                long index = (long)y * N + x;
                int whole = Math.toIntExact (index / POINTERS_PER_ELEMENT);
                int frac = (int) (index % POINTERS_PER_ELEMENT);

                int val = (int) ((pointers[whole] >> (frac*BITS_PER_POINTER)) & 0x3);
                return Pointer.values() [val];
            }
        
          public void
        walk (IntBiConsumer caseMatch, IntBiConsumer caseMismatch, IntBiConsumer caseGapInA, IntBiConsumer caseGapInB) {
            
                int i = N-1, j = M-1;
                while ( ! (i == 0 && j == 0))
                {
                    switch (getPointer (i, j))
                    {
                        case MATCH:
                            caseMatch.accept (i-1, j-1);
                            --i; --j;
                            break;
                            
                        case MISMATCH:
                            caseMismatch.accept (i-1, j-1);
                            --i; --j;
                            break;

                        case SKIP_A:
                            caseGapInA.accept (i-1, j-1);
                            --j;
                            break;

                        case SKIP_B:
                            caseGapInB.accept (i-1, j-1);
                            --i;
                            break;
                    }
                }
            }
        

          public List<IntTuple>
        getMatches() {
            
                List<IntTuple> matches = new ArrayList<>();
                
                walk ( (i,j) -> matches.add (new IntTuple (i,j))
                     , (i,j) -> {}
                     , (i,j) -> {}
                     , (i,j) -> {} );
                
                return matches;
            }

          public String
        print (int[] a, int[] b) {
            
                StringBuilder topRow = new StringBuilder();
                StringBuilder middleRow = new StringBuilder();
                StringBuilder bottomRow = new StringBuilder();

                DecimalFormat format = new DecimalFormat("00");

                walk ( (i,j) -> 
                            {
                                topRow.insert    (0, format.format (a[i]) + " ");
                                middleRow.insert (0, "|| ");
                                bottomRow.insert (0, format.format (b[j]) + " ");
                            }
                     , (i,j) -> 
                            {
                                topRow.insert    (0, format.format (a[i]) + " ");
                                middleRow.insert (0, "   ");
                                bottomRow.insert (0, format.format (b[j]) + " ");
                            }
                     , (i,j) -> 
                            {
                                topRow.insert    (0, "__ ");
                                middleRow.insert (0, "   ");
                                bottomRow.insert (0, format.format (b[j]) + " ");
                            }
                     , (i,j) -> 
                            {
                                topRow.insert    (0, format.format (a[i]) + " ");
                                middleRow.insert (0, "   ");
                                bottomRow.insert (0, "__ ");
                            } );

                return topRow + "\n" + middleRow + "\n" + bottomRow;
            }


          public String
        print (String[] sequenceA, String[] sequenceB) {
            
                return print(sequenceA, sequenceB, Integer.MAX_VALUE);
            }

          public String
        print (String[] sequenceA, String[] sequenceB, int charsPerLine) {  

                StringBuilder topRow = new StringBuilder();
                StringBuilder middleRow = new StringBuilder();
                StringBuilder bottomRow = new StringBuilder();

                walk ( (i,j) -> 
                            {
                                int len = sequenceA[i].length();
                                topRow.insert    (0, sequenceA[i] + " ");
                                middleRow.insert (0, repeat ('|', len) + " ");
                                bottomRow.insert (0, sequenceB[j] + " ");
                            }
                     , (i,j) -> 
                            {
                                int lenA = sequenceA[i].length();
                                int lenB = sequenceB[j].length();
                                int len = Math.max (lenA, lenB);
                                topRow.insert    (0, sequenceA[i] + repeat (' ', len-lenA) + " ");
                                middleRow.insert (0, repeat (' ', len) + " ");
                                bottomRow.insert (0, sequenceB[j] + repeat (' ', len-lenB) + " ");
                            }
                     , (i,j) -> 
                            {
                                int len = sequenceB[j].length();
                                topRow.insert    (0, repeat ('_', len) + " ");
                                middleRow.insert (0, repeat (' ',len) + " ");
                                bottomRow.insert (0, sequenceB[j] + " ");
                            }
                     , (i,j) -> 
                            {
                                int len = sequenceA[i].length();
                                topRow.insert    (0, sequenceA[i] + " ");
                                middleRow.insert (0, repeat (' ',len) + " ");
                                bottomRow.insert (0, repeat ('_', len) + " ");
                            } );

                StringBuilder retVal = new StringBuilder();
                for(int n = 0; n < topRow.length(); n+=charsPerLine)
                {
                    int first = n, last = Math.min(n+charsPerLine, topRow.length());
                    retVal.append(topRow.substring(first, last)).append("\n");
                    retVal.append(middleRow.substring(first, last)).append("\n");
                    retVal.append(bottomRow.substring(first, last)).append("\n");
                }

                return retVal.toString();
            }

    }
}
