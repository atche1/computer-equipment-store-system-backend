CREATE TABLE product_images (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  product_id BIGINT NOT NULL,
  image_url VARCHAR(1000) NOT NULL,
  is_main TINYINT(1) NOT NULL DEFAULT 0,
  CONSTRAINT fk_product_images_product
    FOREIGN KEY (product_id) REFERENCES products(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  INDEX idx_product_images_product_id (product_id)
) ENGINE=InnoDB;
