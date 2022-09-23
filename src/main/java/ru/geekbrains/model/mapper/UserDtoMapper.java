package ru.geekbrains.model.mapper;

import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.geekbrains.model.User;
import ru.geekbrains.model.dto.UserDto;
@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserDtoMapper {
    @Mapping(target = "password", ignore = true)
    UserDto map(User user);

    @Mapping(target = "id", ignore = true)
    User map(UserDto dto);
    @Mapping(source = "password", target = "password", qualifiedByName = "encode")
    User map(UserDto dto, @Context PasswordEncoder encoder);

    @Named("encode")
    default String encode(String password, @Context PasswordEncoder encoder) {
        return encoder.encode(password);
    }

}
