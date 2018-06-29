package com.code.body.readInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.code.body.packageObj.EveryPackageVO;
import com.gameserver.common.msg.MessageType;

/**
 * 
 * 读取所有的接口
 * @author JavaServer
 *
 */
public class ReadInterfaceUtils {
	//所有的接口的 msg和 data
	static List<EveryPackageVO> EveryPackageVOS = new ArrayList<EveryPackageVO>();
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, IllegalArgumentException, IllegalAccessException{
//		Class clazz = Class.forName("com.code.body.packageObj.EveryPackageVO");
		//生成所有的接口
		readInterfaces();
		//再生成 TypeScript 调用
		produceInvoke();
		//生成MessageType
		produceMessageType();
		
		
		System.out.println("ok喽... ...");
	}
	
	





	/**
     * 判断 是大端 还是 小端
     * 将字节数组（byte[]）转为整形(int)
     */
   /* public static void main(String[] args) throws IOException
    {
        byte[] byteAr = new byte[]{0x78,0x56,0x34,0x12};
        ByteArrayInputStream bais = new ByteArrayInputStream(byteAr);
        DataInputStream dis = new DataInputStream(bais);
        System.out.println(Integer.toHexString(dis.readInt()));
    }*/
	
	//读取game_server 中的接口（每个包下边都有接口）
	public static void readInterfaces() throws ClassNotFoundException, IOException{
		File interfaceFile = new File("C:\\d_sortwire\\eclipse_workspace\\game_server\\src\\com\\gameserver");
		File[] interfaceFiles = interfaceFile.listFiles();
		
		
		//一个接口 一个接口的生成
		for(File file:interfaceFiles){
			//每一个package包
			File[] insideFiles = file.listFiles();
			if(insideFiles == null){
				continue;
			}
			if(!file.getName().equals("bazoo") 
					&& !file.getName().equals("bazooachieve")
					&& !file.getName().equals("bazoogift")
					&& !file.getName().equals("bazoonewguy")
					&& !file.getName().equals("bazoorank")
					&& !file.getName().equals("bazoosignin")
					&& !file.getName().equals("bazootask")
					&& !file.getName().equals("bazoopersonal")
					&& !file.getName().startsWith("chat")
					&& !file.getName().startsWith("common")
					&& !file.getName().startsWith("human")
					&& !file.getName().startsWith("item")
					&& !file.getName().startsWith("mail")
					&& !file.getName().startsWith("notice")
					&& !file.getName().startsWith("recharge")
					&& !file.getName().startsWith("relation")
					&& !file.getName().startsWith("player")){
				continue;
			}
			EveryPackageVO EveryPackageVO = new EveryPackageVO();
			EveryPackageVO.setFileName(file.getName());
			//有个特殊的 com.core.util.KeyValuePair 单独放进去
			if(file.getName().equals("human")){
				EveryPackageVO.getDatas().add(Class.forName("com.core.util.KeyValuePair"));
			}
			for(File insideFile:insideFiles){
				//每个包的package和data
				//过滤出接口和data
				if(insideFile.getName().equals("msg")){
					String[] perMsgs = insideFile.list();
					for(String perMsg:perMsgs){
						if(!perMsg.endsWith("java") 
								|| perMsg.contains("Slot")
								|| perMsg.contains("CGMessage")
								|| perMsg.contains("GCMessage")
								|| perMsg.contains("CGExpDouble")
								|| perMsg.contains("GCExpDouble")
								|| perMsg.contains("GCLevelGiftMore")
								|| perMsg.contains("GCHumanBagInfoData")
								|| perMsg.contains("CGRoomBigwinGift")
								|| perMsg.contains("CGHumanVideoNum")
								|| perMsg.contains("GCHumanVideoNum")
								|| perMsg.contains("CGHumanNewGuide")
								|| perMsg.contains("CGHumanDetailInfoTodayView")
								
								|| perMsg.contains("CGDealWithReward")
								|| perMsg.contains("CGDeleteMail")
								|| perMsg.contains("CGReadMail")
								|| perMsg.contains("CGReceiveAll")
								|| perMsg.contains("CGSendMail")
								|| perMsg.contains("GCDealWithReward")
								|| perMsg.contains("GCDeleteMail")
								|| perMsg.contains("GCReadMail")
								|| perMsg.contains("GCReceiveAll")
								|| perMsg.contains("GCSendMail")
								
								
								|| perMsg.contains("CGCouponExist")
								|| perMsg.contains("CGOrderAmazon")
								|| perMsg.contains("CGOrderAmazonDelivery")
								|| perMsg.contains("CGRequestOrderMol")
								|| perMsg.contains("CGRequestOrderMycard")
								|| perMsg.contains("CGValidateOrderMol")
								|| perMsg.contains("CGValidateOrderMycard")
								|| perMsg.contains("GCCouponExist")
								|| perMsg.contains("GCDoubleTurn")
								|| perMsg.contains("GCMolOrder")
								|| perMsg.contains("GCMycardAuthcode")
								|| perMsg.contains("GCObtainCoupon")
								|| perMsg.contains("GCOrderAmazonDelivery")
								
								|| perMsg.contains("CGEnterFriendsRoom")
								|| perMsg.contains("CGFacebookNum")
								|| perMsg.contains("CGFriendGift")
								|| perMsg.contains("CGFriendGiftGet")
								|| perMsg.contains("CGLoadFriendGiftList")
								|| perMsg.contains("CGStrangerList")
								
								|| perMsg.contains("GCFriendGift")
								|| perMsg.contains("GCFriendGiftGet")
								|| perMsg.contains("GCFriendGiftSync")
								|| perMsg.contains("GCLoadFriendGiftList")
								|| perMsg.contains("GCStrangerList")
								
								){
							continue;
						}
						if(!perMsg.startsWith("CG") && !perMsg.startsWith("GC") ){
							continue;
						}
						String[] msgArr = perMsg.split("\\.");
						EveryPackageVO.getMsgs().add(Class.forName("com.gameserver."+file.getName()+"."+insideFile.getName()+"."+msgArr[0]));
						EveryPackageVO.addUnderLineMsg(msgArr[0]);
					}
				}
				if(insideFile.getName().equals("data")){
					String[] perDatas = insideFile.list();
					File[] secondDatas = insideFile.listFiles();
					//data下边还有包 例如 texas
					for(File secondData:secondDatas){
						String[] secondDataArr = secondData.list();
						if(secondDataArr != null){
							for(String secondName :secondDataArr){
								String[] dataArr = secondName.split("\\.");
								EveryPackageVO.getDatas().add(Class.forName("com.gameserver."+file.getName()+"."+insideFile.getName()+"."+secondData.getName()+"."+dataArr[0]));
							}
						}
					}
					//
					for(String perData:perDatas){
						if(!perData.endsWith("java")){
							continue;
						}
						String[] dataArr = perData.split("\\.");
						EveryPackageVO.getDatas().add(Class.forName("com.gameserver."+file.getName()+"."+insideFile.getName()+"."+dataArr[0]));
					}
				}
				
			}
			
			EveryPackageVOS.add(EveryPackageVO);
		}
		
		//代码生成 都放在这里
		String target = "C:\\d_sortwire\\eclipse_workspace\\h5CodeBuilder\\h5code";
		File targetFile = new File(target);
		//如果存在就删除,删除完在创建
		if(targetFile.exists()){
			deleteDir(targetFile);
			targetFile.mkdir();
		}else{//不存在就直接创建
			targetFile.mkdir();
		}
		
		//然后接着弄
		for(EveryPackageVO everyPackageVO:EveryPackageVOS){
			String fileName = everyPackageVO.getFileName();
			List<Object> msgs = everyPackageVO.getMsgs();
			List<Object> datas = everyPackageVO.getDatas();
			
			//创建每个包的文件夹
			String perPackage = "C:\\d_sortwire\\eclipse_workspace\\h5CodeBuilder\\h5code\\";
			String perPackageNew = perPackage+fileName;
			File perPackageFile = new File(perPackageNew);
			perPackageFile.mkdir();
			//创建msg包
			String perPackageMsg = perPackageNew+"\\msg";
			File perPackageMsgFile = new File(perPackageMsg);
			perPackageMsgFile.mkdir();
			//创建data包
			String perPackageData = perPackageNew+"\\data";
			File perPackageDataFile = new File(perPackageData);
			perPackageDataFile.mkdir();
			
			
			//在每个包下生成文件
			writeFiles(perPackageMsg,perPackageData,msgs,datas);
		}
		
		
		
		
	}

	//在每个包下生成文件
	@SuppressWarnings("rawtypes")
	private static void writeFiles(String perPackageMsg,String perPackageData, List<Object> msgs,
			List<Object> datas) throws IOException {
		//所有的msg都写到msg包里
		for(Object obj:msgs){
			Class clazz = (Class)obj;
			String content = getContent(clazz);
			String msgFileStr = perPackageMsg+"\\"+clazz.getSimpleName()+".ts";
			writeFile(content,msgFileStr);
			
			
		}
		//所有的data都写到data包里
		for(Object obj:datas){
			Class clazz = (Class)obj;
			String content = getContent(clazz);
			String dataFileStr = perPackageData+"\\"+clazz.getSimpleName()+".ts";
			writeFile(content,dataFileStr);
		}
		
	}
	
	/**
	 * 获取内容
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static String getContent( Class clazz) {
		String clazzName = clazz.getSimpleName();
		StringBuilder content =  new StringBuilder();
		content.append("module game \r\n{\r\n");
		content.append("\texport class "+clazzName+"\r\n\t{\r\n");
				
		Field[] fields = clazz.getDeclaredFields();
		//生成字段
		for(Field field:fields){
			String fieldName = field.getName();
			if(fieldName.contains("_")){
				continue;
			}
			if(fieldName.equals("logger")){
				continue;
			}
			//判断对象是否为数组
			String fieldType = field.getType().getSimpleName();
			Class fieldTypeClass = field.getClass();
			if(fieldTypeClass.isArray()){
				if(fieldType.startsWith("long") || fieldType.startsWith("int")){
					content.append("\t\tpublic "+fieldName+":number[];\r\n");
				}else if(fieldType.startsWith("String")){
					content.append("\t\tpublic "+fieldName+":string[];\r\n");
				}else{
					content.append("\t\tpublic "+fieldName+":"+fieldType+"[];\r\n");
				}
			}else{
				if(fieldType.equals("long") || fieldType.equals("int") 
						|| fieldType.equals("Integer") || fieldType.equals("Long") 
						|| fieldType.equals("short") || fieldType.equals("Short")){
					content.append("\t\tpublic "+fieldName+":number;\r\n");
				}else if(fieldType.equals("String")){
					content.append("\t\tpublic "+fieldName+":string;\r\n");
					
				}else if(fieldType.equals("long[]") || fieldType.equals("int[]") 
						|| fieldType.equals("Integer[]") || fieldType.equals("Long[]")
						|| fieldType.equals("short[]") || fieldType.equals("Short[]")){
					
					content.append("\t\tpublic "+fieldName+":number[];\r\n");
					
				}else if(fieldType.equals("String[]")){
					content.append("\t\tpublic "+fieldName+":string[];\r\n");
				}else{
					content.append("\t\tpublic "+fieldName+":"+fieldType+";\r\n");
				}
			}
		}
		//生成 get set 函数
		for(Field field:fields){
			String fieldName = field.getName();
			if(fieldName.contains("_")){
				continue;
			}
			//判断对象是否为数组
//			String fieldType = field.getType().getSimpleName();
//			Class fieldTypeClass = field.getClass();
			/*if(fieldTypeClass.isArray()){
				String c = String.valueOf(fieldName.charAt(0));
				content.append("        public  get"+fieldName.replaceFirst(c, c.toUpperCase())+"(){\r\n"
							 + "        	return this."+fieldName+"\r\n"
							 + "        }\r\n");
				content.append("        public  set"+fieldName.replaceFirst(c, c.toUpperCase())+"( "+fieldName+":"+fieldType+"[]){\r\n"
							+  "        		this."+fieldName+"="+fieldName+"\r\n"
							+  "        }\r\n");
			}else{
				String c = String.valueOf(fieldName.charAt(0));
				content.append("        public  get"+fieldName.replaceFirst(c, c.toUpperCase())+"(){\r\n"
							 + "        	return this."+fieldName+"\r\n"
							 + "        }\r\n");
				content.append("        public  set"+fieldName.replaceFirst(c, c.toUpperCase())+"( "+fieldName+":"+fieldType+"){\r\n"
							 + "        		this."+fieldName+"="+fieldName+"\r\n"
							 + "        }\r\n");
			}*/
		}
		
		
						
		content.append("\t}\r\n");
		content.append("}");
		return content.toString();
	}

	@SuppressWarnings("resource")
	private static void writeFile(String content,String targetFileName) throws IOException{
		FileOutputStream fileOutputStream = new FileOutputStream(targetFileName);
		byte[] b = content.getBytes();
		fileOutputStream.write(b);
	}
	
	
	
	/**
	 * 删除文件夹
	 * @param dir
	 * @return
	 */
   private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
   
   
   

   /**
    * 生成TypeScript 调用
 * @throws IOException 
    */
	private static void produceInvoke() throws IOException {
		String target = "C:\\d_sortwire\\eclipse_workspace\\h5CodeBuilder\\h5code";
		File targetFile = new File(target);
		File[] everyfiles = targetFile.listFiles();
		
		//在每个 包下 插入一个 message 包，用来生成 GC 或者 CG 的调用
		for(File perFile:everyfiles){
			File[] existFile=perFile.listFiles();
			if(existFile == null){
				continue;
			}
			
			String finalTarget = perFile.toString()+"\\message";
			File finalTargetFile = new File(finalTarget);
			//如果存在就删除,删除完在创建
			if(finalTargetFile.exists()){
				deleteDir(finalTargetFile);
				finalTargetFile.mkdir();
			}else{//不存在就直接创建
				finalTargetFile.mkdir();
			}
			
			//过滤 出 msg 来
			File msgFile = null;
			for(File f:existFile){
				if(f.getName().equals("msg")){
					msgFile=f;
				}
			}
			//生成 GC
			writeCG_GCContent(finalTargetFile,msgFile,perFile.getName());
			//生成CG
			writeCG_CGContent(finalTargetFile,msgFile,perFile.getName());
			
		}
	}
   
   
   private static void writeCG_CGContent(File finalTargetFile, File msgFile,
		String packageName) throws IOException {
	   String clazzName = packageName;
	   String c = String.valueOf(clazzName.charAt(0));
	   clazzName = clazzName.replaceFirst(c, c.toUpperCase());
	   StringBuilder content =  new StringBuilder();
	   content.append("module game\r\n{\r\n");
	   content.append("\texport class "+clazzName+"CGMessage\r\n\t{\r\n");
	   
	   
	   
	   
	   EveryPackageVO everyPackageVO = getMsg(packageName);
	   for(int i=0;i<everyPackageVO.getMsgWithUnderlines().size();i++){
		   Object msg=everyPackageVO.getMsgWithUnderlines().get(i);
		   String typeName = (String)msg;
		   if(typeName.startsWith("CG")){
			   String noUnderLine = typeName;
			   String noUnderLineName = noUnderLine.replaceAll("_", "");
			   content.append("\t\tpublic static "+typeName+"(data:"+noUnderLineName+")\r\n\t\t{\r\n");
			   content.append("\t\t\tlet msgOut : NetMessageOut = new NetMessageOut(NetMessageType."+typeName.toUpperCase()+", data);\r\n");
			   content.append("\t\t\tNetMessageHandler.sendMessage(msgOut);\r\n");
			   content.append("\t\t}\r\n");
			   if(i != everyPackageVO.getMsgWithUnderlines().size()-1){
				   content.append("\r\n");
			   }
		   }
	   }
	   content.append("\t}\r\n");
	   content.append("}\r\n");
	  
	   writeFile(content.toString(),finalTargetFile.toString()+"\\"+clazzName+"CGMessage.ts");
	
   }







   private static void writeCG_GCContent(File finalTargetFile,File msgFile,String packageName) throws IOException {
	   String clazzName = packageName;
	   String c = String.valueOf(clazzName.charAt(0));
	   clazzName = clazzName.replaceFirst(c, c.toUpperCase());
	   StringBuilder content =  new StringBuilder();
	   content.append("module game\r\n{\r\n");
	   content.append("\texport class "+clazzName+"GCMessage extends AbstractMessageReceiver\r\n\t{\r\n");
	   content.append("\t\tprivate static instance : "+clazzName+"GCMessage;\r\n");
	   content.append("\t\tpublic static getInstance() : "+clazzName+"GCMessage\r\n\t\t{\r\n");
	   content.append("\t\t\tif("+clazzName+"GCMessage.instance == null)\r\n");
	   content.append("\t\t\t\t"+clazzName+"GCMessage.instance = new "+clazzName+"GCMessage();\r\n");
	   content.append("\t\t\treturn "+clazzName+"GCMessage.instance;\r\n");
	   content.append("\t\t}\r\n");
	   
	   
	   EveryPackageVO everyPackageVO = getMsg(packageName);
	   String[] allMsg = msgFile.list();
	   content.append("\t\tpublic collectObservers() : void\r\n\t\t{\r\n");
	   for(Object msg:everyPackageVO.getMsgWithUnderlines()){
		   String typeName = (String)msg;
		   if(typeName.startsWith("GC")){
			   content.append("\t\t\tthis.register(NetMessageType."+typeName.toUpperCase()+", this."+typeName.toUpperCase()+");\r\n");
		   }
	   }
	   content.append("\t\t}\r\n");
	   content.append("\r\n");
	   for(int i=0;i<everyPackageVO.getMsgWithUnderlines().size();i++){
		   Object msg=everyPackageVO.getMsgWithUnderlines().get(i);
		   String typeName = (String)msg;
		   if(typeName.startsWith("GC")){
			   String noUnderLine = typeName;
			   String noUnderLineName = noUnderLine.replaceAll("_", "");
			   content.append("\t\tpublic "+typeName.toUpperCase()+"(data:NetMessageIn) : void\r\n\t\t{\r\n");
			   content.append("\t\t\tlet msg:"+noUnderLineName+" = data.getContent<"+noUnderLineName+">();\r\n");
			   content.append("\t\t\t"+clazzName+"Handler.getInstance()."+typeName.toUpperCase()+"(msg);\r\n");
			   content.append("\t\t}\r\n");
			   if(i != everyPackageVO.getMsgWithUnderlines().size()-1){
				   content.append("\r\n");
			   }
		   }
	   }
	   content.append("\t}\r\n");
	   content.append("}\r\n");
	  
	   writeFile(content.toString(),finalTargetFile.toString()+"\\"+clazzName+"GCMessage.ts");
	
   }



   private static EveryPackageVO getMsg(String packageName){
	   for(EveryPackageVO every:EveryPackageVOS){
		   if(every.getFileName().equals(packageName)){
			   return every;
		   }
	   }
	   
	   return null;
   }




/**
	 * 生成  MessageType
	 * @param dir
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
   private static void produceMessageType() throws IOException, IllegalArgumentException, IllegalAccessException {
	   Class clazz = MessageType.class;
	   String targetPackage = "C:\\d_sortwire\\eclipse_workspace\\h5CodeBuilder\\h5code\\Net"+clazz.getSimpleName()+".ts";
	   File file = new File(targetPackage);
	   //如果存在就删除,删除完在创建
 	   if(file.exists()){
	 	   deleteDir(file);
 	   }
	   StringBuilder content =  new StringBuilder();
	   content.append("module game\r\n{\r\n");
	   content.append("\texport enum Net"+clazz.getSimpleName()+"\r\n\t{\r\n");
	   Field[] fields = clazz.getFields();
	   for(int i=0;i<fields.length;i++){
		   Field field = fields[i];
		   String fieldName = field.getName();
		   if(fieldName.equals("CG_ENTER_FRIENDS_ROOM")){
			   System.out.println();
		   }
		   if(containTheUseLess(fieldName)){
			   continue;
		   }
		   String fieldType = field.getType().getSimpleName();
		   if(i==fields.length-1){//最后一行
			   content.append("\t\t"+fieldName.toUpperCase()+"="+field.get(null)+"\r\n");
		   }else{
			   content.append("\t\t"+fieldName.toUpperCase()+"="+field.get(null)+",\r\n");
		   }
	   }
	   content.append("\t};\r\n");
	   content.append("}");
	   writeFile(content.toString(),file.toString());
   }
   
   
   private static boolean containTheUseLess(String fieldName){
	   if(    fieldName.contains("SLOT")
		   || fieldName.contains("TEXAS")
		   || fieldName.contains("CLUB")
		   || fieldName.contains("BACCART")
		   || fieldName.contains("ACTIVITY")
		   || fieldName.contains("LUCKY")
		   || fieldName.contains("JACKPOT")
		   || fieldName.contains("STAUS_BEGIN")
		   || fieldName.contains("COMMON_BEGIN")
		   || fieldName.contains("PLAYER_BEGIN")
		   || fieldName.contains("GC_SNG_RANK")
		   || fieldName.contains("HUMAN_BEGIN")
		   || fieldName.contains("CG_ROOM_BIGWIN_GIFT")
		   || fieldName.contains("CG_HUMAN_VIDEO_NUM")
		   || fieldName.contains("CG_HUMAN_NEW_GUIDE")
		   || fieldName.contains("CG_HUMAN_DETAIL_INFO_TODAY_VIEW")
		   || fieldName.contains("CG_EXP_DOUBLE")
		   || fieldName.contains("GC_EXP_DOUBLE")
		   || fieldName.contains("SHOP_BEGIN")
		   || fieldName.contains("CG_SHOP_LIST")
		   || fieldName.contains("GC_SHOP_LIST")
		   || fieldName.contains("CG_BUY_ITEM")
		   || fieldName.contains("GC_BUY_ITEM")
		   || fieldName.contains("WEEK_CARD_BEGIN")
		   || fieldName.contains("GC_HUMAN_WEEK_CARD_INFO_DATA")
		   || fieldName.contains("CG_WEEK_CARD_GET")
		   || fieldName.contains("GC_WEEK_CARD_GET")
		   || fieldName.contains("MONTH_CARD_BEGIN")
		   || fieldName.contains("GC_HUMAN_MONTH_CARD_INFO_DATA")
		   || fieldName.contains("CG_MONTH_CARD_GET")
		   || fieldName.contains("GC_MONTH_CARD_GET")
		   || fieldName.contains("ITEM_BEGIN")
		   || fieldName.contains("GC_HUMAN_BAG_INFO_DATA")
		   || fieldName.contains("RANK_BEGIN")
		   || fieldName.contains("CG_COMMON_RANK")
		   || fieldName.contains("GC_COMMON_RANK")
		   || fieldName.contains("MAIL_BEGIN")
		   || fieldName.contains("CG_SEND_MAIL")
		   || fieldName.contains("GC_SEND_MAIL")
		   || fieldName.contains("CG_READ_MAIL")
		   || fieldName.contains("GC_READ_MAIL")
		   || fieldName.contains("CG_DELETE_MAIL")
		   || fieldName.contains("GC_DELETE_MAIL")
		   || fieldName.contains("CG_DEAL_WITH_REWARD")
		   || fieldName.contains("GC_DEAL_WITH_REWARD")
		   || fieldName.contains("CG_RECEIVE_ALL")
		   || fieldName.contains("GC_RECEIVE_ALL")
		   || fieldName.contains("RELATION_BASE")
		   || fieldName.contains("CG_RECEIVE_ALL")
		   || fieldName.contains("CG_RECEIVE_ALL")
		   || fieldName.contains("CG_RECEIVE_ALL")
		   
		   || fieldName.contains("CG_LOAD_FRIEND_GIFT_LIST")
		   || fieldName.contains("GC_LOAD_FRIEND_GIFT_LIST")
		   || fieldName.contains("CG_FRIEND_GIFT")
		   || fieldName.contains("GC_FRIEND_GIFT")
		   || fieldName.contains("GC_FRIEND_GIFT_SYNC")
		   || fieldName.contains("CG_FRIEND_GIFT_GET")
		   || fieldName.contains("GC_FRIEND_GIFT_GET")
		   || fieldName.contains("CG_STRANGER_LIST")
		   || fieldName.contains("GC_STRANGER_LIST")
		   || fieldName.contains("CG_FACEBOOK_NUM")
		   || fieldName.contains("CG_ENTER_FRIENDS_ROOM")
		   
		   
		   || fieldName.contains("GC_LEVEL_GIFT_MORE")
		   || fieldName.contains("SIGN_IN_BASE")
		   || fieldName.contains("CG_SIGN_IN")
		   || fieldName.contains("GC_SIGN_IN")
		   || fieldName.contains("GC_SIGN_IN_INFO_DATA")
		   || fieldName.contains("NOTICE_BASE")
		   || fieldName.contains("MISC_BASE")
		   || fieldName.contains("CG_ONLINE_REWARD")
		   || fieldName.contains("GC_ONLINE_REWARD")
		   || fieldName.contains("GC_MISC_INFO_DATA")
		   || fieldName.contains("CG_NEW_USER")
		   || fieldName.contains("GC_NEW_USER")
		   || fieldName.contains("CG_KOREAN_SB")
		   || fieldName.contains("GC_KOREAN_SB")
		   || fieldName.contains("VIP_BASE")
		   || fieldName.contains("CG_VIP_BUY")
		   || fieldName.contains("GC_VIP_BUY")
		   || fieldName.contains("CG_VIP_GET")
		   || fieldName.contains("GC_VIP_GET")
		   || fieldName.contains("GC_HUMAN_VIP_INFO_DATA")
		   || fieldName.contains("CG_VIP_CREATE_ROOM")
		   || fieldName.contains("GC_VIP_CREATE_ROOM")
		   || fieldName.contains("RECHARGE_BASE")
		   || fieldName.contains("VIP_BASE")
		   || fieldName.contains("GC_MYCARD_AUTHCODE")
		   || fieldName.contains("CG_VALIDATE_ORDER_MYCARD")
		   || fieldName.contains("CG_REQUEST_ORDER_MYCARD")
		   || fieldName.contains("CG_ORDER_AMAZON")
		   || fieldName.contains("CG_ORDER_AMAZON_DELIVERY")
		   || fieldName.contains("GC_ORDER_AMAZON_DELIVERY")
		   || fieldName.contains("CG_REQUEST_ORDER_MOL")
		   || fieldName.contains("CG_VALIDATE_ORDER_MOL")
		   || fieldName.contains("GC_MOL_ORDER")
		   || fieldName.contains("GC_DOUBLE_TURN")
		   || fieldName.contains("GC_OBTAIN_COUPON")
		   || fieldName.contains("CG_COUPON_EXIST")
		   || fieldName.contains("GC_COUPON_EXIST")
		   || fieldName.contains("TASK_BASE")
		   || fieldName.contains("CG_DAILY_TASK_GET")
		   || fieldName.contains("GC_DAILY_TASK_GET")
		   || fieldName.contains("GC_TASK_INFO_DATA")
		   || fieldName.contains("GC_TASK_INFO_DATA_CHANGE")
		   || fieldName.contains("CG_TASK_PROGRESS")
		   || fieldName.contains("GC_TASK_PROGRESS")
		   || fieldName.contains("ITEM_BASE")
		   || fieldName.contains("GC_HUNAMN_PROGRESS=6807")
		||fieldName.contains("GC_HUNAMN_PROGRESS_SINGLE")
		||fieldName.contains("GC_MONTH_OR_WEEK")
		||fieldName.contains("CG_MONTH_WEEK_LEFT_TIME")
		||fieldName.contains("GC_MONTH_WEEK_LEFT_TIME")
		||fieldName.contains("CG_FUNCTION")
		||fieldName.contains("GC_FUNCTION_LEFT_TIME")
		||fieldName.contains("CG_BACCARAT_QUICK_START")
		||fieldName.contains("CG_BIG_ZHUANPAN")
		||fieldName.contains("CG_SPIN_ZHUANPAN")
				||fieldName.contains("GC_BIG_ZHUANPAN")
				||fieldName.contains("GC_SPIN_ZHUANPAN_FREE")
				||fieldName.contains("GC_SPIN_ZHUANPAN_NOFREE")
				||fieldName.contains("GC_ROTARY_TABLE")
				||fieldName.contains("CG_DEMO_TYPE")
				||fieldName.contains("CG_SNG_RANK_INFO")
				||fieldName.contains("CG_TOURNAMENT_GET_LIST")
				||fieldName.contains("GC_TOURNAMENT_GET_LIST")
				||fieldName.contains("CG_WEIXIN_ENTER")
				||fieldName.contains("GC_WINNER_WHEEL")
				||fieldName.contains("VIP_NEW_BASE")
				||fieldName.contains("GC_VIP_NEW_DATA")
				||fieldName.contains("LOBBY_BASE")
				||fieldName.contains("RANKNEW_BASE")
				||fieldName.contains("CG_REQUEST_RANK")
				||fieldName.contains("GC_RANK_LIST")
				||fieldName.contains("CG_HUMAN_RANK")
				||fieldName.contains("GC_HUMAN_RANK")
				||fieldName.contains("GIFT_BASE")
				||fieldName.contains("CG_REQUEST_GIFT")
				||fieldName.contains("GC_REQUEST_GIFT")
				||fieldName.contains("CG_REQUEST_GIFT_TIME_END")
				||fieldName.contains("GC_NEW_COMER_GIFT")
						||fieldName.contains("CG_NEW_COMER_GIFT")
						||fieldName.contains("ACHIEVEMENT_BASE")
				||fieldName.contains("CG_ACH_INFO")
				||fieldName.contains("CG_RECEIVE_ACH")
				||fieldName.contains("GC_ACH_INFO")
				||fieldName.contains("GC_RECEIVE_ACH")
				||fieldName.contains("CONVERSIONCODE_BASE")
				||fieldName.contains("CG_CONVERSION_CODE")
				||fieldName.contains("GC_CONVERSION_CODE")
				||fieldName.contains("CHARM_EXCHANGE_BASE")
				||fieldName.contains("CG_CHARM_EXCHANGE")
				||fieldName.contains("GC_CHARM_EXCHANGE")
				||fieldName.contains("CG_COLLECT_INIT")
				||fieldName.contains("GC_COLLECT_INIT")
				||fieldName.contains("CG_RAFFLE")
				||fieldName.contains("GC_RAFFLE")
				||fieldName.contains("GC_GET_VOUCHERS")
				||fieldName.contains("CG_CARD_EXCHANGE")
				||fieldName.contains("GC_CARD_EXCHANGE")
				||fieldName.contains("GC_TREASURY_BASE")
				||fieldName.contains("CG_TREASURY")
				||fieldName.contains("GC_TREASURY")
				||fieldName.contains("GC_WORLD_BOSS_BASE")
				||fieldName.contains("CG_OPEN_PANEL")
				||fieldName.contains("CG_GET_BOSS_INFO")
				||fieldName.contains("GC_GET_BOSS_INFO")
				||fieldName.contains("CG_GET_RANK_INFO")
				||fieldName.contains("GC_GET_RANK_INFO")
				||fieldName.contains("GC_REFRESH_BOSS_INFO")
				||fieldName.contains("CG_BOSS_START_END_INFO")
				||fieldName.contains("GC_BOSS_START_END_INFO")
				||fieldName.contains("GC_SELF_ATTACK_BLOOD_INFO")
				||fieldName.contains("CG_RETURN_BLOOD")
				||fieldName.contains("GC_RETURN_BLOOD")
				||fieldName.contains("CG_REFRESH_BOSS_INFO")
				||fieldName.contains("GC_BEFORE_START")
				||fieldName.contains("CG_BEFORE_START")
				||fieldName.contains("	GC_GIVEALIKE_BASE")
				||fieldName.contains("CG_GIVEALIKE_SAVE")
				||fieldName.contains("	GC_GET_GIVEALIKE_INFO")
				||fieldName.contains("CG_ISNOT_GIVEALIKE")
				||fieldName.contains("GC_ISNOT_GIVEALIKE")
				||fieldName.contains("GC_NEWBIE_BASE")
				||fieldName.contains("CG_GET_SAVE_POINT")
				||fieldName.contains("GC_GET_SAVE_POINT")
				||fieldName.contains("CG_SAVE_POINT")
				||fieldName.contains("GC_SAVE_POINT")
				||fieldName.contains("GC_PAY_GUIDE_BASE")
				||fieldName.contains("GC_PAY_GUIDE")
				||fieldName.contains("CG_ITEM_INVOKE")
				||fieldName.contains("GC_GET_GIVEALIKE_INFO")
				||fieldName.contains("GC_GIVEALIKE_BASE")
				||fieldName.contains("GC_HUNAMN_PROGRESS")
		 
	    ){
		   return true;
	   }
	   return false;
   }
   
   
}
