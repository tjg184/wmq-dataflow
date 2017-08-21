package org.springframework.cloud.stream.app.wmq.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.jms.ChannelPublishingJmsMessageListener;
import org.springframework.integration.jms.JmsMessageDrivenEndpoint;
import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Session;

@EnableBinding(Source.class)
public class MqSource {

    @Autowired
    private Source channels;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private Destination destination;

    @Bean
    public JmsMessageDrivenEndpoint adapter() {
        return new JmsMessageDrivenEndpoint(container(), listener());
    }

    @Bean
    public ChannelPublishingJmsMessageListener listener() {
        ChannelPublishingJmsMessageListener listener = new ChannelPublishingJmsMessageListener();
        listener.setRequestChannel(channels.output());
        return listener;
    }

    @Bean
    public AbstractMessageListenerContainer container() {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        container.setSessionTransacted(true);
        container.setDestination(destination);
        return container;
    }
}
