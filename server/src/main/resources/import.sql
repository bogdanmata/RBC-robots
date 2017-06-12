INSERT INTO public.t_user(username, email_address, first_name, last_name, password)
VALUES 
  ('admin', 'bogdanmata@gmail.com', 'Bogdan', 'Mata', md5('admin')), 
  ('user', 'bogdanmata@gmail.com', 'Bogdan', 'Mata', md5('user'));
  
INSERT INTO public.t_user_role(username, role)
VALUES 
  ('admin', 'ROBOT_READ'), 
  ('admin', 'ROBOT_EDIT'),
  ('admin', 'ROBOT_DELETE'),
  ('user', 'ROBOT_READ');

commit;