package org.loadtest.util;

import com.couchbase.lite.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
class DatabaseManager {

    private Database database;

    DatabaseManager (){
        CouchbaseLite.init();
    }

    void createDatabase() throws CouchbaseLiteException {
        DatabaseConfiguration config = new DatabaseConfiguration();
        config.setDirectory(ConfigUtil.getDBLocation());
        database  = new Database(ConfigUtil.getDBName(), config);

        Database.log.getConsole().setDomains(LogDomain.ALL_DOMAINS);
        Database.log.getConsole().setLevel(LogLevel.DEBUG);

        Database.log.getFile().setConfig(getLogFileConfiguration());
        Database.log.getFile().setLevel(LogLevel.VERBOSE);
    }

    void closeDatabase(){
        try {
            database.close();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    Database getDatabase(){
        return this.database;
    }

    private static LogFileConfiguration getLogFileConfiguration() {
        LogFileConfiguration LogCfg = new LogFileConfiguration(ConfigUtil.getDBLogFileLocation());
        LogCfg.setMaxSize(10240);
        LogCfg.setMaxRotateCount(10);
        LogCfg.setUsePlaintext(false);
        return LogCfg;
    }
}
