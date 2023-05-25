package com.ujjwal.cleanartitecturedemo.service;

import com.ujjwal.cleanartitecturedemo.domain.entity.OrderResponse;
import com.ujjwal.cleanartitecturedemo.domain.exception.OrderDetailsInternalServerException;
import com.ujjwal.cleanartitecturedemo.domain.exception.OrderNotFoundException;
import com.ujjwal.cleanartitecturedemo.domain.repository.OrderDetailsRepository;
import com.ujjwal.cleanartitecturedemo.domain.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Override
    public Flux<OrderResponse> getOrderDetails(String orderId) {

        return orderDetailsRepository.getOrderDetailsByOrderId(orderId)
                .onErrorResume(
                        ex -> Mono.error(
                                        new OrderDetailsInternalServerException("Internal server exception while retrieving order details")))
                .switchIfEmpty(
                        Mono.error(
                                new OrderNotFoundException("Order Details Not Found")));
    }
}
