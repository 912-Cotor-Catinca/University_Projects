package threads;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerBuffer {
    private static final int CAPACITY = 1;
    private final Queue<Integer> queue = new LinkedList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void put(int val) {
        lock.lock();
        try{
            while(queue.size() == CAPACITY) {
                System.out.println(Thread.currentThread().getName() +
                        " : Queue is full, waiting...");
                condition.await();
            }
            queue.add(val);
            System.out.printf("%s added %d into the queue %n", Thread.currentThread().getName(), val);
            condition.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public int get() {
        lock.lock();
        try{
            while (queue.size() == 0) {
                System.out.println(Thread.currentThread().getName() + " : Buffer is empty, waiting...");
                condition.await();
            }
            Integer val = queue.poll();
            if (val != null) {
                System.out.printf("%s consumed %d from the queue %n", Thread.currentThread().getName(), val);
                condition.signal();
            }
            return val;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
