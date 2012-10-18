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
 * 写日志信息类
 * 
 * @author 李延
 */
public class WriterLogInfo {
	// 待写入日志信息对象
	private List<LogInfo> logInfos = new ArrayList<LogInfo>();
	// 仿真起始时间
	private Calendar timeSimulateBegin;
	// 仿真结束时间
	private Calendar timeSimulateEnd;

	public void setTimeSimulateBegin(Calendar timeSimulateBegin) {
		this.timeSimulateBegin = timeSimulateBegin;
	}

	public void setTimeSimulateEnd(Calendar timeSimulateEnd) {
		this.timeSimulateEnd = timeSimulateEnd;
	}

	/**
	 * 添加写入信息
	 * @param logInfo
	 * 信息对象
	 */
	public void addInfo(LogInfo logInfo) {
		logInfos.add(logInfo);
	}

	/**
	 * 将日志信息写到日志文件中
	 */
	public void writeToFile() {
		// 定义文件内容字符流
		StringBuffer bufferContent = new StringBuffer();
		for (int i = 0; i < logInfos.size(); i++) {
			bufferContent.append("当前场内车数:");
			bufferContent.append(logInfos.get(i).getAmountInPark());
			bufferContent.append(" 本次仿真累计入场车数:");
			bufferContent.append(logInfos.get(i).getAmountEnter());
			bufferContent.append(" 本次仿真累计出场车数:");
			bufferContent.append(logInfos.get(i).getAmountExit());
			bufferContent.append(" 本次仿真汽车的平均停车时间:");
			bufferContent
					.append(logInfos.get(i).getParkingTimeAverage() + "\n");

		}
		String Content = bufferContent.toString();

		// 定义文件名字
		StringBuffer bufferFileName = new StringBuffer();
		// 定义时间格式
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		bufferFileName.append(sf.format(timeSimulateBegin.getTime()));
		sf = new SimpleDateFormat("_HHmmss");
		bufferFileName.append(sf.format(timeSimulateEnd.getTime()));
		bufferFileName.append(".rpt");
		// 定义文件名字
		String fileName = bufferFileName.toString();
		// 生成文件到当前目录
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
