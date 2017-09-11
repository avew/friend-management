package id.aseprojali.social.rest.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by avew on 9/11/17.
 */
@Data
@Builder
public class MessageResponse {

    private String status;
    private String message;
}
