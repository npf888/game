module game 
{
	export class GCRoomInit
	{
		public pubOrPri:number;
		public lastPassportId:number;
		public roomNum:string;
		public waitTime:number;
		public turnWait:number;
		public guessWait:number;
		public onePoint:number;
		public cowSwingToBegin:number;
		public cowOneRoundTime:number;
		public cowLookDiceValueTime:number;
		public cowEndCountTime:number;
		public reShakingTimes:number;
		public showHandWhoTurn:number;
		public showHandShakeTime:number;
		public showHandEndToStart:number;
		public bankPassportId:number;
		public endCountInfo:EndCountInfo[];
		public showHandBet:ShowHandBet[];
		public returnPlayerInfo:ReturnPlayerInfo[];
	}
}