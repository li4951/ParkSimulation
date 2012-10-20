package edu.xidian.liyan.park.car;

import edu.xidian.liyan.employee.Employee;
import edu.xidian.liyan.park.access.EntranceReadCar;

/**
 * �����࣬ר�������ɽ����ĳ�����
 * @author ����
 */
public class CarFactory {
	
	/**
	 * @param limit �Ϸ�id�����Ʒ�Χ
	 * @return ����һ��������복���ĳ����󣬼�����һ������ڶ���������
	 */
	public static Car produceCar(int limit){
		int numId = (int) (Math.random() * limit);
		int parkingTime = (int)(Math.random() * 50);
		String id = String.valueOf(numId);
		return new EntranceReadCar(new Employee(id), 2, 1, parkingTime);
	}
}
