package com.clpsz.test.sharding;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by clpsz on 2017/3/14.
 */
public class PluginCommon {
    public static final Set<Class<?>> SINGLE_PARAM_CLASSES = Sets.newHashSet();

    public PluginCommon() {
    }

    static {
        SINGLE_PARAM_CLASSES.add(Integer.TYPE);
        SINGLE_PARAM_CLASSES.add(Integer.class);
        SINGLE_PARAM_CLASSES.add(Long.TYPE);
        SINGLE_PARAM_CLASSES.add(Long.class);
        SINGLE_PARAM_CLASSES.add(Short.TYPE);
        SINGLE_PARAM_CLASSES.add(Short.class);
        SINGLE_PARAM_CLASSES.add(Byte.TYPE);
        SINGLE_PARAM_CLASSES.add(Byte.class);
        SINGLE_PARAM_CLASSES.add(Float.TYPE);
        SINGLE_PARAM_CLASSES.add(Float.class);
        SINGLE_PARAM_CLASSES.add(Double.TYPE);
        SINGLE_PARAM_CLASSES.add(Double.class);
        SINGLE_PARAM_CLASSES.add(Boolean.TYPE);
        SINGLE_PARAM_CLASSES.add(Boolean.class);
        SINGLE_PARAM_CLASSES.add(Character.TYPE);
        SINGLE_PARAM_CLASSES.add(Character.class);
        SINGLE_PARAM_CLASSES.add(String.class);
    }
}
