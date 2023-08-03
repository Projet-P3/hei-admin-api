create table "transcript-version" if not exists (
    id      varchar     primary key    not null,
    transcript_id       varchar     references "transcript" (id),
    ref     integer     not null,
    create_by_user_id       integer     references "user" (id),
    create_by_user_role     varchar     not null,
    creation_datetime    timestamp with time zone not null
);