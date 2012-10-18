package edu.xidian.liyan.park.access;

/**
 * ����࣬�̳��Թؿ��࣬��Ҫ�������ɸ���ʵ�֡�
 * ���õ���ģʽ�����췽��Ϊ˽�С���ͨ��getInstance�������ϵͳ��Ψһ���ڵ���ڶ���
 * @author ����
 */
public class Entrance extends Access{

	private static Entrance entrance = null;
	
	//��ڵ���ʾ��Ϣ��һ�������Ϊ����ӭ���١�����������������ǡ�����������
	private String message; 
	
    private Entrance() {
		super();
		this.message = "��ӭ����";
	}

	private Entrance(int readTime, int liftTime, int downTime) {
		super(readTime, liftTime, downTime);
		this.message = "��ӭ����";
	}

	/**
	 * @return ����ϵͳ�е���ڶ���
	 */
	public static Entrance getInstance(){  
        if(entrance == null){  
            entrance = new Entrance();  
        }  
        return entrance;  
    }
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
