package io.tonycox.grid;

import org.apache.ignite.*;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;

/**
 * @author Anton Solovev
 * @since 30.01.17.
 */
public class Main {

	public static void main(String[] args) {
		Ignition.setClientMode(true);

		Ignite ignite = Ignition.start();
		CacheConfiguration<Integer, SharedClass> config = new CacheConfiguration<>();
		config.setName("demoCache");
		config.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
		IgniteCache<Integer, SharedClass> cache = ignite.getOrCreateCache(config);

		SharedClass ggg = new SharedClass("GGG");
		ggg.setIngerType(2);
		ggg.transType = "NOTNULL";
		SharedClass just = new SharedClass("just object");
		just.setIngerType(100);

		cache.put(1, ggg);
		cache.put(2, just);

		System.out.println(cache.get(1));
		System.out.println(cache.get(2));
		System.out.println(cache.get(3));
	}
}
