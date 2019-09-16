use logs;

create table event(
  transaction_id int NOT NULL,
  reporting_service VARCHAR(30), 
  category VARCHAR(30), 
  requestor VARCHAR(30),
  request VARCHAR(500),
  response VARCHAR(500),
  timestamp TIMESTAMP,
  PRIMARY KEY(transaction_id));
