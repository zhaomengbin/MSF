package com.example.amqp.amqp;

import com.rabbitmq.client.Recoverable;
import com.rabbitmq.client.RecoveryListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/5
 * @since 1.0.0
 */
@Slf4j
public class RabbitRecoverListener implements RecoveryListener {

    @Override
    public void handleRecovery(Recoverable recoverable) {
    log.info("rabbit handleRecovery : {}",recoverable);
    }

    @Override
    public void handleRecoveryStarted(Recoverable recoverable) {
        log.info("rabbit handleRecoveryStarted : {}",recoverable);
    }
}