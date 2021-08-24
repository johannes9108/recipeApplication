package com.iths.jh.RecipeApplication.domain.dto;

import com.iths.jh.RecipeApplication.domain.Recipe;
import com.iths.jh.RecipeApplication.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class DTOUtilities {


    public static <E,D> E convertToEntity(D dto, Class<E> eClass, ModelMapper modelMapper){
        return modelMapper.map(dto, eClass);
    }

    public static <D,E> D convertToDto(E entity , Class<D> dClass, ModelMapper modelMapper) {
        return modelMapper.map(entity, dClass);
    }

}
