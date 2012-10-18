package edu.xidian.liyan.park.access;


import org.apache.log4j.Logger;

import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.drivelane.DriveLaneManager;
import edu.xidian.liyan.park.drivelane.SearchSpaceCar;
import edu.xidian.liyan.park.parkinglot.ParkingLot;
import edu.xidian.liyan.park.space.SpaceManager;
import edu.xidian.liyan.park.space.ToLaneCar;

/**
 * ��ڳ���ͨ���ࣨ�����Ϸ�������̧���׼��ͨ����
 * @author ����
 */
public class EntrancePassCar extends Car {
	private Logger logger = Logger.getLogger(EntrancePassCar.class);
	//�����ڳ����ϵ�λ��
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
	 * @return �Ƿ����ǰ��(��+2λ�û���+2λ���г���׼���뿪��λʱ��������ͣǰ�����ȴ�ǰ��������)
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
		if(position == 1){//��������ȫ���복��
			logger.debug("(" + getEmployee().getId() + ")" + " Has Passed! Entrance will be closed after 2 seconds");
			entrance.down();
			SearchSpaceCar searchSpaceCar = new SearchSpaceCar(this);
			parkingLot.add(searchSpaceCar);
			return searchSpaceCar.nextStep();
		} else if (canMoveForward()) {//ǰ��ͨ��
			if(position >= 0){
				driveLaneManager.setState(position, null);
			}
			driveLaneManager.setState(++position, this);
		}
		return null;
	}
}
