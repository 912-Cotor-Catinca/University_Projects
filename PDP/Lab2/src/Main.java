import domain.Vector;
import threads.Consumer;
import threads.Producer;
import threads.ProducerConsumerBuffer;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Vector vector1 = new Vector(Arrays.asList(1, 2, 3, 4));
        Vector vector2 = new Vector(Arrays.asList(4, 3, 2, 1));

        ProducerConsumerBuffer helper = new ProducerConsumerBuffer();
        Producer producer = new Producer(helper, vector1, vector2);
        Consumer consumer = new Consumer(helper, vector1.getLength());

        producer.start();
        consumer.start();

    }
}
