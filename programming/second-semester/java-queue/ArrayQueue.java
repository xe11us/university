package queue;

public class ArrayQueue extends AbstractQueue implements Queue {
    private int head = 0;
    private Object[] elements = new Object[4];

    public void enqueueImpl(Object element) {
        ensureCapacity(size + 1);

        elements[(head + size) % elements.length] = element;
    }

    public Object elementImpl() {
        return elements[head];
    }

    public void remove() {
        elements[head] = null;
        head = (head + 1) % elements.length;
    }

    public void clear() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
        size = 0;
        head = 0;
    }

    public ArrayQueue makeCopy() {
        final ArrayQueue copy = new ArrayQueue();
        copy.size = size;
        copy.head = head;

        copy.elements = new Object[elements.length];

        for (int i = 0; i < elements.length; i++) {
            copy.elements[i] = elements[i];
        }

        return copy;
    }

    protected Queue getEmpty() {
        return new ArrayQueue();
    }

    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity <= elements.length) {
            return;
        }

        Object[] newElements = new Object[2 * requiredCapacity];

        int pos = 0;

        while (size > 0) {
            newElements[pos++] = dequeue();
        }

        size = pos;
        head = 0;
        elements = newElements;
    }
}
