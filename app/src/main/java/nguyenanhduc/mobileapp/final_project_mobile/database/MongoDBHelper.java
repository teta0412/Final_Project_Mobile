package nguyenanhduc.mobileapp.final_project_mobile.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBHelper {
    // Replace with your actual MongoDB Atlas connection string
    private static final String CONNECTION_STRING = "mongodb+srv://bive199:<db_password>@mobiledb.ehjpy.mongodb.net/?retryWrites=true&w=majority&appName=MobileDB";
    private static final String DATABASE_NAME = "mobile_monopoly";

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            ConnectionString connString = new ConnectionString(CONNECTION_STRING);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .retryWrites(true)
                    .build();
            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase(DATABASE_NAME);
        }
        return database;
    }

    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
        }
    }
}