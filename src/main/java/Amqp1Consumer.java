import com.swiftmq.amqp.AMQPContext;
import com.swiftmq.amqp.v100.client.Connection;
import com.swiftmq.amqp.v100.client.Consumer;
import com.swiftmq.amqp.v100.client.Session;
import com.swiftmq.amqp.v100.messaging.AMQPMessage;
import com.swiftmq.amqp.v100.types.AMQPString;
import org.jboss.logging.Logger;

public class Amqp1Consumer {

    Logger log = Logger.getLogger(Amqp1Producer.class.getName());

    public Amqp1Consumer(){

    }

    public void read() {
        AMQPContext context = new AMQPContext(0);
        Connection connection = new Connection(context, Main.hostName, Main.port, Main.username, Main.password);

        try {
            connection.connect();
            this.log.info("Connected to RabbitMQ");

            Session session = connection.createSession(100L, 100L);
            Consumer consumer = session.createConsumer(Main.queueName, 200, 1, true, "bla");
            AMQPMessage message = consumer.receive();

            String messageReceived = ((AMQPString)message.getAmqpValue().getValue()).getValue();

            message.accept();
            this.log.info("Message received: " + messageReceived);

        } catch (Exception e) {
            this.log.error("ERROR: failed to fetch message from RabbitMQ");
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }
}
