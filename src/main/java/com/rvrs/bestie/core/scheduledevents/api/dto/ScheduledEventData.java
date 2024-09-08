package com.rvrs.bestie.core.scheduledevents.api.dto;

import com.rvrs.bestie.core.scheduledevents.domain.Location;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventMetadata;
import com.rvrs.bestie.core.scheduledevents.domain.ScheduledEventType;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public record ScheduledEventData(String title,
                                 String description,
                                 Location location,
                                 LocalDateTime scheduledDateTime,
                                 ScheduledEventType scheduledEventType,
                                 ScheduledEventMetadata scheduledEventMetadata) {
}
