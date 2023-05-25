package com.ujjwal.cleanartitecturedemo.api;


import com.ujjwal.cleanartitecturedemo.common.OrderDetailsConstants;
import com.ujjwal.cleanartitecturedemo.domain.entity.OrderResponse;
import com.ujjwal.cleanartitecturedemo.domain.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping(OrderDetailsConstants.ORDER_DETAILS_BY_ORDER_ID)
    @ResponseStatus(HttpStatus.OK)
    public Flux<OrderResponse> getOrderDetails(@PathVariable("order-id") String orderId) {
        return orderDetailsService.getOrderDetails(orderId);
        }
}
