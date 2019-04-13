package com.rabbitcrud.rabbitcrud.annotation;

import java.lang.annotation.*;

/**
 * 数据表字段注解，用于标注字段
 * @author dxy
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableColumn {

    String value() default "";
}
