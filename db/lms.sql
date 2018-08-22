drop database if exists lms;
create database lms default character set utf8 default collate utf8_bin;
use lms;

create table user (
	USERID varchar(36) primary key,
    LOGINID varchar(10) not null unique,
    PASSWORD varchar(25) not null,
    PASSWORDEXPIRYTMS bigint not null,
    EMAILID varchar(255),
    USERROLE int(2) not null
);

insert into user (USERID, LOGINID, PASSWORD, PASSWORDEXPIRYTMS, EMAILID, USERROLE) 
VALUES 
('10000000-0000-0000-0000-000000000001', 'admin', 'admin', 999999999999999, 'admin@lms.com', 0),
('10000000-0000-0000-0000-000000000002', 'librarian', 'librarian', 999999999999999, 'librarian@lms.com', 1),
('10000000-0000-0000-0000-000000000003', 'user', 'user', 999999999999999, 'user@lms.com', 2);

create table access_role(
	ACCESSROLEID varchar(36) primary key,
    ACCESSTYPE int(2) not null,
    ROLE int(2) not null    
);

create table session (
	SESSIONID varchar(36) primary key,
	USERID varchar(36),
	LASTACCESSTIME bigint not null,
	LOGGEDINIPADDRESS varchar(128) not null,
	LOGGEDINTIME bigint not null,
	FOREIGN KEY (USERID) REFERENCES user(USERID)
);

create table book (
	BOOKID varchar(36) primary key,
    TITLE varchar(255) not null,
    AUTHOR varchar(255) not null,
    DESCRIPTION varchar(1024),
    EDITION varchar(50),
    CATEGORY varchar(50),
    LANGUAGE varchar(50)
);

create table inventory (
	BOOKID varchar(36) primary key,
    TOTAL int(2) not null default 0,
    ISSUED int(2) not null default 0,
    REQUESTED int(2) not null default 0,
    FOREIGN KEY (BOOKID) REFERENCES book(BOOKID)
);

create table issue (
	ISSUEID varchar(36) primary key,
    BOOKID varchar(36) not null,
    USERID varchar(36) not null,
    ISSUEDATE bigint not null,
    RETURNDATE bigint not null,
    NOOFREISSUES int(2) not null default 0,
    FINE int(4) default 0,
    STATUS int(2) not null,
    FOREIGN KEY (BOOKID) REFERENCES book(BOOKID),
    FOREIGN KEY (USERID) REFERENCES user(USERID)
);

create table request (
	REQUESTID varchar(36) primary key,
    BOOKID varchar(36) not null,
    USERID varchar(36) not null,
    REQUESTDATE bigint not null,
    FOREIGN KEY (BOOKID) REFERENCES book(BOOKID),
    FOREIGN KEY (USERID) REFERENCES user(USERID)
);


INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 0, 0);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 0, 1);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 0, 2);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 1, 0);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 2, 0);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 2, 1);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 2, 2);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 3, 0);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 3, 1);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 4, 0);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 5, 0);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 5, 1);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 5, 2);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 6, 1);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 7, 0);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 7, 1);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 7, 2);
INSERT INTO access_role (ACCESSROLEID, ACCESSTYPE, ROLE) VALUES (UUID(), 8, 1);
