package org.loadtest.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@EqualsAndHashCode
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Beer {

        private String name;
        private String abv;
        private String ibv;
        private String srm;
        private String upc;

        private String type;

        private String brewery_id;

        private String updated;

        private String description;
        private String style;

        private String category;

}
