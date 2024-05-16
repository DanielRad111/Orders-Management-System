create table Bill
(
    id          int          not null
        primary key,
    clientName  varchar(255) null,
    productName varchar(255) null,
    quantity    int          null,
    date        date         null,
    constraint id
        unique (id)
);

create table client
(
    id    int auto_increment
        primary key,
    name  varchar(255) null,
    email varchar(255) null
);

create table `order`
(
    id        int auto_increment
        primary key,
    clientId  int null,
    productId int null,
    quantity  int null,
    constraint order_client_id_fk
        foreign key (clientId) references assignment3.client (id),
    constraint order_product_id_fk
        foreign key (productId) references assignment3.product (id)
);

create table product
(
    id       int          not null
        primary key,
    name     varchar(255) null,
    quantity int          null
);


