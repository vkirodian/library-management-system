drop database if exists lms;
create database lms default character set utf8 default collate utf8_bin;
use lms;

create table USER (
	USERID varchar(36) primary key,
    LOGINID varchar(10) not null unique,
    PASSWORD varchar(255) not null,
    EMAILID varchar(255)
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