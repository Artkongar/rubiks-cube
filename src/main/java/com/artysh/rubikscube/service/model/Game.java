package com.artysh.rubikscube.service.model;

import com.artysh.rubikscube.service.usertypes.JsonbTypes;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "game")
@Entity(name = "game")
@Data
public class Game {

    @Id
    private UUID id;

    @Column(name = "size")
    private Integer size;

    @Type(value = JsonbTypes.class)
    @Column(name = "red_side")
    private ArrayNode redSide;

    @Type(value = JsonbTypes.class)
    @Column(name = "white_side")
    private ArrayNode whiteSide;

    @Type(value = JsonbTypes.class)
    @Column(name = "green_side")
    private ArrayNode greenSide;

    @Type(value = JsonbTypes.class)
    @Column(name = "yellow_side")
    private ArrayNode yellowSide;

    @Type(value = JsonbTypes.class)
    @Column(name = "blue_side")
    private ArrayNode blueSide;

    @Type(value = JsonbTypes.class)
    @Column(name = "orange_side")
    private ArrayNode orangeSide;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<History> histories = new ArrayList<>();
}
