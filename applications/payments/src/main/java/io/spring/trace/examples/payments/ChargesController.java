package io.spring.trace.examples.payments;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class ChargesController {
    private static Logger log = org.slf4j.LoggerFactory.getLogger(ChargesController.class);

    @RequestMapping("/charge-card")
    public ResponseMessage chargeCard() {
        log.info("/charge-card called");
        doCharge();

        return new ResponseMessage("card successfully charged!");
    }

    private void doCharge() {
        try {
            int processingTime = ThreadLocalRandom.current().nextInt(50,199);
            log.debug("================ DEBUG ================");
            log.debug("card-charge-process start...");
            Thread.sleep(processingTime);
            log.debug("card-charge-process finish...(" + processingTime + "ms)");
            log.debug("========================================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
