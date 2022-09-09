package ru.geekbrains.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/product")

public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping
    public String listPage(
            @RequestParam Optional<String> titleFilter, Model model) {
        if(titleFilter.isEmpty()||titleFilter.get().isBlank()){
        model.addAttribute("products", productRepository.findAll());
        } else {
            model.addAttribute("products",
//                    productRepository.findAllByTitleLikeIgnoreCase("%" + titleFilter.get() + "%"));
            productRepository.productByTitle("%" + titleFilter.get() + "%"));
        }
        return "product";
    }
    @GetMapping("/{id}")
    public String form(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productRepository.findById(id));
        return "product_form";
    }
    /*удаление при нажатии на кнопку*/
    @GetMapping("/del/{id}")
    public String delete(@PathVariable("id") long id){
        EntityManagerFactory entityManagerFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from Product p where p.id=:id").setParameter("id", id).executeUpdate();
        entityManager.getTransaction().commit();
        return "redirect:/product";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("product", new Product());
        return "product_form";
    }
    @PostMapping
    public String save(@Valid Product product, BindingResult binding) {
        if(binding.hasErrors()||product.getTitle().isEmpty()||product.getCost()==0){
            return "product_form";
        }
        productRepository.save(product);
        return "redirect:/product";
    }

}
