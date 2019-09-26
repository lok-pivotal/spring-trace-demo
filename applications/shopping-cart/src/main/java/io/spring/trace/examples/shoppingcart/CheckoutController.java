package io.spring.trace.examples.shoppingcart;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckoutController {
    private static Logger log = org.slf4j.LoggerFactory.getLogger(CheckoutController.class);

    @Autowired
    private OrdersConnector orders;

    @Autowired
    private NotificationsConnector notifications;

    @RequestMapping("/checkout")
    public ResponseEntity<ResponseMessage> tracePath() {
        log.info("/checkout called");

        OrderResponse response = orders.processOrder();
        int status = response.isSuccess() ? 200 : 503;
        log.debug("================ DEBUG ================");
        log.debug("debug-level log message ... " + status + ": " + response.getMessage());
        log.debug("========================================");

        notifications.send(response.getOrderId(), response.getMessage());

        return ResponseEntity.status(status).body(new ResponseMessage(response.getOrderId(),response.getMessage()));
    }
}
