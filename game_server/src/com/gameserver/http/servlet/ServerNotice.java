package com.gameserver.http.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.alibaba.fastjson.JSONObject;
import com.gameserver.common.Globals;
import com.gameserver.http.vo.NoticeVO;
import com.gameserver.notice.Notice;

/**
 * 跑马灯
 * @author JavaServer
 *
 */
@SuppressWarnings("serial")
public class ServerNotice extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			BufferedReader  br = new BufferedReader(new  InputStreamReader((ServletInputStream)req.getInputStream(),"UTF-8"));
			StringBuffer sb = new StringBuffer("");  
            String temp;  
            while ((temp = br.readLine()) != null) {  
                sb.append(temp);  
            }  
            br.close();  
            NoticeVO NoticeVO = JSONObject.parseObject(sb.toString(),NoticeVO.class);
            Notice rNotice = Globals.getNoticeService().getNoticeById(NoticeVO.getId());
            if(rNotice != null){
            	if(rNotice.getIsDelete() ==1){
            		return;
            	}
            	rNotice.setContent(NoticeVO.getContent());
            	rNotice.setCreateTime(NoticeVO.getCreateTime());
            	rNotice.setUpdateTime(NoticeVO.getUpdateTime());
            	rNotice.setDailyEndTime(Long.valueOf(NoticeVO.getDailyEndTime()).intValue());
            	rNotice.setDailyStartTime(Long.valueOf(NoticeVO.getDailyStartTime()).intValue());
            	rNotice.setStartTime(NoticeVO.getStartTime());
            	rNotice.setEndTime(NoticeVO.getEndTime());
            	rNotice.setIntervalTime(NoticeVO.getIntervalTime());
            	rNotice.setModified();
            	Globals.getNoticeService().updateNotice(rNotice);
            }else{
            	rNotice = new Notice();
            	rNotice.setContent(NoticeVO.getContent());
            	rNotice.setCreateTime(NoticeVO.getCreateTime());
            	rNotice.setUpdateTime(NoticeVO.getUpdateTime());
            	rNotice.setDailyEndTime(Long.valueOf(NoticeVO.getDailyEndTime()).intValue());
            	rNotice.setDailyStartTime(Long.valueOf(NoticeVO.getDailyStartTime()).intValue());
            	rNotice.setStartTime(NoticeVO.getStartTime());
            	rNotice.setEndTime(NoticeVO.getEndTime());
            	rNotice.setIntervalTime(NoticeVO.getIntervalTime());
            	rNotice.setIsDelete(0);
            	//上一次播放时间
            	rNotice.setLastNoticeTime(new Date().getTime());
            	rNotice.setDbId(NoticeVO.getId());
            	rNotice.active();
            	rNotice.setInDb(false);
            	rNotice.setModified();
            	Globals.getNoticeService().updateNotice(rNotice);
            }
            /**
             * 设置跑马灯的业务
             */
          /*  Globals.getNoticeService().setNoticeRun(rNotice.getDbId());*/
            
		}catch(Exception e){
			e.printStackTrace();
			resp.getWriter().print("{'errorCode':1}");
			return;
		}
		
		resp.getWriter().print("{'errorCode':0}");
	}
	
	/**
	 * 这里主要是删除
	 * @throws IOException 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String id = (String)req.getParameter("id");
		if(StringUtils.isNotBlank(id) && NumberUtils.isNumber(id)){
			  Notice rNotice = Globals.getNoticeService().getNoticeById(Integer.valueOf(id));
			  if(rNotice != null){
				  rNotice.setIsDelete(1);
				  rNotice.setModified();
				  Globals.getNoticeService().removeNotice(rNotice);
			  }
			  resp.getWriter().print("{'errorCode':0}");
			  return;
		}
		
		resp.getWriter().print("{'errorCode':1}");
	}
}
