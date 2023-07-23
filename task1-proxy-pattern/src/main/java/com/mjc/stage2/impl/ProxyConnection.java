package com.mjc.stage2.impl;


import com.mjc.stage2.Connection;

import java.util.ArrayList;
import java.util.List;

public class ProxyConnection implements Connection {
    private RealConnection realConnection;

    public ProxyConnection(RealConnection realConnection) {
        this.realConnection = realConnection;
    }

    public void reallyClose() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        int n = connectionPool.getFreeConnectionsCount();

        List<Connection> connections = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Connection connection = connectionPool.getConnection();
            connection.close();
            connections.add(connection);
        }

        for (int i = 0; i < n; i++) {
            connectionPool.releaseConnection(connections.get(i));
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
