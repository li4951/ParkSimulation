package edu.xidian.liyan.park.drivelane;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.car.*;

/**
 * ����������
 * @author ����
 */
public class DriveLaneManager {
	private Logger logger = Logger.getLogger(DriveLaneManager.class);
	//��������
	private int length;
	//����״̬���飬�洢��������ĳ�������û����Ϊ��
	private Car[] positionState;
	
	public DriveLaneManager(int length) {
		this.length = length;
		positionState = new Car[length];
	}
	
	public int getLength() {
		return length;
	}

	/**
	 * ��ȡ����λ��x�ϵĳ�����
	 * @param x ����ѯ�ĳ���λ��
	 * @return ���ظó�����
	 */
	public Car getState(int x){
		return positionState[x];
	}
	
	/**
	 * ���ó���λ��xΪ����car
	 * @param x �����õĳ���λ��
	 * @param car ��ռ��xλ�õĳ�����
	 */
	public void setState(int x, Car car){
		if(positionState != null){
			positionState[x] = car;
		}else{
			logger.error("setState wrong");
		}
	}
	
}
