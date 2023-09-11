package org.loadtest.util;

import com.couchbase.lite.ConsoleLogger;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Log;

public class DbUtils {

    private static Database database;
    private static DatabaseReplicator replicator;

    static {
        DatabaseManager dbManager = new DatabaseManager();
        try {
            dbManager.createDatabase();
            database = dbManager.getDatabase();
            replicator = new DatabaseReplicator(database);

        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    public static Database getDatabase() {
        return database;
    }

    public static void startReplication() {
        replicator.startReplication();
    }

    public static void stopReplication() {
        replicator.stopReplication();
    }

    public static void addReplicationListener() {
        replicator.initializeListener();
    }

    public static void getPendingDocsForReplication() {
        replicator.getPendingDocIds();
    }

    public static Boolean getReplicationAction() {
        return ConfigUtil.getReplicatorAction();
    }

}
