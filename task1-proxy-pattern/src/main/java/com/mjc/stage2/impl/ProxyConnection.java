package com.mjc.stage2.impl;


import com.mjc.stage2.Connection;

public class ProxyConnection implements Connection {
    private RealConnection realConnection;

    public ProxyConnection(RealConnection realConnection) {
        this.realConnection = realConnection;
    }

    public void reallyClose() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        while (connectionPool.getFreeConnectionsCount() != 0) {
            Connection connection = connectionPool.getConnection();
            connection.close();
        }
    }

    @Override
    public void close() {
        if (!realConnection.isClosed()) {
            realConnection.close();
        }
    }

    @Override
    public boolean isClosed() {
        return realConnection.isClosed();
    }
}
