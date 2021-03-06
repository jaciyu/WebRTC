package com.webrtc.arti.actionhandler;




import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.webrtc.arti.common.ActionType;
import com.webrtc.arti.common.AnswerType;
import com.webrtc.arti.core.ArtiSwitchBoardManager;
import com.webrtc.arti.domain.Client;
import com.webrtc.arti.domain.Server;

/**
 * 当人工总机用户“点击注销”、"退出"、“掉线” 时进行处理
 */
public class LogoutActionHandler extends ActionHandler{
	
	@Override
	public void dispatch(Client client, Queue<Client> requestClientQueue,Map<String, Server> allServerMap, PriorityQueue<Server> idleServerQueue,Map<String, Server> busyServerMap) {
		
		String servername = client.getUsername();
		
		//获取要被删的server
		if(allServerMap.containsKey(servername)) //存在
		{
			Server server = allServerMap.get(servername);
			Map<String, Client> servingClientMap = server.getServingClientMap(); //先拿着它之前服务的用户名单再说
			
			/**
			 * 删除操作
			 */
			//是否在忙碌表中
			if(busyServerMap.containsKey(servername)) //在
			{
				busyServerMap.remove(servername);
			}
			else  //在空闲队列中
			{
				idleServerQueue.remove(server);
			}
			//从总表中删除
			allServerMap.remove(servername);
			
			/**
			 * 后续处理操作
			 */
			if(allServerMap.isEmpty()) //一个客服也没了，通知所有这些用户--> 客服正在休息中，请稍后再试
			{
				if(!requestClientQueue.isEmpty()) //若等待队列仍有用户
				{
					ArtiSwitchBoardManager.sendAnswerToWCS(requestClientQueue, AnswerType.NONEARTI);
					requestClientQueue.clear(); //清空等待队列，需再次请求才能进入队列
				}
				
				if(!servingClientMap.isEmpty()) //若server刚才正服务于若干名用户
					ArtiSwitchBoardManager.sendAnswerToWCS(servingClientMap, AnswerType.NONEARTI);
			}
			else //仍有客服，通知server刚才正在服务的所有用户-->人工总机出现异常，已为您重新连接
			{
				if(!servingClientMap.isEmpty())
				{
					ArtiSwitchBoardManager.sendAnswerToWCS(servingClientMap, AnswerType.ARTILOGOUT);
					
					//为这三个用户重新请求人工总机服务
					ActionHandler requestActionHandler= new RequestActionHandler();
					for(String key : servingClientMap.keySet())
					{
						requestActionHandler.dispatch(servingClientMap.get(key), requestClientQueue, allServerMap, idleServerQueue, busyServerMap);
					}
				}	
			}
		}
		
		ArtiSwitchBoardManager.removeSession(servername); //删除会话
		printAll(requestClientQueue,idleServerQueue, busyServerMap, allServerMap);
	}

	@Override
	public int getType() {

		return ActionType.LOGOUT;
	}

}
