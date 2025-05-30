package org.myframework.extra.dict;

public interface EnumDict<T> {

    String getLabel();

    T getValue();

    Integer getSort();

    Integer getIsDefault();

    Integer getStyle();
}
