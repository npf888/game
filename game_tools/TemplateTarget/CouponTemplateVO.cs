/**
 * CouponTemplate.txt
 * 
 * @author CodeGenerator, don't	modify this file please.
 * 
 */
 public class CouponTemplateVO
{
		/** 策划表数据主键 */
	public int id;
		/** 名字id */
	public int nameId;
		/** 描述 */
	public int descrip;
		/** 描述 */
	public string langDesc;
		/** 第二大分类 */
	public string icon;
		/** 优惠券有效期（秒） */
	public int couponDuration;
		/** 优惠券额外筹码百分比  获得筹码数 = 首充翻倍、VIP翻倍后的筹码数 X （1+ 优惠券额外筹码百分比/100 ) */
	public int couponExtraChip;
		/** 权重 */
	public int weight;
		/** 对应item表里的id; 一一对应 */
	public int itemID;
	
}