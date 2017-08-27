package com.cn21.study.springBean.lookup;

public abstract class GetBeanTest {
	public void showMe(){
		this.getBean().showMe();
	}
	//抽象方法，根据配置文件确定该方法的真正子类,是teacher类还是sutdent类
	public abstract User getBean();
}
