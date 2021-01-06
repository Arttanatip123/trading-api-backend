package com.example.trading.controllers;

import com.example.trading.entities.Product;
import com.example.trading.entities.UserShop;
import com.example.trading.service.APIResponse;
import com.example.trading.service.ProductRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/product")
public class ProductController {
    APIResponse res = new APIResponse();
    UserShop userShop = new UserShop();

    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/list")   
    public Object list() {

        return productRepository.findAll();
    }

    @GetMapping("/detail/{idProduct}")
    public Object detail(@PathVariable int  idProduct){

        return productRepository.findById(idProduct);
    }
    
    @GetMapping("/findbyiduser")
    public Object listId(@RequestParam int userId) {
        List<Product> product = productRepository.findAllByIdUserShop(userId);
        //System.out.println(product);
        return product;
    }

    @PostMapping("/save")
    public Object save(Product product, @RequestParam(value = "fileImg", required = false) MultipartFile fileImg){
        APIResponse res = new APIResponse();
        Random rnd = new Random();
        try {
            if (fileImg != null){
                int count = (int) productRepository.count();
                char a = (char) (rnd.nextInt(26) + 'a');
                char b = (char) (rnd.nextInt(26) + 'a');

                product.setIdProduct(count + 1);
                product.setProductImg(count + 1 + String.valueOf(a) + String.valueOf(b) + ".png");

                File fileToSave = new File("C://img//product//" + product.getIdProduct()+ a + b + ".png");
                fileImg.transferTo(fileToSave);

                product = productRepository.save(product);
                res.setData(product);
                res.setStatus(0);
                res.setMsg("Save Success...");
            }
        }catch (Exception err){
            res.setStatus(1);
            res.setMsg("err : " + err.toString());
        }
        return res;
    }

    @PostMapping("/update")
    public  Object update(Product product, @RequestParam(value = "fileImg", required = false) MultipartFile fileImg ){
        Random rnd = new Random();
        try {
            if(fileImg != null){
                Product productDb = productRepository.findById(product.getIdProduct());

                //TODO Delete Old Img
                File fileToDelete = new File("C://img//product//" + productDb.getProductImg());

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
                File fileToSave = new File("C://img//product//" + product.getIdProduct() + a + b + ".png");
                fileImg.transferTo(fileToSave);
                product.setProductImg(product.getIdProduct() + String.valueOf(a) + String.valueOf(b) + ".png");
                product = productRepository.save(product);

                res.setData(product);
                res.setMsg("Update Success");
                res.setStatus(0);
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
            InputStream in = new FileInputStream("C://img//product//" + imageName);
            return IOUtils.toByteArray(in);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/remove")
    public Object remove(@RequestParam int productId){
        productRepository.deleteById(productId);
        res.setStatus(0);
        res.setMsg("Remove product success!");
        return res;
    }


}
