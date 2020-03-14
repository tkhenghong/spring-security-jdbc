-- Another SQL file that will eb read by Spring when launching the application.
INSERT INTO users(username, password, enabled)values('user', 'pass', true);
INSERT INTO users(username, password, enabled)values('admin', 'pass', true);

INSERT INTO authorities(username, authority)values ('user', 'ROLE_USER');
INSERT INTO authorities(username, authority)values ('admin', 'ROLE_ADMIN');