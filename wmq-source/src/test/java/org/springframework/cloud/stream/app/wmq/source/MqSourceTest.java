package org.springframework.cloud.stream.app.wmq.source;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.context.annotation.Bean;

import javax.jms.Destination;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={MqSourceTest.TestConfig.class, MqApplication.class})
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class MqSourceTest {

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private Source source;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void testMessageIsSentOut() throws InterruptedException {
        jmsTemplate.convertAndSend("MQD1.DEAD.QUEUE", "my message");
        BlockingQueue<Message<?>> messages =  messageCollector.forChannel(source.output());
        Message m = messages.poll(1000, TimeUnit.MILLISECONDS);
        assertNotNull(m);
    }

    public static class TestConfig {
        @Bean
        public ActiveMQConnectionFactory connectionFactory() {
            return new ActiveMQConnectionFactory("vm://localhost");
        }

        @Bean
        public Destination destination() {
            try {
                return new ActiveMQQueue("MQD1.DEAD.QUEUE");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
