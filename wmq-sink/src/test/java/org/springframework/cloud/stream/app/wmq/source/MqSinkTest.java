package org.springframework.cloud.stream.app.wmq.source;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.app.wmq.sink.MqApplication;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.Destination;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={MqSinkTest.TestConfig.class, MqApplication.class})
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class MqSinkTest {

    @Autowired
    private MessageCollector messageCollector;

    @Autowired
    private Sink sink;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void testMessageIsSentOut() {
        Message<String> message = new GenericMessage<>("awesome message");
        boolean b = sink.input().send(message);
        assertTrue(b);
        javax.jms.Message m = jmsTemplate.receive("MQD1.DEAD.QUEUE");
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
