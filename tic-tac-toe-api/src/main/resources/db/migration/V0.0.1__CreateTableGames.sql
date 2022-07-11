CREATE TABLE games (
	id uuid primary key,
	actual_player varchar(1) not null,
	status varchar(10) not null,
	winner varchar(1),
	created_at timestamp with time zone not null, 
  	updated_at timestamp with time zone not null
);

create index idx_games on games(id);