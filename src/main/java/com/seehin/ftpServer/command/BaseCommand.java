package com.seehin.ftpServer.command;



import com.seehin.ftpServer.entity.User;

import java.io.BufferedWriter;

public interface BaseCommand {

    void executeCommand(String datas, BufferedWriter writer, User user);
}
