package edu.xidian.liyan.park.access;

/**
 * �����࣬�̳��Թؿ��࣬��Ҫ�������ɸ���ʵ�֡�
 * ���õ���ģʽ�����췽��Ϊ˽�С���ͨ��getInstance����ϵͳ��Ψһ���ڵĳ��ڶ���
 * @author ����
 */
public class Exit extends Access {

	private static Exit exit = null;  
	
	private Exit() {
		super();
	}

	private Exit(int readTime, int liftTime, int downTime) {
		super(readTime, liftTime, downTime);
	}

	public static Exit getInstance(){  
        if(exit == null){  
            exit = new Exit();  
        }  
        return exit;  
    }
}
