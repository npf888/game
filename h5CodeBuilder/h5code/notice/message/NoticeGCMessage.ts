module game
{
	export class NoticeGCMessage extends AbstractMessageReceiver
	{
		private static instance : NoticeGCMessage;
		public static getInstance() : NoticeGCMessage
		{
			if(NoticeGCMessage.instance == null)
				NoticeGCMessage.instance = new NoticeGCMessage();
			return NoticeGCMessage.instance;
		}
		public collectObservers() : void
		{
			this.register(NetMessageType.GC_NOTICE_INFO_DATA, this.GC_NOTICE_INFO_DATA);
			this.register(NetMessageType.GC_NOTICE_INFO_DATA_MULTI, this.GC_NOTICE_INFO_DATA_MULTI);
		}

		public GC_NOTICE_INFO_DATA(data:NetMessageIn) : void
		{
			let msg:GCNoticeInfoData = data.getContent<GCNoticeInfoData>();
			NoticeHandler.getInstance().GC_NOTICE_INFO_DATA(msg);
		}

		public GC_NOTICE_INFO_DATA_MULTI(data:NetMessageIn) : void
		{
			let msg:GCNoticeInfoDataMulti = data.getContent<GCNoticeInfoDataMulti>();
			NoticeHandler.getInstance().GC_NOTICE_INFO_DATA_MULTI(msg);
		}
	}
}
