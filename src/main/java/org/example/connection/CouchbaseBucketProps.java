package org.example.connection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CouchbaseBucketProps {

    private String name;
    private String userName;
    private String password;
}
