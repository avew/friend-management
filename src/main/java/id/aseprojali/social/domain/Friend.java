package id.aseprojali.social.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * Created by avew on 9/11/17.
 */
@Document(collection = "friends")
@Data
@Builder
public class Friend implements Serializable {

    @Id
    private String email;

    @Field(value = "connections")
    private List<String> connections;

    @Field(value = "subscibers")
    private List<String> subscibers;

    @Field(value = "blockeds")
    private List<String> blockeds;


}
