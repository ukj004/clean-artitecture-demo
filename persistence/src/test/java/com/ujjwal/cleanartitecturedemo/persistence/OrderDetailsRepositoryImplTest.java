package com.ujjwal.cleanartitecturedemo.persistence;

import com.ujjwal.cleanartitecturedemo.domain.entity.OrderResponse;
import com.ujjwal.cleanartitecturedemo.domain.exception.OrderDetailsInternalServerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import org.springframework.data.mongodb.core.query.Query;
import java.math.BigInteger;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OrderDetailsRepositoryImplTest {
    @Mock
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @InjectMocks
    private OrderDetailsRepositoryImpl orderDetailsRepositoryImpl;



    @Test
    void getOrderDetailsTest() {
        Query query = Query.query(Criteria.where("orderId").is("12346"));
        when(reactiveMongoTemplate.find(query, OrderResponse.class,"orderDetails"))
                .thenReturn(Flux.just(OrderResponse.builder()
                        .orderId("12346")
                        .productName("Apple Watch")
                        .productCompany("Apple")
                        .productPrice(BigInteger.valueOf(30000))
                        .build()));

        StepVerifier.create(orderDetailsRepositoryImpl.getOrderDetailsByOrderId("12346"))
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
        Query query = Query.query(Criteria.where("orderId").is("12346"));
        doThrow(
                new OrderDetailsInternalServerException("Error occurred while retrieving Order Details with order id :: 12346"))
                .when(reactiveMongoTemplate)
                .find(query, OrderResponse.class,"orderDetails");
        Assertions.assertThrows(OrderDetailsInternalServerException.class,
                () -> {
                    orderDetailsRepositoryImpl.getOrderDetailsByOrderId("12346");
                }, "Error occurred while retrieving Order Details with order id :: 12346");
    }
}
