package id.aseprojali.social.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by avew on 9/11/17.
 */
@Data
@Builder
public class Friend implements Serializable {

    @DBRef
    private Account account;

    private boolean subscribe;

    private boolean update;

}
