package org.example.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ChangeFeed {

    @JsonProperty("id")
    private String id;

    @JsonProperty("message")
    private String message;

    @JsonProperty("changes")
    private List<Revision> changes;

    @JsonProperty("seq")
    private String seq;

}
