package com.rabbitcrud.rabbitcrud.annotation;

import java.lang.annotation.*;

/**
 * 数据库表注解，用于标注对应的表
 * @author dxy
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {

    public String value() default "";
}
