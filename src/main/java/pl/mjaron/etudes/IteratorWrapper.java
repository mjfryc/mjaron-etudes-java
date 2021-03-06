package pl.mjaron.etudes;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Allows getting current iterated element with {@link #curr()} method.
 *
 * @param <T>
 */
public class IteratorWrapper<T> implements Iterator<T> {

    public final Iterator<T> it;
    private T curr = null;

    public IteratorWrapper(Iterator<T> it) {
        this.it = it;
    }

    /**
     * Provides underlying iterator.
     *
     * @return Underlying iterator object.
     */
    public Iterator<T> getIterator() {
        return this.it;
    }

    /**
     * Refers current element. It may be null.
     *
     * @return Currently referred element. It may be null when {@link #next()} hasn't been called or element is null.
     */
    public T curr() {
        return this.curr;
    }

    /**
     * @return True if next element is available, false otherwise.
     * @see Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    /**
     * @return Next element.
     * @see Iterator#next()
     */
    @Override
    public T next() {
        this.curr = it.next();
        return this.curr;
    }

    /**
     * @see Iterator#remove()
     */
    @Override
    public void remove() {
        it.remove();
    }

    /**
     * @param action Action called for each element.
     * @see Iterator#forEachRemaining(Consumer)
     */
    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        it.forEachRemaining(action);
    }
}
