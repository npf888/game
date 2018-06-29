package com.core.converter;

/**
 * 
 * 转换器的抽象实现
 * @author Thinker
 */
public class AbstractConverter<FROM,TO> implements Converter<FROM,TO>
{
	@Override
	public TO convert(FROM src)
	{
		return null;
	}

	@Override
	public FROM reverseConvert(TO src)
	{
		return null;
	}
}
