package ru.geekbrains.persist;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findAllByTitleLikeIgnoreCase(String titleFilter);

    @Query (value = """
    select * from Product p
    where p.title like :title
    """, nativeQuery = true)

    List<Product> productByTitle(String title);

}
