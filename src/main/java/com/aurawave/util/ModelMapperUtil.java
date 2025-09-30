package com.aurawave.util;

import org.modelmapper.ModelMapper;

public class ModelMapperUtil {
    private static final ModelMapper modelMapper = new ModelMapper();
    private ModelMapperUtil() {}

    public static <D, E> D convertEntityToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
}
