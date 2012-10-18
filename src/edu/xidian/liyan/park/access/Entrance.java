package edu.xidian.liyan.park.access;

/**
 * 入口类，继承自关卡类，主要方法已由父类实现。
 * 采用单例模式，构造方法为私有。可通过getInstance返回入口系统中唯一存在的入口对象。
 * @author 李延
 */
public class Entrance extends Access{

	private static Entrance entrance = null;
	
	//入口的提示信息，一般情况下为“欢迎光临”，车场满的情况下是“车场已满”
	private String message; 
	
    private Entrance() {
		super();
		this.message = "欢迎光临";
	}

	private Entrance(int readTime, int liftTime, int downTime) {
		super(readTime, liftTime, downTime);
		this.message = "欢迎光临";
	}

	/**
	 * @return 返回系统中的入口对象
	 */
	public static Entrance getInstance(){  
        if(entrance == null){  
            entrance = new Entrance();  
        }  
        return entrance;  
    }
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
