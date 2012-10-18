package edu.xidian.liyan.park.drivelane;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.access.EntrancePassCar;
import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.parkinglot.ParkingLot;
import edu.xidian.liyan.park.space.SpaceManager;
import edu.xidian.liyan.park.space.ToLaneCar;

/**
 * 寻找车位的车辆类（在车道上前进，寻找空车位）
 * @author 李延
 */
public class SearchSpaceCar extends Car{
	private Logger logger = Logger.getLogger(SearchSpaceCar.class);
	//当前在车道上的位置
	private int position;

	private ParkingLot parkingLot = ParkingLot.getInstance();
	private SpaceManager spaceManager = parkingLot.getSpaceManager();
	private DriveLaneManager driveLaneManager = parkingLot.getDriveLaneManager(); 
	
	public int getPosition() {
		return position;
	}

	public SearchSpaceCar(EntrancePassCar entrancePassCar){
		this.position = entrancePassCar.getPosition();
		setEmployee(entrancePassCar.getEmployee());
		setLength(entrancePassCar.getLength());
		setSpeed(entrancePassCar.getSpeed());
		setParkingTime(entrancePassCar.getParkingTime());
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
	
	@Override
	public Car nextStep() {
		
		int spacePosition = -1; 
		if(spaceManager.get(position * 2 - 2) == null){//左侧车位(上排)是否空
			spacePosition = position * 2 - 2;
		} else if(spaceManager.get(position * 2 - 1) == null){//右侧车位(下排)是否空
			spacePosition = position * 2 - 1;
		} else if (canMoveForward()){
			driveLaneManager.setState(position + 1, this);
			driveLaneManager.setState(position, null);
			position += 1;
		}
		//找到空车位	
		if(spacePosition != -1){
			logger.debug("(" + getEmployee().getId() + ")" + " found the free space(" + spacePosition +")! it will park there after 2 seconds");
			return new ToSpaceCar(this, spacePosition);
		}
		return this;
	}
}
