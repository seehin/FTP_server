package com.seehin.ftpServer.command.impl;

import com.seehin.ftpServer.command.BaseCommand;
import com.seehin.ftpServer.entity.User;
import com.seehin.ftpServer.sever.FileServer;
import com.seehin.ftpServer.utils.AccountUtil;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Objects;

public class ListCommand implements BaseCommand {

    @Override
    public void executeCommand(String datas, BufferedWriter writer, User user) {
        File file = new File(AccountUtil.getRootPath());
        if (!file.isDirectory()) {
            try {
                writer.write("210  文件目录不存在！\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //拼接文件目录字符串
            StringBuilder dirList = new StringBuilder();
            int count = 1;
            for (String item : Objects.requireNonNull(file.list())) {
                File itemFile = new File(file + File.separator + item);
                String size = FileServer.getFileSize(itemFile);
                if (StringUtils.isEmpty(size)) {
                    size = "dir";
                } else {
                    size += "	file";
                }
                dirList.append(count + "	" + item + "	" + size);
                dirList.append("\r\n");
                count++;
            }
            try {
                //告知客户端：服务器向另外一个端口发送数据
                writer.write("150 open ascii mode...\r\n");
                writer.flush();
                //与客户端发来的ip和端口号连接,自身端口设置为20
                Socket socket = new Socket(user.getIp(), user.getPort(), null, 20);
                System.out.println(socket.getLocalPort());
                BufferedWriter portWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"gbk"));
                portWriter.write(dirList.toString());
                portWriter.flush();
                socket.close();
                writer.write("220 transfer complete...\r\n");
                writer.flush();
                System.out.println(dirList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
