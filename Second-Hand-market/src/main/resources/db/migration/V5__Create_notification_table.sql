create table notification
(
	id bigint auto_increment,
	notifier bigint not null,
	receiver bigint not null,
	outerId bigint not null,
	type int not null,
	gmt_create bigint not null,
	status int default 0,
	NOTIFIER_NAME VARCHAR(100),
	OUTER_TITLE VARCHAR(256),
	constraint notification_pk
		primary key (id)
);