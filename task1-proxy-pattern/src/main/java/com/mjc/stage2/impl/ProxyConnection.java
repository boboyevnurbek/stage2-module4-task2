package com.mjc.stage2.impl;


import com.mjc.stage2.Connection;

public class ProxyConnection implements Connection {
    private RealConnection realConnection;

    public ProxyConnection(RealConnection realConnection) {
        this.realConnection = realConnection;
    }

    public void reallyClose() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        if(connection.isClosed()){
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void close() {
        realConnection.close();
    }

    @Override
    public boolean isClosed() {
        return realConnection.isClosed();
    }
}
