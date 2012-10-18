package edu.xidian.liyan.park.space;

import edu.xidian.liyan.park.car.*;

/**
 * ��λ���ݴ�ȡ�ӿ�
 * ������SpaceDaoMemory���ڴ��д洢����SpaceDaoDb�����ݿ�洢����ʵ�֣���ϵͳ��ʱֻʵ��ǰ��
 * @author ����
 */
public interface SpaceDao {
	
	/**
	 * @return ��ó�λ����
	 */
	public int getCapacity();
	
	/**
	 * @return ��õ�ǰ�Ѿ�ռ�õĳ�λ��
	 */
	public int getSize();
	
	/**
	 * @param position
	 * @return ����position��λ�ĳ����󣬿ձ�ʾ�޳�ռ��
	 */
	public Car get(int position);
	
	/**
	 * @param car ��Ӹó�����position��λ
	 * @param position
	 */
	public void add(Car car, int position);
	
	/**
	 * @param position
	 * @return �Ƴ�position��λ�ĳ����󲢷���
	 */
	public Car remove(int position);
	
	/**
	 * @return �Ƿ����г�λ����ռ��
	 */
	public boolean isFull();
}
