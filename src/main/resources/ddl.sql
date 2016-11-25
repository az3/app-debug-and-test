CREATE TABLE demo
(
  mynum       bigint        NOT NULL,
  othernum    bigint        NOT NULL,
  myjson      varchar(5000) NOT NULL
);
PARTITION TABLE demo ON COLUMN mynum;

-- stored procedures
CREATE PROCEDURE PARTITION ON TABLE demo COLUMN mynum FROM CLASS debugandtest.ProcA;
CREATE PROCEDURE PARTITION ON TABLE demo COLUMN mynum FROM CLASS debugandtest.ProcB;
CREATE PROCEDURE FROM CLASS debugandtest.Unpack;
CREATE PROCEDURE FROM CLASS debugandtest.BuggyProc;


/*
CREATE TABLE library (
  id              INTEGER NOT NULL,
  val             VARCHAR(15),
  last_updated    TIMESTAMP,
  PRIMARY KEY (uid)
);
PARTITION TABLE library ON COLUMN id;
CREATE PROCEDURE PARTITION ON TABLE library COLUMN id FROM CLASS com.cagricelebi.voltdb.sample.procedure.EventOne;

*/