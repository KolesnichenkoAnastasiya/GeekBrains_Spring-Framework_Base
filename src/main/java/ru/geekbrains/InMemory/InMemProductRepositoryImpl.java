package ru.geekbrains.InMemory;


import org.springframework.stereotype.Repository;
import ru.geekbrains.persist.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//@Repository("persistentProductRepository")
//public interface ProductRepository extends JpaRepository<Product,Long> {
@Repository
public class InMemProductRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;
    public List<Product> findAll() {
        return entityManager.createQuery("select  p  from  Product p order by p.id asc ", Product.class).getResultList();
    }
    public Optional<Product> findById(long id) {
        return Optional.ofNullable(entityManager.find(Product.class, id));
    }
    @Transactional(Transactional.TxType.REQUIRED)
    public Product save (Product product) {
        if (product.getId()!=null) {
            entityManager.merge(product);
        } else {
            entityManager.persist(product);
        }
        return product;
    }
    @Transactional(Transactional.TxType.REQUIRED)
     public String deleteById (long id) {
        entityManager.createQuery("delete from Product p where p.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        return "redirect: product";
        }
}
