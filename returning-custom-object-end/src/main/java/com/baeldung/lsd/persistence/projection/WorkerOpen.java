package com.baeldung.lsd.persistence.projection;

import org.springframework.beans.factory.annotation.Value;

public interface WorkerOpen {
    Long getId();

    @Value("#{target.firstName} #{target.lastName}")
    String getName();

}
