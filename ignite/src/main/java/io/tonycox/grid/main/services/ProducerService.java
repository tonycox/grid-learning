package io.tonycox.grid.main.services;

import org.apache.ignite.services.Service;

import javax.cache.Cache;
import java.util.Collection;
import java.util.Map;

/**
 * @author Anton Solovev
 * @since 02.03.17.
 */
public interface ProducerService extends Service {
    String NAME = "producer";

    void publish(String topic, Map<String, Collection<Cache.Entry<?, ?>>> updates);
}
