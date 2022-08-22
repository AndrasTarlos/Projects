package exception;

/**
 * <h1>UnknownSortingTypeException</h1>
 * @author: Andras Tarlos
 * @version: 1.0
 * @date: 18.06.2022
 * <h2>Description</h2>
 * An exception that gets thrown when the programmer requests
 * a non-existing sorting type.
 */
public class UnknownSortingTypeException extends Exception {
    public UnknownSortingTypeException() {
        super("There is no such sorting type...");
    }
}
