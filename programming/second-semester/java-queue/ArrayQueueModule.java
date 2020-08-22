package queue;

import java.util.Arrays;

//inv: size >= 0
//     forall i = 0...size - 1: queue[i] != null
public class ArrayQueueModule {
    private static int size = 0;
    private static int head = 0;
    private static Object[] elements = new Object[4];

    //pre: element != null
    //post: size = size' + 1
    //      queue[size - 1] = element
    //      forall i = 0...size - 2: queue[i] = queue'[i]
    public static void enqueue(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        elements[(head + size) % elements.length] = element;
        size++;
    }

    //pre: queue != null
    //     element != null
    //post: size = size' + 1
    //      queue[0] = element
    //      forall i = 1...size - 1: queue[i] = queue'[i - 1]
    public static void push(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        size++;

        head = (head - 1 + elements.length) % elements.length;
        elements[head] = element;
    }

    //pre: size > 0
    //post: result = queue[0]
    //      size = size'
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    public static Object element() {
        assert size != 0;
        return elements[head];
    }

    //pre: size > 0
    //post: result = queue[size' - 1]
    //      size = size'
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    public static Object peek() {
        assert size != 0;
        return elements[(head + size - 1 + elements.length) % elements.length];
    }

    //pre: size > 0
    //post: size = size' - 1
    //      result = queue[0]
    //      forall i = 0...size - 1: queue[i] = queue'[i + 1]
    public static Object dequeue() {
        assert  size != 0;
        Object container = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return container;
    }

    //pre: size > 0
    //post: result = queue[size' - 1]
    //      size = size' - 1
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    public static Object remove() {
        assert size != 0;
        Object container = elements[(head + size - 1 + elements.length) % elements.length];
        elements[(head + size - 1 + elements.length) % elements.length] = null;
        size--;
        return container;
    }

    //post: result = size
    //      size = size'
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    public static int size() {
        return size;
    }

    //post: (result = true && size = 0) || (result = false && size > 0)
    //      size = size'
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    public static boolean isEmpty() {
        return size == 0;
    }

    //post: size = 0
    public static void clear() {
        Arrays.fill(elements, null);
        size = 0;
        head = 0;
    }


    //pre: length = elements.length
    //     requiredCapacity > elements.length
    //post: size = size'
    //      head = head'
    //      elements.length >= requiredCapacity
    //      forall i = 0...length - 1: elements[i] = elements'[(head + i) % n]
    //      forall i = length...length * 2 - 1: elements[i] = null
    private static void ensureCapacity(int requiredCapacity) {
            if (requiredCapacity <= elements.length) {
                return;
            }

            int newCapacity = elements.length;
            while (newCapacity < requiredCapacity) {
                newCapacity *= 2;
            }

            Object[] newElements = new Object[newCapacity];

            int pos = 0;

            while (size > 0) {
                newElements[pos++] = dequeue();
            }

            size = pos;
            head = 0;
            elements = newElements;
        }
}
