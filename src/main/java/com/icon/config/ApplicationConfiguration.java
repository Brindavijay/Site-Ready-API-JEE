package com.icon.config;


import com.icon.annotation.MongoCollection;
import com.icon.exception.ApplicationConfigException;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class ApplicationConfiguration {


    private static final Logger LOGGER = Logger.getLogger(ApplicationConfiguration.class);
    private static final String MONGO_PROPERTIES_FILE = "mongodb.properties";
    private static final String MONGO_PACKAGE_SCAN = "com.icon.dto";

    //Mongo Properties Key
    private static final String MONGO_HOST_KEY = "host";
    private static final String MONGO_PORT_KEY = "port";
    private static final String MONGO_DATABASE_KEY = "database";

    private  MongoConfig mongoConfig;
    private MongoDatabase mongoDatabase;



   @PostConstruct
    public void loadConfigurations(){

        createMongoConfiguration();
        createMongoDatabase();

        Reflections reflections = new Reflections(MONGO_PACKAGE_SCAN);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(MongoCollection.class);

        String collectionName;
        for (Class<?> clazz : annotated) {
            collectionName = clazz.getAnnotation(MongoCollection.class).name();
            if(!GenericValidator.isBlankOrNull(collectionName)) {

                if(mongoDatabase.getCollection(collectionName) == null) {
                    mongoDatabase.createCollection(collectionName);
                }
            }
        }

    }


    public void createMongoDatabase(){

        MongoClient mongoClient = new MongoClient( mongoConfig.getHost() , mongoConfig.getPort() );
        mongoDatabase = mongoClient.getDatabase(mongoConfig.getDatabaseName());
    }

    private void createMongoConfiguration(){
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = getFileBasedConfigurationFileBasedConfigurationBuilder(MONGO_PROPERTIES_FILE);
        try
        {
            Configuration config = builder.getConfiguration();
            MongoConfig mongoConfig = new MongoConfig();
            mongoConfig.setDatabaseName(config.getString(MONGO_DATABASE_KEY));
            mongoConfig.setHost(config.getString(MONGO_HOST_KEY));
            mongoConfig.setPort(config.getInt(MONGO_PORT_KEY));
            this.mongoConfig = mongoConfig;
        }
        catch(ConfigurationException exception)
        {
            LOGGER.error("MongoDD Properties load failed", exception);
            throw new ApplicationConfigException("Can't load Mongodb properties", exception);
        }
    }

    private FileBasedConfigurationBuilder<FileBasedConfiguration> getFileBasedConfigurationFileBasedConfigurationBuilder(String fileName) {
        Parameters params = new Parameters();
        return new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.properties()
                        .setFileName(fileName));
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }
}
