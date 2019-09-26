package io.spring.trace.examples.shoppingcart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ContextConfiguration
public class CheckoutControllerTest {
    private MockMvc mvc;

    @InjectMocks
    CheckoutController subject;

    @Mock
    private OrdersConnector payments;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(subject).build();
    }

    @Test
    public void processOrderReturnsSuccess_whenChargeCardIsSuccessful() throws Exception {
        doReturn(new OrderResponse(ResponseEntity.ok().build())).when(payments).processOrder();

        this.mvc.perform(get("/checkout").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"message\":\"order processed successfully\"}"));
    }

    @Test
    public void processOrderReturnsFailure_whenChargeCardFails() throws Exception {
        OrderResponse orderResponse = new OrderResponse(ResponseEntity.badRequest().body("invalid content"));
        doReturn(orderResponse).when(payments).processOrder();

        this.mvc.perform(get("/checkout").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().string("{\"message\":\"unable to process order, please try again.\"}"));
    }
}
