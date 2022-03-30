package com.shshetudev.api.users.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class Utils {

    public static  <T> T map(Class<?> sourceClass, Class<T> targetClass) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Matching strategy is strict
        return mapper.map(sourceClass, targetClass);
    }
}
