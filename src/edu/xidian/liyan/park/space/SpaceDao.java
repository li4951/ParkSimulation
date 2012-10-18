package edu.xidian.liyan.park.space;

import edu.xidian.liyan.park.car.*;

/**
 * 车位数据存取接口
 * 由子类SpaceDaoMemory（内存中存储）或SpaceDaoDb（数据库存储）来实现，本系统暂时只实现前者
 * @author 李延
 */
public interface SpaceDao {
	
	/**
	 * @return 获得车位容量
	 */
	public int getCapacity();
	
	/**
	 * @return 获得当前已经占用的车位数
	 */
	public int getSize();
	
	/**
	 * @param position
	 * @return 返回position车位的车对象，空表示无车占用
	 */
	public Car get(int position);
	
	/**
	 * @param car 添加该车对象到position车位
	 * @param position
	 */
	public void add(Car car, int position);
	
	/**
	 * @param position
	 * @return 移除position车位的车对象并返回
	 */
	public Car remove(int position);
	
	/**
	 * @return 是否所有车位均被占用
	 */
	public boolean isFull();
}
