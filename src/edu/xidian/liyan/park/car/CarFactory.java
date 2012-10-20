package edu.xidian.liyan.park.car;

import edu.xidian.liyan.employee.Employee;
import edu.xidian.liyan.park.access.EntranceReadCar;

/**
 * 工厂类，专用于生成进场的车对象
 * @author 李延
 */
public class CarFactory {
	
	/**
	 * @param limit 合法id的限制范围
	 * @return 返回一个申请进入车场的车对象，即产生一个“入口读卡车对象”
	 */
	public static Car produceCar(int limit){
		int numId = (int) (Math.random() * limit);
		int parkingTime = (int)(Math.random() * 50);
		String id = String.valueOf(numId);
		return new EntranceReadCar(new Employee(id), 2, 1, parkingTime);
	}
}
