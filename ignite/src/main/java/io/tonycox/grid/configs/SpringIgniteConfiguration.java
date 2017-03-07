package io.tonycox.grid.configs;

import org.apache.ignite.IgniteSpringBean;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.DiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.TcpDiscoveryIpFinder;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Anton Solovev
 * @since 02.03.17.
 */
@Configuration
@Import(SpringCacheStoreConfiguration.class)
public abstract class SpringIgniteConfiguration implements Serializable {

    @Autowired
    private SpringCacheStoreConfiguration cacheStoreCfg;

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public String gridName() {
        return UUID.randomUUID().toString();
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public abstract String localHost();

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public abstract List<String> discoverIps();

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public DiscoverySpi discoverySpi() {
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        spi.setIpFinder(ipFinder());
        return spi;
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public TcpDiscoveryIpFinder ipFinder() {
        TcpDiscoveryVmIpFinder tdVmIp = new TcpDiscoveryVmIpFinder();
        tdVmIp.setAddresses(discoverIps());
        return tdVmIp;
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public IgniteConfiguration igniteConfiguration() {
        return new IgniteConfiguration() {{
            setCacheConfiguration(
                    cacheStoreCfg.userCacheConfiguration(),
                    cacheStoreCfg.justCacheConfiguration());
            setLocalHost(localHost());
            setGridName(gridName());
            setDiscoverySpi(discoverySpi());
        }};
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public IgniteSpringBean igniteBean() {
        IgniteSpringBean ignite = new IgniteSpringBean();
        ignite.setConfiguration(igniteConfiguration());
        return ignite;
    }

}
