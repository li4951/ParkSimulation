package edu.xidian.liyan.park.car;

import java.util.Calendar;

/**
 * ����������
 * @author ����
 */

public abstract class Car {

	//ÿ�����϶������гֿ�Ա��
	private Employee employee;
	private int length;
	private int speed;
	//ͣ��ʱ��
	private int parkingTime;
	//���뵱ǰ״̬����ǰ״̬������������ʱ��
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
	 * ��һ������
	 * @return ������һʱ�̵ĳ����󣨲�ͬ��״̬�������ܵ�ǰ�������������µĳ��������� 
	 */
	public abstract Car nextStep();
}
