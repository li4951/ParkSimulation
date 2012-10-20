package edu.xidian.liyan.park.access;

import org.apache.log4j.Logger;

import edu.xidian.liyan.employee.Employee;
import edu.xidian.liyan.employee.EmployeeDao;
import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.car.DeadCar;
import edu.xidian.liyan.park.parkinglot.ParkingLot;

/**
 * ��ڶ���������(����ڴ�����)
 * @author ����
 */
public class EntranceReadCar extends Car {
	private Logger logger = Logger.getLogger(EntranceReadCar.class);

	//��ڶ���
	private Entrance entrance = Entrance.getInstance();
	//Ա���������ݷ��ʶ���
	private EmployeeDao employeeDao = EmployeeDao.getInstance();
	//����ͣ�����������
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
		if(Entrance.OPENED == entrance.getState()){//���˴�
			logger.debug("(" + getEmployee().getId() + " in the entrance)" + " has opened, begin to pass");
			EntrancePassCar entrancePassCar = new EntrancePassCar(this);
			return entrancePassCar;
		}else if(entrance.isReadOver()){//������Ϸ�
			String id = entrance.read(this);
			logger.debug("(" + id + " in the entrance)" + " read finished");
			if(id != null && employeeDao.contains(id) && !parkingLot.contains(id)){
				logger.debug("(" + id + " in the entrance)" + " Id is correct, begin to lift!");
				entrance.lift();
			}else{
				System.out.println("Ա������������" + employeeDao.contains(id) + "�������������" + parkingLot.contains(id));
				logger.debug("(" + id + " in the entrance)" + " Id is not correct, it will be remove!");
				entrance.setUsed(false);
				return new DeadCar(this, "invalidId");
			}
		}
		return null;	//��ȷ����²����ߵ���
	}
}
