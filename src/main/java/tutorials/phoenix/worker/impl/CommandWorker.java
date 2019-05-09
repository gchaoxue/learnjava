package tutorials.phoenix.worker.impl;

import tutorials.phoenix.snapshot.SnapshotService;
import tutorials.phoenix.worker.DefaultWorkerImpl;

/**
 * Created by gchaoxue on 2018/12/4
 */
public class CommandWorker<T> extends DefaultWorkerImpl<T> {

    public CommandWorker(SnapshotService snapshotService) {
        super(snapshotService);
    }

    @Override
    public void startup() {
        if (!init()) {
            // log error and exit
            return;
        }

        if (!eventSourcing()) {
            // log error and exit
            return;
        }
    }

    public void startThread() {

    }

    public boolean init() {
        // 1. scan commands and events
        //

        return true;
    }

    @Override
    public boolean eventSourcing(){

        // get basic offsets

        // load snapshot and update offsets
        loadSnapshot();

        // check offsets value

        // rerun events

        // rerun commands

        // print es results

        return true;
    }
}
