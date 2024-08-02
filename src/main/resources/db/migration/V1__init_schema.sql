CREATE TABLE IF NOT EXISTS t_users (
    id SERIAL PRIMARY KEY,
    ip_address VARCHAR(45),
    input_string TEXT,
    translated_string TEXT
);
