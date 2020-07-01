package com.seehin.ftpServer.client;

import com.seehin.ftpServer.command.BaseCommand;
import com.seehin.ftpServer.command.impl.*;
import com.seehin.ftpServer.entity.User;

/**
 * @Description 工厂设计模式: 指令集
 * @author Seehin
 * @date 2020/6/28 17:29
 */
public class CommandClient {

    public static BaseCommand parseCommand(String name) {
        BaseCommand command = null;
        switch (name) {
            //更改当前目录
            case "CWD":
                command = new CwdCommand();
                break;
            //输入用户名
            case "USER":
                command = new UserCommand();
                break;
            //输入用户密码
            case "PASS":
                command = new PassCommand();
                break;
            //上传文件
            case "STOR":
                command = new StorCommand();
                break;
            //下载文件
            case "RETR":
                command = new RetrCommand();
                break;
            //查看端口号
            case "PORT":
                command = new PortCommand();
                break;
            //查看文件目录
            case "LIST":
                command = new ListCommand();
                break;
            //退出服务器
            case "QUIT":
                command = new QuitCommand();
                break;
            default:
                break;
        }
        return command;
    }
}
