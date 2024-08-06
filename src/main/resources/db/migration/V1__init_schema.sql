CREATE TABLE IF NOT EXISTS t_users (
    id SERIAL PRIMARY KEY,
    ip_address VARCHAR(45) NOT NULL ,
    input_string TEXT NOT NULL ,
    translated_string TEXT NOT NULL
);