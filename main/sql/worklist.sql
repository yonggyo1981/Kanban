CREATE TABLE worklist (
	idx INT NOT NULL AUTO_INCREMENT,
    gid BIGINT NOT NULL,
    memNo INT,
    status ENUM('ready', 'progress', 'done') DEFAULT 'ready',
    subject VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    regDt DATETIME DEFAULT NOW(),
    modDt DATETIME,
    PRIMARY KEY(idx)
);