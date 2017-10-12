# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table addfields (
  ida                       bigint auto_increment not null,
  first_name                varchar(255),
  second_name               varchar(255),
  address                   varchar(255),
  email                     varchar(255),
  id                        varchar(255),
  block_number              varchar(255),
  land_size                 varchar(255),
  cultivation_size          varchar(255),
  password                  varchar(255),
  status                    varchar(255),
  constraint pk_addfields primary key (ida))
;

create table fertilizer (
  auto_no                   bigint auto_increment not null,
  id_number                 varchar(255),
  user_name                 varchar(255),
  phone_number              varchar(255),
  brand_name                varchar(255),
  quantity                  varchar(255),
  description               varchar(255),
  status                    varchar(255),
  land_size                 varchar(255),
  cultivation               varchar(255),
  payment                   varchar(255),
  wanna                     varchar(255),
  constraint pk_fertilizer primary key (auto_no))
;

create table inquiry (
  num                       bigint auto_increment not null,
  name                      varchar(255),
  id_number                 varchar(255),
  phone_number              varchar(255),
  date                      varchar(255),
  message                   varchar(255),
  status                    varchar(255),
  constraint pk_inquiry primary key (num))
;

create table mfarm_details (
  num                       bigint auto_increment not null,
  full_name                 varchar(255),
  idnumber                  varchar(255),
  address                   varchar(255),
  email                     varchar(255),
  town                      varchar(255),
  location                  varchar(255),
  land_size                 varchar(255),
  cultivation_size          varchar(255),
  constraint pk_mfarm_details primary key (num))
;

create table news_post (
  num                       bigint auto_increment not null,
  event                     varchar(255),
  news                      varchar(255),
  date                      varchar(255),
  constraint pk_news_post primary key (num))
;

create table register_admin (
  num                       bigint auto_increment not null,
  full_name                 varchar(255),
  email                     varchar(255),
  gender                    varchar(255),
  username                  varchar(255),
  password                  varchar(255),
  constraint pk_register_admin primary key (num))
;

create table seeds (
  auto                      bigint auto_increment not null,
  id_number                 varchar(255),
  user_name                 varchar(255),
  phone_number              varchar(255),
  seed_brand                varchar(255),
  quantity                  varchar(255),
  description               varchar(255),
  land_size                 varchar(255),
  cultivation               varchar(255),
  status                    varchar(255),
  payment                   varchar(255),
  constraint pk_seeds primary key (auto))
;

create table seeds_stock (
  num                       bigint auto_increment not null,
  brand                     varchar(255),
  quantity                  varchar(255),
  totalp                    varchar(255),
  price                     varchar(255),
  constraint pk_seeds_stock primary key (num))
;

create table stock_details (
  num                       bigint auto_increment not null,
  brand                     varchar(255),
  quantity                  varchar(255),
  totalp                    varchar(255),
  price                     varchar(255),
  constraint pk_stock_details primary key (num))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table addfields;

drop table fertilizer;

drop table inquiry;

drop table mfarm_details;

drop table news_post;

drop table register_admin;

drop table seeds;

drop table seeds_stock;

drop table stock_details;

SET FOREIGN_KEY_CHECKS=1;

