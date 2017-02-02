package io.tonycox.hazelcast;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.Map;

/**
 * @author Anton Solovev
 * @since 02.02.17.
 */
public class StartClient {
	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
		// change ip according to dockerized hazelcast
		clientConfig.getNetworkConfig().addAddress("172.17.0.4:5701");
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

		Map<Integer, String> mapCustomers = client.getMap("customers");
		mapCustomers.put(1, "Joe");
		mapCustomers.put(2, "Ali");
		mapCustomers.put(3, "Avi");

		IMap map = client.getMap("customers");
		System.out.println("Map Size:" + map.size());
	}
}
