package demos.leaderselection;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gchaoxue on 2019/5/30
 */
public class ZookeeperHaStateImpl implements HaState {

    private static final String LEADER_PATH_PREFIX = "/leader-election/";
    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperHaStateImpl.class);
    private static final int SESSION_TIMEOUT_MS = 3 * 1000;
    private static final int CONNECTION_TIME_MS = 9 * 1000;

    private volatile State state;

    private String zkConnect;
    private String serviceName;
    private String instanceId;
    private LeaderSelector leaderSelector;
    private Thread thread;

    /**
     * HaState service start once and throw it into the spring container.
     *
     * @param serviceName
     * @param zkConnect address list for zookeeper connection
     */
    public ZookeeperHaStateImpl(String serviceName, String zkConnect, String instanceId) {
        this.serviceName = serviceName;
        this.zkConnect = zkConnect;
        this.state = State.NEW;

        if (this.serviceName == null || this.serviceName.isEmpty()) {
            throw new IllegalArgumentException("invalid service name: null or empty");
        }
        if (this.zkConnect == null || zkConnect.isEmpty()) {
            throw new IllegalArgumentException("invalid zookeeper connect: null or empty");
        }
        thread = new Thread(() -> {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorFramework client =
                    CuratorFrameworkFactory.newClient(zkConnect, SESSION_TIMEOUT_MS, CONNECTION_TIME_MS, retryPolicy);
            client.start();
            LOG.info("curator client started: zkConnect<{}>, serviceName<{}>", zkConnect, serviceName);

            leaderSelector = new LeaderSelector(client, LEADER_PATH_PREFIX + serviceName, new LeaderSelectorListener() {
                @Override
                public void takeLeadership(CuratorFramework client) throws Exception {
                     LOG.info("instance<{}> get leadership", instanceId);
                     state = State.MASTER;
                     while(!Thread.interrupted()) {
                         try {
                             Thread.sleep(1000);
                         } catch (InterruptedException e) {
                             Thread.currentThread().interrupt();
                         }
                     }
                     state = State.STOP;
                     LOG.info("instance<{}> release leadership,", instanceId);
                }

                @Override
                public void stateChanged(CuratorFramework client, ConnectionState newState) {
                    LOG.info("connection state changed: newState<{}>", newState);
                    if (newState == ConnectionState.SUSPENDED || newState == ConnectionState.LOST) {
                        LOG.info("try to interrupt the leadership");
                        leaderSelector.interruptLeadership();
                    }
                }
            });

            state = State.SLAVE;
            leaderSelector.start();
        }, "Zookeeper HA state service");
        thread.start();
    }

    @Override
    public State getState() {
        return this.state;
    }
}
