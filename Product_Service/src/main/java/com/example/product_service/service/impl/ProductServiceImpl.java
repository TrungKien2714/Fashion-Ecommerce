package com.example.product_service.service.impl;

import com.example.product_service.dto.ProductFilter;
import com.example.product_service.dto.request.CreateProductReq;
import com.example.product_service.dto.request.LockProductItem;
import com.example.product_service.dto.request.LockProductReq;
import com.example.product_service.entity.Product;
import com.example.product_service.mapper.ProductMapper;
import com.example.product_service.repository.CategoryRepository;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final RedissonClient redissonClient;

    @Override
    public Product create(CreateProductReq req) {
        var existedCategory=categoryRepository.findById(req.getCategoryId());
        if(existedCategory.isEmpty()){
            throw new RuntimeException("Category not found");
        }
        Product product = productMapper.fromCreateRequest(req);
        product.setCreatedDate(Instant.now());
        product.setDeleted(false);
        product.setLastModifiedDate(Instant.now());
        return productRepository.save(product);
        }
    @Override
    public List<Product> search(ProductFilter filter) {
        return productRepository.findByIdIn(filter.getIds());
     }

    @Override
    @Transactional
    @CacheEvict(value = "product_search",allEntries = true)
    public void lock(LockProductReq req) {
        List<LockProductItem> items=req.getItems();
        List<String> sortedIds=items.stream().map(LockProductItem::getId).sorted().collect(Collectors.toList());
        String lockKey="lock:products:"+String.join(",",sortedIds);
        RLock lock=redissonClient.getLock(lockKey);
        try {
            if(lock.tryLock(10,5, TimeUnit.SECONDS)){
                log.info("Acquired lock for products: {}", sortedIds);
                var productIdQuantityMap=items.stream().collect(Collectors.toMap(LockProductItem::getId,LockProductItem::getQuantity));
                List<Product> products=productRepository.findByIdIn(new ArrayList<>(productIdQuantityMap.keySet()));
                if(products.isEmpty()){
                    throw new RuntimeException("Product not found");
                }
                products.forEach(product -> {
                    int remainStock=product.getStock()-productIdQuantityMap.get(product.getId());
                    if(remainStock<=0){
                        throw new RuntimeException("Product "+product.getId()+" has no stock");
                    }
                    product.setStock(remainStock);
                });
                productRepository.saveAll(products);
            }else {
                throw new RuntimeException("Server busy , please try again later");
            }
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new RuntimeException("Process interrupted");
    }finally {
            log.info("Waiting for unlock [{}]",lockKey);
            lock.unlock();
            log.info("Unlock success for [{}]",lockKey);
        }
    }
}
