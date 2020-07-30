package com.rmamedov.deasy.etlstarter.config.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.rmamedov.deasy.etlstarter.serializer.CheckResultSerializer;
import com.rmamedov.deasy.model.kafka.OrderMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastInstanceConfig {

    @Bean
    public IMap<String, OrderMessage> addressCheckResultMap(final HazelcastInstance hazelcastInstance,
                                                            final HazelcastProperties hazelcastProperties) {

        return hazelcastInstance.getMap(hazelcastProperties.getMapName());
    }

    @Bean
    public HazelcastInstance hazelcastInstance(final HazelcastProperties hazelcastProperties,
                                               final CheckResultSerializer checkResultSerializer) {

        final MapConfig mapConfig = new MapConfig(hazelcastProperties.getEtlCacheName());
        mapConfig.setTimeToLiveSeconds(hazelcastProperties.getTimeToLive());
        mapConfig.setMaxIdleSeconds(hazelcastProperties.getMaxIdleSeconds());
        final Config config = new Config(hazelcastProperties.getEtlCacheName());
        config.addMapConfig(mapConfig);
        config.getSerializationConfig().addSerializerConfig(serializerConfig(checkResultSerializer));
        return Hazelcast.newHazelcastInstance(config);
    }

    private SerializerConfig serializerConfig(final CheckResultSerializer checkResultSerializer) {
        return new SerializerConfig()
                .setTypeClass(OrderMessage.class)
                .setImplementation(checkResultSerializer);
    }
}
