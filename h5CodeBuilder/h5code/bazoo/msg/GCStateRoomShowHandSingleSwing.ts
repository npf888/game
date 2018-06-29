module game 
{
	export class GCStateRoomShowHandSingleSwing
	{
		public status:number;
		public lastPassportId:number;
		public passportId:number[];
		public leftTimes:number;
		public leftSecond:number;
		public diceInfo:DiceInfo[];
		public showHandBet:ShowHandBet[];
	}
}