package com.hong.worldma.entity.wx;

/**
 * @Description:
 * @Author: hong
 * @Date: 2017-09-12
 */
public class WxUploadImgResp {
    public BaseResponse BaseResponse;
    public String MediaId;
    public int StartPos;
    public int CDNThumbImgHeight;
    public int CDNThumbImgWidth;

    public com.hong.worldma.entity.wx.BaseResponse getBaseResponse() {
        return BaseResponse;
    }

    public void setBaseResponse(com.hong.worldma.entity.wx.BaseResponse baseResponse) {
        BaseResponse = baseResponse;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public int getStartPos() {
        return StartPos;
    }

    public void setStartPos(int startPos) {
        StartPos = startPos;
    }

    public int getCDNThumbImgHeight() {
        return CDNThumbImgHeight;
    }

    public void setCDNThumbImgHeight(int CDNThumbImgHeight) {
        this.CDNThumbImgHeight = CDNThumbImgHeight;
    }

    public int getCDNThumbImgWidth() {
        return CDNThumbImgWidth;
    }

    public void setCDNThumbImgWidth(int CDNThumbImgWidth) {
        this.CDNThumbImgWidth = CDNThumbImgWidth;
    }
}
