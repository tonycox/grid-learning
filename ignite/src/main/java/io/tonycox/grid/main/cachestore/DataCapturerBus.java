package io.tonycox.grid.main.cachestore;

import io.tonycox.grid.main.services.ProducerService;
import org.apache.ignite.Ignite;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.internal.processors.cache.CacheEntryImpl;
import org.apache.ignite.resources.CacheNameResource;
import org.apache.ignite.resources.CacheStoreSessionResource;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.transactions.Transaction;

import javax.cache.Cache;
import javax.cache.integration.CacheWriterException;
import java.util.*;

/**
 * @author Anton Solovev
 * @since 03.03.17.
 */
public class DataCapturerBus<K, V> extends StabCacheStore<K, V> {

    /**
     * Name of session property which contains information about caches changed during transaction.
     */
    private static final String CACHES_PROPERTY_NAME = "CACHES_PROPERTY_NAME";

    /**
     * Name of session property which contains information about entries changed in specific cache during transaction.
     */
    private static final String BUFFER_PROPERTY_NAME = "BUFFER_PROPERTY_NAME";

    @IgniteInstanceResource
    private Ignite ignite;

    @CacheNameResource
    private String cacheName;

    @CacheStoreSessionResource
    private CacheStoreSession session;

    private Collection<Cache.Entry<?, ?>> getBuffer() {
        Map<Object, Object> properties = session.properties();
        Set<String> caches = (Set<String>) properties.get(CACHES_PROPERTY_NAME);
        if (caches == null) {
            properties.put(CACHES_PROPERTY_NAME, caches = new HashSet<>());
            properties.put(BUFFER_PROPERTY_NAME, new HashMap<String, Map>());
        }
        Map<String, Collection<Cache.Entry<?, ?>>> buffer = (Map<String, Collection<Cache.Entry<?, ?>>>) properties.get(BUFFER_PROPERTY_NAME);
        if (caches.add(cacheName)) {
            Collection<Cache.Entry<?, ?>> cacheBuffer = new ArrayList<>();
            buffer.put(cacheName, cacheBuffer);
            return cacheBuffer;
        } else {
            return buffer.get(cacheName);
        }
    }

    @Override
    public void write(Cache.Entry<? extends K, ? extends V> entry) throws CacheWriterException {
        Transaction transaction = session.transaction();
        if (transaction == null) {
            Collection<Cache.Entry<?, ?>> entries =
                    Collections.singletonList(new CacheEntryImpl(entry.getKey(), entry.getValue()));

            publish(Collections.singletonMap(cacheName, entries));
        } else {
            getBuffer().add(new CacheEntryImpl<>(entry.getKey(), entry.getValue()));
        }
    }

    @Override
    public void sessionEnd(boolean commit) throws CacheWriterException {
        Transaction transaction = session.transaction();
        if (transaction == null) {
            return;
        }
        Map<Object, Object> properties = session.properties();
        if (!commit) {
            Map bigBuffer = (Map) properties.get(BUFFER_PROPERTY_NAME);
            if (bigBuffer != null) {
                bigBuffer.remove(cacheName);
            }
        }
        Set<String> caches = (Set<String>) properties.get(CACHES_PROPERTY_NAME);
        if (caches != null && caches.remove(cacheName) && caches.isEmpty()) {
            Map<String, Collection<Cache.Entry<?, ?>>> buffer =
                    (Map<String, Collection<Cache.Entry<?, ?>>>) properties.get(BUFFER_PROPERTY_NAME);
            publish(buffer);
        }
    }

    private void publish(Map<String, Collection<Cache.Entry<?, ?>>> buffer) {
        ProducerService producer = ignite.services().serviceProxy(ProducerService.NAME, ProducerService.class, false);
        producer.publish("topic", buffer);
    }
}
