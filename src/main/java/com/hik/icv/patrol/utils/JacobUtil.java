package com.hik.icv.patrol.utils;

import com.jacob.activeX.ActiveXComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description java桥工具类
 * @Author LuoJiaLei
 * @Date 2020/6/8
 * @Time 13:37
 */
public class JacobUtil {

    private static final Logger logger = LoggerFactory.getLogger(JacobUtil.class);

    /**
     * @Description 创建/开启vissim服务
     * @Author LuoJiaLei
     * @Date 2020/4/28
     * @Time 11:29
     * @return com.jacob.activeX.ActiveXComponent
     */
    public static synchronized ActiveXComponent vissimCreateComServer() {
        //指定版本打开vissim软件
        ActiveXComponent vissim = new ActiveXComponent("430");
        return vissim;
    }

    /**
     * @Description 销毁/关闭vissim服务
     * @Author LuoJiaLei
     * @Date 2020/4/28
     * @Time 12:35
     * @param vissim: vissim对象
     */
    public static synchronized void vissimCloseComServer(ActiveXComponent vissim) {
        if (vissim == null) {
            return;
        }
        vissim.safeRelease();
    }


    public static void main(String[] args) {

        String vc = "{\"expectSpeed\":80,\"rsuDistance\":200,\"inLengthArray\":[98,388,72,126],\"randSeed\":38,\"keyEvaluation\":[true,true,true,true,true,true,true,],\"simSpeed\":1,\"densityArray\":[800,804,805,808],\"simPeriod\":30,\"vehicleFlowArray\":[0.26,0.64,0.1],\"numRuns\":1,\"obuDelay\":2,\"outLengthArray\":[96,388,70,126],\"useMaxSimSpeed\":true,\"simRes\":7}";


        System.out.println(vc);


    }

}