package io.tonycox.grid.main.cachestore;

import org.apache.ignite.cache.store.CacheStore;

import javax.cache.configuration.Factory;

/**
 * @author Anton Solovev
 * @since 03.03.17.
 */
public class DataCapturerBusFactory<K, V> implements Factory<CacheStore<K, V>> {
    @Override
    public CacheStore<K, V> create() {
        return new DataCapturerBus<>();
    }
}
