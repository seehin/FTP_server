package com.seehin.ftpServer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @Description 用于进入ftp服务器前的用户登录
 * @Author Seehin
 * @Date 2020/6/28 23:39
 */
@Data
@AllArgsConstructor
public class AccountUtil {
    /***/
    private final static Logger logger = LoggerFactory.getLogger(AccountUtil.class);
    /**上下文中的resource的xml文件*/
    private static final String CONFIG_FILE = "server.xml";
    /**系统的默认目录*/
    private static String rootDir;
    /**用于存储用户的信息和密码*/
    private static HashMap<String, String> users = new HashMap<>();

    /**利用JDOM解析器解析XML文件中的用户数据*/
    public static void initAccount() {
        //取出该项目的路径:E:\Java\idea\ftp-server
        StringBuffer pathName = new StringBuffer(System.getProperty("user.dir"));
        //拼接路径
        pathName.append("/src/main/resources/");
        pathName.append(CONFIG_FILE);
        //pathName = "E:\Java\idea\ftp-server/src/main/resources/server.xml"
        File file = new File(pathName.toString());
        //
        try {
            //利用JDOM解析器构建SAXBuilder实例
            SAXBuilder builder = new SAXBuilder();
             Document parse = builder.build(file);
            Element root = parse.getRootElement();

            //配置服务器的默认目录
            rootDir = System.getProperty("user.dir") + root.getChildText("rootDir");
            System.out.println("rootDir is: " + rootDir);

            //在控制台中打印出所有允许登录的用户
            Element elements = root.getChild("users");
            List<Element> element = elements.getChildren();
            System.out.println("所有用户信息：");
            for (Element user : element) {
                String username;
                String password;
                username = user.getChildText("username");
                password = user.getChildText("password");
                System.out.println("用户名：" + username);
                System.out.println("密  码：" + password);
                users.put(username, password);
            }
        } catch (JDOMException | IOException e) {
            logger.warn("控制台打印允许登录用户的数据出错"+e.getMessage()+e.getStackTrace());
        }
    }

    /**
     * 查看是否存在该用户名
     * @param username cmd输入的用户名
     * @return bool
     */
    public static boolean hasUsername(String username) {
        return users.get(username) != null;
    }

    /**
     * 取出该用户的密码，用于对比
     * @param userName 用户名
     * @return string
     */
    public static String getPassword(String userName) {
        return users.get(userName);
    }

    /**
     * 取出系统的默认路径
     * @return string
     */
    public static String getRootPath() {
        return rootDir;
    }

    /**
     * 修改当前路径
     * @param newRootDir 新的路径
     */
    public static void setRootDir(String newRootDir){
        rootDir = newRootDir;
    }
}
