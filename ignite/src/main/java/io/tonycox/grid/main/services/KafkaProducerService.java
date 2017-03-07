package io.tonycox.grid.main.services;

import org.apache.ignite.Ignite;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceContext;

import javax.cache.Cache;
import java.util.Collection;
import java.util.Map;


/**
 * @author Anton Solovev
 * @since 01.03.17.
 */
public class KafkaProducerService implements ProducerService {

    private transient Publisher publisher;

    @IgniteInstanceResource
    private transient Ignite ignite;

    @Override
    public void cancel(ServiceContext ctx) {
    }

    @Override
    public void init(ServiceContext ctx) throws Exception {
        publisher = new Publisher(ignite);
    }

    @Override
    public void execute(ServiceContext ctx) throws Exception {
    }

    public void publish(String topic, Map<String, Collection<Cache.Entry<?, ?>>> updates) {
        publisher.publish(topic, updates);
    }
}
