package com.ujjwal.cleanartitecturedemo.service;


import com.ujjwal.cleanartitecturedemo.domain.entity.OrderResponse;
import com.ujjwal.cleanartitecturedemo.domain.exception.OrderDetailsInternalServerException;
import com.ujjwal.cleanartitecturedemo.domain.exception.OrderNotFoundException;
import com.ujjwal.cleanartitecturedemo.domain.repository.OrderDetailsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OrderDetailsServiceImplTest {
    @Mock
    private OrderDetailsRepository orderDetailsRepository;

    @InjectMocks
    private OrderDetailsServiceImpl orderDetailsServiceImpl;



    @Test
    void getOrderDetailsTest() {

        when(orderDetailsRepository.getOrderDetailsByOrderId(anyString()))
                .thenReturn(Flux.just(OrderResponse.builder()
                        .orderId("12346")
                        .productName("Apple Watch")
                        .productCompany("Apple")
                        .productPrice(BigInteger.valueOf(30000))
                        .build()));
        StepVerifier.create(orderDetailsServiceImpl.getOrderDetails(anyString()))
                .expectNextMatches(response -> response instanceof OrderResponse
                        && "12346".equals(response.getOrderId())
                        && "Apple Watch".equals(response.getProductName())
                        && "Apple".equals(response.getProductCompany())
                        && BigInteger.valueOf(30000).equals(response.getProductPrice()) )
                .expectComplete()
                .verify();
    }

    @Test
    void getOrderDetails_InternalServerExceptionTest() {
        doThrow(
                new OrderDetailsInternalServerException("Internal server exception while retrieving order details"))
                .when(orderDetailsRepository)
                .getOrderDetailsByOrderId(anyString());
        Assertions.assertThrows(OrderDetailsInternalServerException.class,
                () -> {
                    orderDetailsServiceImpl.getOrderDetails(anyString());
                }, "Internal server exception while retrieving order details");
    }


 @Test
    void getOrderDetails_DataNotFoundExceptionTest() {
     when(orderDetailsRepository.getOrderDetailsByOrderId(anyString()))
             .thenReturn(Flux.empty());
     StepVerifier.create(orderDetailsServiceImpl.getOrderDetails(anyString()))
             .expectNextCount(0)
             .expectErrorMatches(throwable -> throwable instanceof OrderNotFoundException
                     && ((OrderNotFoundException) throwable).exceptionMessage.equals("Order Details Not Found")
             )
             .verify();
    }

}
