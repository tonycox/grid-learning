package io.tonycox.grid;

import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author Anton Solovev
 * @since 01.03.17.
 */
public class ConfigurationBuilder {

    private String[] ips;
    private String gridName;
    private String localhost;

    private ConfigurationBuilder() {
    }

    public static ConfigurationBuilder begin() {
        return new ConfigurationBuilder();
    }

    public ConfigurationBuilder configureIp(String... ips) {
        this.ips = ips;
        return this;
    }

    public ConfigurationBuilder withName(String gridName) {
        this.gridName = gridName;
        return this;
    }

    public ConfigurationBuilder localhost(String localhost) {
        this.localhost = localhost;
        return this;
    }

    @NotNull
    public IgniteConfiguration build() {
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setLocalHost(localhost);
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder tdVmIp = new TcpDiscoveryVmIpFinder();
        tdVmIp.setAddresses(Arrays.asList(ips));
        spi.setIpFinder(tdVmIp);
        cfg.setDiscoverySpi(spi);
        if (gridName != null) {
            cfg.setGridName(gridName);
        } else {
            cfg.setGridName(UUID.randomUUID().toString());
        }
        return cfg;
    }
}
