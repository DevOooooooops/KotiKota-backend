create table if not exists "project_comment" (
    id varchar constraint comment_pk primary key default uuid_generate_v4(),
    project_id varchar references project("id") not null ,
    note int check ( note >= 0 and note <= 5 ),
    author_id varchar references "user"("id") not null,
    content varchar not null
);