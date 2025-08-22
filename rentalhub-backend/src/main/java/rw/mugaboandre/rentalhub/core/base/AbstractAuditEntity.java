package rw.mugaboandre.rentalhub.core.base;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import rw.mugaboandre.rentalhub.core.util.utilClass.RequestContext;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
public abstract class AbstractAuditEntity {

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_from_ip", updatable = false)
    private String createdFromIp;

    @Column(name = "modified_from_ip")
    private String modifiedFromIp;

    @PrePersist
    public void prePersist() {
        String ip = getCurrentRequestIp();
        createdFromIp = ip;
        modifiedFromIp = ip;
    }

    @PreUpdate
    public void preUpdate() {
        modifiedFromIp = getCurrentRequestIp();
    }

    /**
     * Fetch the current request IP address.
     * Falls back to ThreadLocal if using JWT filters with RequestContext.
     */
    private String getCurrentRequestIp() {
        // First, try ThreadLocal (from JwtAuthenticationFilter)
        String ip = RequestContext.getCurrentIp();
        if (ip != null) {
            return ip;
        }

        // Fallback to RequestContextHolder
        var requestAttributes = org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof org.springframework.web.context.request.ServletRequestAttributes servletRequestAttributes) {
            var request = servletRequestAttributes.getRequest();
            ip = request.getHeader("X-FORWARDED-FOR");
            if (ip == null || ip.isEmpty()) {
                ip = request.getRemoteAddr();
            }
            return ip;
        }

        return "UNKNOWN";
    }
}
