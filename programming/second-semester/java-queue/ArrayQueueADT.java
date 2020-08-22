package queue;

import java.util.Arrays;

//inv: size >= 0
//     forall i = 0...size - 1: queue[i] != null
public class ArrayQueueADT {
    private int size = 0;
    private int head = 0;
    private Object[] elements = new Object[4];

    //pre: queue != null
    //     element != null
    //post: size = size' + 1
    //      queue[size - 1] = element
    //      forall i = 0...size - 2: queue[i] = queue'[i]
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null;
        assert queue != null;
        ensureCapacity(queue,queue.size + 1);

        queue.elements[(queue.head + queue.size) % queue.elements.length] = element;

        queue.size++;
    }

    //pre: queue != null
    //     element != null
    //post: size = size' + 1
    //      queue[0] = element
    //      forall i = 1...size - 1: queue[i] = queue'[i - 1]
    public static void push(ArrayQueueADT queue, Object element) {
        assert element != null;
        assert queue != null;
        ensureCapacity(queue,queue.size + 1);
        queue.size++;

        queue.head = (queue.head - 1 + queue.elements.length) % queue.elements.length;
        queue.elements[queue.head] = element;
    }

    //pre: queue != null
    //     size > 0
    //post: result = queue[0]
    //      size = size'
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    public static Object element(ArrayQueueADT queue) {
        assert queue != null;
        assert queue.size != 0;
        return queue.elements[queue.head];
    }

    //pre: queue != null
    //     size > 0
    //post: result = queue[size' - 1]
    //      size = size'
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    public static Object peek(ArrayQueueADT queue) {
        assert queue != null;
        assert queue.size != 0;
        return queue.elements[(queue.head + queue.size - 1 + queue.elements.length) % queue.elements.length];
    }

    //pre: queue != null
    //     size > 0
    //post: size = size' - 1
    //      result = queue[0]
    //      forall i = 0...size - 1: queue[i] = queue'[i + 1]
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue != null;
        assert queue.size != 0;
        Object container = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        queue.size--;
        return container;
    }

    //pre: queue != null
    //     size > 0
    //post: result = queue[size' - 1]
    //      size = size' - 1
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    public static Object remove(ArrayQueueADT queue) {
        assert queue != null;
        assert queue.size != 0;
        Object container = queue.elements[(queue.head + queue.size - 1 + queue.elements.length) % queue.elements.length];
        queue.elements[(queue.head + queue.size - 1 + queue.elements.length) % queue.elements.length] = null;
        queue.size--;
        return container;
    }

    //pre: queue != null
    //post: result = size
    //      size = size'
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    public static int size(ArrayQueueADT queue) {
        assert queue != null;
        return queue.size;
    }

    //pre: queue != null
    //post: (result = true && size = 0) || (result = false && size > 0)
    //      size = size'
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    public static boolean isEmpty(ArrayQueueADT queue) {
        assert queue != null;
        return queue.size == 0;
    }

    //pre: queue != null
    //post: size = 0
    public static void clear(ArrayQueueADT queue) {
        assert queue != null;
        Arrays.fill(queue.elements, null);
        queue.size = 0;
        queue.head = 0;
    }


    //pre: queue != null
    //     length = elements.length
    //     requiredCapacity > elements.length
    //post: size = size'
    //      head = head'
    //      elements.length >= requiredCapacity
    //      forall i = 0...length - 1: elements[i] = elements'[(head + i) % n]
    //      forall i = length...length * 2 - 1: elements[i] = null
    private static void ensureCapacity(ArrayQueueADT queue, int requiredCapacity) {
            if (requiredCapacity <= queue.elements.length) {
                return;
            }

            int newCapacity = queue.elements.length;
            while (newCapacity < requiredCapacity) {
                newCapacity *= 2;
            }

            Object[] newElements = new Object[newCapacity];

            int pos = 0;

            while (queue.size > 0) {
                newElements[pos++] = dequeue(queue);
            }

            queue.size = pos;
            queue.head = 0;
            queue.elements = newElements;
        }
}
