package io.tonycox.grid.main.cachestore;

import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.jetbrains.annotations.Nullable;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.util.Collection;
import java.util.Map;

/**
 * @author Anton Solovev
 * @since 06.03.17.
 *
 * Just for debug
 */
public abstract class StabCacheStore<K, V> implements CacheStore<K, V> {

    @Override
    public void loadCache(IgniteBiInClosure<K, V> clo, @Nullable Object... args) throws CacheLoaderException {
        System.out.println(clo);
    }

    @Override
    public void sessionEnd(boolean commit) throws CacheWriterException {
        System.out.println(commit);
    }

    @Override
    public V load(K key) throws CacheLoaderException {
        System.out.println(key);
        return null;
    }

    @Override
    public Map<K, V> loadAll(Iterable<? extends K> keys) throws CacheLoaderException {
        System.out.println(keys);
        return null;
    }

    @Override
    public void write(Cache.Entry<? extends K, ? extends V> entry) throws CacheWriterException {
        System.out.println(entry);
    }

    @Override
    public void writeAll(Collection<Cache.Entry<? extends K, ? extends V>> entries) throws CacheWriterException {
        System.out.println(entries);
    }

    @Override
    public void delete(Object key) throws CacheWriterException {
        System.out.println(key);
    }

    @Override
    public void deleteAll(Collection<?> keys) throws CacheWriterException {
        System.out.println(keys);
    }
}
