package com.seehin.ftpServer.command.impl;

import com.seehin.ftpServer.command.BaseCommand;
import com.seehin.ftpServer.entity.User;
import com.seehin.ftpServer.utils.AccountUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * @Description [todo]
 * @Author Seehin
 * @Date 2020/7/1 17:09
 */
public class CwdCommand implements BaseCommand {

    @Override
    public void executeCommand(String datas, BufferedWriter writer, User user) {
        //
        String returnLastPage = "..";

        if (returnLastPage.equals(datas)){
            //说明要返回上一页
            AccountUtil.setRootDir(disposeRootPathAfterExitDoc());
            System.out.println("当前的文件路径："+AccountUtil.getRootPath());
        }else {
            //说明要进入新的文件夹
            AccountUtil.setRootDir(disposeRootPathAfterEnterDoc(datas));
            System.out.println("当前的文件路径："+AccountUtil.getRootPath());
        }
        try {
            writer.write("200 "+AccountUtil.getRootPath()+"\r\n");
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String disposeRootPathAfterEnterDoc(String datas){
        return AccountUtil.getRootPath()+File.separator+datas;
    }

    private String disposeRootPathAfterExitDoc(){
        //反转后的地址
        StringBuffer pathOfReverse = new StringBuffer(AccountUtil.getRootPath()).reverse();
        String newPath = pathOfReverse.toString();

        //取出第一个 \ 的索引序号
        int index = newPath.indexOf("\\");
        newPath = newPath.substring(index+1);
        pathOfReverse = new StringBuffer(newPath).reverse();
        System.out.println("处理后的字符串"+pathOfReverse);
        return pathOfReverse.toString();
    }
}
