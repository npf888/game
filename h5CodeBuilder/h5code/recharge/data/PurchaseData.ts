module game 
{
	export class PurchaseData
	{
		public packageName:string;
		public orderId:string;
		public productId:string;
		public developerPayload:string;
		public purchaseTime:number;
		public purchaseState:number;
		public purchaseToken:string;
	}
}