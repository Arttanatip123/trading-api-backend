package com.example.trading.controllers;
import com.example.trading.config.Config;

import com.example.trading.model.entities.UserShop;
import com.example.trading.model.service.APIResponse;
import com.example.trading.model.service.UserShopRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;

@RestController
@RequestMapping("/shop")
public class UserShopController {
    @Autowired
    private UserShopRepository userShopRepository;
    APIResponse res = new APIResponse();
     UserShop userShop = new UserShop();

    @GetMapping("/list")
    public Object list(){
        return userShopRepository.findAll();

    }

    @PostMapping("/detail")
    public Object detail(@RequestParam int idUserShop){
        UserShop userShopDb = new UserShop();
        try {
            userShopDb = userShopRepository.findById(idUserShop).get();
            userShop = userShopDb;
        }catch (Exception e){
            res.setStatus(1);
            userShop = null;
        }
        return userShop;
    }

    @PostMapping("/status")
    public Object shopStatus(@RequestParam int idUserShop, @RequestParam String shopStatus){
        UserShop userShopDb = userShopRepository.findById(idUserShop).get();
        if(userShopDb.getShopName() != null){
            userShopDb.setShopStatus(shopStatus);
            userShop = userShopRepository.save(userShopDb);
            res.setStatus(0);
        }else {
            res.setStatus(1);
        }
        return res;
    }



    @PostMapping("/check")
    public Object checkShop(@RequestParam int idUserShop){
        UserShop userShopDb = userShopRepository.findById(idUserShop).get();
        System.out.println(userShopDb);

        return 0;
    }

    @PostMapping("/register")
    public Object register(UserShop userShop){
        userShop = userShopRepository.save(userShop);
        System.out.println(userShop);
        res.setData(userShop);
        res.setStatus(0);
        return res;
    }

    @PostMapping("/save")
    public Object save(UserShop userShop, @RequestParam(value = "fileImg", required = false) MultipartFile fileImg){
        System.out.println(userShop);
        APIResponse res = new  APIResponse();
        Random rnd = new Random();
        try{
            if (fileImg != null){
                char a = (char) (rnd.nextInt(26) + 'a');
                File fileToSave = new File(Config.IMG_SHOP_URL + userShop.getIdUserShop() + String.valueOf(a) + ".png");
                fileImg.transferTo(fileToSave);
                userShop.setShopImg(userShop.getIdUserShop()+ String.valueOf(a) + ".png");
                userShop = userShopRepository.save(userShop);
                res.setData(userShop);
                res.setStatus(0);
            } else {
                userShop = userShopRepository.save(userShop);
                res.setData(userShop);
                res.setStatus(0);
            }
        }catch (Exception err){
            res.setStatus(1);
            res.setMsg("err : " + err.toString());
        }

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getResource(@RequestParam String imageName) throws  Exception{
        try {
            InputStream inputStream = new FileInputStream(Config.IMG_SHOP_URL + imageName);
            var inImg =  IOUtils.toByteArray(inputStream);
            inputStream.close();
            return inImg;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
