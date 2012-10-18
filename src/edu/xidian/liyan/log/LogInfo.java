package edu.xidian.liyan.log;

/**
 * ��־��Ϣ��
 * @author ����
 */
public class LogInfo {
	// ��ǰ���ڳ���
	private int amountInPark;
	// ���η����ۼ��볡����
	private int amountEnter;
	// ���η����ۼƳ�������
	private int amountExit;
	// ���η���������ƽ��ͣ��ʱ��
	private int parkingTimeAverage;

	public LogInfo(int amountInPark, int amountEnter, int amountExit,
			int parkingTimeAverage) {
		super();
		this.amountInPark = amountInPark;
		this.amountEnter = amountEnter;
		this.amountExit = amountExit;
		this.parkingTimeAverage = parkingTimeAverage;
	}

	public int getAmountInPark() {
		return amountInPark;
	}

	public void setAmountInPark(int amountInPark) {
		this.amountInPark = amountInPark;
	}

	public int getAmountEnter() {
		return amountEnter;
	}

	public void setAmountEnter(int amountEnter) {
		this.amountEnter = amountEnter;
	}

	public int getAmountExit() {
		return amountExit;
	}

	public void setAmountExit(int amountExit) {
		this.amountExit = amountExit;
	}

	public int getParkingTimeAverage() {
		return parkingTimeAverage;
	}

	public void setParkingTimeAverage(int parkingTimeAverage) {
		this.parkingTimeAverage = parkingTimeAverage;
	}

}
