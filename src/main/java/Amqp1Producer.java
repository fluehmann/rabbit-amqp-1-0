import org.jboss.logging.Logger;
import com.swiftmq.amqp.AMQPContext;
import com.swiftmq.amqp.v100.client.Connection;
import com.swiftmq.amqp.v100.client.Producer;
import com.swiftmq.amqp.v100.client.Session;
import com.swiftmq.amqp.v100.generated.messaging.message_format.AmqpValue;
import com.swiftmq.amqp.v100.messaging.AMQPMessage;
import com.swiftmq.amqp.v100.types.AMQPString;

public class Amqp1Producer {

    Logger log = Logger.getLogger(Amqp1Producer.class.getName());

    public Amqp1Producer(){

    }

    public void send(String message2send, int id) {
        String messageString = message2send + id;
        AMQPContext context = new AMQPContext(0);
        Connection connection = new Connection(context, Main.hostName, Main.port, Main.username, Main.password);

        try {
            connection.connect();
            this.log.info("Connected to RabbitMQ");
            Session session = connection.createSession(100L,100L);
            Producer amqpProducer = session.createProducer(Main.queueName, 1);

            AMQPMessage message = new AMQPMessage();
            message.setAmqpValue(new AmqpValue(new AMQPString(messageString)));
            amqpProducer.send(message);

            this.log.info("Message sent: " + messageString + "\n");
            amqpProducer.close();

        } catch (Exception e) {
            this.log.error("ERROR: Connection to RabbitMQ failed" + "\n" + e);
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }
}
