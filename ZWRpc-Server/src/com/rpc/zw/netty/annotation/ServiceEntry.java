package com.rpc.zw.netty.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * service·�����
 * 
 * @author ZZ
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServiceEntry {
	/**
	 * ����Id
	 * 
	 * @return ����Id
	 */
	short serviceId();

	/**
	 * ����汾��,Ĭ��0
	 * 
	 * @return
	 */
	byte version() default (byte) 0;

	/**
	 * ��������
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * ���л���ʽ,Ĭ��json
	 * 
	 * @return
	 */
	byte serializationOption() default 0x00;
}
