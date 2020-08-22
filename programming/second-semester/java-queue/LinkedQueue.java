package queue;

public class LinkedQueue extends AbstractQueue implements Queue {
    private Node head;
    private Node tail;

    public void enqueueImpl(Object element) {
        Node tmp = tail;
        tail = new Node(element, null);

        if (size == 0) {
            head = tail;
        }
        else {
            tmp.next = tail;
        }
    }

    public Object elementImpl() {
        return head.value;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public LinkedQueue makeCopy() {
        final LinkedQueue copy = new LinkedQueue();
        copy.size = size;
        copy.head = head;
        copy.tail = tail;
        return copy;
    }

    public void remove() {
        head = head.next;
    }

    protected Queue getEmpty() {
        return new LinkedQueue();
    }

    private class Node {
        private Object value;
        private Node next;

        public Node(Object value, Node next) {
            assert value != null;

            this.value = value;
            this.next = next;
        }
    }
}
