package io.spring.trace.examples.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
@RepositoryRestResource(collectionResourceRel = "order-list", itemResourceRel = "order-entry", path = "order-db")
public interface OrderRepo extends JpaRepository<OrderEntity, UUID> {
}
