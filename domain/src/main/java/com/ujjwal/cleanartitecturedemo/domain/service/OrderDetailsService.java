package com.ujjwal.cleanartitecturedemo.domain.service;

import com.ujjwal.cleanartitecturedemo.domain.entity.OrderResponse;
import reactor.core.publisher.Flux;

public interface OrderDetailsService {

    Flux<OrderResponse> getOrderDetails(String orderId);
}
