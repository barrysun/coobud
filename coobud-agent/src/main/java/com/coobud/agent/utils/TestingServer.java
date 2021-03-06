package com.coobud.agent.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * manages an internally running ZooKeeper server. FOR TESTING PURPOSES ONLY
 */
public class TestingServer implements Closeable
{
    static
    {
        ByteCodeRewrite.apply();
    }

    private final TestingZooKeeperServer testingZooKeeperServer;
    private final InstanceSpec spec;

    /**
     * Create the server using a random port
     *
     * @throws Exception errors
     */
    public TestingServer() throws Exception
    {
        this(-1, null, true);
    }

    /**
     * Create the server using a random port
     *
     * @param start True if the server should be started, false otherwise
     * @throws Exception errors
     */
    public TestingServer(boolean start) throws Exception
    {
        this(-1, null, start);
    }

    /**
     * Create and start the server using the given port
     *
     * @param port the port
     * @throws Exception errors
     */
    public TestingServer(int port) throws Exception
    {
        this(port, null, true);
    }

    /**
     * Create the server using the given port
     *
     * @param port  the port
     * @param start True if the server should be started, false otherwise
     * @throws Exception errors
     */
    public TestingServer(int port, boolean start) throws Exception
    {
        this(port, null, start);
    }

    /**
     * Create and start the server using the given port
     *
     * @param port          the port
     * @param tempDirectory directory to use
     * @throws Exception errors
     */
    public TestingServer(int port, File tempDirectory) throws Exception
    {
        this(port, tempDirectory, true);
    }

    /**
     * Create the server using the given port
     *
     * @param port          the port
     * @param tempDirectory directory to use
     * @param start         True if the server should be started, false otherwise
     * @throws Exception errors
     */
    public TestingServer(int port, File tempDirectory, boolean start) throws Exception
    {
        this(new InstanceSpec(tempDirectory, port, -1, -1, true, -1), start);
    }

    /**
     * Create the server using the given port
     *
     * @param spec  instance details
     * @param start True if the server should be started, false otherwise
     * @throws Exception errors
     */
    public TestingServer(InstanceSpec spec, boolean start) throws Exception
    {
        this.spec = spec;
        testingZooKeeperServer = new TestingZooKeeperServer(new QuorumConfigBuilder(spec));

        if ( start )
        {
            testingZooKeeperServer.start();
        }
    }

    /**
     * Return the port being used
     *
     * @return port
     */
    public int getPort()
    {
        return spec.getPort();
    }

    /**
     * Returns the temp directory being used
     *
     * @return directory
     */
    public File getTempDirectory()
    {
        return spec.getDataDirectory();
    }

    /**
     * Start the server
     *
     * @throws Exception
     */
    public void start() throws Exception
    {
        testingZooKeeperServer.start();
    }

    /**
     * Stop the server without deleting the temp directory
     */
    public void stop() throws IOException
    {
        testingZooKeeperServer.stop();
    }

    /**
     * Restart the server. If the server is currently running it will be stopped
     * and restarted. If it's not currently running then it will be started. If
     * it has been closed (had close() called on it) then an exception will be
     * thrown.
     *
     * @throws Exception
     */
    public void restart() throws Exception
    {
        testingZooKeeperServer.restart();
    }

    /**
     * Close the server and any open clients and delete the temp directory
     */
    @Override
    public void close() throws IOException
    {
        testingZooKeeperServer.close();
    }

    /**
     * Returns the connection string to use
     *
     * @return connection string
     */
    public String getConnectString()
    {
        return spec.getConnectString();
    }
}
