package nguyenanhduc.mobileapp.final_project_mobile.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.List;

public class LeaderboardDAO {
    private static final String COLLECTION_NAME = "leaderboard";

    public static void addScoreToLeaderboard(Document player, int score) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        Document leaderboardEntry = new Document()
                .append("player_id", player.getObjectId("player_id"))
                .append("username", player.getString("username"))
                .append("total_score", score)
                .append("total_plays", 1);

        collection.insertOne(leaderboardEntry);
    }

    public static void updatePlayerScore(Document player, int score) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        Document leaderboardEntry = collection.find(Filters.eq("player_id", player.getObjectId("player_id"))).first();
        if (leaderboardEntry != null) {
            int currentScore = leaderboardEntry.getInteger("total_score");
            int currentPlays = leaderboardEntry.getInteger("total_plays");
            leaderboardEntry.put("total_score", currentScore + score);
            leaderboardEntry.put("total_plays", currentPlays + 1);
            collection.replaceOne(Filters.eq("player_id", player.getObjectId("player_id")), leaderboardEntry);
        } else {
            addScoreToLeaderboard(player, score);
        }
    }

    public static List<Document> getTopScores(int limit) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        return collection.find()
                .sort(Sorts.descending("total_score"))
                .limit(limit)
                .into(new java.util.ArrayList<>());
    }
    public static List<Document> getTopPlays(int limit) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        return collection.find()
                .sort(Sorts.descending("total_plays"))
                .limit(limit)
                .into(new java.util.ArrayList<>());
    }

    public static Document getPlayerScoreEntry(ObjectId playerId) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        return collection.find(Filters.eq("player_id", playerId)).first();
    }

    public static void deletePlayerScoreEntry(Document scoreEntry) {
        MongoDatabase database = MongoDBHelper.getDatabase();
        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

        collection.deleteOne(Filters.eq("player_id", scoreEntry.getObjectId("player_id")));
    }
}