package nguyenanhduc.mobileapp.final_project_mobile.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.Date;

import nguyenanhduc.mobileapp.final_project_mobile.database.MongoDBHelper;

public class PlayerDAO {
    private static final String COLLECTION_NAME = "players";

    public static void createPlayer(String username) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        Document playerDoc = new Document()
                .append("username", username)
                .append("creation_date", new Date())
                .append("player_id", new ObjectId());

        collection.insertOne(playerDoc);
    }

    public static Document getPlayerByUsername(String username) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        return collection.find(Filters.eq("username", username)).first();
    }

    public static void updatePlayer(Document player) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        collection.replaceOne(Filters.eq("player_id", player.getObjectId("player_id")), player);
    }

    public static void deletePlayer(Document player) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        collection.deleteOne(Filters.eq("player_id", player.getObjectId("player_id")));
    }
}