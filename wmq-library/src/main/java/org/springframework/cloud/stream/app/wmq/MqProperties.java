package org.springframework.cloud.stream.app.wmq;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jms.queue")
public class MqProperties {
    /**
     * Queue manager to connect to
     */
    private String queueManager;

    /**
     * Host name of MQ instance
     */
    private String host;

    /**
     * Port of MQ instance
     */
    private int port;

    /**
     * MQ channel to use for connection
     */
    private String channel;

    /**
     * Queue name to connect
     */
    private String queueName;

    public String getQueueManager() {
        return queueManager;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getChannel() {
        return channel;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setQueueManager(String queueManager) {
        this.queueManager = queueManager;
    }
}