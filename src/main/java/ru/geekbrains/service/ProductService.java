package ru.geekbrains.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.model.dto.ProductDto;
import ru.geekbrains.model.mapper.ProductDtoMapper;
import ru.geekbrains.repository.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDtoMapper mapper;
    private  final ProductRepository productRepository;
    public Page<ProductDto> findAllByFilter(String titleFilter, String costFilter, int page, int size, String sortField){
       titleFilter=titleFilter==null|| titleFilter.isBlank() ? null : "%" + titleFilter.trim() + "%";
       costFilter=costFilter==null|| costFilter.isBlank() ? null : "%" + costFilter.trim() + "%";

       return productRepository.productByFilter(titleFilter, costFilter, PageRequest.of(page,size, Sort.by(sortField)))
                .map(mapper::map);
    }

    public Optional<ProductDto> findProductById(Long id) {
        return productRepository.findById(id).map(mapper::map);
    }

//    public void saveOrUpdate(ProductDto dto) {
//        if(!productRepository.findById(dto.getId()).isPresent()){
//            productToDto(productRepository.save(new Product(dto.getTitle(), dto.getCost())));
//        } else {
//            Optional <Product> prod = productRepository.findById(dto.getId());
//            if(prod.isPresent()){
//                Product product = prod.get();
//                product.setTitle(dto.getTitle());
//                product.setCost(dto.getCost());
//                productToDto(product);
//            }}
//    }

    public void save(ProductDto dto) {
    productRepository.save(mapper.map(dto));
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
