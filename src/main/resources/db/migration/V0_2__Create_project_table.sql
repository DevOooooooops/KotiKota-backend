DO
$$
    BEGIN
        IF NOT EXISTS(SELECT FROM pg_type WHERE typname = 'project_status') THEN
            CREATE TYPE project_status AS ENUM ('OPEN', 'CLOSE');
        END IF;
        IF NOT EXISTS(SELECT FROM pg_type WHERE typname = 'project_health') THEN
            CREATE TYPE project_health AS ENUM ('SUCCESS', 'FAILED', 'IN_PROGRESS');
        END IF;
    END
$$;

create table if not exists "project" (
    id varchar constraint project_pk primary key default uuid_generate_v4(),
    name varchar not null unique,
    description varchar,
    target_amount integer not null default 0,
    deadline date,
    owner_id varchar not null references "user"(id),
    status project_status not null default 'OPEN',
    health project_health not null default 'IN_PROGRESS'
)