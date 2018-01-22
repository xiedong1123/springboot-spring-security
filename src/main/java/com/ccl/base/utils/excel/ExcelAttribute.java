package com.ccl.base.utils.excel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * @ClassName：ExcelAttribute
 * @Description：需要excle导出列的放在属性上的注解
 * @Author：zhengjing
 * @Date：2017年5月20日上午10:33:16
 * @version：1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
public @interface ExcelAttribute {
	
	abstract String headName();//导出到Excel中的列头名字.
}
