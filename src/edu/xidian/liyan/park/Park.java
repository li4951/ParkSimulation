package edu.xidian.liyan.park;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edu.xidian.liyan.log.LogInfo;
import edu.xidian.liyan.log.WriterLogInfo;
import edu.xidian.liyan.park.access.*;
import edu.xidian.liyan.park.car.*;
import edu.xidian.liyan.park.parkinglot.ParkingLot;

/**
 * @author ����
 * ͣ��������
 * ��Ҫ�������ԣ���ڣ����ڣ������ڣ����г���
 */
public class Park {
	
	private static Logger logger = Logger.getLogger(Park.class);
	private Access entrance;
	private Access exit;
	//���ڹ������
	private ParkingLot parkingLot;
	//���г�������
	private List<Car> cars;
	//���ò�����Ա������
	private int e;
	//���ò��������ų�λ��
	private int n;
	//��־�ļ��������
	private WriterLogInfo writerLogInfo = new WriterLogInfo();
	
	//�����������Ե�������Ϊ�˷����ӡ������ڳ��ͳ����뿪��ĳ����������ʱû�п��ǵ������ڴ����޲���ʵ�����Ρ�
	private EntranceReadCar entranceCar;
	private DeadCar exitCar;
	
	public Park(int e, int n) {
		this.e = e;
		this.n = n;
		entrance = Entrance.getInstance();
		exit = Exit.getInstance();
		parkingLot = ParkingLot.getInstance();
		parkingLot.initParkingLot(n);
		EmployeeDao.getInstance().initEmployees(e);
		cars = new ArrayList<Car>();
		logger.debug("Init success");
	}
	
	/**
	 * ͣ����ϵͳ���溯����ÿ����ˢ��һ�θ���ض���״̬
	 */
	public void simulate(){
		int timeCount = 0;
		int amountIn = 0;
		int amountOut = 0;
		int parkingTimeSum = 0;
		
		writerLogInfo.setTimeSimulateBegin(Calendar.getInstance());		
		while(true){
			System.out.println("��" + timeCount++ +"���");
			//ÿ��һ���ӱ���һ��д����־�ļ�
			if(timeCount != 0 && timeCount % 60 == 0){
				LogInfo logInfo = new LogInfo(parkingLot.getSize(), amountIn, amountOut, amountOut==0? 0 : parkingTimeSum / amountOut);
				writerLogInfo.addInfo(logInfo);
				if(timeCount > 250 && parkingLot.getSize()== 0){
					break;
				}
			}
			//1:��ڿ��� 2:����û�� 3:(Ϊ���淽�㣬ǰ250����) �����µ��볡��
			if(!Entrance.getInstance().isUsed() && !parkingLot.isFull() && timeCount < 250){
				Car randomCar = CarFactory.produceCar(e);
				if(randomCar != null){
					cars.add(randomCar);
				}
			}
			//��ںͳ��ڶ���ÿ��������Ƿ���ɣ�������״̬�����������Ƿ���ȫ���£��޸�����״̬
			entrance.refresh();
			exit.refresh();
			//ˢ�����г��������״̬
			int deleteCarNum = -1;
			for(int i = 0; i < cars.size(); i++){
				Car oldCar = cars.get(i);
				Car newCar = cars.get(i).nextStep();
				if(newCar != null){
					cars.set(i, newCar);
				}
				//ÿ��һ�����볡��in++
				if(oldCar instanceof EntranceReadCar && newCar instanceof EntrancePassCar){
					amountIn++;
				}
				if(cars.get(i) instanceof EntranceReadCar){
					entranceCar = (EntranceReadCar)cars.get(i);
				}
				if(cars.get(i) instanceof DeadCar){
					deleteCarNum = i;
					exitCar = (DeadCar)cars.get(i);
					if(exitCar.getDeadReason().equals("hasPassed")){
						parkingTimeSum += exitCar.getParkingTime();
						amountOut++;
					}
				}
			}
			if(deleteCarNum != -1){
				cars.remove(deleteCarNum);	
			}
			//��ӡ������״̬
			print();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			System.out.print("����1�룬");
		}
		writerLogInfo.setTimeSimulateEnd(Calendar.getInstance());
		writerLogInfo.writeToFile();
	}
	
	/**
	 * ���ش�ӡ����
	 */
	public void print(){
		printSpace(0);//0��ʾ���ų�λ
		printEntrance();
		printLane();
		printExit();
		printSpace(1);//1��ʾ���ų�λ
	}
	
	/**
	 * ��ӡͣ��λ��״̬����--������У�����Ϊ����ID
	 * @param flag ��ʶ0Ϊ��ӡ���ų�λ��1Ϊ��ӡ���ų�λ
	 */
	public void printSpace(int flag){
		System.out.print("  |##|");
		for(int i = flag; i < parkingLot.getCapacity(); i += 2){
			Car car = parkingLot.getSpaceManager().get(i);
			if(car != null){
				System.out.print(car.getEmployee().getId().length() < 2 ? "0" + car.getEmployee().getId() : car.getEmployee().getId());
			}else{
				System.out.print("--");
			}
			System.out.print("|");
		}
		System.out.println("##|");
	}

	/**
	 * ��ӡ��������ʱΪ�ո񣬷���Ϊ����ID
	 */
	/*public void printLane(){
		for(int i = 0; i < parkingLot.getDriveLaneManager().getLength(); i++){
			Car car = parkingLot.getDriveLaneManager().getState(i);
			if(car != null){
				System.out.print(car.getId().length() < 2 ? "0" + car.getId() : car.getId());
			}else{
				System.out.print("  ");//�����գ�
			}
			if(i != parkingLot.getDriveLaneManager().getLength() - 1){
				System.out.print(" ");
			}
		}
	}*/
	
	public void printLane(){
		for(int i = 0; i < parkingLot.getDriveLaneManager().getLength(); i++){
			Car car = parkingLot.getDriveLaneManager().getState(i);
			if(car != null){
				System.out.print(car.getEmployee().getId().length() < 2 ? "0" + car.getEmployee().getId() : car.getEmployee().getId());
			}else if(i + 1 < parkingLot.getDriveLaneManager().getLength() && parkingLot.getDriveLaneManager().getState(i + 1) != null){
				System.out.print("@@");//�����գ�
			}else{
				System.out.print("  ");//�����գ�
			}
			if(i != parkingLot.getDriveLaneManager().getLength() - 1){
				System.out.print(" ");
			}
		}
	}
	
	/**
	 * ��ӡ��� ��o����(open)��ڿ���; ��c����(closed)��ڹر�
	 */
	public void printEntrance(){
		if(entranceCar != null){
			String entranceCarNum = entranceCar.getEmployee().getId();
			System.out.print(entranceCarNum.length() < 2 ? "0" + entranceCarNum : entranceCarNum);
			entranceCar = null;
		}else{
			System.out.print("  ");
		}
		if(entrance.getState() == Entrance.OPENED){
			System.out.print("O");
		}else{
			System.out.print("C");
		}
	}
	
	/**
	 * ��ӡ���� ��o����(open)���ڿ���; ��c����(closed)���ڹر�
	 */
	public void printExit(){
		if(exit.getState() == Exit.OPENED){
			System.out.print("O");
		}else{
			System.out.print("C");
		}
		if(exitCar != null && exitCar.getDeadReason().equals("hasPassed")){
			String exitCarNum = exitCar.getEmployee().getId();
			System.out.print(exitCarNum.length() < 2 ? "0" + exitCarNum : exitCarNum);
			exitCar = null;
		}
		System.out.println();
	}
	
	public static void main(String[] args){
		PropertyConfigurator.configure("./config/log4j.properties");
		new Park(20, 15).simulate();
	}
}
