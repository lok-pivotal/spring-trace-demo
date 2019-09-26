package io.spring.trace.examples.shoppingcart;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class OrderResponse {
    private final boolean success;
    private final UUID orderId;
    private final String message;

    public OrderResponse(ResponseEntity<String> orderResponse) {
        this.success = orderResponse.getStatusCode().is2xxSuccessful();
        UUID id = null;
        try {
            final String idStr = new ObjectMapper().readTree(orderResponse.getBody()).at("/orderId").asText();
            id = UUID.fromString(idStr);
        }catch (IOException e){
            throw new IllegalArgumentException("fail to parse json body", e);
        }
        this.orderId = id;
        if (this.success) {
            this.message = "order processed successfully";
        } else {
            this.message = "unable to process order, please try again.";
        }
    }

    public OrderResponse(boolean success, UUID orderId, String message) {
        this.success = success;
        this.orderId = orderId;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderResponse that = (OrderResponse) o;
        return success == that.success &&
                orderId.equals(that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, orderId);
    }
}
