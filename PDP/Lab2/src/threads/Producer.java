package threads;

import domain.Vector;

public class Producer extends Thread {

    public int length;
    public ProducerConsumerBuffer buffer;
    public Vector vector1, vector2;

    public Producer(ProducerConsumerBuffer buffer, Vector vector1, Vector vector2){
        super("Producer");
        this.buffer = buffer;
        this.vector1 = vector1;
        this.vector2 = vector2;
        this.length = vector1.getLength();
    }

    @Override
    public void run() {
        for (int i = 0; i < length; i++) {
            System.out.printf("Producer: Sending %d * %d = %d\n", vector1.get(i), vector2.get(i), vector1.get(i) * vector2.get(i));
            buffer.put(vector1.get(i) * vector2.get(i));
        }
    }
}
