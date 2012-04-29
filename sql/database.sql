create database testbank;

create table stock_information(

id serial primary key,
information_date date not null,
ticker char(5) not null,
name varchar(100) not null,
isin char(12) not null,
currency char(3) not null,
marketplace char(3) not null,
listname varchar(100) not null,
average_price numeric(6,3) not null,
open_price numeric(6,3) not null,
high_price numeric(6,3) not null,
low_price numeric(6,3) not null,
last_close_price numeric(6,3) not null,
last_price numeric(6,3) not null,
price_change_percentage numeric(6,3) not null,
best_bid numeric(6,3) not null,
best_ask numeric(6,3) not null,
trades integer not null,
volume integer not null,
turnover numeric(12,2) not null
);

CREATE INDEX "pkIndex" ON stock_information USING btree(id);

CREATE INDEX "dateIndex" ON stock_information USING btree(information_date);