package io.spring.trace.examples.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@EnableBinding(Sink.class)
public class EventHandler {
    private static Logger log = org.slf4j.LoggerFactory.getLogger(EventHandler.class);

    @StreamListener(Sink.INPUT)
    public void handle(@Payload Object payload, @Header(name="order_id") UUID orderId) {
        log.info("consume event, order: " + orderId + ", payload: " + payload);
        doNotify();
    }

    private void doNotify() {
        try {
            int processingTime = ThreadLocalRandom.current().nextInt(20,299);
            log.debug("================ DEBUG ================");
            log.debug("notification-process start...");
            Thread.sleep(processingTime);
            log.debug("notification-process finish...(" + processingTime + "ms)");
            log.debug("========================================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
