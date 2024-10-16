package ci.org.recycle.models;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntityAudit {

    @CreatedBy
    @Column(updatable = false, nullable = false)
    protected UUID createdBy;
    @LastModifiedBy
    @Column(insertable = false)
    protected UUID modifiedBy;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    protected LocalDateTime createdAt;
    @LastModifiedDate
    @Column(insertable = false)
    protected LocalDateTime updatedAt;
}
