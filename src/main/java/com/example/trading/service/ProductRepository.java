package com.example.trading.service;
import com.example.trading.entities.Product;
import com.example.trading.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

   Product findById(int idProduct);
   //Product findByUserName(String userName);
   //List<Product> findAllByUserName(String userName);
   List<Product> findAllByIdUserShop(int idUserShop);

    List<Product> findByproductNameLike(String value);
}
