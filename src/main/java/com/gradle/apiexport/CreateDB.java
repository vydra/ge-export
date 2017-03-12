package com.gradle.apiexport;

import org.knowm.yank.PropertiesUtils;
import org.knowm.yank.Yank;

import java.util.Properties;

public class CreateDB {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Properties dbProps = PropertiesUtils.getPropertiesFromClasspath("POSTGRES.properties");

        Properties createTableProps = new Properties();
        createTableProps.put("DROP_TASKS", "DROP TABLE tasks");
        createTableProps.put("CREATE_TASKS","CREATE TABLE tasks(\n" +
                "   id                   bigserial PRIMARY KEY   NOT NULL,\n" +
                "   task_id              text     NOT NULL,   \n" +
                "   build_id             text    NOT NULL,  \n" +
                "   path                 text     NOT NULL, \n" +
                "   duration_millis      int  NOT NULL" +
                ");");

        Yank.setupDefaultConnectionPool(dbProps);
        Yank.addSQLStatements(createTableProps);

        // create table
        Yank.executeSQLKey("DROP_TASKS", null);
        Yank.executeSQLKey("CREATE_TASKS", null);

        Yank.releaseDefaultConnectionPool();
    }
}

/*
pg_ctl -D /usr/local/var/postgres/data -l /usr/local/var/postgres/data/server.log start

 */