package fr.eservices.promos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.eservices.promos.model.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>
{
    List<Order> findByCustomerIdOrderByCreatedOnDesc(String customerId);
}