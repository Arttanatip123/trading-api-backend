package com.example.trading.controllers;


import com.example.trading.model.entities.Order;
import com.example.trading.model.entities.UserProfile;
import com.example.trading.model.entities.UserShop;
import com.example.trading.model.service.APIResponse;
import com.example.trading.model.service.OrderRepository;

import com.example.trading.model.service.UserProfileRepository;
import com.example.trading.model.service.UserShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
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
        //TODO ดึงข้อมูล ผู้สั่ง
        UserProfile userProfile = userProfileRepository.findById(userId).get();
        //TODO ดึงข้อมูลร้านปลายทาง
        UserShop userShop = userShopRepository.findById(shopId).get();

        order.setIdUserProfile(userId);
        order.setUserName(userProfile.getUserName());
        order.setIdUserShop(shopId);
        order.setShopName(userShop.getShopName());
        //TODO หากไม่มีร้านค้านำ idShop ไปค้น userName มาแทน shopName
        if(order.getShopName() == null){
            UserProfile userProfile1 = userProfileRepository.findById(shopId).get();
            order.setShopName(userProfile1.getUserName());
        }
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
        Collections.reverse(orders);
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
        Collections.reverse(orders);
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
