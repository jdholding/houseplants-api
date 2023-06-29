CREATE EXTENSION IF NOT EXISTS unaccent;
CREATE EXTENSION IF NOT EXISTS ltree;
CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE SCHEMA IF NOT EXISTS houseplants;

CREATE TABLE houseplants.plants (
	id serial4 NOT NULL,
	trefle_id int4 NULL,
	date_created timestamptz NOT NULL DEFAULT now(),
	species varchar(255) NULL,
	genus varchar(255) NULL,
	family varchar(255) NULL,
	parent_id int4 NULL,
	common_name varchar(255) NULL,
	user_id int4 NULL,
	CONSTRAINT plants_pkey PRIMARY KEY (id)
);

CREATE TABLE houseplants.photos (
	id serial4 NOT NULL,
	plant_id int4 NOT NULL,
	photo_uri varchar(255) NOT NULL,
	date_created timestamptz NOT NULL DEFAULT now(),
	thumbnail_uri varchar(255) NOT NULL,
	action_id int4 NULL,
	CONSTRAINT photos_pkey PRIMARY KEY (plant_id)
);

CREATE TABLE houseplants.comments (
	id serial4 NOT NULL,
	plant_id int4 NOT NULL,
	user_id int4 NOT NULL,
	date_created timestamptz NOT NULL DEFAULT now(),
	text varchar(4000) NULL,
	is_hidden bool NOT NULL DEFAULT false,
	CONSTRAINT comments_pkey PRIMARY KEY (id)
);

CREATE TABLE houseplants.actions (
	id serial4 NOT NULL,
	plant_id int4 NOT NULL,
	user_id int4 NOT NULL,
	type_id int4 NOT NULL,
	date_created timestamptz NOT NULL DEFAULT now(),
	CONSTRAINT actions_pkey PRIMARY KEY (id)
);

CREATE TABLE houseplants.action_types (
	id serial4 NOT NULL,
	label varchar(255) NULL,
	CONSTRAINT action_types_pkey PRIMARY KEY (id)
);

CREATE TABLE houseplants.users (
	id serial4 NOT NULL,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	email varchar(255) NULL,
	username varchar(255) NOT NULL,
	CONSTRAINT unique_login UNIQUE (username),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);


