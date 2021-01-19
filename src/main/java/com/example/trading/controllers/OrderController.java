package com.example.trading.controllers;

import com.example.trading.entities.Order;
import com.example.trading.entities.Product;
import com.example.trading.entities.UserProfile;
import com.example.trading.entities.UserShop;
import com.example.trading.service.APIResponse;
import com.example.trading.service.OrderRepository;
import com.example.trading.service.UserProfileRepository;
import com.example.trading.service.UserShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/order")
public class OrderController {
    APIResponse res = new APIResponse();
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserShopRepository userShopRepository;

    @GetMapping("/test")
    public Object test(){
        res.setStatus(0);
        return res;
    }

    @PostMapping("/makeorder")
    public Object makeOrder(@RequestParam int userId, int shopId, String timeReceive, int totalPrice, String product)  {
        Order order = new Order();
        UserProfile userProfile = userProfileRepository.findById(userId).get();
        UserShop userShop = userShopRepository.findById(shopId).get();

        order.setIdUserProfile(userId);
        order.setUserName(userProfile.getUserName());
        order.setIdUserShop(shopId);
        order.setShopName(userShop.getShopName());
        order.setTimeReceive(timeReceive);
        order.setTotalPrice(totalPrice);
        order.setProductList(product);
        order.setOrderStatus(1);
        System.out.println(order.getProductList());
        orderRepository.save(order);
        res.setStatus(0);

//        String str = product;
//        JSONArray array = new JSONArray(str);
//        for (int i = 0; i < array.length(); i++){
//            JSONObject object = array.getJSONObject(i);
//            System.out.println(object.getString("idProduct"));
//            System.out.println(object.getString("numberOfItem"));
//        }
        return res;
    }

    @PostMapping("/user")
    public Object getOrderUser(@RequestParam int userId){
        List<Order> orders = orderRepository.findAllByIdUserProfile(userId);
        return orders;
    }
    @PostMapping("/productbyid")
    public Object orderDetail(@RequestParam int idOrderList){
        Order order = orderRepository.findById(idOrderList).get();

        return order;
    }

    @PostMapping("/shop")
    public Object getOrderShop(@RequestParam int shopId){
        List<Order> orders = orderRepository.findAllByIdUserShop(shopId);
        return orders;
    }

    @PostMapping("/update_status")
    public Object updateStatus(@RequestParam int idOrderList, @RequestParam int status){
        Order order = orderRepository.findById(idOrderList).get();
        order.setOrderStatus(status);
        orderRepository.save(order);
        res.setStatus(0);
        return res;
    }
}
