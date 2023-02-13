public class Main {

    public static String hostName="127.0.0.1";
    public static String queueName="testqueue-amqp-1-0";
    public static String username="user";
    public static String password="password";
    public static int port = 5672;

    public static void main(String[] args) throws Exception {

        Amqp1Producer producer = new Amqp1Producer();
        for (int i = 0; i < 500; i++) {
            producer.send("Message with ID", i);
        }

        Amqp1Consumer consumer = new Amqp1Consumer();
        while (true) {
            consumer.read();
        }
    }
}