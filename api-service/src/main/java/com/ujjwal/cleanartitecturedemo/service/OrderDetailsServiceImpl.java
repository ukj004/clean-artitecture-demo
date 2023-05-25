package com.ujjwal.cleanartitecturedemo.service;

import com.ujjwal.cleanartitecturedemo.common.OrderDetailsConstants;
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
                                        new OrderDetailsInternalServerException(OrderDetailsConstants.ORDER_DETAILS_INTERNAL_SERVER_ERROR_MSG)))
                .switchIfEmpty(
                        Mono.error(
                                new OrderNotFoundException(OrderDetailsConstants.ORDER_DETAILS_DATA_NOT_FOUND_ERROR_MSG + orderId)));
    }
}
