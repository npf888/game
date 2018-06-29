package com.gameserver.ip.geoip;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;

/**
 * 根据IP查询 用户地址
 * @author JavaServer
 *
 */
public class IPSearchService  implements InitializeRequired {

	private DatabaseReader readerCity = null;
//	private DatabaseReader readerCountry = null;
	
	@Override
	public void init() {
//		String contextPath = System.getProperty("user.dir");
		String cfgPath = "GeoLite2-City.mmdb";
		URL url = ClassLoader.getSystemResource(cfgPath);
		File databaseCity = new File(url.getPath());
//		File databaseCountry = new File(contextPath+"/maxmindmap/GeoLite2-Country.mmdb");
		try {
			readerCity = new DatabaseReader.Builder(databaseCity).build();
//			readerCountry = new DatabaseReader.Builder(databaseCountry).build();
		} catch (Exception e) {
			Loggers.IP_SERVICE.error("", e);
		}
	}
	/**
	 * 将IP转换成国家代号
	 */
	public String searchCountryByIp(String ip){
		try{
			CityResponse cityResponse = null;
//				CountryResponse countryResponse = null;
			InetAddress ipAddress = InetAddress.getByName(ip);
			cityResponse = readerCity.city(ipAddress);
//				countryResponse = readerCountry.country(ipAddress);
			
			Country country = cityResponse.getCountry();
			Loggers.IP_SERVICE.info(country.getIsoCode());            // 'US'
			Loggers.IP_SERVICE.info(country.getName());               // 'United States'
			Loggers.IP_SERVICE.info(country.getNames().get("zh-CN")); // '美国'
			return country.getIsoCode();
		}catch(Exception e){
		}
		return "miss";//没有查询到
	}
}
