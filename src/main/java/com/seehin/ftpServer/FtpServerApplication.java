package com.seehin.ftpServer;

import com.seehin.ftpServer.init.FtpInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FtpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FtpServerApplication.class, args);
        FtpInit ftpServer = new FtpInit();
        ftpServer.listen();
    }

}
