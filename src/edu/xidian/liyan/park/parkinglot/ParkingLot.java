package edu.xidian.liyan.park.parkinglot;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.xidian.liyan.park.car.*;
import edu.xidian.liyan.park.drivelane.DriveLaneManager;
import edu.xidian.liyan.park.space.SpaceManager;

/**
 * ���ڹ�����
 * ��ɶ�ͣ�����ڵĹ������������ͳ�λ
 * ��ΪΨһ���ڵĹ����࣬ͬ��Ϊ����ģʽ�����췽��˽�У�ͨ��getInstance��ô˹������
 * @author ����
 */
public class ParkingLot {

	//��������
	private int capacity;
	//�������г�����
	private int size;
	//���ڳ�������
	private Set<Car> carSet;
	//�����������
	private DriveLaneManager driveLaneManager;
	//��λ�������
	private SpaceManager spaceManager;
	
	private static ParkingLot parkingLot = null;
	
	private ParkingLot() {
		super();
	}
	
	/**
	 * @return ��ô˹������һ��ʵ��
	 */
	public static ParkingLot getInstance(){  
        if(parkingLot == null){  
            parkingLot = new ParkingLot();  
        }  
        return parkingLot;  
    }  
	
	/**
	 * ��ʼ��
	 * @param capacity ϵͳ�ⲿ���ò���"n"(α����)�����������������2
	 */
	public void initParkingLot(int capacity) {
		this.capacity = capacity * 2;
		size = 0;
		carSet = new HashSet<Car>();
		driveLaneManager = new DriveLaneManager(capacity + 2);
		spaceManager = new SpaceManager(capacity * 2);
	}
	
	public int getCapacity() {
		return capacity;
	}

	public int getSize() {
		return size;
	}

	public DriveLaneManager getDriveLaneManager() {
		return driveLaneManager;
	}

	public SpaceManager getSpaceManager() {
		return spaceManager;
	}
	
	/**
	 * @param id ����⳵����id
	 * @return �����Ƿ��Ѿ�������id
	 */
	public boolean contains(String id){
		for(Iterator<Car> it = carSet.iterator(); it.hasNext();){
			if(id != null && id.equals(it.next().getEmployee().getId())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return ���ڳ����Ƿ�����
	 */
	public boolean isFull(){
		if(size >= capacity){
			return true;
		}
		return false;
	}
	
	/**
	 * @param car ����ӳ�����
	 */
	public void add(Car car){
		if(carSet.add(car)){
			size++;
		}
	}
	
	/**
	 * @param car ���Ƴ�������
	 */
	public boolean remove(Car car){
		if(carSet.remove(car)){
			size--;
			return true;
		}
		return false;
	}
	
}
