package com.icon.dao;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.icon.config.ApplicationConfiguration;
import com.icon.dto.Item;
import com.icon.exception.BadRequestException;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.apache.log4j.Logger;
import org.bson.Document;


import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton

public class ItemDaoImpl implements ItemDao{

    private static final Logger LOGGER = Logger.getLogger(ItemDaoImpl.class);

    @Inject
    private ApplicationConfiguration applicationConfiguration;

    @Override
    public void save(Item item)  throws BadRequestException{

        try {

            Document document = new Document(new ObjectMapper().convertValue(item, Map.class));
            getMongoCollection().insertOne(document);

        }catch(Exception e){
            throw new BadRequestException("Save failed "+ e.getMessage());
        }

    }

    @Override

    public Item getItem(Long itemId) throws BadRequestException{

        Document document = getMongoCollection().find(BasicDBObject.parse("{'itemId':" + itemId + "}")).first();

       if(document != null) {
        try {
            return new ObjectMapper().readValue(document.toJson(), Item.class);
        }catch(Exception e){
            throw new BadRequestException("Item Parse Exception "+ e.getMessage());
        }
       }

        throw new BadRequestException("Item not found");
    }


    private MongoCollection<Document> getMongoCollection(){
        MongoCollection<Document> mongoCollection = applicationConfiguration.getMongoDatabase().getCollection("items");
        return mongoCollection;
    }
}
