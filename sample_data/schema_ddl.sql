DROP TABLE IF EXISTS public.consumable CASCADE;
DROP TABLE IF EXISTS public.shield CASCADE;
DROP TABLE IF EXISTS public.weapon CASCADE;
DROP TABLE IF EXISTS public.game_state CASCADE;
DROP TABLE IF EXISTS public.player CASCADE;
DROP TABLE IF EXISTS public.inventory CASCADE;
DROP TABLE IF EXISTS public.door CASCADE;
DROP TABLE IF EXISTS public.maps CASCADE;

CREATE TABLE public.game_state (
    id serial NOT NULL PRIMARY KEY,
    game_state_name varchar NOT NULL,
    current_map_number integer NOT NULL,
    saved_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    player_id integer NOT NULL -- FK +
);

CREATE TABLE public.player (
   id serial NOT NULL PRIMARY KEY,
   player_name text NOT NULL,
   hp integer NOT NULL,
   x integer NOT NULL,
   y integer NOT NULL,
   inventory_id integer NOT NULL --FK
);

CREATE TABLE public.inventory (
   id serial NOT NULL PRIMARY KEY,
   key boolean NOT NULL,
   health_potion integer,
   freeze_potion integer,
   weapon_id integer, -- FK
   shield_id integer -- FK
);

CREATE TABLE public.maps (
    id serial NOT NULL PRIMARY KEY,
    map_number integer,
    game_state_id integer, -- FK
    map text
);

CREATE TABLE public.consumable (
     id serial NOT NULL PRIMARY KEY,
     x integer,
     y integer,
     type varchar not null,
     map_id integer -- FK
);

CREATE TABLE public.weapon (
    id serial NOT NULL PRIMARY KEY,
    x integer,
    y integer,
    damage integer NOT NULL,
    crit integer,
    name VARCHAR NOT NULL,
    map_id integer -- FK
);

CREATE TABLE public.shield (
   id serial NOT NULL PRIMARY KEY,
   x integer,
   y integer,
   defense integer NOT NULL,
   name VARCHAR NOT NULL,
   map_id integer -- FK
);

CREATE TABLE public.door (
    id serial NOT NULL PRIMARY KEY,
    x integer,
    y integer,
    is_open boolean NOT NULL,
    map_id integer -- FK
);

ALTER TABLE ONLY public.game_state
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id);

ALTER TABLE ONLY public.player
    ADD CONSTRAINT fk_inventory_id FOREIGN KEY (inventory_id) REFERENCES public.inventory(id);

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT fk_weapon_id FOREIGN KEY (weapon_id) REFERENCES public.weapon(id),
    ADD CONSTRAINT fk_shield_id FOREIGN KEY (shield_id) REFERENCES public.shield(id);
    -- ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id);

ALTER TABLE ONLY public.maps
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES public.game_state(id);

ALTER TABLE ONLY public.consumable
    ADD CONSTRAINT fk_map_id FOREIGN KEY (map_id) REFERENCES public.maps(id);

ALTER TABLE ONLY public.weapon
    ADD CONSTRAINT fk_map_id FOREIGN KEY (map_id) REFERENCES public.maps(id);

ALTER TABLE ONLY public.shield
    ADD CONSTRAINT fk_map_id FOREIGN KEY (map_id) REFERENCES public.maps(id);

ALTER TABLE ONLY public.door
    ADD CONSTRAINT fk_map_id FOREIGN KEY (map_id) REFERENCES public.maps(id);