create table if not exists game (
    id UUID CONSTRAINT game_id_pk PRIMARY KEY,
    size integer not null,
    red_side jsonb not null,
    white_side jsonb not null,
    green_side jsonb not null,
    yellow_side jsonb not null,
    blue_side jsonb not null,
    orange_side jsonb not null
);

create table if not exists history (
    id UUID CONSTRAINT history_id_pk PRIMARY KEY,
    version integer,
    game_id UUID,
    x integer,
    y integer,
    z integer,
    rotation varchar(255),
    CONSTRAINT history_gane_id FOREIGN KEY(game_id) REFERENCES game(id)
);



