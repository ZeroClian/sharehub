package cn.zeroclian.sharehub.handler;

import cn.hutool.core.util.StrUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ZeroClian
 * @date 2022-03-26 8:24 下午
 */
@Component
public class TextHandler implements WxMpMessageHandler {

    private final String UNKONW = "未识别字符串";

    @Autowired
    private LoginHandler loginHandler;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager)
            throws WxErrorException {
        String openId = wxMpXmlMessage.getFromUser();
        String content = wxMpXmlMessage.getContent();
        String result = UNKONW;
        if (StrUtil.isNotBlank(content)) {
            content = content.toUpperCase().trim();
            //处理登陆字符串
            if (content.indexOf("ZC") == 0) {
                result = loginHandler.handle(openId, content, wxMpService);
            }
        }

        return WxMpXmlOutMessage.TEXT()
                .content(result)
                .fromUser(wxMpXmlMessage.getToUser())
                .toUser(wxMpXmlMessage.getFromUser())
                .build();
    }
}
