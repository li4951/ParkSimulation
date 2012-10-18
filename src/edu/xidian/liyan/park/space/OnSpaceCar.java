package edu.xidian.liyan.park.space;

import java.util.Calendar;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.access.EntrancePassCar;
import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.drivelane.DriveLaneManager;
import edu.xidian.liyan.park.drivelane.ToSpaceCar;
import edu.xidian.liyan.park.parkinglot.ParkingLot;

/**
 * ͣ��״̬�µĳ����ࣨ����ͣ���ڳ�λ�ϣ�
 * @author ����
 */
public class OnSpaceCar extends Car {

	private Logger logger = Logger.getLogger(EntrancePassCar.class);
	//��λ��
	private int spacePosition;
	//�����ϵ�λ��
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
	 * @param spacePosition ����⳵λ��
	 * @return ���ظó�Ϊ�Ƿ�Ϊһ��ToLaneCar����
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
	 * @return �ж��Ƿ�����ƶ��������ϣ����ڳ����Ͽ��ܻ��б�ĳ��������߳�����������
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
