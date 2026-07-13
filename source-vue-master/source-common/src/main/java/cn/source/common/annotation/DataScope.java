package cn.source.common.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 *
 * @author ruoyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope
{
    /**
     * 部门表的别名
     */
    public String deptAlias() default "";

    /**
     * 部门id字段名
     */
    public String deptField() default "dept_id";

    /**
     * 用户表的别名
     */
    public String userAlias() default "";

    /**
     * 用户id字段名
     */
    public String userField() default "user_id";
}
