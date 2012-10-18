package edu.xidian.liyan.park.space;

import java.util.Calendar;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.space.OnSpaceCar;
import edu.xidian.liyan.park.drivelane.DriveLaneManager;
import edu.xidian.liyan.park.drivelane.InLaneCar;
import edu.xidian.liyan.park.parkinglot.ParkingLot;


/**
 * 从车位上停到车道上的车辆类（本类只是个过渡类，costTime后该对象消失，新的状态的车对象诞生）
 * @author 李延
 */
public class ToLaneCar extends Car {

	private Logger logger = Logger.getLogger(ToLaneCar.class);
	//离开车位后在车道上的坐标
	private int position;
	//对应的车位号
	private int spacePosition;
	//移动到车道上所需时间
	private int costTime = 2;
	
	private ParkingLot parkingLot = ParkingLot.getInstance();
	private SpaceManager spaceManager = parkingLot.getSpaceManager();
	private DriveLaneManager driveLaneManager = parkingLot.getDriveLaneManager(); 
	
	public int getPosition() {
		return position;
	}
	
	public int getSpacePosition() {
		return spacePosition;
	}

	public ToLaneCar(OnSpaceCar onSpaceCar){
		this.spacePosition = onSpaceCar.getSpacePosition();
		this.position = onSpaceCar.getSpacePosition() / 2 + 1;
		setEmployee(onSpaceCar.getEmployee());
		setLength(onSpaceCar.getLength());
		setSpeed(onSpaceCar.getSpeed());
		setParkingTime(onSpaceCar.getParkingTime());
		setTimeStateBegin(Calendar.getInstance());
		
	}
	
	@Override
	public Car nextStep() {
		long passedTime = (System.currentTimeMillis() - getTimeStateBegin().getTimeInMillis()) / 1000;
		if(passedTime >= costTime){
			logger.debug("(" + getEmployee().getId() + ")" + "lay on the lane successfully");
			driveLaneManager.setState(position, this);
			spaceManager.remove(spacePosition);
			InLaneCar inLaneCar = new InLaneCar(this);
			return inLaneCar;
		}
		return null;
	}

}
