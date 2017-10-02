package com.hong.worldma.service.wx;

import com.alibaba.fastjson.JSON;
import com.hong.worldma.entity.wx.ReqMethodParam;
import com.hong.worldma.util.wx.WxJs;
import com.sun.imageio.plugins.bmp.BMPImageReader;
import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.jpeg.JPEGImageReader;
import com.sun.imageio.plugins.png.PNGImageReader;
import com.hong.worldma.entity.wx.ReqHeader;
import com.hong.worldma.entity.wx.WxUploadImgResp;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-12
 */
@Service
public class WxUploadImgService {

    private static Logger logger = LoggerFactory.getLogger(WxUploadImgService.class);
    @Autowired
    private OkHttpClient okClient;

    public String upLoadImg(ReqMethodParam param, String pathname) {
        String mediaId;
        File file = new File(pathname);
        long fileLength = file.length();
        String fileName = file.getName();
//        System.out.println("-----------------------------------fileName = " + fileName);
        //默认类型
        String mediaType = "image/png";
        //获得图片的类型
        ImageInputStream imageInputStream = null;
        try {
            imageInputStream = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> iter = ImageIO.getImageReaders(imageInputStream);
            if (null != iter && iter.hasNext()) {
                ImageReader reader = iter.next();
                if (reader instanceof GIFImageReader)
                    mediaType = "image/gif";
                else if (reader instanceof JPEGImageReader)
                    mediaType = "image/jpeg";
                else if (reader instanceof PNGImageReader)
                    mediaType = "image/png";
                else if (reader instanceof BMPImageReader)
                    mediaType = "application/x-bmp";
//                mediaType = reader.getFormatName(); //获得图片的类型
            }
        } catch (IOException e) {
            logger.error("----------WxUploadImg---------获得图片的类型错误------无法上传图片", e);
            return null;
        }
        logger.info("------------WxUploadImg----------------文件类型= " + mediaType);
        RequestBody fileBody = RequestBody.create(MediaType.parse(mediaType), file);
        String cookie = new StringBuffer().append("mm_lang=zh_CN; ").append(param.getCv().getWxuin()).append("; ").append(param.getCv().getWxsid())
                .append("; ").append(param.getCv().getWxloadtime()).append("; ").append(param.getCv().getWebwx_data_ticket())
                .append("; ").append(param.getCv().getWebwxuvid()).append("; ").append(param.getCv().getWebwx_auth_ticket())
                .append("; ").append(param.getCv().getWxpluginkey()).toString();
        logger.info("----------------------WxUploadImg--reqUrl--cookie = " + cookie);
        RequestBody requestBody = new MultipartBody.Builder("---------------------------2474672725102")
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", "WU_FILE_1")
                .addFormDataPart("name", fileName)
                .addFormDataPart("type", mediaType)
                .addFormDataPart("lastModifiedDate", new Date() + "")
                .addFormDataPart("size", fileLength + "")
                .addFormDataPart("mediatype", "pic")
                .addFormDataPart("uploadmediarequest", "{\"UploadType\":2,\"BaseRequest\":{\"Uin\":" + param.getP().getWxuin() + ",\"Sid\":\"" + param.getP().getWxsid() + "\",\"Skey\":\"" + param.getP().getSkey() + "\",\"DeviceID\":\"" + WxJs.getDeviceID() + "\"},\"ClientMediaId\":" + new Date().getTime() + ",\"TotalLen\":" + fileLength + ",\"StartPos\":0,\"DataLen\":" + fileLength + ",\"MediaType\":4,\"FromUserName\":\"" + param.getUserName() + "\",\"ToUserName\":\"filehelper\",\"FileMd5\":\"f00332ae484531bcd0e834561d077614\"}")
                .addFormDataPart("webwx_data_ticket", param.getCv().getWebwx_data_ticket())
                .addFormDataPart("pass_ticket", param.getP().getPass_ticket())
                .addFormDataPart("filename", fileName, fileBody)
                .build();
        Request request = new Request.Builder()
                .url(param.getLoadmediaUrl())
                .addHeader("Host", param.getLoadmediaHost())
                .addHeader("Accept", ReqHeader.Accept[0])
                .addHeader("Accept-Language", ReqHeader.Accept_Language)
                .addHeader("Connection", ReqHeader.Connection)
                .addHeader("Referer", param.getReferer())
                .addHeader("user-Agent", ReqHeader.User_Agent)
                .addHeader("Content-Length", fileLength+"")
                .addHeader("Origin", param.getReferer())
                .addHeader("DNT", ReqHeader.DNT)
                .addHeader("Cookie", cookie)
                .post(requestBody)
                .build();
        Response response = null;
        String result = null;
        try {
            response = okClient.newCall(request).execute();
            result = response.body().string();
            logger.info("------------------WxUploadImg--reqUrlResp = " + result);
            WxUploadImgResp wxUploadImgResp = JSON.parseObject(result, WxUploadImgResp.class);
            mediaId = wxUploadImgResp.getMediaId();
            return mediaId;
        } catch (IOException e) {
            logger.error("------------------WxUploadImg----------------ReqERROR = ", e);
        }
//        final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
//        OkHttpClient okHttpClient = httpBuilder
//                //设置超时
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS)
//                .build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                logger.info("-------------------WxUploadImg--reqUrlResp = " + response.body().string());
//            }
//        });
        return null;
    }
}
