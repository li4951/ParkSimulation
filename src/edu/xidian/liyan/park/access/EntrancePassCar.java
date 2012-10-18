package edu.xidian.liyan.park.access;


import org.apache.log4j.Logger;

import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.drivelane.DriveLaneManager;
import edu.xidian.liyan.park.drivelane.SearchSpaceCar;
import edu.xidian.liyan.park.parkinglot.ParkingLot;
import edu.xidian.liyan.park.space.SpaceManager;
import edu.xidian.liyan.park.space.ToLaneCar;

/**
 * 入口车辆通过类（读卡合法，栏杆抬起后，准备通过）
 * @author 李延
 */
public class EntrancePassCar extends Car {
	private Logger logger = Logger.getLogger(EntrancePassCar.class);
	//车辆在车道上的位置
	private int position;
	
	private ParkingLot parkingLot = ParkingLot.getInstance();
	private DriveLaneManager driveLaneManager = parkingLot.getDriveLaneManager();
	private SpaceManager spaceManager = parkingLot.getSpaceManager();
	private Entrance entrance = Entrance.getInstance();
	
	public EntrancePassCar(EntranceReadCar entranceReadCar){
		this.position = -1;
		setEmployee(entranceReadCar.getEmployee());
		setLength(entranceReadCar.getLength());
		setSpeed(entranceReadCar.getSpeed());
		setParkingTime(entranceReadCar.getParkingTime());
	}
	
	public int getPosition() {
		return position;
	}

	/**
	 * @return 是否可以前进(左+2位置或右+2位置有车辆准备离开车位时，本车暂停前进，等待前方车先走)
	 */
	private boolean canMoveForward(){
		if (!(spaceManager.get((position + 2) * 2 - 1) instanceof ToLaneCar)
				&& !(spaceManager.get((position + 2) * 2 - 2) instanceof ToLaneCar)
				&& driveLaneManager.getState(position + 2) == null) {
			return true;
		}
		return false;
	}
	
	public Car nextStep(){
		if(position == 1){//车辆已完全进入车场
			logger.debug("(" + getEmployee().getId() + ")" + " Has Passed! Entrance will be closed after 2 seconds");
			entrance.down();
			SearchSpaceCar searchSpaceCar = new SearchSpaceCar(this);
			parkingLot.add(searchSpaceCar);
			return searchSpaceCar.nextStep();
		} else if (canMoveForward()) {//前方通畅
			if(position >= 0){
				driveLaneManager.setState(position, null);
			}
			driveLaneManager.setState(++position, this);
		}
		return null;
	}
}
