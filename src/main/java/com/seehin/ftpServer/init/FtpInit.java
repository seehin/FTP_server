package com.seehin.ftpServer.init;

import com.seehin.ftpServer.client.ClientConnection;
import com.seehin.ftpServer.sever.ThreadServer;
import com.seehin.ftpServer.utils.AccountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description 初始化服务器
 * @Author Seehin
 * @Date 2020/6/28 01:12
 */
public class FtpInit {

    /***/
    private final static Logger logger = LoggerFactory.getLogger(FtpInit.class);
    /***/
    private ServerSocket serverSocket;

    public FtpInit() {
        //通过该方法将所有的允许的用户信息导入内存
        AccountUtil.initAccount();
        try {
            serverSocket = new ServerSocket(9907);
        } catch (IOException e) {
            logger.warn("开辟socket:9907出错"+e.getMessage()+e.getStackTrace());
        }
    }

    public void listen() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientConnection connection = new ClientConnection(socket);
                ThreadServer.getThreadPool().execute(connection);
            }
        } catch (IOException e) {
            logger.warn("线程出错，请检查FtpClient.listen"+e.getMessage()+e.getStackTrace());
        }
    }

}
