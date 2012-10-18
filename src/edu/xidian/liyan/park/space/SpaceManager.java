package edu.xidian.liyan.park.space;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.car.*;

/**
 * ��λ������
 * @author ����
 */
public class SpaceManager {
	private Logger logger = Logger.getLogger(SpaceManager.class);
	//��λ���ݴ�ȡ����
	private SpaceDao spaceDao;
	
	public SpaceManager(int capacity){
		spaceDao = new SpaceDaoMemory(capacity);
	}
	
	/**
	 * @return ���س�λ����
	 */
	public int getCapacity(){
		return spaceDao.getCapacity();
	}
	
	/**
	 * @param position
	 * @return ����position��λ��ͣ�ŵĳ����������򷵻�null
	 */
	public Car get(int position) {
		return spaceDao.get(position);
	}
	
	/**
	 * ����������car,��ӵ�positionλ��
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
	 * @return �Ƴ�positionλ�õĳ�����
	 */
	public Car remove(int position){
		if(spaceDao.get(position) == null){
			logger.error("the parameter 'position' is not correct");
			return null;
		}
		return spaceDao.remove(position);
	}
	
	/**
	 * @return ��λ�Ƿ��Ѿ�����ռ��
	 */
	public boolean isFull(){
		if(spaceDao.isFull()){
			return true;
		}else{
			return false;
		}
	}
}
