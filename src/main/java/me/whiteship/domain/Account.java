package me.whiteship.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Baik KeeSun
 */
@Entity
@Getter
@Setter
public class Account {

    @Id @GeneratedValue
    private Long id;

    private String username;

    private String password;

    @Lob
    private String introduction;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joined;

}
