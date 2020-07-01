package com.seehin.ftpServer.command.impl;

import com.seehin.ftpServer.command.BaseCommand;
import com.seehin.ftpServer.entity.User;

import java.io.BufferedWriter;
import java.io.IOException;

public class QuitCommand implements BaseCommand {

    @Override
    public void executeCommand(String datas, BufferedWriter writer, User user) {
        try {
            writer.write("221 再见\r\n");
            writer.flush();
            user.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
