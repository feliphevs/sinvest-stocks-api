DROP TABLE IF EXISTS STOCKS;
create table stocks(
    id int primary key,
    market_cap int,
    stock_symbol varchar(255),
    stock_name varchar(255),
    ask_min numeric,
    ask_max numeric,
    bid_min numeric,
    bid_max numeric,
    created_on timestamp not null default current_timestamp,
    updated_on timestamp not null default current_timestamp
);

insert into stocks(id, market_cap, stock_symbol, stock_name, ask_min, ask_max, bid_min, bid_max) values (1, 12345, 'BEEF', 'MINERVA', 0, 0, 0, 0);
insert into stocks(id, market_cap, stock_symbol, stock_name, ask_min, ask_max, bid_min, bid_max) values (2, 13579, 'EMBR', 'EMBRAER', 0, 0, 0, 0);
insert into stocks(id, market_cap, stock_symbol, stock_name, ask_min, ask_max, bid_min, bid_max) values (3, 54321, 'DESK', 'DESKTOP', 0, 0, 0, 0);