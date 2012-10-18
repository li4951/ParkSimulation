package edu.xidian.liyan.park.drivelane;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.access.EntrancePassCar;
import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.parkinglot.ParkingLot;
import edu.xidian.liyan.park.space.SpaceManager;
import edu.xidian.liyan.park.space.ToLaneCar;

/**
 * Ѱ�ҳ�λ�ĳ����ࣨ�ڳ�����ǰ����Ѱ�ҿճ�λ��
 * @author ����
 */
public class SearchSpaceCar extends Car{
	private Logger logger = Logger.getLogger(SearchSpaceCar.class);
	//��ǰ�ڳ����ϵ�λ��
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
	
	@Override
	public Car nextStep() {
		
		int spacePosition = -1; 
		if(spaceManager.get(position * 2 - 2) == null){//��೵λ(����)�Ƿ��
			spacePosition = position * 2 - 2;
		} else if(spaceManager.get(position * 2 - 1) == null){//�Ҳ೵λ(����)�Ƿ��
			spacePosition = position * 2 - 1;
		} else if (canMoveForward()){
			driveLaneManager.setState(position + 1, this);
			driveLaneManager.setState(position, null);
			position += 1;
		}
		//�ҵ��ճ�λ	
		if(spacePosition != -1){
			logger.debug("(" + getEmployee().getId() + ")" + " found the free space(" + spacePosition +")! it will park there after 2 seconds");
			return new ToSpaceCar(this, spacePosition);
		}
		return this;
	}
}
