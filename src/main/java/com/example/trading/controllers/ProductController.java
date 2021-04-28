package com.example.trading.controllers;

import com.example.trading.config.Config;

import com.example.trading.model.entities.Product;
import com.example.trading.model.entities.UserProfile;
import com.example.trading.model.entities.UserShop;
import com.example.trading.model.service.*;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {
    APIResponse res = new APIResponse();
    UserShop userShop = new UserShop();

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserShopRepository userShopRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private NotificationService notificationService;
    
    @GetMapping("/list")   
    public Object list() {
        List<Product> products = productRepository.findAll();
        Collections.reverse(products);
        return products;
    }

    @PostMapping("/search")
    public Object search(@RequestParam String value){
        ArrayList shopArrList = new ArrayList();
        ArrayList arrShop = new ArrayList();

        List dataShop = userShopRepository.findByshopNameLike("%"+value+"%");
        List dataProduct = productRepository.findByproductNameLike("%"+value+"%");

        System.out.println(dataShop);
        System.out.println(dataProduct);

        for(int i=0; i<dataShop.size();i++){
            UserShop userShopLst = (UserShop) dataShop.get(i);
            arrShop.add(userShopLst.getIdUserShop());
        }

        for(int i=0; i<dataProduct.size();i++){
            Product productLst = (Product) dataProduct.get(i);
            arrShop.add(productLst.getIdUserShop());
        }
        Set set = new HashSet(arrShop);  //ลด idUserShop ที่ซ้ำกัน
        arrShop.clear();
        arrShop.addAll(set);

        for (int i=0; i<arrShop.size(); i++){
            UserShop userShop1 = userShopRepository.findById((Integer) arrShop.get(i)).get();
            shopArrList.add(userShop1);
        }
        System.out.println(shopArrList);
        return shopArrList;
    }

    @GetMapping("/detail/{idProduct}")
    public Object detail(@PathVariable int  idProduct){
        return productRepository.findById(idProduct);
    }
    
    @GetMapping("/findbyiduser")
    public Object listId(@RequestParam int userId) {
        List<Product> product = productRepository.findAllByIdUserShop(userId);
        return product;
    }

    @PostMapping("/save")
    public Object save(Product product, @RequestParam(value = "fileImg", required = false) MultipartFile fileImg){
        APIResponse res = new APIResponse();
        Random rnd = new Random();
        try {
//            if (fileImg != null){
//                //int count = (int) productRepository.count();
//                char a = (char) (rnd.nextInt(26) + 'a');
//                char b = (char) (rnd.nextInt(26) + 'a');
//
//                //.setIdProduct(count + 1);
//                product.setProductImg(product.getIdProduct() + String.valueOf(a) + String.valueOf(b) + ".png");
//                File fileToSave = new File( Config.IMG_PRODUCT_URL + product.getIdProduct()+ a + b + ".png");
//                fileImg.transferTo(fileToSave);
//
//                product = productRepository.save(product);
//                res.setData(product);
//                res.setStatus(0);
//                res.setMsg("Save Success...");
//                return res;
//            }
            //TODO เรียก Notification
            UserProfile userProfileDb = userProfileRepository.findById(product.getIdUserShop()).get();
            List<UserProfile> allUser = userProfileRepository.findAll();
            for (int i = 0; i < allUser.size(); i++) {
              double distance =  calculateDistance(
                      Double.parseDouble(userProfileDb.getUserLat()),
                      Double.parseDouble(userProfileDb.getUserLng()),
                      Double.parseDouble(allUser.get(i).getUserLat()),
                      Double.parseDouble(allUser.get(i).getUserLng())
              );
              if(distance <= 20.0){
                  System.out.println(Double.toString(distance)  + " KM");
                  notificationService.sendToDevice(allUser.get(i).getFcmToken(),"มีสินค้าใหม่บริเวณใกล้เคียง",product.getProductName()+" ถูกเพิ่มในระบบ");

              }

            }
        }catch (Exception err){
            res.setStatus(1);
            res.setMsg("err : " + err.toString());
            return res;
        }
        return res;
    }

    @GetMapping("/thread")
    public Object thread(){
        Thread thread = new Thread(new Multi());
        System.out.println("Calling thread...");
        thread.start();
        System.out.println("Called thread...");       
        return res;
    }

    @PostMapping("/update")
    public  Object update(Product product, @RequestParam(value = "fileImg", required = false) MultipartFile fileImg ){
        Random rnd = new Random();
        try {
            if(fileImg != null){
                Product productDb = productRepository.findById(product.getIdProduct());

                //TODO ลบรูปภาพเดิมก่อน
                File fileToDelete = new File(Config.IMG_PRODUCT_URL + productDb.getProductImg());

                if(fileToDelete.delete())
                {
                    System.out.println("File deleted successfully");
                }
                else
                {
                    System.out.println("Failed to delete the file");
                }

                //TODO Save Img, ImgName
                char a = (char) (rnd.nextInt(26) + 'a');
                char b = (char) (rnd.nextInt(26) + 'a');
                File fileToSave = new File(Config.IMG_PRODUCT_URL + product.getIdProduct() + a + b + ".png");
                fileImg.transferTo(fileToSave);

                product.setProductImg(product.getIdProduct() + String.valueOf(a) + String.valueOf(b) + ".png");
                product = productRepository.save(product);

                res.setData(product);
                res.setMsg("Update Success");
                res.setStatus(0);
            }else{
                System.out.println(product);
                Product productDb = productRepository.findById(product.getIdProduct());
                product.setProductImg(productDb.getProductImg());
                product = productRepository.save(product);

            }
        }catch (Exception err){
            err.printStackTrace();
            res.setMsg("Update Failed");
            res.setStatus(1);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getResource(@RequestParam String imageName) throws  Exception{
        try {
            InputStream in = new FileInputStream(Config.IMG_PRODUCT_URL + imageName);
            var inImg =  IOUtils.toByteArray(in);
            in.close();
            return inImg;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/remove")
    public Object remove(@RequestParam int productId){
        System.out.println(productId);
        Product product = productRepository.findById(productId);
        System.out.println(product);
        try{

            File fileToDelete = new File(Config.IMG_PRODUCT_URL + product.getProductImg());
            Files.delete(Path.of(String.valueOf(fileToDelete)));
            productRepository.deleteById(productId);
            res.setStatus(0);
            res.setMsg("Remove product success!");

        }catch (Exception e){
            e.printStackTrace();
            res.setStatus(1);
        }

        return res;
    }
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2){
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return (dist);
    }
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
