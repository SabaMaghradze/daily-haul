package com.app.daily_haul.repository;

import com.app.daily_haul.dto.CartResponse;
import com.app.daily_haul.model.CartItem;
import com.app.daily_haul.model.Product;
import com.app.daily_haul.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);

    void deleteByUser(User user);
}
