CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(255) UNIQUE NOT NULL,
                                     password VARCHAR(255) NOT NULL,
                                     role VARCHAR(50) NOT NULL,
                                     email VARCHAR(255) UNIQUE,
                                     first_name VARCHAR(255),
                                     last_name VARCHAR(255),
                                     date_of_birth DATE,
                                     gender VARCHAR(50),
                                     phone_number VARCHAR(50),
                                     address TEXT,
                                     insurance_provider VARCHAR(255),
                                     insurance_policy_number VARCHAR(255),
                                     reset_token VARCHAR(255),
                                     reset_token_expiry_date TIMESTAMP
);

