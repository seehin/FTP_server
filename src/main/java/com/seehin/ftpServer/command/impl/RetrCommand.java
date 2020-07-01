package com.seehin.ftpServer.command.impl;

import com.seehin.ftpServer.command.BaseCommand;
import com.seehin.ftpServer.entity.User;
import com.seehin.ftpServer.utils.AccountUtil;
import org.omg.PortableInterceptor.IORInfoOperations;

import java.io.*;
import java.net.Socket;

public class RetrCommand implements BaseCommand {


    @Override
    public void executeCommand(String datas, BufferedWriter writer, User user) {
        File file = new File(AccountUtil.getRootPath() + File.separator + datas);
        if (file.exists()) {
            try {
                writer.write("150 open ascii mode...\r\n");
                writer.flush();
                //开始传输
                Socket socket = new Socket(user.getIp(), user.getPort(), null, 20);
                OutputStream outputStream = socket.getOutputStream();
                FileInputStream inputStream = new FileInputStream(file);
                int length = 0;
                byte[] buff = new byte[1024];
                while ((length = inputStream.read(buff)) != -1) {
                    outputStream.write(buff, 0, length);
                }
                //传输结束
                inputStream.close();
                outputStream.close();
                socket.close();

                writer.write("220 transfer complete...\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                writer.write("220  文件不存在\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
