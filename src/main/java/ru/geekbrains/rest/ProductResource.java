package ru.geekbrains.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.model.dto.ProductDto;
import ru.geekbrains.service.ProductService;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor

public class ProductResource {
    private final ProductService service;

    @GetMapping
    public Page<ProductDto> listPage(
            @RequestParam(required = false) String titleFilter,
            @RequestParam(required = false) String costFilter,
            @RequestParam(required = false) Optional<Integer> page,
            @RequestParam(required = false) Optional<Integer> size,
            @RequestParam(required = false) Optional<String> sortField
            ) {
        Integer pageValue = page.orElse(1) - 1;
        Integer sizeValue = size.orElse(10);
        String sortFieldValue=sortField.filter(s-> !s.isBlank()).orElse("id");
        Page<ProductDto> allByFilter = service.findAllByFilter(titleFilter, costFilter, pageValue, sizeValue, sortFieldValue);
        return allByFilter;
    }

    @GetMapping("/{id}")
    public ProductDto form(@PathVariable("id") long id, Model model) {
        ProductDto productDto = service.findProductById(id)
                .orElseThrow(()->new EntityNotFoundException("Product not found"));
        return productDto;
    }

    @PostMapping
    public ProductDto saveProduct(@RequestBody ProductDto productDto){
        if (productDto.getId()!=null||productDto.getCost()<=0) {
            throw new IllegalArgumentException("Created product shouldn't have id or cost <=0");
        }
        service.save(productDto);
        return productDto;
    }

    @DeleteMapping("{id}")
    public void deleteProductById (@PathVariable long id) {
         try {
             service.deleteProductById(id);
         } catch (EntityNotFoundException e) {
             throw new EntityNotFoundException("Fail deleting a product with the specified id was not found");
         }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundExceptionHandler(Model model, EntityNotFoundException e) {
        model.addAttribute("message", e.getMessage());
        return "not_found";
    }

}