package com.codeburps.client.product;

import com.codeburps.client.product.model.Product;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ProductClientImpl implements ProductClient {

    @Value("${api.catalog.baseurl}")
    private String apiBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    // change to LRU in prod
    private Map<Integer, Product> cache = new HashMap<>();

    @Override
    @CircuitBreaker(name = "product", fallbackMethod = "getProductFallback")
    public Product getProduct(int productId) {
        var product = restTemplate.getForObject(apiBaseUrl + "/product/" + productId, Product.class);
        cache.put(productId, product);
        return product;
    }

    public Product getProductFallback(int productId, CallNotPermittedException exception) {
        log.error("Unable to get product: " + exception.getMessage());
        return cache.get(productId);
    }
}
