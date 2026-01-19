CREATE TABLE product_attribute_values (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  product_id BIGINT NOT NULL,
  attribute_id BIGINT NOT NULL,
  value_text VARCHAR(500) NULL,
  value_number DECIMAL(15,3) NULL,
  value_boolean TINYINT(1) NULL,
  CONSTRAINT fk_pav_product
    FOREIGN KEY (product_id) REFERENCES products(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_pav_attribute
    FOREIGN KEY (attribute_id) REFERENCES attributes(id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  INDEX idx_pav_product_id (product_id),
  INDEX idx_pav_attribute_id (attribute_id)
) ENGINE=InnoDB;
