package com.ujjwal.cleanartitecturedemo.persistence;

import com.ujjwal.cleanartitecturedemo.domain.entity.OrderResponse;
import com.ujjwal.cleanartitecturedemo.domain.exception.OrderDetailsInternalServerException;
import com.ujjwal.cleanartitecturedemo.domain.repository.OrderDetailsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@AllArgsConstructor
@Slf4j
@Component
public class OrderDetailsRepositoryImpl implements OrderDetailsRepository {

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Flux<OrderResponse> getOrderDetailsByOrderId(String orderId) {
        log.info("Retrieving Order details from Database with for order Id : {}", orderId);
        Query query = Query.query(Criteria.where("orderId").is(orderId));
        return reactiveMongoTemplate.find(query,OrderResponse.class,"orderDetails")
                .doOnError(ex -> log.error(ex.getMessage()))
                .onErrorResume(
                ex ->
                        Mono.error(
                                new OrderDetailsInternalServerException(
                                        "Error occurred while retrieving Order Details with order id ::"  + orderId)));
    }

}
