package com.zeromk.study.springBean.lookup;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	
	public static void main(String []args){
		ApplicationContext bf = new ClassPathXmlApplicationContext("com/zeromk/study/springBean/lookup/lookupTest.xml");
		GetBeanTest myTest = (GetBeanTest)bf.getBean( "getBeanTest" );
		myTest.showMe();
	}
	
}
