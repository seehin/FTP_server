package com.seehin.ftpServer.command.impl;

import com.seehin.ftpServer.command.BaseCommand;
import com.seehin.ftpServer.entity.User;

import java.io.BufferedWriter;
import java.io.IOException;

public class PortCommand implements BaseCommand {

    @Override
    public void executeCommand(String datas, BufferedWriter writer, User user) {
        String[] result = datas.split(",");
        String ip = result[0] + "." + result[1] + "." + result[2] + "." + result[3];
        System.out.println("ip:" + ip);
        int port = Integer.parseInt(result[4]) * 256 + Integer.parseInt(result[5]);
        System.out.println("port:" + port);
        try {
            writer.write("200 Received IP number and port number\r\n");
            writer.flush();
            user.setIp(ip);
            user.setPort(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
