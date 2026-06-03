CREATE TABLE users (
                       id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                       email       VARCHAR(255) NOT NULL UNIQUE,
                       password    VARCHAR(255),                    -- nullable for OAuth users
                       full_name   VARCHAR(100) NOT NULL,
                       avatar_url  VARCHAR(500),
                       provider    ENUM('LOCAL','GOOGLE') DEFAULT 'LOCAL',
                       provider_id VARCHAR(255),                    -- Google sub ID
                       is_verified BOOLEAN DEFAULT FALSE,
                       is_active   BOOLEAN DEFAULT TRUE,
                       created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       INDEX idx_email (email),
                       INDEX idx_provider_id (provider_id)
);