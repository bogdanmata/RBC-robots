INSERT INTO t_user(username, email_address, first_name, last_name, password)
VALUES 
  ('admin', 'bogdanmata@gmail.com', 'Bogdan', 'Mata', 'admin'), 
  ('user', 'bogdanmata@gmail.com', 'Bogdan', 'Mata', 'user');
  
INSERT INTO t_user_role(username, role)
VALUES 
  ('admin', 'ROBOT_READ'), 
  ('admin', 'ROBOT_EDIT'),
  ('admin', 'ROBOT_DELETE'),
  ('user', 'ROBOT_READ');
  
INSERT INTO t_robot(id, name, price, description, created_by, created)
VALUES 
  (nextval('seq_robot'), 'The Wise Runner', 12.30, 'This is The Wise Runner model A', 'admin', current_timestamp),
  (nextval('seq_robot'), 'The Beautiful Gerbil', 10.00, 'This is The Beautiful Gerbil model A', 'admin', current_timestamp),
  (nextval('seq_robot'), 'The Royal Nightingale', 123.50, 'This is The Royal Nightingale model A', 'admin', current_timestamp),
  (nextval('seq_robot'), 'The Hairy Hare', 56.50, 'This is The Hairy Hare model A', 'admin', current_timestamp),
  (nextval('seq_robot'), 'The Swift Dino', 100.00, 'This is The Swift Dino model A', 'admin', current_timestamp),
  (nextval('seq_robot'), 'The Lovable Raccoon', 15.00, 'This is The Lovable Raccoon model A', 'admin', current_timestamp),
  (nextval('seq_robot'), 'Titan', 86.00, 'This is Titan model A', 'admin', current_timestamp),
  (nextval('seq_robot'), 'Big Blue', 400.00, 'This is Big Blue model A', 'admin', current_timestamp),
  (nextval('seq_robot'), 'Dino', 50.50, 'This is Dino model A', 'admin', current_timestamp),
  (nextval('seq_robot'), 'Beaker', 200.00, 'This is Beaker model A', 'admin', current_timestamp);
commit;