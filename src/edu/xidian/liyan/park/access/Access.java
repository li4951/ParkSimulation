package edu.xidian.liyan.park.access;

import java.util.Calendar;
import edu.xidian.liyan.park.car.Car;

/**
 * 关卡类
 * 入口和出口的父类，实现作为一个关卡应有的操作
 * @author 李延
 */
public class Access {
	//使用具名常量作为关卡的state取值
	public final static int CLOSED = 0;
	public final static int OPENED = 1;
	
	//使用枚举类型作为操作类型
	public enum OperateType{
		READ, LIFT, DOWN
	};
	
	//当前关卡所进行的动作
	private OperateType operateType; 
	//关卡状态：OPENED或CLOSED
	private int state;
	//读卡所花时间,可以通过有参构造方法修改
	private int readTime = 1;
	//抬起栏杆所需时间,可以通过有参构造方法修改
	private int liftTime = 2;
	//放下栏杆所需时间,可以通过有参构造方法修改
	private int downTime = 2;
	//当前关卡动作的开始时间
	private Calendar timeOperateBegin;
	//关卡是否空闲
	private boolean used;
	//读卡完毕否
	private boolean readOver;
	//当前车辆是否通过
	private boolean passed;
	
    public Access() {
		super();
		state = CLOSED;
		used = false;
		readOver = false;
	}

	public Access(int readTime, int liftTime, int downTime) {
		this();
		this.readTime = readTime;
		this.liftTime = liftTime;
		this.downTime = downTime;
	}

	public boolean isReadOver() {
		return readOver;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	public OperateType getOperateType() {
		return operateType;
	}

	public void setOperateType(OperateType operateType) {
		this.operateType = operateType;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public boolean isPassed() {
		return passed;
	}
	
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	
	/**
	 * @param car 当前需要经过关卡的车辆对象
	 */
	public String read(Car car){
		if(readOver){//第二次被调用，读取完毕，返回ID
			readOver = false;
			return car.getEmployee().getId();
		}else{
			setOperateType(OperateType.READ);//第一次被调用，发出读取命令
			timeOperateBegin = Calendar.getInstance();//重设当前状态车的起始时间
			
			setUsed(true);//‘标记开始使用’，下面有与之相对应的‘标记使用完毕’。
			return "";
		}
	}
	
	/**
	 * 发出抬起栏杆动作
	 */
	public String lift(){
		setOperateType(OperateType.LIFT);
		timeOperateBegin = Calendar.getInstance();//重设当前状态车的起始时间
		return "";
	}
	
	/**
	 * 发出放下栏杆动作
	 */
	public String down(){
		setOperateType(OperateType.DOWN);
		timeOperateBegin = Calendar.getInstance();//重设当前状态车的起始时间
		return "";
	}
	
	/**
	 * 刷新关卡状态，每秒检查操作是否完成，并更新状态，例如栏杆是否完全降下（时间已到），修改栏杆状态
	 */
	public void refresh(){
		if(timeOperateBegin == null)
			return;
		long passedTime = (System.currentTimeMillis() - timeOperateBegin.getTimeInMillis()) / 1000;
		
		switch (operateType) {
		case READ:
			if(passedTime >= readTime){//如果离发出“读卡”命令已达到readTime,则设置isReadOver为true
				readOver = true;
				timeOperateBegin = null;
			}
			break;
		case LIFT:
			if(passedTime >= liftTime){//如果离发出“抬起栏杆”命令已达到liftTime,则修改状态为OPEN
				state = OPENED;
				timeOperateBegin = null;
			}
			break;
		case DOWN:
			if(passedTime >= downTime){//如果离发出“放下栏杆”命令已达到downTime,则修改状态为CLOSED
				state = CLOSED;
				timeOperateBegin = null;
				setUsed(false);//‘标记使用完毕’。
			}
			break;
		default:
			break;
		}
	}
}
