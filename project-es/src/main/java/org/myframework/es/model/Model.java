package org.myframework.es.model;

import lombok.val;

import java.io.Serializable;

@SuppressWarnings({"UnnecessaryLocalVariable", "unchecked", "unused"})
public abstract class Model<T extends Model<T>> implements Serializable {

    public T self() {
        val $lombok$self = (T) this;
        return $lombok$self;
    }
}
