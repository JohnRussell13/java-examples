package com.server;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        TransferServer server = new TransferServer();
        server.start(6666);
    }
}
