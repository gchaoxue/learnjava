package tutorials.phoenix.worker;

import tutorials.phoenix.annotation.AggregatorDefinition;
import tutorials.phoenix.message.Message;
import tutorials.phoenix.mq.Consumer;
import tutorials.phoenix.mq.Producer;
import tutorials.phoenix.repository.Repository;
import tutorials.phoenix.snapshot.SnapshotService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.rmi.MarshalException;

/**
 * Created by gchaoxue on 2018/12/4
 */
public abstract class DefaultWorkerImpl<T> implements Worker {

    protected String name;
    protected long commandStartOffset;
    protected long eventStartOffset;

    protected Repository<T> repository;

    protected Consumer cmdConsumer;
    protected Consumer eventConsumer;
    protected Producer cmdProducer;
    protected Producer eventProducer;

    private SnapshotService snapshotServiceImpl;

    public DefaultWorkerImpl(SnapshotService snapshotService) {
        this.snapshotServiceImpl = snapshotService;
    }

    /**
    * Run event-sourcing process.
    *
    * @return
    */
    public abstract boolean eventSourcing();

    /** Get the field name which defining the aggregatorId of a business message handling method.
     *
     * @param aggregatorClassName the target business aggregator class name.
     * @param methodName the target business handling method name.
     * @param argumentClassName the argument class name of the method.
     * @return a <code>String</code> indicating the target field name.
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    private String getAggregatorIdFieldName(String aggregatorClassName, String methodName, String argumentClassName)
            throws ClassNotFoundException, NoSuchMethodException {

        Class aggregatorClass;  // throws ClassNotFoundException with msg
        try {
            aggregatorClass = Class.forName(aggregatorClassName);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Aggregator class not found: " + aggregatorClassName, e);
        }
        Class argumentClass;  // throws ClassNotFoundException with msg
        try {
            argumentClass = Class.forName(argumentClassName);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Argument class not found: " + argumentClassName, e);
        }

        // todo: how to handle RuntimeException - SecurityException
        Method method;
        try {
            method = aggregatorClass.getDeclaredMethod(methodName, argumentClass);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException("Method: " + methodName + "(" + argumentClassName + ")" +
                    " not found in aggregator class: " + aggregatorClassName);
        }
        
        AggregatorDefinition definition = method.getAnnotation(AggregatorDefinition.class);
        String fieldName = definition.aggregatorId();

        if (null == fieldName || fieldName.isEmpty()) {
            throw new RuntimeException("aggregatorId is null or empty within @AggregatorDefinition");
        }

        return fieldName;
    }

    private String getAggregatorId(Message message, String fieldName) {
        return null;
    }

    public boolean loadSnapshot() {
        // invoke default impl
        snapshotServiceImpl.loadSnapshot();

        // modify offsets
        commandStartOffset = 123;
        eventStartOffset = 123;

        // maybe check offsets

        return true;
    }

    public boolean saveSnapshot() {

        // maybe check offsets or some other things
        snapshotServiceImpl.saveSnapshot();

        return true;
    }

    public String getName() {
        return name;
    }

    public long getCommandStartOffset() {
        return commandStartOffset;
    }

    public long getEventStartOffset() {
        return eventStartOffset;
    }
}
