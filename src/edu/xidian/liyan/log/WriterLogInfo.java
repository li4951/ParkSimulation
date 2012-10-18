package edu.xidian.liyan.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * д��־��Ϣ��
 * 
 * @author ����
 */
public class WriterLogInfo {
	// ��д����־��Ϣ����
	private List<LogInfo> logInfos = new ArrayList<LogInfo>();
	// ������ʼʱ��
	private Calendar timeSimulateBegin;
	// �������ʱ��
	private Calendar timeSimulateEnd;

	public void setTimeSimulateBegin(Calendar timeSimulateBegin) {
		this.timeSimulateBegin = timeSimulateBegin;
	}

	public void setTimeSimulateEnd(Calendar timeSimulateEnd) {
		this.timeSimulateEnd = timeSimulateEnd;
	}

	/**
	 * ���д����Ϣ
	 * @param logInfo
	 * ��Ϣ����
	 */
	public void addInfo(LogInfo logInfo) {
		logInfos.add(logInfo);
	}

	/**
	 * ����־��Ϣд����־�ļ���
	 */
	public void writeToFile() {
		// �����ļ������ַ���
		StringBuffer bufferContent = new StringBuffer();
		for (int i = 0; i < logInfos.size(); i++) {
			bufferContent.append("��ǰ���ڳ���:");
			bufferContent.append(logInfos.get(i).getAmountInPark());
			bufferContent.append(" ���η����ۼ��볡����:");
			bufferContent.append(logInfos.get(i).getAmountEnter());
			bufferContent.append(" ���η����ۼƳ�������:");
			bufferContent.append(logInfos.get(i).getAmountExit());
			bufferContent.append(" ���η���������ƽ��ͣ��ʱ��:");
			bufferContent
					.append(logInfos.get(i).getParkingTimeAverage() + "\n");

		}
		String Content = bufferContent.toString();

		// �����ļ�����
		StringBuffer bufferFileName = new StringBuffer();
		// ����ʱ���ʽ
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		bufferFileName.append(sf.format(timeSimulateBegin.getTime()));
		sf = new SimpleDateFormat("_HHmmss");
		bufferFileName.append(sf.format(timeSimulateEnd.getTime()));
		bufferFileName.append(".rpt");
		// �����ļ�����
		String fileName = bufferFileName.toString();
		// �����ļ�����ǰĿ¼
		File logFile = new File("./log/" + fileName);
		try {
			FileWriter fileWriter = new FileWriter(logFile);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(Content);
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
