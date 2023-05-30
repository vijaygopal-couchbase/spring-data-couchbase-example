package org.example.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Brewery {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_rev")
    private String rev;

    @JsonProperty("name")
    private String name;

    @JsonProperty("abv")
    private int abv;

    @JsonProperty("ibu")
    private int ibu;

    @JsonProperty("srm")
    private int srm;

    @JsonProperty("upc")
    private int upc;

    @JsonProperty("type")
    private String type;

    @JsonProperty("updated")
    private String updated;

    @JsonProperty("brewery_id")
    private String brewery_id;

    @JsonProperty("description")
    private String description;

    @JsonProperty("style")
    private String style;

    @JsonProperty("category")
    private String category;
}
