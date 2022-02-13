package com.example.amqp.amqp;

import com.rabbitmq.client.ShutdownSignalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionListener;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/5
 * @since 1.0.0
 */
@Slf4j
public class RabbitConnectionListener implements ConnectionListener {

    @Override
    public void onCreate(Connection connection) {
        log.info("rabbit connection onCreate: {}",connection);
    }

    @Override
    public void onClose(Connection connection) {
        log.info("rabbit connection onClose: {}",connection);
    }

    @Override
    public void onShutDown(ShutdownSignalException signal) {
        log.info("rabbit connection onShutDown: {}",signal);

    }

}