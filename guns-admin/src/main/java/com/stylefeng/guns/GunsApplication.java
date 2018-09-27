package com.stylefeng.guns;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



import com.stylefeng.guns.modular.api.entity.AqiRoot;
import com.stylefeng.guns.modular.weatherdata.entity.hf.WeatherRoot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import com.stylefeng.guns.core.util.DbUtils;
import com.stylefeng.guns.modular.system.dao.AqiMapper;
import com.stylefeng.guns.modular.system.dao.CityMapper;
import com.stylefeng.guns.modular.system.dao.WeatherMapper;
import com.stylefeng.guns.modular.system.model.Aqi;
import com.stylefeng.guns.modular.system.model.City;
import com.stylefeng.guns.modular.system.model.Weather;
import com.stylefeng.guns.core.util.WeatherUtil;
import java.net.URLEncoder;

/**
 * SpringBoot方式启动类
 *
 * @author stylefeng
 * @Date 2017/5/21 12:06
 */
@SpringBootApplication
public class GunsApplication {

    private final static Logger logger = LoggerFactory.getLogger(GunsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GunsApplication.class, args);
        
        logger.info("GunsApplication is success!");
        String key="xxx";
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 参数：1、任务体 2、首次执行的延时时间
        //      3、任务执行间隔 4、间隔时间单位
        //25个城市1个任务

        service.scheduleAtFixedRate(()->doOrder(1,20,key), 1, 60, TimeUnit.MINUTES);
        
        service.scheduleAtFixedRate(()->doOrder(21,40,key), 5, 60, TimeUnit.MINUTES);
   
        service.scheduleAtFixedRate(()->doOrder(41,60,key), 10, 60, TimeUnit.MINUTES);
      
        service.scheduleAtFixedRate(()->doOrder(61,80, key), 15, 60, TimeUnit.MINUTES);
        
        service.scheduleAtFixedRate(()->doOrder(81,100, key), 20, 60, TimeUnit.MINUTES);
        
        service.scheduleAtFixedRate(()->doOrder(101,120,key), 25, 60, TimeUnit.MINUTES);
        
        service.scheduleAtFixedRate(()->doOrder(121,140,key), 30, 60, TimeUnit.MINUTES);
        
        service.scheduleAtFixedRate(()->doOrder(141,160,key), 35, 60, TimeUnit.MINUTES);
        
        service.scheduleAtFixedRate(()->doOrder(161,180,key), 40, 60, TimeUnit.MINUTES);
        
        service.scheduleAtFixedRate(()->doOrder(181,200,key), 45, 60, TimeUnit.MINUTES);
        
        service.scheduleAtFixedRate(()->doOrder(201,220,key), 50, 60, TimeUnit.MINUTES);
        
        service.scheduleAtFixedRate(()->doOrder(221,250,key), 55, 60, TimeUnit.MINUTES);
    }
    

    //从第n个城市到m城市写库
    public static void doOrder(int startnum, int endnum, String key) {
    	System.out.println("正在处理天气数据读取，正在读取城市列表");
    	ApplicationContext applicationContext = DbUtils.getApplicationContext();
    	CityMapper cityMapper= applicationContext.getBean(CityMapper.class);
    	List<City> cityList = cityMapper.selectList(null);
    	//判断城市数量和当前处理数
    	if (cityList.isEmpty() | (cityList.size()<startnum))
    	{
    		
    	}
    	else {
    		System.out.println("正在处理第"+startnum+"城市");
    		//循环处理
    		//for(int i=(startnum-1);i<Math.min(cityList.size()+startnum-1, endnum);i++) {
    			for(int i=(startnum-1);i< endnum;i++) {	
    		try {
    			if(i<cityList.size())
    			{
    				doweather(cityList.get(i).getCity(), key);
    			}else
    			{
    				
    			}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		}
    	}
    }
    
    

    
    //处理某城市天气读取和入库
    public static void doweather(String city, String key) throws UnsupportedEncodingException {
    		
    	    String cityutf8 = URLEncoder.encode(city, "UTF-8"); 
    		final String USER_AGENT = "Mozilla/5.0";
    		try {
    			String url="https://free-api.heweather.com/s6/weather/now?key="+key+"&location="+cityutf8;
    			//String path="https://free-api.heweather.com/s6/weather/now";
    		//String cityutf8 =encodeURI(encodeURI(city));
    		
    		URL obj = new URL(url);
    		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    		// optional default is GET
    		con.setRequestMethod("GET");
    		con.setRequestProperty("Charset", "UTF-8");
    		//add request header
    		con.setRequestProperty("User-Agent", USER_AGENT);
    	
//    		con.setRequestProperty("key", key);
//    		con.setRequestProperty("path", path);
//    		con.setRequestProperty("city", city);

    		int responseCode = con.getResponseCode();
    		System.out.println("\nSending 'GET' request to URL : " + url+city);
    		System.out.println("Response Code : " + responseCode);

    		BufferedReader in = new BufferedReader(
    		        new InputStreamReader(con.getInputStream(),"UTF-8"));
    		String inputLine;
    		StringBuffer response = new StringBuffer();

    		while ((inputLine = in.readLine()) != null) {
    			response.append(inputLine);
    		}
    		in.close();
    		
    		String url2="https://free-api.heweather.com/s6/air/now?key="+key+"&location="+cityutf8;
    		//String path2="https://free-api.heweather.com/s6/air/now";
    		
    		URL obj2 = new URL(url2);
    		HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();

    		// optional default is GET
    		con2.setRequestMethod("GET");
//    		con2.setRequestProperty("key", key);
//    		con2.setRequestProperty("path", path2);
//    		con2.setRequestProperty("city", city);
    		
    		//add request header
    		con2.setRequestProperty("User-Agent", USER_AGENT);
    		con2.setRequestProperty("Charset", "UTF-8");
    		int responseCode2 = con2.getResponseCode();
    		System.out.println("\nSending 'GET' request to URL : " + url2+city);
    		System.out.println("Response Code : " + responseCode2);

    		BufferedReader in2 = new BufferedReader(
    		        new InputStreamReader(con2.getInputStream(),"UTF-8"));
    		String inputLine2;
    		StringBuffer response2 = new StringBuffer();

    		while ((inputLine2 = in2.readLine()) != null) {
    			response2.append(inputLine2);
    		}
    		in2.close();
    	    	 
    	    	//to json
    	       String json=response.toString();
    	       String json2=response2.toString();
    	       
    	       System.out.println("Response Code : " + json.length()+" "+ json2.length());
    	        if(json.length()*json2.length()<1) { 
    	        	System.out.println("出问题了……………………"+new Date());
    	        	//System.out.println("json长度~"+json.length());
    	        	}else {
    	    	
    	    	//转换javabeen
    	    	WeatherRoot weather = JSON.parseObject(json, new TypeReference<WeatherRoot>() {});
    	    	AqiRoot aqi = JSON.parseObject(json2, new TypeReference<AqiRoot>() {});
    	    	System.out.println("当前天气:"+new Date());
    	    	
    	    	System.out.println("城市："+weather.getHeWeather6().get(0).getBasic().getLocation()); 
    	    	System.out.println("温度："+weather.getHeWeather6().get(0).getNow().getTmp());
//    	    	System.out.println("天气状况："+weather.getHeWeather6().get(0).getNow().getCondTxt());
//    	    	System.out.println("更新时间："+weather.getHeWeather6().get(0).getUpdate().getLoc());
//    	    	System.out.println("湿度："+weather.getHeWeather6().get(0).getNow().getHum());
//    	    	System.out.println("风向："+weather.getHeWeather6().get(0).getNow().getWindDir());
//    	    	System.out.println("风速："+weather.getHeWeather6().get(0).getNow().getWindSpd());
    	    	//System.out.println("pm2.5："+weather.getHeWeather6().get(0).getNow().g);
    	    	
    	    	
    	    	//写库
    	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    		    //写入weather库
    	    	ApplicationContext applicationContext = DbUtils.getApplicationContext();
    		   WeatherMapper weatherMapper= applicationContext.getBean(WeatherMapper.class);
    		   
    		   Weather entity =new Weather();
    		   entity.setCity(weather.getHeWeather6().get(0).getBasic().getLocation());
       		   entity.setUpdatetime(formatter.parse(weather.getHeWeather6().get(0).getUpdate().getLoc()));
    		   entity.setLogtime(new Date());
    		   entity.setWeathers(weather.getHeWeather6().get(0).getNow().getCondTxt());
    		   entity.setTemp(Float.valueOf(weather.getHeWeather6().get(0).getNow().getTmp()));
    		   entity.setHumidity(Float.valueOf(weather.getHeWeather6().get(0).getNow().getHum()));
    		   entity.setPm25(Float.valueOf(aqi.getHeWeather6().get(0).getAirNowCity().getPm25()));
    		   entity.setWind(Float.valueOf(weather.getHeWeather6().get(0).getNow().getWindSpd()));
    		   entity.setDiretion(weather.getHeWeather6().get(0).getNow().getWindDir());
    		   entity.setWindpower(Float.valueOf(weather.getHeWeather6().get(0).getNow().getWindSc()));
    		   entity.setPressure(Float.valueOf(weather.getHeWeather6().get(0).getNow().getPres()));
    		   weatherMapper.insert(entity);
    		   System.out.println("写完weather库:"+new Date());
    		   System.out.println(weather);
    		   //写入aqi库
    		   AqiMapper aqiMapper= applicationContext.getBean(AqiMapper.class);
    		   com.stylefeng.guns.modular.system.model.Aqi entity1 =new Aqi();
    		   entity1.setCity(weather.getHeWeather6().get(0).getBasic().getLocation());
    		  entity1.setUpdatetime(formatter.parse(aqi.getHeWeather6().get(0).getAirNowCity().getPubTime()));
    		   //System.out.println(aqi);
    		   entity1.setLogtime(new Date());
    		   entity1.setSo2(Float.valueOf(aqi.getHeWeather6().get(0).getAirNowCity().getSo2()));
    		   //entity1.setSo224(Float.valueOf(weather.getResult().getAqi().getSo224()));
    		   entity1.setNo2(Float.valueOf(aqi.getHeWeather6().get(0).getAirNowCity().getNo2()));
    		   //entity1.setNo224(Float.valueOf(weather.getResult().getAqi().getNo224()));
    		   entity1.setCo(Float.valueOf(aqi.getHeWeather6().get(0).getAirNowCity().getCo()));
    		   //entity1.setCo24(Float.valueOf(weather.getResult().getAqi().getCo24()));
    		   entity1.setO3(Float.valueOf(aqi.getHeWeather6().get(0).getAirNowCity().getO3()));
    		   //entity1.setO38(Float.valueOf(weather.getResult().getAqi().getO38()));
    		   //entity1.setO324(Float.valueOf(weather.getResult().getAqi().getO324()));
    		   entity1.setPm10(Float.valueOf(aqi.getHeWeather6().get(0).getAirNowCity().getPm10()));
    		   //entity1.setPm1024(Float.valueOf(weather.getResult().getAqi().getPm1024()));
    		   entity1.setPm25(Float.valueOf(aqi.getHeWeather6().get(0).getAirNowCity().getPm25()));
    		   //entity1.setPm2524(Float.valueOf(weather.getResult().getAqi().getPm2524()));
    		   //entity1.setIso2(Float.valueOf(weather.getResult().getAqi().getIso2()));
    		   //entity1.setIno2(Float.valueOf(weather.getResult().getAqi().getIno2()));
    		   //entity1.setIco(Float.valueOf(weather.getResult().getAqi().getIco()));
    		   //entity1.setIo3(Float.valueOf(weather.getResult().getAqi().getIo3()));
    		   //entity1.setIo38(Float.valueOf(weather.getResult().getAqi().getIo38()));
    		   //entity1.setIpm10(Float.valueOf(weather.getResult().getAqi().getIpm10()));
    		   //entity1.setIpm25(Float.valueOf(weather.getResult().getAqi().getIpm25()));
    		   entity1.setAqi(Float.valueOf(aqi.getHeWeather6().get(0).getAirNowCity().getAqi()));
    		   entity1.setPrimarypollutant(aqi.getHeWeather6().get(0).getAirNowCity().getMain());
    		   entity1.setQuality(aqi.getHeWeather6().get(0).getAirNowCity().getQlty());

    		   aqiMapper.insert(entity1);
    		   System.out.println("写完aqi库:"+new Date());
    		   System.out.println(aqi);
    	    	}
    		       		    
    	    } catch (Exception e) {
    	    	e.printStackTrace();
    	    }
    	}
	
    
    
    //utf8


		private static String changeCharSet(
    			String str, String newCharset) throws UnsupportedEncodingException {
    		if (str != null) {
    			// 用默认字符编码解码字符串。
    			byte[] bs = str.getBytes();
    			// 用新的字符编码生成字符串
    			return new String(bs, newCharset);
    		}
    		return str;
    	}
    	
    	/**
    	 * 字符串转化为UTF-8
    	 * @param str
    	 * @return
    	 */
    	public static String toUTF8(String str){
    		String result = str;
    		try {
    	        result = changeCharSet(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
    	        e.printStackTrace();
            }
    		return result;
    	}
    	
    	/**
    	 * 字节数组转化为UTF-8
    	 * @param bty
    	 * @return
    	 */
    	public static String toUTF8(byte[] bty){
    		try {
    	        if (bty.length > 0) {
    	        	return new String(bty, "UTF-8");
    	        }
            } catch (UnsupportedEncodingException e) {
    	        e.printStackTrace();
            }
    		return new String(bty);
    	}

    
}
