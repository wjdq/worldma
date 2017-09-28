package com.hong.worldma.service.wx;

import com.hong.worldma.dto.ServerResponse;
import com.hong.worldma.entity.wx.ReqUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-09
 */
@Service
public class CutInService {
    private static Logger logger = LoggerFactory.getLogger(CutInService.class);

    @Autowired
    private OkHttpClient okClient;
    @Autowired
    private WaitLoginService loginService;

    private String getUUID() {
        Request request = new Request.Builder()
                .url(ReqUrl.jslogin).build();
        logger.info("------------------jsloginReq------------");
        Response response = null;
        String result = null;
        try {
            response = okClient.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                logger.info("------------------jsloginResp = " + result);
                return result;
            }
        } catch (IOException e) {
            logger.error("------------------jsloginReqERROR = ", e);
        }
        return null;
    }

    public ServerResponse<String> getQRcode() {
        String jsloginBody = getUUID();
        if (jsloginBody != null) {
            //从jslogin返回值中截取二维码标识
            String uuid = jsloginBody.split("\"")[1];
            logger.info("------------------截取的uuid值 = " + uuid);
            //返回二维码UUID，并开启新的线程，执行后续请求
            WxThreadPool.cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    loginService.waitLoginReq(uuid);
                }
            });
            return ServerResponse.createBySuccess(uuid);
        }else {
            return ServerResponse.createByErrorMessage("无法获取二维码");
        }
    }
}
