package com.codeburps.api;

import com.codeburps.model.Product;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductControllerImpl implements ProductController {

    // Just for testing
    private String test;

    @Override
    public Product getProduct(int productId) throws Exception {

        // Just for testing start
        if ("timeout".equals(test)) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if ("error".equals(test)) {
            throw new Exception();
        }
        // Just for testing end

        return new Product(productId, "name-" + productId, (Math.random() * (500 - 50)) + 50);
    }

    // Just for testing
    @Override
    public String test(String test) throws Exception {
        return this.test = test;
    }


}
