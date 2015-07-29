package me.whiteship.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Baik KeeSun
 */
public class AccountDto {

    @Getter
    @Setter
    public static class CreateRequest {
        @NotEmpty @Size(min = 5)
        private String username;
        @NotEmpty @Size(min = 5)
        private String password;
    }

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private String username;
        private Date joined;
    }

}
