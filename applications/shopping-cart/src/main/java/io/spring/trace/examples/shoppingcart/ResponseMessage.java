package io.spring.trace.examples.shoppingcart;

import java.util.Objects;
import java.util.UUID;

public class ResponseMessage {
    private UUID orderId;
    private String message;

    public ResponseMessage(UUID orderId, String message) {
        this.orderId = orderId;
        this.message = message;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseMessage that = (ResponseMessage) o;
        return orderId.equals(that.orderId) &&
                message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, message);
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "orderId=" + orderId +
                ", message='" + message + '\'' +
                '}';
    }
}
