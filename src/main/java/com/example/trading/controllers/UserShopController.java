package com.example.trading.controllers;
import com.example.trading.entities.UserShop;
import com.example.trading.service.APIResponse;
import com.example.trading.service.UserShopRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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

    @GetMapping("/detail/{idShop}")
    public Object detail(@PathVariable int idShop){

        return userShopRepository.findById(idShop).get();
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
        try{
            if (fileImg != null){
                File fileToSave = new File("C://img//shop//" + userShop.getIdUserShop() + ".png");
                fileImg.transferTo(fileToSave);
                userShop.setShopImg(userShop.getIdUserShop() + ".png");
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
            InputStream in = new FileInputStream("C://img//shop//" + imageName);
            return IOUtils.toByteArray(in);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
