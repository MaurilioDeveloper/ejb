package com.rci.omega2.ejb.dao.jdbc;

import java.sql.Connection;
import javax.annotation.Resource;
import javax.sql.DataSource;

public class JdbcBaseDAO{

    
    @Resource(mappedName="java:/jboss/datasources/omega2DS") 
    private DataSource jdbcDataSource;

    private Connection connection;

    protected Connection getJdbcConnection() throws Exception {
        if(connection == null || connection.isClosed()){
            connection = jdbcDataSource.getConnection();
        }
        return connection;
    }

    
    protected void jdbcClose() throws Exception {
        if(!connection.isClosed()){
            connection.close();
        }
    }
    
    protected void jdbcCommit() throws Exception {
        if(!connection.isClosed()){
            connection.commit();
        }
    }

    protected void jdbcRollback() throws Exception {
        if(!connection.isClosed()){
            connection.rollback();
        }
    }
    
    protected void jdbcCommitAndClose() throws Exception {
        if(!connection.isClosed()){
            connection.commit();
            connection.close();
        }
    }

    protected void jdbcRollbackAndClose() throws Exception {
        if(!connection.isClosed()){
            connection.rollback();
            connection.close();
        }
    }

    public void setJdbcDataSource(DataSource jdbcDataSource) {
        this.jdbcDataSource = jdbcDataSource;
    }

    
}
