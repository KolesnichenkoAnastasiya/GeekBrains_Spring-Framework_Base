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
             where (:titleFilter is null  or p.title like :titleFilter)
             and (:costFilter is null or p.cost like :costFilter)
             """,
             countQuery = """
             select count(*) from Product p
             where (:titleFilter is null or p.title like :titleFilter)
             and (:costFilter is null or p.cost like :costFilter)
            """,
            nativeQuery = true)

    List<Product> productByFilter(String titleFilter, String costFilter);

}
