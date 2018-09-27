package com.stylefeng.guns.modular.api.entity;

import java.util.ArrayList;

public class HeWeather6 {

	  private AirNowCity air_now_city;

	  public AirNowCity getAirNowCity() { return this.air_now_city; }

	  public void setAirNowCity(AirNowCity air_now_city) { this.air_now_city = air_now_city; }

	  private ArrayList<AirNowStation> air_now_station;

	  public ArrayList<AirNowStation> getAirNowStation() { return this.air_now_station; }

	  public void setAirNowStation(ArrayList<AirNowStation> air_now_station) { this.air_now_station = air_now_station; }

	  private Basic basic;

	  public Basic getBasic() { return this.basic; }

	  public void setBasic(Basic basic) { this.basic = basic; }

	  private String status;

	  public String getStatus() { return this.status; }

	  public void setStatus(String status) { this.status = status; }

	  private Update update;

	  public Update getUpdate() { return this.update; }

	  public void setUpdate(Update update) { this.update = update; }
}
