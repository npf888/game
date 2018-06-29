package com.gameserver.bazoo.data;

public class BlackWhiteDiceInfo {
		//用户ID
		private long passportId;
		private int isOut;
		
		private int[] diceValues;
		
		//被移除的色子的值
		private int[] removeDiceValues;

		public long getPassportId() {
			return passportId;
		}

		public void setPassportId(long passportId) {
			this.passportId = passportId;
		}

		public int[] getDiceValues() {
			return diceValues;
		}

		public void setDiceValues(int[] diceValues) {
			this.diceValues = diceValues;
		}

		public int[] getRemoveDiceValues() {
			return removeDiceValues;
		}

		public void setRemoveDiceValues(int[] removeDiceValues) {
			this.removeDiceValues = removeDiceValues;
		}

		public int getIsOut() {
			return isOut;
		}

		public void setIsOut(int isOut) {
			this.isOut = isOut;
		}

		
		
		
		

}
