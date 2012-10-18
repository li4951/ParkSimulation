package edu.xidian.liyan.park.space;

import edu.xidian.liyan.park.car.Car;

/**
 * ��λ��ȡ�ӿ�ʵ���ࣨ�ڴ��д洢��
 * @author ����
 */
public class SpaceDaoMemory implements SpaceDao {

	//��λ����
	private int capacity;
	//��λ���飬Ԫ�ؿ���Ϊ������Ҳ����Ϊ�գ�ż���±�Ϊ���ų�λ�������±�Ϊ���ų�λ
	private Car[] spaceSet;
	//�Ѿ�ռ�õĳ�λ��
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
