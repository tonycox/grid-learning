package io.tonycox.grid.configs;

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
public class IgniteCfgBuilder {

    private String[] ips;
    private String gridName;
    private String localhost;
    private boolean isClient;

    private IgniteCfgBuilder() {
    }

    public static IgniteCfgBuilder begin() {
        return new IgniteCfgBuilder();
    }

    public IgniteCfgBuilder findIp(String... ips) {
        this.ips = ips;
        return this;
    }

    public IgniteCfgBuilder withName(String gridName) {
        this.gridName = gridName;
        return this;
    }

    public IgniteCfgBuilder localhost(String localhost) {
        this.localhost = localhost;
        return this;
    }

    @NotNull
    public IgniteConfiguration build() {
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder tdVmIp = new TcpDiscoveryVmIpFinder();
        tdVmIp.setAddresses(Arrays.asList(ips));
        spi.setIpFinder(tdVmIp);

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setLocalHost(localhost);
        cfg.setClientMode(isClient);

        cfg.setDiscoverySpi(spi);
        if (gridName != null) {
            cfg.setGridName(gridName);
        } else {
            cfg.setGridName(UUID.randomUUID().toString());
        }
        return cfg;
    }

    public IgniteCfgBuilder isClient(boolean isClient) {
        this.isClient = isClient;
        return this;
    }
}
