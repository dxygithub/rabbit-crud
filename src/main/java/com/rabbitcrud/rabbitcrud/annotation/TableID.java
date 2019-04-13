package com.rabbitcrud.rabbitcrud.annotation;

import java.lang.annotation.*;

/**
 * 数据表主键，用于标注主键类型
 * @author dxy
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableID {

    String value() default "";
}
