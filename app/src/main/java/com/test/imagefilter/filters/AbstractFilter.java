package com.test.imagefilter.filters;

/**
 * Base image filter class with common functionality
 */
public abstract class AbstractFilter implements Filter {

    @Override
    public String filterCodeName() {
        return this.getClass().getSimpleName();
    }
}
