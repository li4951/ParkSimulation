package edu.xidian.liyan.park.drivelane;

import java.util.Calendar;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.access.EntrancePassCar;
import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.parkinglot.ParkingLot;
import edu.xidian.liyan.park.space.OnSpaceCar;
import edu.xidian.liyan.park.space.SpaceManager;

/**
 * �ӳ�����ͣ����λ�ϵĳ����ࣨ����ֻ�Ǹ������࣬costTime��ö�����ʧ���µ�״̬�ĳ���������
 * @author ����
 */
public class ToSpaceCar extends Car {

	private Logger logger = Logger.getLogger(EntrancePassCar.class);
	//�ӳ���ͣ�ŵ���λ������ʱ�䣬�����޸�
	private int costTime = 2;
	//��Ҫͣ�ŵ��ĳ�λ��
	private int spacePosition;
	//�����ϵ�λ��
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
