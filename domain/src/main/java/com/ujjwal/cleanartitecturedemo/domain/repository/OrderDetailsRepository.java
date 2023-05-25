package com.ujjwal.cleanartitecturedemo.domain.repository;


import com.ujjwal.cleanartitecturedemo.domain.entity.OrderResponse;
import reactor.core.publisher.Flux;

public interface OrderDetailsRepository {
    public Flux<OrderResponse> getOrderDetailsByOrderId(final String orderId);
}
