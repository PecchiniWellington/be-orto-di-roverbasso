package com.ortoroverbasso.ortorovebasso.utils;

import java.util.Arrays;

import org.springframework.beans.BeanWrapperImpl;

public class BeanUtilsHelper {

    public static String[] getNullPropertyNames(Object source) {

        final BeanWrapperImpl wrapper = new BeanWrapperImpl(source);

        return Arrays.stream(wrapper.getPropertyDescriptors())
                .filter(pd -> wrapper.getPropertyValue(pd.getName()) == null)
                .map(pd -> pd.getName())
                .toArray(String[]::new);
    }
}
