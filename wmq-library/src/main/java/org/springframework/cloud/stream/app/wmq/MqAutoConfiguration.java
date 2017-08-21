package org.springframework.cloud.stream.app.wmq;

import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQXAConnectionFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;

@Configuration
@AutoConfigureBefore(JmsAutoConfiguration.class)
@AutoConfigureAfter({ JndiConnectionFactoryAutoConfiguration.class })
@ConditionalOnClass({ ConnectionFactory.class, MQXAConnectionFactory.class })
@ConditionalOnMissingBean(ConnectionFactory.class)
@EnableConfigurationProperties(MqProperties.class)
@Import({ MqAutoConfiguration.MqConnectionFactoryConfiguration.class })
public class MqAutoConfiguration {

    @Configuration
    public class MqConnectionFactoryConfiguration {
        @Bean
        public Destination destination(MqProperties properties) {
            try {
                return new MQQueue(properties.getQueueName());
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }

        @Bean
        public ConnectionFactory jmsConnectionFactory(MqProperties properties) {
            MQXAConnectionFactory factory = new MQXAConnectionFactory();
            try {
                factory.setHostName(properties.getHost());
                factory.setQueueManager(properties.getQueueManager());
                factory.setChannel(properties.getChannel());
                factory.setPort(properties.getPort());
                factory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }

            return factory;
        }
    }

}
