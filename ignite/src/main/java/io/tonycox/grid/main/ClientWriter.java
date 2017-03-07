package io.tonycox.grid.main;

import io.tonycox.grid.configs.IgniteCfgBuilder;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.transactions.Transaction;

import static org.apache.ignite.transactions.TransactionConcurrency.OPTIMISTIC;
import static org.apache.ignite.transactions.TransactionIsolation.SERIALIZABLE;

/**
 * @author Anton Solovev
 * @since 02.03.17.
 */
public class ClientWriter {
    public static void main(String[] args) {

        IgniteConfiguration cfg = IgniteCfgBuilder.begin()
                .isClient(true)
                .localhost("127.0.0.3")
                .findIp("127.0.0.1:47500..47509")
                .build();

        Ignite ignite = Ignition.start(cfg);

        IgniteCache<String, String> userCache = ignite.cache("userCache");
        IgniteCache<String, String> justCache = ignite.cache("justCache");

        for (int i = 0; true; i++) {
            try (Transaction tx = ignite.transactions().txStart(OPTIMISTIC, SERIALIZABLE)) {
                userCache.put(String.valueOf(i), String.valueOf((i * i) % 9));
                justCache.put(String.valueOf(i) + 1, String.valueOf((i * i) % 9));
                tx.commit();
            }
        }
    }
}
