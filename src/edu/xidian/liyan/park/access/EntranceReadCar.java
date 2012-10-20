package edu.xidian.liyan.park.access;

import org.apache.log4j.Logger;

import edu.xidian.liyan.employee.Employee;
import edu.xidian.liyan.employee.EmployeeDao;
import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.car.DeadCar;
import edu.xidian.liyan.park.parkinglot.ParkingLot;

/**
 * 入口读卡车辆类(在入口处读卡)
 * @author 李延
 */
public class EntranceReadCar extends Car {
	private Logger logger = Logger.getLogger(EntranceReadCar.class);

	//入口对象
	private Entrance entrance = Entrance.getInstance();
	//员工管理数据访问对象
	private EmployeeDao employeeDao = EmployeeDao.getInstance();
	//场内停车场管理对象
	private ParkingLot parkingLot = ParkingLot.getInstance();
	
	public EntranceReadCar(Employee employee, int length, int speed, int parkingTime) {
		setEmployee(employee);
		setLength(length);
		setSpeed(speed);
		setParkingTime(parkingTime);
		logger.debug("Create a new car, Begin to read Id ...... " + employee.getId());
		
		Entrance entrance = Entrance.getInstance();
		entrance.read(this);
	}
	
	@Override
	public Car nextStep() {
		if(Entrance.OPENED == entrance.getState()){//栏杆打开
			logger.debug("(" + getEmployee().getId() + " in the entrance)" + " has opened, begin to pass");
			EntrancePassCar entrancePassCar = new EntrancePassCar(this);
			return entrancePassCar;
		}else if(entrance.isReadOver()){//读卡完毕否
			String id = entrance.read(this);
			logger.debug("(" + id + " in the entrance)" + " read finished");
			if(id != null && employeeDao.contains(id) && !parkingLot.contains(id)){
				logger.debug("(" + id + " in the entrance)" + " Id is correct, begin to lift!");
				entrance.lift();
			}else{
				System.out.println("员工包不包含：" + employeeDao.contains(id) + "场里包不包含：" + parkingLot.contains(id));
				logger.debug("(" + id + " in the entrance)" + " Id is not correct, it will be remove!");
				entrance.setUsed(false);
				return new DeadCar(this, "invalidId");
			}
		}
		return null;	//正确情况下不会走到这
	}
}
