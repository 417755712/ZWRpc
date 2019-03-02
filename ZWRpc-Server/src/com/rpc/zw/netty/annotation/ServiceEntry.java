package com.rpc.zw.netty.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * service路由入口
 * 
 * @author ZZ
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServiceEntry {
	/**
	 * 服务Id
	 * 
	 * @return 服务Id
	 */
	short serviceId();

	/**
	 * 服务版本号,默认0
	 * 
	 * @return
	 */
	byte version() default (byte) 0;

	/**
	 * 服务名称
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * 序列化方式,默认json
	 * 
	 * @return
	 */
	byte serializationOption() default 0x00;
}
