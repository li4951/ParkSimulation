package edu.xidian.liyan.park.drivelane;

import java.util.Calendar;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.parkinglot.ParkingLot;
import edu.xidian.liyan.park.space.SpaceManager;
import edu.xidian.liyan.park.space.ToLaneCar;
import edu.xidian.liyan.park.access.Exit;
import edu.xidian.liyan.park.access.ExitReadCar;

/**
 * ʻ����ڳ����ࣨ�뿪ͣ��λ��ʻ����ڣ�
 * @author ����
 */
public class InLaneCar extends Car {

	private Logger logger = Logger.getLogger(InLaneCar.class);
	//����λ��
	private int position;
	
	private ParkingLot parkingLot = ParkingLot.getInstance();
	private SpaceManager spaceManager = parkingLot.getSpaceManager();
	private DriveLaneManager driveLaneManager = parkingLot.getDriveLaneManager(); 
	
	public int getPosition() {
		return position;
	}
	
	public InLaneCar(ToLaneCar toLaneCar){
		this.position = toLaneCar.getPosition();
		setEmployee(toLaneCar.getEmployee());
		setLength(toLaneCar.getLength());
		setSpeed(toLaneCar.getSpeed());
		setParkingTime(toLaneCar.getParkingTime());
		setTimeStateBegin(Calendar.getInstance());
		
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
		if(position < driveLaneManager.getLength() - 3){
			if(canMoveForward()){
				driveLaneManager.setState(position + 1, this);
				driveLaneManager.setState(position, null);
				position += 1;
			}
		}else if(position == driveLaneManager.getLength() - 3 && driveLaneManager.getState(position + 2) == null){
			driveLaneManager.setState(position + 1, this);
			driveLaneManager.setState(position, null);
			position += 1;
		}else if(position == driveLaneManager.getLength()- 2 && !Exit.getInstance().isUsed()){
			ExitReadCar exitReadCar = new ExitReadCar(this);
			driveLaneManager.setState(driveLaneManager.getLength() - 1, exitReadCar);
			driveLaneManager.setState(position, null);
			logger.debug("(" + getEmployee().getId() + ") arrive in the exit, begin to read id......");
			return exitReadCar;
		}		
		return null;
	}

}
