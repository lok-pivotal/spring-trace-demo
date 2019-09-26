package io.spring.trace.examples.shoppingcart;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@EnableBinding(Source.class)
public class NotificationsConnector {

    private static Logger log = org.slf4j.LoggerFactory.getLogger(NotificationsConnector.class);

    @Autowired
    Source source;

    public void send(UUID orderId, String msg){
        Message message = MessageBuilder
                .withPayload(msg)
                .setHeader("order_id", orderId)
                .build();

        source.output().send(message);

        log.info("send event, order: " + orderId);

    }

}
