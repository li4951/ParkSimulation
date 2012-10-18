package edu.xidian.liyan.park.car;

import java.util.Calendar;

/**
 * 车辆抽象类
 * @author 李延
 */

public abstract class Car {

	//每辆车上都会坐有持卡员工
	private Employee employee;
	private int length;
	private int speed;
	//停车时间
	private int parkingTime;
	//进入当前状态（当前状态车对象诞生）的时间
	private Calendar timeStateBegin;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getParkingTime() {
		return parkingTime;
	}
	
	public void setParkingTime(int parkingTime) {
		this.parkingTime = parkingTime;
	}
	
	public Calendar getTimeStateBegin() {
		return timeStateBegin;
	}
	
	public void setTimeStateBegin(Calendar timeStateBegin) {
		this.timeStateBegin = timeStateBegin;
	}
	
	@Override
	public boolean equals(Object obj) {
		Car otherCar = (Car)obj;
		return employee.equals(otherCar.getEmployee());
	}

	@Override
	public int hashCode() {
		return employee.hashCode();
	}
	
	/**
	 * 下一步动作
	 * @return 返回下一时刻的车对象（不同的状态），可能当前车对象消亡，新的车对象诞生。 
	 */
	public abstract Car nextStep();
}
