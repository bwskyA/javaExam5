CREATE TABLE doctor (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255) NOT NULL,
                        lastname VARCHAR(255) NOT NULL,
                        speciality VARCHAR(255) NOT NULL,
                        nip VARCHAR(10) UNIQUE NOT NULL,
                        deleted BOOLEAN DEFAULT FALSE
);
