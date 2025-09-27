CREATE TABLE manufacturers (
  id           RAW(16) DEFAULT SYS_GUID() NOT NULL,
  name         VARCHAR2(255),
  created_date TIMESTAMP(6),
  modify_date  TIMESTAMP(6),
  CONSTRAINT pk_manufacturers PRIMARY KEY (id)
);