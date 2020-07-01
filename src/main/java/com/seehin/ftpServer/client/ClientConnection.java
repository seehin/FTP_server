package com.seehin.ftpServer.client;



import com.seehin.ftpServer.command.BaseCommand;
import com.seehin.ftpServer.entity.User;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.Socket;
/**
 * @Description
 * @Author Seehin
 * @Date 2020/6/28 00:21
 */
public class ClientConnection implements Runnable {

	private Socket socket;
	private BufferedWriter writer;
	private BufferedReader reader;

	public ClientConnection(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"gbk"));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"gbk"));

			// 为各个command传输信息
			User user = new User();
			user.setSocket(socket);
			// 第一次建立连接
			writer.write(">>>connect successfully...\r\n");
			writer.flush();
			reader.readLine();
			writer.write("\r\n");
			writer.flush();

			// 接受客户端发来的信息
			while (true) {
				if (!socket.isClosed()) {
					String result = null;
					try {
						result = reader.readLine();
					} catch (Exception e) {
						System.out.println("客户端强制关闭了服务器");
					}
					// 打印出客户端发送的内容
					System.out.println(result);
					if (!StringUtils.isEmpty(result)) {
						String[] dates = result.split(" ");
						BaseCommand command = CommandClient.parseCommand(dates[0]);
						if (command != null) {
							// 当客户端发来的数据只有命令没有后面的数据时候
							if (dates.length == 1) {
								command.executeCommand("", writer, user);
							} else {
								command.executeCommand(dates[1], writer, user);
							}
						} else {
							writer.write("没有这个命令！\r\n");
							writer.flush();
						}
					} else {
						// 这时客户端强行关闭了连接
						reader.close();
						writer.close();
						socket.close();
						break;
					}
				} else {
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
