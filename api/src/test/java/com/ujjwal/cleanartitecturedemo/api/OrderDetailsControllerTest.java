package com.ujjwal.cleanartitecturedemo.api;

import com.ujjwal.cleanartitecturedemo.domain.entity.OrderResponse;
import com.ujjwal.cleanartitecturedemo.domain.service.OrderDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class OrderDetailsControllerTest {

    @InjectMocks
    private OrderDetailsController orderDetailsController;

    @Mock
    private OrderDetailsService orderDetailsService;

    /**
     * This is to test the controller class as it is giving the desired response or not.
     * so, this test will fail as we have not defined the service method.
     * Now, we will refactor the code to pass this test by adding service method returning normal object.
     */
    @Test
    void checkOrderDetails_Controller_CorrectResponse() {
        when(orderDetailsService.getOrderDetails(anyString()))
                .thenReturn(Flux.just(OrderResponse.builder()
                        .orderId("12346")
                        .productName("Apple Watch")
                        .productCompany("Apple")
                        .productPrice(BigInteger.valueOf(30000))
                        .build()));
        StepVerifier.create(orderDetailsController.getOrderDetails(anyString()))
                .expectNextMatches(response -> response instanceof OrderResponse
                        && "12346".equals(response.getOrderId())
                        && "Apple Watch".equals(response.getProductName())
                        && "Apple".equals(response.getProductCompany())
                        && BigInteger.valueOf(30000).equals(response.getProductPrice()))
                .expectComplete()
                .verify();
    }

}
