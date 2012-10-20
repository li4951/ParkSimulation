package edu.xidian.liyan.park.access;

import java.util.Calendar;
import edu.xidian.liyan.park.car.Car;

/**
 * �ؿ���
 * ��ںͳ��ڵĸ��࣬ʵ����Ϊһ���ؿ�Ӧ�еĲ���
 * @author ����
 */
public class Access {
	//ʹ�þ���������Ϊ�ؿ���stateȡֵ
	public final static int CLOSED = 0;
	public final static int OPENED = 1;
	
	//ʹ��ö��������Ϊ��������
	public enum OperateType{
		READ, LIFT, DOWN
	};
	
	//��ǰ�ؿ������еĶ���
	private OperateType operateType; 
	//�ؿ�״̬��OPENED��CLOSED
	private int state;
	//��������ʱ��,����ͨ���вι��췽���޸�
	private int readTime = 1;
	//̧����������ʱ��,����ͨ���вι��췽���޸�
	private int liftTime = 2;
	//������������ʱ��,����ͨ���вι��췽���޸�
	private int downTime = 2;
	//��ǰ�ؿ������Ŀ�ʼʱ��
	private Calendar timeOperateBegin;
	//�ؿ��Ƿ����
	private boolean used;
	//������Ϸ�
	private boolean readOver;
	//��ǰ�����Ƿ�ͨ��
	private boolean passed;
	
    public Access() {
		super();
		state = CLOSED;
		used = false;
		readOver = false;
	}

	public Access(int readTime, int liftTime, int downTime) {
		this();
		this.readTime = readTime;
		this.liftTime = liftTime;
		this.downTime = downTime;
	}

	public boolean isReadOver() {
		return readOver;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	public OperateType getOperateType() {
		return operateType;
	}

	public void setOperateType(OperateType operateType) {
		this.operateType = operateType;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public boolean isPassed() {
		return passed;
	}
	
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	
	/**
	 * @param car ��ǰ��Ҫ�����ؿ��ĳ�������
	 */
	public String read(Car car){
		if(readOver){//�ڶ��α����ã���ȡ��ϣ�����ID
			readOver = false;
			return car.getEmployee().getId();
		}else{
			setOperateType(OperateType.READ);//��һ�α����ã�������ȡ����
			timeOperateBegin = Calendar.getInstance();//���赱ǰ״̬������ʼʱ��
			
			setUsed(true);//����ǿ�ʼʹ�á�����������֮���Ӧ�ġ����ʹ����ϡ���
			return "";
		}
	}
	
	/**
	 * ����̧�����˶���
	 */
	public String lift(){
		setOperateType(OperateType.LIFT);
		timeOperateBegin = Calendar.getInstance();//���赱ǰ״̬������ʼʱ��
		return "";
	}
	
	/**
	 * �����������˶���
	 */
	public String down(){
		setOperateType(OperateType.DOWN);
		timeOperateBegin = Calendar.getInstance();//���赱ǰ״̬������ʼʱ��
		return "";
	}
	
	/**
	 * ˢ�¹ؿ�״̬��ÿ��������Ƿ���ɣ�������״̬�����������Ƿ���ȫ���£�ʱ���ѵ������޸�����״̬
	 */
	public void refresh(){
		if(timeOperateBegin == null)
			return;
		long passedTime = (System.currentTimeMillis() - timeOperateBegin.getTimeInMillis()) / 1000;
		
		switch (operateType) {
		case READ:
			if(passedTime >= readTime){//����뷢���������������ѴﵽreadTime,������isReadOverΪtrue
				readOver = true;
				timeOperateBegin = null;
			}
			break;
		case LIFT:
			if(passedTime >= liftTime){//����뷢����̧�����ˡ������ѴﵽliftTime,���޸�״̬ΪOPEN
				state = OPENED;
				timeOperateBegin = null;
			}
			break;
		case DOWN:
			if(passedTime >= downTime){//����뷢�����������ˡ������ѴﵽdownTime,���޸�״̬ΪCLOSED
				state = CLOSED;
				timeOperateBegin = null;
				setUsed(false);//�����ʹ����ϡ���
			}
			break;
		default:
			break;
		}
	}
}
