import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueSender {

    private static final String QUEUE_NAME = "smart_meter_data";

    public static void sendDataToQueue(String jsonData) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("woodpecker.rmq.cloudamqp.com"); // Replace with your CloudAMQP server details
        factory.setPort(5672); // CloudAMQP typically uses port 5672 for non-SSL connections
        factory.setUsername("eqxfpanf");
        factory.setPassword("9vubXoJXNd2kRDWRfpOJ8M6-P7Bwhp_v");
        factory.setVirtualHost("eqxfpanf");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, jsonData.getBytes());
            System.out.println("Sent: " + jsonData);
        }
    }
}
