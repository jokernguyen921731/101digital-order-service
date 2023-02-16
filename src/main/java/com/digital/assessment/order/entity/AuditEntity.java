package com.digital.assessment.order.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class AuditEntity {
    @CreatedDate
    @Column(
            name = "created_at",
            updatable = false
    )
    private Instant createdAt;
    @LastModifiedDate
    @Column(
            name = "updated_at"
    )
    private Instant updatedAt;
    @CreatedBy
    @Column(
            name = "created_by",
            updatable = false,
            length = 50
    )
    private String createdBy;
    @LastModifiedBy
    @Column(
            name = "updated_by",
            length = 50
    )
    private String updatedBy;

    public AuditEntity() {
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setCreatedAt(final Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(final Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(final String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AuditEntity)) {
            return false;
        } else {
            AuditEntity other = (AuditEntity)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$createdAt = this.getCreatedAt();
                    Object other$createdAt = other.getCreatedAt();
                    if (this$createdAt == null) {
                        if (other$createdAt == null) {
                            break label59;
                        }
                    } else if (this$createdAt.equals(other$createdAt)) {
                        break label59;
                    }

                    return false;
                }

                Object this$updatedAt = this.getUpdatedAt();
                Object other$updatedAt = other.getUpdatedAt();
                if (this$updatedAt == null) {
                    if (other$updatedAt != null) {
                        return false;
                    }
                } else if (!this$updatedAt.equals(other$updatedAt)) {
                    return false;
                }

                Object this$createdBy = this.getCreatedBy();
                Object other$createdBy = other.getCreatedBy();
                if (this$createdBy == null) {
                    if (other$createdBy != null) {
                        return false;
                    }
                } else if (!this$createdBy.equals(other$createdBy)) {
                    return false;
                }

                Object this$updatedBy = this.getUpdatedBy();
                Object other$updatedBy = other.getUpdatedBy();
                if (this$updatedBy == null) {
                    if (other$updatedBy != null) {
                        return false;
                    }
                } else if (!this$updatedBy.equals(other$updatedBy)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AuditEntity;
    }

    public int hashCode() {
        int result = 1;
        Object $createdAt = this.getCreatedAt();
        result = result * 59 + ($createdAt == null ? 43 : $createdAt.hashCode());
        Object $updatedAt = this.getUpdatedAt();
        result = result * 59 + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        Object $createdBy = this.getCreatedBy();
        result = result * 59 + ($createdBy == null ? 43 : $createdBy.hashCode());
        Object $updatedBy = this.getUpdatedBy();
        result = result * 59 + ($updatedBy == null ? 43 : $updatedBy.hashCode());
        return result;
    }

    public String toString() {
        Instant var10000 = this.getCreatedAt();
        return "AuditEntity(createdAt=" + var10000 + ", updatedAt=" + this.getUpdatedAt() + ", createdBy=" + this.getCreatedBy() + ", updatedBy=" + this.getUpdatedBy() + ")";
    }
}
