package gr.vacay.vacay.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt"},
        allowGetters = true
)
public abstract class AuditModel implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(
            name = "created_at",
            updatable = false,
            nullable = false
    )
    private Date createdAt;
}
