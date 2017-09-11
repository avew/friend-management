package id.aseprojali.social.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by avew on 9/11/17.
 */
@Document(collection = "t_account")
@Data
@Builder
public class Account extends AbstractAuditingEntity implements Serializable {

    @Id
    private String id;

    private String email;

}
