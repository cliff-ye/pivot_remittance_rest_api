package com.wellTech.pivotbank.dtoMapper;

import com.wellTech.pivotbank.dto.UserDTO;
import com.wellTech.pivotbank.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(user.getFirstName(),
                            user.getLastName(),
                            user.getOtherName(),
                            user.getGender(),
                            user.getAddress(),
                            user.getCountryOfOrigin(),
                            user.getEmail(),
                            user.getPhoneNumber(),
                            user.getOtherPhoneNumber()
                );
    }
}
