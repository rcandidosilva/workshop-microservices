insert into role (id, name) values (1, 'USER');
insert into role (id, name) values (2, 'MANAGER');
insert into role (id, name) values (3, 'ADMIN');

insert into user (id, username, password, enabled, expired, locked) values (1, 'barry', 't0ps3cr3t', true, false, false);
insert into user (id, username, password, enabled, expired, locked) values (2, 'larry', 't0ps3cr3t', true, false, false);
insert into user (id, username, password, enabled, expired, locked) values (3, 'root', 't0ps3cr3t', true, false, false);

insert into user_roles (user_id, roles_id) values (1, 1);
insert into user_roles (user_id, roles_id) values (2, 1);
insert into user_roles (user_id, roles_id) values (2, 2);
insert into user_roles (user_id, roles_id) values (3, 1);
insert into user_roles (user_id, roles_id) values (3, 2);
insert into user_roles (user_id, roles_id) values (3, 3);

insert into auth_client_details (id, client_id, client_secret, grant_types, scopes, secret_required, refresh_token_validity, access_token_validity) values (1, 'client-password', 'secret', 'password', 'oauth2', true, 86400, 86400);
insert into auth_client_details (id, client_id, client_secret, grant_types, scopes, secret_required, refresh_token_validity, access_token_validity) values (2, 'client_credentials', 'secret', 'client_credentials', 'oauth2', true, 86400, 86400);
insert into auth_client_details (id, client_id, client_secret, grant_types, scopes, secret_required, refresh_token_validity, access_token_validity) values (3, 'client-auth-code', 'secret', 'authorization_code', 'oauth2', true, 86400, 86400);
insert into auth_client_details (id, client_id, client_secret, grant_types, scopes, secret_required, refresh_token_validity, access_token_validity) values (4, 'client-implicit', 'secret', 'implicit', 'oauth2', true, 86400, 86400); 