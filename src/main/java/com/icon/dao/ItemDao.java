package com.icon.dao;


import com.icon.dto.Item;
import com.icon.exception.BadRequestException;


public interface ItemDao {

    void save(Item item) throws BadRequestException;

    Item getItem(Long itemId) throws BadRequestException;
}
