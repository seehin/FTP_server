package com.seehin.ftpServer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.Socket;

/**
 * @Description 用户信息实体
 * @Author Seehin
 * @Date 2020/6/28 23:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String username;

    private String password;

    private String ip;

    private int port;

    private Socket socket;

}
