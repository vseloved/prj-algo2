/*
 * Copyright Almson Corp.
 */
package course.advalgos;

/**
 *
 * @author Aleksandr Dubinsky
 */
@FunctionalInterface
public interface IntBiConsumer {
    
    /**
     * Performs this operation on the given arguments.
     */
    void accept(int i, int j);
}
