package com.seehin.ftpServer.command.impl;

import com.seehin.ftpServer.command.BaseCommand;
import com.seehin.ftpServer.entity.User;
import com.seehin.ftpServer.utils.AccountUtil;
import com.seehin.ftpServer.utils.SHA256Util;

import java.io.BufferedWriter;
import java.io.IOException;

public class PassCommand implements BaseCommand {

    @Override
    public void executeCommand(String passWord, BufferedWriter writer, User user) {
        String passWord2Md5 = SHA256Util.encoder(passWord);
        if (passWord2Md5.equals(SHA256Util.encoder(AccountUtil.getPassword(user.getUsername())))) {
            System.out.println("密码正确: " + passWord + " ,SHA245密码：" + passWord2Md5);
            try {
                writer.write("230 密码正确，欢迎回来！\r\n");
                writer.flush();
                user.setPassword(passWord2Md5);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                writer.write("530 密码错误!\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
