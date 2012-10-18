package edu.xidian.liyan.park.space;

import edu.xidian.liyan.park.car.Car;

/**
 * 车位存取接口实现类（内存中存储）
 * @author 李延
 */
public class SpaceDaoMemory implements SpaceDao {

	//车位容量
	private int capacity;
	//车位数组，元素可能为车对象，也可能为空，偶数下表为上排车位，奇数下标为下排车位
	private Car[] spaceSet;
	//已经占用的车位数
	private int size;

	public SpaceDaoMemory(int capacity) {
		spaceSet = new Car[capacity];
		this.capacity = capacity;
		size = 0;
	}
	
	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public Car get(int position) {
		return spaceSet[position];
	}

	@Override
	public void add(Car car, int position) {
		spaceSet[position] = car;
		size++;
	}

	@Override
	public Car remove(int position) {
		Car car = spaceSet[position];
		spaceSet[position] = null;
		size--;
		return car;
	}

	@Override
	public boolean isFull() {
		if(size >= capacity){
			return true;
		}
		return false;
	}

	
}
