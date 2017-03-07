package io.tonycox.grid.main;

import io.tonycox.grid.configs.KafkaFabrique;
import io.tonycox.grid.configs.SpringIgniteConfiguration;
import io.tonycox.grid.main.services.KafkaProducerService;
import io.tonycox.grid.main.services.ProducerService;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteSpringBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * @author Anton Solovev
 * @since 01.03.17.
 */
public class MainGrid {

    public static void main(String[] args) {

        System.out.println("Main Cluster starts");

        @Configuration
        class MainConfig extends SpringIgniteConfiguration {
            @Override
            public String localHost() {
                return "127.0.0.1";
            }

            @Override
            public List<String> discoverIps() {
                return Collections.singletonList(localHost() + ":47500..47509");
            }
        }

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(
                MainConfig.class, KafkaFabrique.class);

        appContext.getBean(IgniteSpringBean.class);
        appContext.getBean(IgniteSpringBean.class);
        appContext.getBean(IgniteSpringBean.class);
        Ignite ignite = appContext.getBean(IgniteSpringBean.class);

        ignite.services().deployClusterSingleton(ProducerService.NAME, new KafkaProducerService());
    }
}
