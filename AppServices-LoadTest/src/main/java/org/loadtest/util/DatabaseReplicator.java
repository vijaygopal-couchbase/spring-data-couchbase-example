package org.loadtest.util;

import com.couchbase.lite.AbstractReplicatorConfiguration.ReplicatorType;
import com.couchbase.lite.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class DatabaseReplicator {

    private Database database;

    private Replicator replicator;

    private ListenerToken replicatorListenerToken;

    DatabaseReplicator(Database database) {

        this.database = database;
        replicator = getReplicator();
    }

    private Replicator getReplicator() {

        ReplicatorConfiguration replicatorConfig = null;
        replicatorConfig = new ReplicatorConfiguration(database, new URLEndpoint(ConfigUtil.getSGEndPoint()));
        replicatorConfig.setReplicatorType(ReplicatorType.PUSH_AND_PULL);

        replicatorConfig.setContinuous(true);
        replicatorConfig.setPinnedServerX509Certificate(ConfigUtil.getAppServicesCert());
        //replicatorConfig.setConflictResolver(new DbConflictResolver());

        BasicAuthenticator authDetails = new BasicAuthenticator(ConfigUtil.getDbUserName(), ConfigUtil.getDBPassword().toCharArray());
        replicatorConfig.setAuthenticator(authDetails);

        replicator = new Replicator(replicatorConfig);

        return replicator;
    }

    void startReplication() {
        replicator.start();
    }

    void stopReplication() {
    	replicator.removeChangeListener(replicatorListenerToken);
    	replicator.stop();
    }

     Set<String> getPendingDocIds() {
        try {
            return replicator.getPendingDocumentIds();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        return Collections.<String>emptySet();
    }

     void initializeListener() {
        replicatorListenerToken = replicator.addDocumentReplicationListener(replication -> {

        });
    }
}
