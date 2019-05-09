package tutorials.phoenix.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gchaoxue on 2018/12/4
 */
public class Repository<T/*Aggregator class*/> {

    // class of the aggregators
    private Class<T> tClass;

    // aggregatorId -> aggregator storage
    private Map<String, T> aggregators = new HashMap<>();

    public Repository(Class<T> tClass) {
        this.tClass = tClass;
    }

    /**
     * Get aggregator object by aggregatorId.
     *
     * @param aggregatorId
     * @return aggregator object if success, null if not exist.
     */
    public T getAggregator(String aggregatorId) {
        return aggregators.getOrDefault(aggregatorId, null);
    }

    /**
     * New aggregator instance by tClass.
     *
     * @return T aggregator instance if success, otherwise return null.
     * @throws ReflectiveOperationException
     */
    public T newAggregator() throws ReflectiveOperationException{

        if (null == tClass) {
            // log error
            throw new NullPointerException("new aggregator failed: tClass is null");
        }

        try {
            T aggregator = tClass.newInstance();
            return aggregator;
        } catch (ReflectiveOperationException e) {
            throw new ReflectiveOperationException("new aggregator failed", e);
        }
    }

    public void putAggregator(String aggregatorId, T aggregator) {
        if (null == aggregatorId) {
            throw new NullPointerException("aggregatorId is null");
        }
        if (null == aggregator) {
            throw new NullPointerException("aggregator is null");
        }
    }

    public void putAggregator(String aggregatorId, String jsonString) {
        // todo: impl
    }

    public void clear() {
        aggregators.clear();
    }

    public void setTClass(Class<T> tClass) {
        this.tClass = tClass;
    }

    public Class<T> getTClass() {
        return tClass;
    }
}
