CREATE TABLE refresh_tokens (
                                id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                token       VARCHAR(512) NOT NULL UNIQUE,
                                user_id     BIGINT UNSIGNED NOT NULL,
                                expiry_date TIMESTAMP NOT NULL,
                                revoked     BOOLEAN DEFAULT FALSE,
                                created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                INDEX idx_token (token),
                                INDEX idx_user_id (user_id)
);