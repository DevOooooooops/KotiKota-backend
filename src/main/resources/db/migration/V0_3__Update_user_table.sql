alter table "user" add column if not exists "balance" integer not null default 0 check ( balance >= 0 );