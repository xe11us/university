package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue {
    protected int size;

    public void enqueue(final Object element) {
        assert element != null;
        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    public Object dequeue() {
        assert size > 0;

        final Object result = element();
        size--;
        remove();
        return result;
    }

    protected abstract void remove();

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    protected abstract void clear();

    protected abstract Queue getEmpty();

    public Queue filter(final Predicate<Object> predicate) {
        final Queue result = getEmpty();

        for (int i = 0; i < size; i++) {
            final Object up = this.dequeue();

            if (predicate.test(up)) {
                result.enqueue(up);
            }

            this.enqueue(up);
        }

        return result;
    }

    public Queue map(final Function<Object, Object> function) {
        final Queue result = getEmpty();

        for (int i = 0; i < size; i++) {
            final Object up = this.dequeue();

            result.enqueue(function.apply(up));

            this.enqueue(up);
        }

        return result;
    }
}
