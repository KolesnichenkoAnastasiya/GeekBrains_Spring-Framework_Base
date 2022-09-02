package ru.geekbrains.persist;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init() {
        this.save(new Product("Кронштейн для снаряда BARFITS настенный черный", 1490f));
        this.save(new Product("Кистевые бинты adidas Boxing Crepe Bandage 350 см синий", 819.5f));
        this.save(new Product("Крепление для боксерского мешка настенно-потолочное 2 отверстия", 812f));
        this.save(new Product("Подвес для боксерского мешка Solo Fight 500, вылет 48 см", 1759f));
        this.save(new Product("Чехол боксерского мешка Чемпион 140х36 см, синий", 2805.5f));
    }
    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }
    public Product findById(long id) {
        return productMap.get(id);
    }
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(identity.incrementAndGet());
        }
        productMap.put(product.getId(), product);
        return product;
    }
    public Map<Long, Product> delete(long id) {
        productMap.remove(id);
        return productMap;
    }
}

