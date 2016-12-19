package com.icon.service;

import com.icon.dao.ItemDao;
import com.icon.dto.Item;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/item/v1")
public class ItemService {

    @Inject
    private ItemDao itemDao;

    @POST
    @Path("/create")
    @Consumes("application/json")
    public Response createItem(final Item item){

        try {
            itemDao.save(item);
        }catch(Exception e){
            return Response.status(400).entity("{'error': 'Not able to save'}").build();
        }

        return Response.status(200).entity("{'info': 'Item created Successfully'}").build();
    }


    @GET
    @Path("/read/{itemId}")
    public Response readItem(@PathParam("itemId") Long itemId){

        try {
            Item item = itemDao.getItem(itemId);
            return Response.status(200).entity(item).build();
        }catch(Exception e){
            return Response.status(400).entity("{'error': 'Item Not found'}").build();
        }


    }
}
