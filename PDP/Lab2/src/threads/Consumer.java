package threads;


public class Consumer extends  Thread {
    public int result = 0;
    public ProducerConsumerBuffer buffer;
    public int length;

    public Consumer(ProducerConsumerBuffer buffer, int length) {
        super("Consumer");
        this.buffer = buffer;
        this.length = length;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.length; ++i) {
            result += buffer.get();
            System.out.printf("Consumer: Result is now %d\n", result);
        }
        System.out.printf("\nConsumer: Final result is %d", result);
    }
}
