package com.aurawave.domain.model;

import lombok.*;

/**
 * Classe representando o relacionamento entre Evento e Item.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventItem {
    private Long itemId;
    private Long eventId;
}
