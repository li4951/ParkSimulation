package edu.xidian.liyan.park.space;

import java.util.Calendar;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.access.EntrancePassCar;
import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.drivelane.DriveLaneManager;
import edu.xidian.liyan.park.drivelane.ToSpaceCar;
import edu.xidian.liyan.park.parkinglot.ParkingLot;

/**
 * 停放状态下的车辆类（车辆停放在车位上）
 * @author 李延
 */
public class OnSpaceCar extends Car {

	private Logger logger = Logger.getLogger(EntrancePassCar.class);
	//车位号
	private int spacePosition;
	//车道上的位置
	private int position;
	
	private ParkingLot parkingLot = ParkingLot.getInstance();
	private SpaceManager spaceManager = parkingLot.getSpaceManager();
	private DriveLaneManager driveLaneManager = parkingLot.getDriveLaneManager(); 
	
	public OnSpaceCar(ToSpaceCar toSpaceCar){
		this.spacePosition = toSpaceCar.getSpacePosition();
		this.position = toSpaceCar.getPosition();
		setEmployee(toSpaceCar.getEmployee());
		setLength(toSpaceCar.getLength());
		setSpeed(toSpaceCar.getSpeed());
		setParkingTime(toSpaceCar.getParkingTime());
		setTimeStateBegin(Calendar.getInstance());
	}
	
	public int getSpacePosition() {
		return spacePosition;
	}

	/**
	 * @param spacePosition 待检测车位号
	 * @return 返回该车为是否为一个ToLaneCar对象
	 */
	private boolean isToLaneCar(int spacePosition){
		if(spacePosition < 0 || spacePosition > spaceManager.getCapacity() - 1 || spacePosition == this.spacePosition){
			return false;
		}else if(spaceManager.get(spacePosition) == null){
			return false;
		}else if(spaceManager.get(spacePosition) instanceof ToLaneCar){
			return true;
		}
		return false;
	} 
	
	/**
	 * @return 判断是否可以移动到车道上（由于车道上可能会有别的车辆，或者出现争道现象）
	 */
	private boolean canToLane(){
		if (driveLaneManager.getState(position - 1) == null
				&& driveLaneManager.getState(position + 1) == null
				&& driveLaneManager.getState(position) == null
				&& !isToLaneCar(2 * position - 4)
				&& !isToLaneCar(2 * position - 3)
				&& !isToLaneCar(2 * position - 2)
				&& !isToLaneCar(2 * position - 1)
				&& !isToLaneCar(2 * position)
				&& !isToLaneCar(2 * position + 1)) {
			return true;
		}
		return false;
	}
	
	@Override
	public Car nextStep() {
		long passedTime = (System.currentTimeMillis() - getTimeStateBegin().getTimeInMillis()) / 1000;
		if (passedTime >= getParkingTime() && canToLane()) {
			logger.debug("(" + getEmployee().getId() + ")" + " time out, to be a ToLaneCar, it will be on the driveLane after 2 seconds");
			ToLaneCar toLaneCar = new ToLaneCar(this);
			spaceManager.add(toLaneCar, spacePosition);
			return toLaneCar;
		}
		return null;
	}

}
