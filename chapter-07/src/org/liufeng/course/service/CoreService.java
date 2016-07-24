package org.liufeng.course.service;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.liufeng.course.message.resp.TextMessage;
import org.liufeng.course.util.MessageUtil;

/**
 * 核心服务类
 * 
 * @author liufeng
 * @date 2013-10-26
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */
	public static String processRequest(HttpServletRequest request) {
		// xml格式的消息数据
		String respXml = null;

		try {
			// 公众账号标识
			String accountId = request.getParameter("accountId");
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			// 事件推送
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					// 判断公众账号标识
					if ("aaa".equals(accountId))
						textMessage.setContent("您好，欢迎关注企业A的官方微信！\n\n我是客服小A，有问题问小A！");
					else if ("bbb".equals(accountId))
						textMessage.setContent("嗨，恭喜你终于找到组织了！\n\n我是客服小B，有问题直接问我哦！");
					respXml = MessageUtil.messageToXml(textMessage);
				} else {
					// TODO 其他情况
				}
			} else {
				// TODO 其他情况
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
}
