-- An SQL file that will eb read by Spring when launching the application.
-- User schema official link: https://docs.spring.io/spring-security/site/docs/3.0.x/reference/appendix-schema.html
-- Copy the JDBC SQL code here
-- But I don't want the default schema
create table users(
  username varchar_ignorecase(50) not null primary key,
  password varchar_ignorecase(50) not null,
  enabled boolean not null);

create table authorities (
  username varchar_ignorecase(50) not null,
  authority varchar_ignorecase(50) not null,
  constraint fk_authorities_users foreign key(username) references users(username));
  create unique index ix_auth_username on authorities (username,authority);