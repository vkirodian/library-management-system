drop database if exists lms;
create database lms default character set utf8 default collate utf8_bin;
use lms;

create table USER (
	USERID varchar(36) primary key,
    LOGINID varchar(10) not null unique,
    PASSWORD varchar(25) not null,
    PASSWORDEXPIRYTMS bigint not null,
    EMAILID varchar(255),
    USERROLE int(2) not null
);

insert into USER (USERID, LOGINID, PASSWORD, PASSWORDEXPIRYTMS, EMAILID, USERROLE) 
VALUES 
('10000000-0000-0000-0000-000000000001', 'admin', 'admin', 0, 'admin@lms.com', 0),
('10000000-0000-0000-0000-000000000002', 'librarian', 'librarian', 0, 'librarian@lms.com', 1);

create table SESSION (
	SESSIONID varchar(36) primary key,
	USERID varchar(36),
	LASTACCESSTIME bigint not null,
	LOGGEDINIPADDRESS varchar(128) not null,
	LOGGEDINTIME bigint not null,
	FOREIGN KEY (USERID) REFERENCES USER(USERID)
);

create table BOOK (
	BOOKID varchar(36) primary key,
    TITLE varchar(255) not null,
    AUTHOR varchar(255) not null,
    DESCRIPTION varchar(1024),
    EDITION varchar(50),
    CATEGORY varchar(50),
    LANGUAGE varchar(50)
);