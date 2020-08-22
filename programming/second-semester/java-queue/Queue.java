package queue;

import java.util.function.Function;
import java.util.function.Predicate;

//inv: size >= 0
//     forall i = 0...size - 1: queue[i] != null
public interface Queue extends Copiable {
    //pre: element != null
    //post: size = size' + 1
    //      queue[size - 1] = element
    //      forall i = 0...size - 2: queue[i] = queue'[i]
    void enqueue(Object element);

    //pre: size > 0
    //post: result = queue[0]
    //      size = size'
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    Object element();

    //pre: size > 0
    //post: size = size' - 1
    //      result = queue[0]
    //      forall i = 0...size - 1: queue[i] = queue'[i + 1]
    Object dequeue();

    //post: result = size
    //      size = size'
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    int size();

    //post: (result = true && size = 0) || (result = false && size > 0)
    //      size = size'
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    boolean isEmpty();

    //post: size = 0
    void clear();

    //post: result = queue
    //      size = size'
    //      forall i = 0...size - 1: queue[i] = queue'[i]
    Queue makeCopy();

    // :NOTE: Произвольная перестановка
    // :NOTE: NullPointerException
    //post: forall j : predicate.test(queue[j]) = true exists i: result[i] = queue[j]
    //      result.length = size({j: predicate.test(queue[j]) = true})
    Queue filter(Predicate<Object> predicate);

    //pre: forall i = 0...size: function(queue[i]) != null
    //post: result.size = this.size
    //      forall i = 0...size: result[i] = function(queue[i])
    Queue map(Function<Object, Object> function);
}
