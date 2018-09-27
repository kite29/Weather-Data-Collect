package com.stylefeng.guns.modular.aqiData.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.ToolUtil;

import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.system.model.Aqi;
import com.stylefeng.guns.modular.system.model.City;
import com.stylefeng.guns.modular.aqiData.service.IAqiService;

/**
 * Aqi数据控制器
 *
 * @author fengshuonan
 * @Date 2018-09-11 14:37:34
 */
@Controller
@RequestMapping("/aqi")
public class AqiController extends BaseController {

    private String PREFIX = "/AqiData/aqi/";

    @Autowired
    private IAqiService aqiService;

    /**
     * 跳转到Aqi数据首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "aqi.html";
    }

    /**
     * 跳转到添加Aqi数据
     */
    @RequestMapping("/aqi_add")
    public String aqiAdd() {
        return PREFIX + "aqi_add.html";
    }

    /**
     * 跳转到修改Aqi数据
     */
    @RequestMapping("/aqi_update/{aqiId}")
    public String aqiUpdate(@PathVariable Integer aqiId, Model model) {
        Aqi aqi = aqiService.selectById(aqiId);
        model.addAttribute("item",aqi);
        LogObjectHolder.me().set(aqi);
        return PREFIX + "aqi_edit.html";
    }

    /**
     * 获取Aqi数据列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	
    	if(ToolUtil.isEmpty(condition)) {
    		return aqiService.selectList(null);
      	}else {
      		EntityWrapper<Aqi> entityWrapper= new EntityWrapper<>();
      		Wrapper<Aqi> wrapper=entityWrapper.like("city",condition);
      		 return aqiService.selectList(wrapper);
      	}
        
    }

    /**
     * 新增Aqi数据
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Aqi aqi) {
        aqiService.insert(aqi);
        return SUCCESS_TIP;
    }

    /**
     * 删除Aqi数据
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer aqiId) {
        aqiService.deleteById(aqiId);
        return SUCCESS_TIP;
    }

    /**
     * 修改Aqi数据
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Aqi aqi) {
        aqiService.updateById(aqi);
        return SUCCESS_TIP;
    }

    /**
     * Aqi数据详情
     */
    @RequestMapping(value = "/detail/{aqiId}")
    @ResponseBody
    public Object detail(@PathVariable("aqiId") Integer aqiId) {
        return aqiService.selectById(aqiId);
    }
}
