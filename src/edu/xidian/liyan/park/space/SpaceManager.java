package edu.xidian.liyan.park.space;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.car.*;

/**
 * 车位管理类
 * @author 李延
 */
public class SpaceManager {
	private Logger logger = Logger.getLogger(SpaceManager.class);
	//车位数据存取对象
	private SpaceDao spaceDao;
	
	public SpaceManager(int capacity){
		spaceDao = new SpaceDaoMemory(capacity);
	}
	
	/**
	 * @return 返回车位容量
	 */
	public int getCapacity(){
		return spaceDao.getCapacity();
	}
	
	/**
	 * @param position
	 * @return 返回position车位上停放的车辆，若无则返回null
	 */
	public Car get(int position) {
		return spaceDao.get(position);
	}
	
	/**
	 * 将车辆对象car,添加到position位置
	 */
	public void add(Car car, int position){
		if(car == null || (position < 0 || position > spaceDao.getCapacity() - 1)){
			logger.error("car or position is not correct"+ car);
		}else{
			spaceDao.add(car, position);
		}
	}
	
	/**
	 * @param position
	 * @return 移除position位置的车对象
	 */
	public Car remove(int position){
		if(spaceDao.get(position) == null){
			logger.error("the parameter 'position' is not correct");
			return null;
		}
		return spaceDao.remove(position);
	}
	
	/**
	 * @return 车位是否已经都被占用
	 */
	public boolean isFull(){
		if(spaceDao.isFull()){
			return true;
		}else{
			return false;
		}
	}
}
