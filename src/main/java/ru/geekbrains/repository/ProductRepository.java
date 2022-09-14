package ru.geekbrains.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.geekbrains.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor <Product> {

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

    Page <Product> productByFilter(String titleFilter, String costFilter, Pageable pageable);

}
