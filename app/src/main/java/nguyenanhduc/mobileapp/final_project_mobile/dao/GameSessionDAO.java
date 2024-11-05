package nguyenanhduc.mobileapp.final_project_mobile.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.Date;
import java.util.List;

import database.MongoDBHelper;

public class GameSessionDAO {
    private static final String COLLECTION_NAME = "game_sessions";

    public static void createGameSession(List<Document> players, int mode) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        Document sessionDoc = new Document()
                .append("players", players)
                .append("start_time", new Date())
                .append("end_time", null) // Set to null initially
                .append("player_id", null) // Set to null initially
                .append("winner_score", 0); // Set to 0 initially

        collection.insertOne(sessionDoc);
    }

    public static List<Document> getGameSessionsByPlayerId(ObjectId playerId) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        return collection.find(Filters.in("players.player_id", playerId)).into(new java.util.ArrayList<>());
    }

    public static void updateGameSession(Document gameSession) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        collection.replaceOne(Filters.eq("game_id", gameSession.getObjectId("game_id")), gameSession);
    }

    public static void deleteGameSession(Document gameSession) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        collection.deleteOne(Filters.eq("game_id", gameSession.getObjectId("game_id")));
    }

    public static void setGameSessionWinner(Document gameSession, ObjectId winnerId, int winnerScore) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        gameSession.put("end_time", new Date());
        gameSession.put("winner_id", winnerId);
        gameSession.put("winner_score", winnerScore);

        collection.replaceOne(Filters.eq("game_id", gameSession.getObjectId("game_id")), gameSession);
    }
}