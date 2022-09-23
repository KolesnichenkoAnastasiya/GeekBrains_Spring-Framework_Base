package ru.geekbrains.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import ru.geekbrains.model.Product;
import ru.geekbrains.model.dto.ProductDto;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductDtoMapper {

    @Mapping(target = "id", ignore = true)
    Product map(ProductDto dto);

    @Mapping(target = "id")
    ProductDto map(Product product);
}
