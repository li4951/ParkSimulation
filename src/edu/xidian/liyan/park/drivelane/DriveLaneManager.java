package edu.xidian.liyan.park.drivelane;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.car.*;

/**
 * 车道管理类
 * @author 李延
 */
public class DriveLaneManager {
	private Logger logger = Logger.getLogger(DriveLaneManager.class);
	//车道长度
	private int length;
	//车道状态数组，存储车道上面的车对象，若没有则为空
	private Car[] positionState;
	
	public DriveLaneManager(int length) {
		this.length = length;
		positionState = new Car[length];
	}
	
	public int getLength() {
		return length;
	}

	/**
	 * 获取车道位置x上的车对象
	 * @param x 待查询的车道位置
	 * @return 返回该车对象
	 */
	public Car getState(int x){
		return positionState[x];
	}
	
	/**
	 * 重置车道位置x为参数car
	 * @param x 待重置的车道位置
	 * @param car 将占用x位置的车对象
	 */
	public void setState(int x, Car car){
		if(positionState != null){
			positionState[x] = car;
		}else{
			logger.error("setState wrong");
		}
	}
	
}
