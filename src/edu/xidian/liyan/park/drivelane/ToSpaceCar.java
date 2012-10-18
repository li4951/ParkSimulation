package edu.xidian.liyan.park.drivelane;

import java.util.Calendar;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.access.EntrancePassCar;
import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.parkinglot.ParkingLot;
import edu.xidian.liyan.park.space.OnSpaceCar;
import edu.xidian.liyan.park.space.SpaceManager;

/**
 * 从车道上停到车位上的车辆类（本类只是个过渡类，costTime后该对象消失，新的状态的车对象诞生）
 * @author 李延
 */
public class ToSpaceCar extends Car {

	private Logger logger = Logger.getLogger(EntrancePassCar.class);
	//从车道停放到车位车所需时间，可以修改
	private int costTime = 2;
	//将要停放到的车位号
	private int spacePosition;
	//车道上的位置
	private int position;
	
	private ParkingLot parkingLot = ParkingLot.getInstance();
	private SpaceManager spaceManager = parkingLot.getSpaceManager();
	private DriveLaneManager driveLaneManager = parkingLot.getDriveLaneManager(); 
	
	public int getPosition() {
		return position;
	}

	public int getSpacePosition() {
		return spacePosition;
	}

	public ToSpaceCar(SearchSpaceCar searchSpaceCar, int spacePosition){
		this.spacePosition = spacePosition;
		this.position = searchSpaceCar.getPosition();
		setEmployee(searchSpaceCar.getEmployee());
		setLength(searchSpaceCar.getLength());
		setSpeed(searchSpaceCar.getSpeed());
		setParkingTime(searchSpaceCar.getParkingTime());
		setTimeStateBegin(Calendar.getInstance());
	}
	
	@Override
	public Car nextStep() {
		long passedTime = (System.currentTimeMillis() - getTimeStateBegin().getTimeInMillis()) / 1000;
		if(passedTime >= costTime){
			logger.debug("(" + getEmployee().getId() + ") has park on No." + spacePosition);
			OnSpaceCar onSpaceCar = new OnSpaceCar(this);
			spaceManager.add(onSpaceCar, spacePosition);
			driveLaneManager.setState(position, null);
			return onSpaceCar;
		}
		return null;
	}

}
