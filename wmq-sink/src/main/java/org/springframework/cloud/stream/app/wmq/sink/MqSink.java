package org.springframework.cloud.stream.app.wmq.sink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.jms.Jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

@EnableBinding(Sink.class)
public class MqSink {

    @Autowired
    private Sink channels;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private Destination destination;

    @Bean
    public IntegrationFlow jmsOutboundGatewayFlow() {
        return IntegrationFlows.from(channels.input())
                .handle(Jms.outboundAdapter(connectionFactory).destination(destination))
                .get();
    }
}
