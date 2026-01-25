CREATE TABLE category_attributes (
  category_id BIGINT NOT NULL,
  attribute_id BIGINT NOT NULL,
  PRIMARY KEY (category_id, attribute_id),
  CONSTRAINT fk_category_attributes_category
    FOREIGN KEY (category_id) REFERENCES categories(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_category_attributes_attribute
    FOREIGN KEY (attribute_id) REFERENCES attributes(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  INDEX idx_category_attributes_attribute_id (attribute_id)
) ENGINE=InnoDB;
