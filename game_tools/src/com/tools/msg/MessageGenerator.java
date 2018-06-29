package com.tools.msg;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.util.ArrayListWrapper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import com.core.util.Assert;
import com.core.util.FileUtil;
import com.core.util.JsScriptHelper;
import com.core.util.StringUtils;
import com.gameserver.common.msg.MessageType;

/**
 * 消息代码生成器Velocity语法
 * @author Thinker
 * 
 */
@SuppressWarnings("unchecked")
public class MessageGenerator 
{
	private static final Logger logger = Logger.getLogger(MessageGenerator.class);

	public static final String TYPE_BYTE = "Byte";
	public static final String TYPE_SHORT = "Short";
	public static final String TYPE_INT = "Integer";
	public static final String TYPE_LONG = "Long";
	public static final String TYPE_FLOAT = "Float";
	public static final String TYPE_DOUBLE = "Double";
	public static final String TYPE_STRING = "String";
	public static final String TYPE_BOOLEAN = "Boolean";
	public static final String TYPE_CCSAFEINT = "CCSafeInt";

	private static final String[] sysTypes = { TYPE_BYTE, TYPE_SHORT, TYPE_INT,
			TYPE_LONG, TYPE_FLOAT, TYPE_DOUBLE, TYPE_STRING, TYPE_BOOLEAN,TYPE_CCSAFEINT };

	/** 服务器端模板列表 */
	private static final String[] serverMsgTemplates = { "cg_message_g.vm", "gc_message_g.vm"};

	/** 客户端模板列表 */
	private static final String[] clientMsgTemplates = { "cg_message_c.vm",	"gc_message_c.vm" };


	/** 消息号与消息类映射模板 */
	private static final String mappingTemplate_game = "message_mapping_g.vm";
	private static final String mappingTemplate_robot = "message_mapping_robot.vm";
	private static final String clientModelTemplate = "client_model.vm";

	/** 生成文件存放的路径列表 */
	private static String _clientRootPath = "..\\game_tools\\target\\";// 暂时生成在当前目录
	private static String _gameServerRootPath = "..\\game_server\\src\\com\\imop\\tr\\gameserver\\";
	private static final String robotRootPath = "..\\robot\\src\\com\\robot\\";
	private static String msgExcelMappingFilePath ="..\\resources\\i18n\\zh_CN\\msg_excel.xls";

	private static final boolean replaceDirectly = false;
	private static final String dataPath = "D:\\tr_WorkSpace\\tr_client\\src\\com\\imop\\game\\tr\\net\\data\\";
	private static final String messagePath = "D:\\tr_WorkSpace\\tr_client\\src\\com\\imop\\game\\tr\\net\\message\\";
	private static final String messageTypePath = "D:\\tr_WorkSpace\\tr_client\\src\\com\\imop\\game\\tr\\net\\";

	private static Map<String, Element> macros;
	private static Map<String, MessageObject> msgs;
	private static Map<String, List<FieldObject>> fields;
	private static Map<String, MessageObject> totalMsg;

	/** */
	public static final String MODEL_DIC = "msg/model/";
	/** */
	public static final String TEMPLATE_DIC = "msg/template/";

	public static final Namespace NAME_SPACE = Namespace
			.getNamespace("http://com.message");
	
	public static final String SERVER_GAME = "game";// gameserver

	public MessageGenerator()
	{
		macros = new HashMap<String, Element>();
		totalMsg = new HashMap<String, MessageObject>();
	}

	public void createMessageFiles(String modelFileName) 
	{
		try
		{
			System.out.println(modelFileName);
			msgs = new HashMap<String, MessageObject>();
			fields = new HashMap<String, List<FieldObject>>();
			String configFilePath = GeneratorHelper.getBuildPath(MODEL_DIC+modelFileName);
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(configFilePath);
			Element root = doc.getRootElement();
			String module = root.getAttributeValue("module");// 所属模块
			List messages = root.getChildren("message", NAME_SPACE);// 消息体定义
			List constants = null;
			Element constantsElement = root.getChild("constants", NAME_SPACE);
			if (constantsElement != null) 
			{
				constants = root.getChild("constants", NAME_SPACE).getChildren();// 常量定义
			} else 
			{
				constants = new ArrayList();
			}
			this.replaceMacros(messages);
			createServerFiles(messages, module);
			createClientFile(messages, module, constants);
			createServerMappingFile(messages, module);
			
			
		
		} catch (Exception e)
		{
			logger.error("", e);
		}
	}


	/**
	 * 生成服务器端文件，每个消息一个文件
	 * 
	 * @param messages
	 * @throws Exception
	 */
	private void createServerFiles(List<Element> messages, String module)throws Exception
	{
	
		for (Iterator<?> i = messages.iterator(); i.hasNext();)
		{
			Element msgElement = (Element) i.next();
			MessageObject msgObj = new MessageObject();
			
			// 设置消息类型类
			msgObj.setImportMsgTypeClazz(_msgTypeClazz);
			String msgType = msgElement.getAttributeValue("type");
			msgObj.setType(msgType);
			msgObj.setClassName(GeneratorHelper.generateServerClassName(msgType));
			msgObj.setModule(module);
			msgObj.setComment(msgElement.getAttributeValue("comment"));

			msgObj.setHandleMethodName(GeneratorHelper
					.generateHandleMethodName(msgType));
			if (msgElement.getAttributeValue("playerQueue") != null)
			{
				msgObj.setPlayerQueue(msgElement.getAttributeValue(
						"playerQueue").equals("true") ? true : false);
			}
			if (msgElement.getAttributeValue("friendQueue") != null)
			{
				msgObj.setFriendQueue(msgElement.getAttribute("friendQueue")
						.getValue().equals("true"));
			}
			if (msgElement.getAttributeValue("guildQueue") != null)
			{
				msgObj.setGuildQueue(msgElement.getAttribute("guildQueue")
						.getValue().equals("true"));
			}
			
			if (msgElement.getAttributeValue("description") != null)
			{
				msgObj.setDescription(msgElement.getAttributeValue("description"));
				msgObj.setCollect(true);
			}
			else
				msgObj.setCollect(false);
			List fElements = msgElement.getChildren("field", NAME_SPACE);
			setMsgObjFields(msgObj, fElements, false, false);
			VelocityContext context = new VelocityContext();
			context.put("message", msgObj);
			context.put("list", msgObj.getFields());
			String templateFileName = "";
			String outputFile = "";
			for (int j = 0; j < serverMsgTemplates.length; j++) 
			{
				String templateName = serverMsgTemplates[j];
				if (templateName.substring(0, 2).equalsIgnoreCase(msgType.substring(0, 2)))
				{
					templateFileName = templateName;
					char lastCharOfTempate = templateName.charAt(templateName
							.length() - 4);
					switch (lastCharOfTempate)
					{
						case 'g':// 放在GameServer
							outputFile = _gameServerRootPath + msgObj.getModule() + "\\"
									+ "msg\\" + msgObj.getClassName() + ".java";
							break;
						default:
							throw new RuntimeException("模板名称非法，" + templateName);
					}
					GeneratorHelper.generate(context, templateFileName,
							outputFile);
				}
			}
			msgs.put(msgObj.getClassName(), msgObj);
			totalMsg.put(msgObj.getClassName(), msgObj);
		}
	}

	/**
	 * 生成客户端文件，每个模块一个文件
	 * 
	 * @param msgElements
	 * @throws Exception
	 */
	private void createClientFile(List<Element> msgElements, String module,
			List<Element> contantElements) throws Exception {

		List<MessageObject> cgMsgs = new ArrayList<MessageObject>();
		List<MessageObject> gcMsgs = new ArrayList<MessageObject>();
		List<MessageObject> allClientMsgs = new ArrayList<MessageObject>();
		for (Iterator i = msgElements.iterator(); i.hasNext();) {
			Element mElement = (Element) i.next();
			MessageObject msgObj = new MessageObject();
			String msgType = mElement.getAttributeValue("type");
			fields.put(msgType, msgObj.getFields());
			msgObj.setType(msgType);
			msgObj.setClassName(GeneratorHelper
					.generateServerClassName(msgType));
			msgObj.setModule(mElement.getAttributeValue("module"));
			msgObj.setComment(mElement.getAttributeValue("comment"));
			msgObj.setHandleMethodName(GeneratorHelper
					.generateHandleMethodName(msgType));
			List fElements = mElement.getChildren("field", NAME_SPACE);
			this.setMsgObjFields(msgObj, fElements, true, false);
			if (msgType.substring(0, 2).equalsIgnoreCase("cg")) {
				cgMsgs.add(msgObj);
			} else if (msgType.substring(0, 2).equalsIgnoreCase("gc")) {
				gcMsgs.add(msgObj);
			} 
			if (msgType.contains("c") || msgType.contains("C")) {
				allClientMsgs.add(msgObj);
			}
		}

		this.createClientModel(allClientMsgs, module);

		List<ConstantObject> contants = new ArrayList<ConstantObject>();
		for (Iterator i = contantElements.iterator(); i.hasNext();) {
			Element constantElement = (Element) i.next();
			String constantName = constantElement.getAttributeValue("name");
			String constantDesc = constantElement.getValue();
			ConstantObject constantObj = new ConstantObject(constantName,
					constantDesc.replaceAll("\\n", "").replaceAll("\\r", "")
							.trim());
			contants.add(constantObj);
		}

		VelocityContext context = new VelocityContext();
		context.put("module", StringUtils.upperCaseFirstCharOnly(module));
		context.put("modulename", module);
		for (String templateFileName : clientMsgTemplates) {
			String outputFileNameSuffix = null;
			String templatePrefix = templateFileName.substring(0, 2);
			if (templatePrefix.equals("gc")) {
				if (gcMsgs.size() == 0) {
					continue;
				}
				context.put("msgs", gcMsgs);
				outputFileNameSuffix = "GCMessage.cs";
			} else if (templatePrefix.equals("cg")) {
				if (cgMsgs.size() == 0) {
					continue;
				}
				context.put("msgs", cgMsgs);
				outputFileNameSuffix = "CGMessage.cs";
			} 
			context.put("constants", contants);
			String outputFilePath; 
			if(replaceDirectly)
			{
				outputFilePath = messagePath
					+ StringUtils.upperCaseFirstCharOnly(module)
					+ outputFileNameSuffix;
			}
			else
			{
				outputFilePath = _clientRootPath + "\\" + module + "\\message\\"
					+ StringUtils.upperCaseFirstCharOnly(module)
					+ outputFileNameSuffix;
			}
			GeneratorHelper.generate(context, templateFileName, outputFilePath);
		}
	}

	/**
	 * 生成客户端数据模型
	 * 
	 * @param messages
	 */
	private void createClientModel(List<MessageObject> messages, String module) {
		Map<String, FieldObject> newTypeFields = new HashMap<String, FieldObject>();
		for (MessageObject msgObj : messages) {
			List<FieldObject> fields = msgObj.getFields();
			for (FieldObject fieldObject : fields) {
				if (fieldObject.getIsNewType()) {
					// 先遍历它的子类型
					for (FieldObject subfieldObject : fieldObject
							.getSubFields()) {
						if (subfieldObject.getIsNewType()) {
							subfieldObject.setType(subfieldObject
									.getClientType());
							if (subfieldObject.getClientType().contains(".")) {
								subfieldObject.setType(GeneratorHelper
										.getClientClassName(subfieldObject
												.getType()));
								subfieldObject.setDirect(true);
							//	continue;
							}
							newTypeFields.put(subfieldObject.getClientType(),
									subfieldObject);
						}
					}
					fieldObject.setType(fieldObject.getClientType());
					if (fieldObject.getClientType().contains(".")) {
						fieldObject.setType(GeneratorHelper
								.getClientClassName(fieldObject.getType()));
						fieldObject.setDirect(true);
					//	continue;
					}
					newTypeFields.put(fieldObject.getClientType(), fieldObject);
				}
			}
		}
		for (FieldObject fieldObject : newTypeFields.values()) {
			VelocityContext context = new VelocityContext();
			context.put("modulename", module);
			context.put("model", fieldObject);
			String outputFilePath; 
			if(fieldObject.isDirect())
			{
				outputFilePath= _clientRootPath +"\\data\\"
				+ fieldObject.getType() + ".cs";
			}
			else
			{
			
				outputFilePath= _clientRootPath + "\\" + module + "\\data\\"
				+ fieldObject.getType() + ".cs";
				
			}
			try {
				GeneratorHelper.generate(context, clientModelTemplate,
						outputFilePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	
	/**
	 * 设置消息对象的字段
	 * 
	 * @param msgObj
	 * @param msgElement
	 */
	private void setMsgObjFields(MessageObject msgObj, List<?> fElements,boolean isClient, boolean isCppClient)
	{
		for (Iterator<?> j = fElements.iterator(); j.hasNext();) 
		{
			Element fElement = (Element) j.next();
			FieldObject field = new FieldObject();
			field.setType(fElement.getAttributeValue("type"));
			field.setCastType(fElement.getAttributeValue("casttype"));
			field.setClientType(fElement.getAttributeValue("clientType"));
			if (StringUtils.isEmpty(field.getClientType()))
			{
				field.setClientType(GeneratorHelper.getClientClassName(field.getType()));
			}
			field.setSmallName(fElement.getAttributeValue("name"));
			field.setComment(fElement.getAttributeValue("comment"));
			List<Element> subFieldElements = fElement.getChildren("field",NAME_SPACE);
			if (fElement.getAttributeValue("bytes") != null)
			{
				field.setBytes(fElement.getAttributeValue("bytes").equals("true") ? true : false);
			}
			if (fElement.getAttributeValue("list") != null)
			{
				field.setList(fElement.getAttributeValue("list").equals("true") ? true : false);
				msgObj.setHasListField(true);
			}

			boolean isSubMsg = "true".equalsIgnoreCase(fElement
					.getAttributeValue("subMsg"));
			field.setSubMsg(isSubMsg);
			if (isSubMsg) 
			{
				field.setSubMsgType(GeneratorHelper
						.generateServerClassName(field.getType()));
				msgObj.setListMsg(true);
				if (isCppClient)
				{
					field.setList(true);
					msgObj.setHasListField(true);
					field.setType("_LZOBEX_" + field.getType());
				}
			}

			// 如果不是系统定义的类型则说明是子消息
			if (!new ArrayListWrapper(sysTypes).contains(field.getType())) {
				if (!isCppClient || !field.isSubMsg()) {
					field.setIsNewType(true);
					if (!isClient && field.getType().indexOf("_") > 0) {
						field.setType(GeneratorHelper
								.generateServerClassName(field.getType()));
					}
				}
			}

			if (subFieldElements.size() > 0) {
				// 获取类名称
				String clazzName = msgObj.getClassName();

				try {
					this.setSubFields(field, subFieldElements);
				} catch (Exception ex) {
					throw new RuntimeException(
						clazzName + "." + field.getBigName() + " error! " + ex.getMessage(), ex
					);
				}
			} else {
				if (isClient && field.getIsNewType()) {// 客户端生成消息的时候要为其补全
					field.setSubFields(fields.get(field.getType()));
				}
				if (!isClient && field.getList() && field.getIsNewType()) {
					String type = field.getType();
					field.setSubFields(msgs.get(type).getFields());
				}
			}
			if (field.isSubMsg() && !isCppClient) {
				msgObj.addSubMsg(field);
			} else {
				msgObj.addField(field);
			}
		}
	}

	/**
	 * @param field
	 * @param subFieldElements
	 */
	private void setSubFields(FieldObject field, List<Element> subFieldElements) {
		List<FieldObject> subFields = new ArrayList<FieldObject>();
		fields.put(field.getType(), subFields);

		for (int i = 0; i < subFieldElements.size(); i++) {
			// 获取子元素
			Element subElement = subFieldElements.get(i);

			if (subElement == null) {
				continue;
			}

			// 获取字段类型
			String fieldType = subElement.getAttributeValue("type");
			// 获取字段类型
			String castType = subElement.getAttributeValue("casttype");
			// 获取字段名称
			String fieldName = subElement.getAttributeValue("name");
			// 获取字段注释
			String fieldComm = subElement.getAttributeValue("comment");

			if (fieldType == null || 
				fieldType.isEmpty()) {
				// 抛出运行时异常
				throw new RuntimeException("field type is empty, fieldIndex = " + i + ", fieldName = " + fieldName + ", comment = " + fieldComm);
			}

			if (fieldName == null || 
				fieldName.isEmpty()) {
				// 抛出运行时异常
				throw new RuntimeException("field name is empty, fieldIndex = " + i + ", fieldName = " + fieldName + ", comment = " + fieldComm);
			}
			
			FieldObject subField = new FieldObject();
			subField.setType(fieldType);
			subField.setCastType(castType);
			subField.setClientType(subElement.getAttributeValue("clientType"));
			if (StringUtils.isEmpty(subField.getClientType())) {
				subField.setClientType(GeneratorHelper
						.getClientClassName(subField.getType()));
			}

			subField.setSmallName(fieldName);
			subField.setComment(fieldComm);

			if (subElement.getAttributeValue("list") != null) {
				subField.setList(subElement.getAttributeValue("list").equals(
						"true") ? true : false);
				field.setHasListField(true);
			}
			subFields.add(subField);
			List<Element> subsubFieldElements = subElement.getChildren("field",
					NAME_SPACE);
			if (subsubFieldElements.size() > 0) {
				this.setSubFields(subField, subsubFieldElements);
			}

			// 如果不是系统定义的类型则说明是子消息
			if (!new ArrayListWrapper(sysTypes).contains(subField.getType())) {
				subField.setIsNewType(true);
				if (subField.getType().indexOf("_") > 0) {
					subField.setType(GeneratorHelper
							.generateServerClassName(subField.getType()));
				}
			}
		}
		field.setSubFields(subFields);
		// 如果配置了子节点，而且类型中不存在包名，则说明是新定义的类型
		if (field.getType().indexOf(".") == -1) {
			field.setNeedCreateType(true);
		}
	}

	/**
	 * 生成映射文件
	 * 
	 * @param msgElements
	 * @param module
	 * @throws Exception
	 */
	private void createServerMappingFile(List<Element> msgElements,String module) throws Exception
	{
		List<MessageObject> toGSMsgs = new ArrayList<MessageObject>();

		for (Iterator<?> i = msgElements.iterator(); i.hasNext();) {
			Element message = (Element) i.next();
			MessageObject msg = new MessageObject();
			String msgType = message.getAttributeValue("type");
			msg.setType(msgType);
			msg.setClassName(GeneratorHelper.generateServerClassName(msgType));
			String msgTypePrefix = msgType.substring(1, 2);
			if (msgTypePrefix.equalsIgnoreCase("g")) {
				toGSMsgs.add(msg);
			} 
		}
		if (toGSMsgs.size() == 0) {
			return;
		}
		VelocityContext context = new VelocityContext();
		context.put("module", module);
		context.put("importMsgTypeClazzSentence", 
			_msgTypeClazz == null ? "" : "import " + _msgTypeClazz.getName() + ";");
		
		String mappingClassName = StringUtils.upperCaseFirstCharOnly(module)
				+ "MsgMappingProvider";
		context.put("classname", mappingClassName);
		if (toGSMsgs.size() > 0) {
			context.put("msgs", toGSMsgs);
			String outputFile = _gameServerRootPath + module + "\\msg\\"
					+ mappingClassName + ".java";
			GeneratorHelper.generate(context, mappingTemplate_game, outputFile);
		}
	}

	/**
	 * 生成客户端消息类型头文件
	 * 
	 * @param msgTypeClazz
	 * 
	 */
	private void createClientMessageType(Class<?> msgTypeClazz) 
	{
		// 断言参数不为空
		Assert.notNull(msgTypeClazz, "clazz");

		StringBuilder builder = new StringBuilder();
		builder.append("public class NetMessageType{\r\n");
		
		/** 添加腾讯 安全协议 */
		builder.append("\tpublic const int ").append("GC_QQPOLICY").append("=").append("51")
		.append(";\r\n");
		
		Class<?> mtClazz = msgTypeClazz;
		Field[] fields = mtClazz.getDeclaredFields();
		Field.setAccessible(fields, true);
		OutputStream out = null;
		try
		{
			Set<Short> msgNumSet = new HashSet<Short>();
			for (int i = 0; i < fields.length; i++)
			{
				String fName = fields[i].getName();
				if (fName.length() <= 3) continue;
				String prefix = fName.substring(0, 3);
				if ((prefix.equals("CG_") || prefix.equals("GC_"))
						&& ((fields[i].getModifiers() & Modifier.STATIC) != 0)
						& ((fields[i].getModifiers() & Modifier.FINAL) != 0)) 
				{
					short messageNumber = fields[i].getShort(null);
					if (messageNumber <= 0)
					{
						throw new RuntimeException("消息号溢出！！");
					} else if (msgNumSet.contains(messageNumber)) 
					{
						throw new RuntimeException(String.format("%s消息号与其他消息号冲突", fName));
					}
					builder.append("\tpublic const int ").append(fName).append("=").append(messageNumber)
							.append(";\r\n");
					msgNumSet.add(messageNumber);
				}
			}
			builder.append("}\r\n");
			String outPath;
			if(replaceDirectly)
			{
				outPath = messageTypePath+ "NetMessageType.cs";
			}
			else
			{
				outPath = _clientRootPath+ "NetMessageType.cs";
			}
			out = new FileOutputStream(outPath);
			byte[] buffers = builder.toString().getBytes("UTF-8");
			out.write(buffers);
			out.flush();
		} catch (Exception e)
		{
			logger.error("Unknown Exception", e);
		} finally
		{
			try 
			{
				out.close();
			} catch (Exception e)
			{
				
			}
		}
	}
	/**
	 * 生成消息类型头文件
	 */
	private void createRobotMappingFile() 
	{
		List<MessageObject> toGSMsgs = new ArrayList<MessageObject>();

		for (MessageObject msg:totalMsg.values()) {
			String msgTypePrefix = msg.getType().substring(1, 2);
			if (msgTypePrefix.equalsIgnoreCase("c")) {
				toGSMsgs.add(msg);
			} 
		}
		if (toGSMsgs.size() == 0) {
			return;
		}
		VelocityContext context = new VelocityContext();

		if (toGSMsgs.size() > 0) {
			context.put("msgs", toGSMsgs);
			String outputFile = robotRootPath + "startup\\"
					 + "RobotMessageMappingProvider.java";
			try {
				GeneratorHelper.generate(context, mappingTemplate_robot, outputFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 创建msg excel
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	private void createExcelMappingFile() 
	{
		// 获取 Excel 文件名称
		System.out.println("创建 " + msgExcelMappingFilePath);
		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
		
		for (MessageObject msg:totalMsg.values()) {
			String msgTypePrefix = msg.getType().substring(0,1);
			if (msgTypePrefix.equalsIgnoreCase("c")) {
				if(msg.getDescription()!=null)
				{
					Map<String,String> temp = new HashMap<String,String>();
				
					
					Field field;
					try {
						field = MessageType.class.getDeclaredField(msg.getType());
						temp.put("id", String.valueOf(field.getShort(null)));
						temp.put("description", msg.getDescription());
					} catch (SecurityException e) {
						
					} catch (NoSuchFieldException e) {
						
					} catch (IllegalArgumentException e) {
					
					} catch (IllegalAccessException e) {
						
					}
					finally
					{
						if(temp.get("id")!=null)
							dataList.add(temp);
					}
					
				}
			} 
		}
		createExcel(dataList,msgExcelMappingFilePath);
		// 输出完成消息
		System.out.println("创建完成!");
	}
		

		/**
		 * 生成Excel
		 * 
		 * @param dataList 多语言数据
		 * @param path 路径
		 */
		public void createExcel(
			List<Map<String, String>> dataList,
			String path) {

			OutputStream fout = null;
			try {
				HSSFWorkbook wb = new HSSFWorkbook();
				HSSFSheet sheet = wb.createSheet("msg_excel");
				// 设置格式
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setAlignment((short) 2);
				HSSFFont cellFont = wb.createFont();
				cellFont.setFontName("宋体");
				cellFont.setFontHeightInPoints((short) 12);
				cellStyle.setFont(cellFont);
				cellStyle.setAlignment((short) 0);
				sheet.setColumnWidth(1, 10000);
				sheet.setColumnWidth(2, 10000);

				// 生成数据区
				if (dataList != null && dataList.size() > 0) {
					for (int row = 0; row < dataList.size(); row++) {
						HSSFRow rowValue = sheet.createRow(row);
						Map<String, String> dataMap = dataList.get(row);

						HSSFCell id = rowValue.createCell(0);
						id.setCellStyle(cellStyle);
						id.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						id.setCellValue(Integer.valueOf(dataMap.get("id")));

						HSSFCell content = rowValue.createCell(1);
						content.setCellStyle(cellStyle);
						content.setCellType(HSSFCell.CELL_TYPE_STRING);
						content.setCellValue(dataMap.get("description"));

					}
				}
				// 写入到文件
				fout = new FileOutputStream(path);
				wb.write(fout);
				fout.flush();
			} catch (Exception e) {
				logger.error("Unknown Exception", e);
			} finally {
				if (fout != null) {
					try {
						fout.close();
					} catch (IOException e) {
						logger.error("IOException", e);
					}
				}
			}
		}

	/**
	 * 加载对应的宏定义模块
	 */
	private void loadModelMacros(String marcoFileName)
	{
		try
		{
			String configFilePath = GeneratorHelper.getBuildPath(MODEL_DIC+ marcoFileName);
			SAXBuilder builder = new SAXBuilder();
			Document doc;
			doc = builder.build(configFilePath);
			Element root = doc.getRootElement();
			List<Element> macroElements = root.getChildren("macro", NAME_SPACE);// 消息体定义
			for (Element macroElement : macroElements)
			{
				macros.put(macroElement.getAttributeValue("id"), macroElement);
			}
			// 处理宏定义中引用其它宏的情况
			for (Element macro : macros.values())
			{
				List<Element> fieldList = macro.getChildren();
				for (Element macroField : fieldList)
				{
					String otherMacroId = macroField.getAttributeValue("macro");
					this.doReplaceMacros(macroField, otherMacroId);
				}
			}
		} catch (Exception e) 
		{
			logger.error("", e);
		}
	}

	/**
	 * 替换消息定义文件中的宏
	 * 
	 * @param messages
	 */
	private void replaceMacros(List<Element> messages)
	{
		for (Element msg : messages)
		{
			String macroId = null;
			// 首先替换message节点中的宏
			macroId = msg.getAttributeValue("macro");
			// 仅一次宏替换(这里假设此时的模板宏中没有还未被处理的嵌套宏)
			if (!this.doReplaceMacros(msg, macroId))
			{
				// 然后替换field节点中的宏
				List<Element> fieldList = msg.getChildren();
				for (Element field : fieldList) {
					macroId = field.getAttributeValue("macro");
					if (!this.doReplaceMacros(field, macroId))
					{
						List<Element> childFields = field.getChildren();
						// 然后替换field的子节点中的宏
						if (childFields != null && childFields.size() > 0) 
						{
							for (Element childField : childFields)
							{
								macroId = childField.getAttributeValue("macro");
								this.doReplaceMacros(childField, macroId);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 替换宏
	 * 
	 * @param element
	 * @param macroId
	 * @return 仅当成功的进行宏替换后返回真
	 */
	private boolean doReplaceMacros(Element element, String macroId)
	{
		if (!StringUtils.isEmpty(macroId))
		{
			if (!macros.containsKey(macroId))
			{
				logger.error("消息配置错误，不存在这样的宏定义：" + macroId);
				return false;
			}
			Element macro = macros.get(macroId);
			Element macroClone = (Element) macro.clone();
			element.addContent(macroClone.removeContent());
			return true;
		}
		return false;
	}

	/** 消息类型类 */
	private static Class<?> _msgTypeClazz = null;

	/**
	 * 设置消息类型类
	 * 
	 * @param clazz
	 * 
	 */
	public static void putSettings(Settings settings)
	{
		// 断言参数不为空
		Assert.notNull(settings, "settings");

		// 客户端根目录
		_clientRootPath = settings.getClientRootPath();
		// 服务器端根目录
		_gameServerRootPath = settings.getGameServerRootPath();
		// 消息类型类
		_msgTypeClazz = settings.getMsgTypeClazz();
	}

	//启动生成消息代码
	public static void main(String[] args) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException 
	{
		if(replaceDirectly)
		{
			FileUtil.cleanFolder(new File(dataPath),".svn");
			FileUtil.cleanFolder(new File(messagePath),".svn");
			FileUtil.delete(new File(messageTypePath+"MessageType.cs"));
			FileUtil.delete(new File(msgExcelMappingFilePath));
		}else
		{
			FileUtil.delete(new File(_clientRootPath));
		}
		Map<String, Object> context = new HashMap<String, Object>();
		MessageGenerator generator = new MessageGenerator();
		generator.loadModelMacros("macros.xml");
		context.put("engine", generator);
		JsScriptHelper.executeScriptFile(GeneratorHelper.getBuildPath("msg/message_generator.js"), context);
		generator.createClientMessageType(_msgTypeClazz);
		generator.createRobotMappingFile();
		generator.createExcelMappingFile();
		
	}

	/**
	 * 自定义配置
	 * 
	 * @author Thinker
	 * 
	 */
	public static class Settings
	{
		/** 客户端根目录 */
		private String _clientRootPath = null;
		/** 服务器端根目录 */
		private String _gameServerRootPath = null;
		/** 消息类型类 */
		private Class<?> _msgTypeClazz = null;

		public Settings() 
		{
			
		}

		/**
		 * 获取客户端根目录
		 * 
		 * @return 
		 * 
		 */
		public String getClientRootPath()
		{
			return this._clientRootPath;
		}

		/**
		 * 设置客户端根目录
		 * 
		 * @param value 
		 * 
		 */
		public void setClientRootPath(String value)
		{
			this._clientRootPath = value;
		}

		/**
		 * 设置服务器端根目录
		 * 
		 * @return 
		 * 
		 */
		public String getGameServerRootPath() 
		{
			return this._gameServerRootPath;
		}

		/**
		 * 设置服务器端根目录
		 * 
		 * @param value 
		 * 
		 */
		public void setGameServerRootPath(String value)
		{
			this._gameServerRootPath = value;
		}

		/**
		 * 获取消息类型类
		 * 
		 * @return 
		 * 
		 */
		public Class<?> getMsgTypeClazz()
		{
			return this._msgTypeClazz;
		}

		/**
		 * 设置消息类型类
		 * 
		 * @param value 
		 * 
		 */
		public void setMsgTypeClazz(Class<?> value) 
		{
			this._msgTypeClazz = value;
		}
	}
}
