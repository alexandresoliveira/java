create table movements (
	id uuid primary key,
	game_id uuid not null,
	player varchar(1) not null,
	x integer not null,
	y integer not null,
	created_at timestamp with time zone not null, 
  	updated_at timestamp with time zone not null,
  	constraint fk_games foreign key (game_id) references games(id)
);

create index idx_movements on movements(id);