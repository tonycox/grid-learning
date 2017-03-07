package io.tonycox.grid.configs;

import io.tonycox.grid.main.cachestore.DataCapturerBusFactory;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.Factory;
import java.io.Serializable;

/**
 * @author Anton Solovev
 * @since 03.03.17.
 */
@Configuration
public class SpringCacheStoreConfiguration implements Serializable {

    @Bean(name = "userCache")
    public <K, V> CacheConfiguration<K, V> userCacheConfiguration() {
        return new CacheConfiguration<K, V>() {{
            setName("userCache");
            setWriteThrough(true);
            setCacheMode(CacheMode.PARTITIONED);
            setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
            setBackups(2);
            setCacheStoreFactory(cacheStoreFactory());
        }};
    }

    @Bean(name = "justCache")
    public <K, V> CacheConfiguration<K, V> justCacheConfiguration() {
        return new CacheConfiguration<K, V>() {{
            setName("justCache");
            setWriteThrough(true);
            setCacheMode(CacheMode.PARTITIONED);
            setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
            setBackups(2);
            setCacheStoreFactory(cacheStoreFactory());
        }};
    }

    @Bean
    public <K, V> Factory<CacheStore<K, V>> cacheStoreFactory() {
        return new DataCapturerBusFactory<>();
    }
}
