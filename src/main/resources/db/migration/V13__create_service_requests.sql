CREATE TABLE service_requests (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  service_id BIGINT NOT NULL,
  status VARCHAR(20) NOT NULL,
  customer_phone VARCHAR(20) NOT NULL,
  description VARCHAR(5000) NOT NULL,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_service_requests_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_service_requests_service
    FOREIGN KEY (service_id) REFERENCES services(id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  INDEX idx_service_requests_user_id (user_id),
  INDEX idx_service_requests_service_id (service_id)
) ENGINE=InnoDB;
