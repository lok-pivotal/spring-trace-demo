package io.spring.trace.examples.orders;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class ProcessOrdersController {
    private static Logger log = org.slf4j.LoggerFactory.getLogger(ProcessOrdersController.class);

    @Autowired
    private PaymentsConnector payments;

    @Autowired
    private OrderRepo repo;

    @RequestMapping("/process-order")
    public ResponseEntity<ResponseMessage> tracePath() {
        log.info("/process-order called");

        // create dummy new order with random total
        OrderEntity newOrder = new OrderEntity();
        newOrder.setStatus("pending");
        newOrder.setTotal(ThreadLocalRandom.current().nextInt(1,999)*10);

        // save new order
        final UUID orderId = repo.save(newOrder).getId();

        log.debug("================ DEBUG ================");
        log.debug("debug-level log message ... order: " + newOrder);

        PaymentResponse response = payments.chargeCard();

        // fetch order and update status
        OrderEntity theOrder = repo.findById(orderId).orElseThrow(()-> new IllegalStateException("unknown orderId: " + orderId));
        if(response.isSuccess()){
            theOrder.setStatus("paid");
        }else{
            theOrder.setStatus("cancelled");
        }
        repo.save(theOrder);

        int status = response.isSuccess() ? 200 : 503;
        log.debug("debug-level log message ... " + status + ": " + response.getMessage());
        log.debug("========================================");
        return ResponseEntity.status(status).body(new ResponseMessage(theOrder.getId(), response.getMessage()));
    }
}
