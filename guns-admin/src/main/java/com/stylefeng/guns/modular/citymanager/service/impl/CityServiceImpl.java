package com.stylefeng.guns.modular.citymanager.service.impl;

import com.stylefeng.guns.modular.system.model.City;
import com.stylefeng.guns.modular.system.dao.CityMapper;
import com.stylefeng.guns.modular.citymanager.service.ICityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 城市表 服务实现类
 * </p>
 *
 * @author Michael Wong
 * @since 2018-09-08
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements ICityService {

}
