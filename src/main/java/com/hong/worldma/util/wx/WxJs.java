package com.hong.worldma.util.wx;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Date;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-09
 */
public class WxJs {
    /**
     *
     * webwxinit请求参数DeviceID
     * */
    public static String getDeviceID(){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        String result = null;
        try {
            engine.eval("function r(){return\"e\"+(\"\"+Math.random().toFixed(15)).substring(2,17)}");
            if (engine instanceof Invocable) {
                // 调用JS方法
                Invocable invocable = (Invocable) engine;
                result = (String) invocable.invokeFunction("r","");
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取轮询监听链接的 r 请求参数值
     */
    public static int getLoginR(){
        int result = (int) ~new Date().getTime();
        return result;
    }
}
