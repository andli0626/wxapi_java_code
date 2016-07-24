package org.liufeng.course.service;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.liufeng.course.message.resp.TextMessage;
import org.liufeng.course.util.GameUtil;
import org.liufeng.course.util.MessageUtil;

/**
 * 核心服务类
 * 
 * @author liufeng
 * @date 2013-09-29
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
		// 默认返回的文本消息内容
		String respContent = GameService.getGameRule();
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content").trim();
				// 查看游戏帮助
				if (content.equalsIgnoreCase("help")) {
					respContent = GameService.getGameRule();
				}
				// 查看游戏战绩
				else if (content.equalsIgnoreCase("score")) {
					respContent = GameService.getUserScore(request, fromUserName);
				}
				// 如果是4位数字并且无重复
				else if (GameUtil.verifyNumber(content) && !GameUtil.verifyRepeat(content)) {
					respContent = GameService.process(request, fromUserName, content);
				}
				// 输入的格式错误
				else {
					respContent = "请输入4个不重复的数字，例如：0269";
				}
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 关注
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "欢迎关注猜数字游戏！\n回复“help”查看游戏玩法。";
				}
			}
			// 设置文本消息的内容
			textMessage.setContent(respContent);
			// 将文本消息对象转换成xml
			respXml = MessageUtil.messageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
}
