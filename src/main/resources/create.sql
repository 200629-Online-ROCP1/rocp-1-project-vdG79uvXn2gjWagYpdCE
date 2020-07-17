drop table if exists role cascade;
create table role
(
	role_id serial primary key,
	role text unique not null
);
drop table if exists accounttype cascade;
create table accounttype
(
	accounttype_id serial primary key,
	type text unique not null
);
drop table if exists accountstatus cascade;
create table accountstatus 
(
	accountstatus_id serial primary key,
	status text unique not null
);
drop table if exists accountholder cascade;
create table accountholder
(
	accountholder_id serial primary key,
	username text unique not null,
	password text not null,
	firstname text not null,
	lastname text not null,
	email text unique not null,
	role integer references role(role_id) on delete restrict
);
drop table if exists account cascade;
create table account 
(
	account_id serial primary key,
	balance numeric(12,2) not null default 0.0,
	deleted boolean default false,
	uuid text,
	accountstatus integer references accountstatus(accountstatus_id) on delete restrict,
	accounttype integer references accounttype(accounttype_id) on delete restrict,
	accountholder integer references accountholder(accountholder_id) on delete restrict
);

