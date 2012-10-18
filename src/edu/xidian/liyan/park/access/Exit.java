package edu.xidian.liyan.park.access;

/**
 * 出口类，继承自关卡类，主要方法已由父类实现。
 * 采用单例模式，构造方法为私有。可通过getInstance返回系统中唯一存在的出口对象。
 * @author 李延
 */
public class Exit extends Access {

	private static Exit exit = null;  
	
	private Exit() {
		super();
	}

	private Exit(int readTime, int liftTime, int downTime) {
		super(readTime, liftTime, downTime);
	}

	public static Exit getInstance(){  
        if(exit == null){  
            exit = new Exit();  
        }  
        return exit;  
    }
}
