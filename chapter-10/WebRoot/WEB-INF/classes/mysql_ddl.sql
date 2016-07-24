create table game(
	game_id int not null auto_increment primary key comment '游戏id',
	open_id varchar(50) not null comment '用户的OpenID',
	game_answer varchar(4) not null comment '猜数字游戏的正确答案',
	create_time varchar(20) not null comment '游戏创建时间',
	game_status int not null comment '游戏状态（0:游戏中 1:胜利 2:失败）',
	finish_time varchar(20) comment '游戏完成时间'
) comment='猜数字游戏的每一局';

create table game_round(
	id int not null auto_increment primary key comment '主键id',
	game_id int not null comment '游戏id',
	open_id varchar(50) not null comment '用户的OpenID',
	guess_number varchar(4) not null comment '用户猜测的数字',
	guess_time varchar(20) not null comment '用户猜测的时间',
	guess_result varchar(4) not null comment '用户猜测的结果,即xAyB',
	FOREIGN KEY(game_id) REFERENCES game(game_id) on delete cascade on update cascade
) comment='猜数字游戏的每一回合';
