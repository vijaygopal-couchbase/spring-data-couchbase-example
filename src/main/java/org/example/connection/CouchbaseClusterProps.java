package org.example.connection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Configuration
@ConfigurationProperties(prefix = "couchbase")
public class CouchbaseClusterProps {
    private String connectionString;
    private String userName;
    private String password;
    private String scopeName;
    @NestedConfigurationProperty
    private CouchbaseBucketProps bucketTravelSample;
}
