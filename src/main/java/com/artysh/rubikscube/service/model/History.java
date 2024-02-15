package com.artysh.rubikscube.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity(name = "history")
@Table(name = "history")
@Data
public class History {

    @Id
    private UUID id;

    @Column(name = "version")
    private Integer version;

    @Column(name = "x")
    private Integer x;

    @Column(name = "y")
    private Integer y;

    @Column(name = "z")
    private Integer z;

    @Column(name ="rotation")
    private String rotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

}
