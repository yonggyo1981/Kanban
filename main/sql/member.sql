CREATE TABLE member (
	memNo INTEGER AUTO_INCREMENT,
    memId VARCHAR(30) NOT NULL,
    memPw VARCHAR(65) NOT NULL,
    memPwHint VARCHAR(50) NOT NULL,
    memNm VARCHAR(30) NOT NULL,
    cellPhone VARCHAR(11),
    regDt DATETIME DEFAULT NOW(),
    modDt DATETIME,
    UNIQUE(memId),
    PRIMARY KEY(memNo)
);

ALTER TABLE member
	ADD socialType ENUM('none', 'kakao', 'naver') DEFAULT 'none' AFTER cellPhone;
	
ALTER TABLE member
	ADD socialId VARCHAR(65) AFTER socialType;