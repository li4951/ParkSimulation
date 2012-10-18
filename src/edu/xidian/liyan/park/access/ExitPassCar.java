package edu.xidian.liyan.park.access;

import java.util.Calendar;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.car.DeadCar;
import edu.xidian.liyan.park.drivelane.DriveLaneManager;
import edu.xidian.liyan.park.parkinglot.ParkingLot;

/**
 * 通过出口车辆类（Id验证通过，栏杆打开后，通过出口）
 * @author 李延
 */
public class ExitPassCar extends Car {
	private Logger logger = Logger.getLogger(ExitPassCar.class);
	
	private int position;
	
	private Exit exit = Exit.getInstance();
	private ParkingLot parkingLot = ParkingLot.getInstance();
	private DriveLaneManager driveLaneManager = parkingLot.getDriveLaneManager();

	public ExitPassCar(ExitReadCar exitReadCar){
		setEmployee(exitReadCar.getEmployee());
		setLength(exitReadCar.getLength());
		setSpeed(exitReadCar.getSpeed());
		setParkingTime(exitReadCar.getParkingTime());
		setTimeStateBegin(Calendar.getInstance());
	}
	
	@Override
	public Car nextStep() {
		long passedTime = (System.currentTimeMillis() - getTimeStateBegin().getTimeInMillis()) / 1000;;
		if(passedTime >= getLength() / getSpeed()){
			logger.debug("(" + getEmployee().getId() + ") has leaved");
			parkingLot.remove(this);
			driveLaneManager.setState(driveLaneManager.getLength() - 1, null);
			exit.down();
			return new DeadCar(this, "hasPassed");
		}
		return null;
	}

}
