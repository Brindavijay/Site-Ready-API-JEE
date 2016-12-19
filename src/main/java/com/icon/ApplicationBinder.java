package com.icon;

import com.icon.config.ApplicationConfiguration;
import com.icon.dao.ItemDao;
import com.icon.dao.ItemDaoImpl;
import com.icon.service.ItemService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(ApplicationConfiguration.class).to(ApplicationConfiguration.class);
        bind(ItemService.class).to(ItemService.class);
        bind(ItemDaoImpl.class).to(ItemDao.class);
    }
}
