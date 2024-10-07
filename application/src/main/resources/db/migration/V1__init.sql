CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    tienda_nube_id BIGINT NOT NULL,
    number INT NOT NULL,
    order_status VARCHAR(255) NOT NULL,
    ship_status VARCHAR(255),
    customer VARCHAR(255),
    CONSTRAINT UK_orders_number_tienda_nube_id UNIQUE (number, tienda_nube_id)
);

CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    tienda_nube_id BIGINT NOT NULL,
    image_path VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    sku VARCHAR(255) NOT NULL,
    ready BOOLEAN DEFAULT false,
    order_id BIGINT NOT NULL,
    order_number INT NOT NULL,
    image_url VARCHAR(255),
    barcode VARCHAR(255),
    check_status INT DEFAULT 0,
    quantity INT NOT NULL,
    CONSTRAINT FK_products_order_id FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT UK_products_tienda_nube_id_order_id_sku_barcode UNIQUE (tienda_nube_id, order_id, sku, barcode)
);
