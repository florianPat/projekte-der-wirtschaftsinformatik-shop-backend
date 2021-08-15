package fhdw.pdw.model.dto;

import fhdw.pdw.model.OrderStatus;

public class PatchOrderStatusDto {
    protected OrderStatus status;

    public PatchOrderStatusDto() {
    }

    public PatchOrderStatusDto(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
