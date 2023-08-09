create table if not exists "transcript_version"
(
    id                      varchar
    constraint transcript_version_pk primary key default uuid_generate_v4(),
    transcript_id           varchar not null
    constraint transcript_version_transcript_id_fk references "transcript"(id),
    ref                     integer,
    created_by_user_id      varchar not null
    constraint transcript_version_user_id_fk references "user"(id),
    created_by_user_role    varchar not null,
    creation_datetime       timestamp with time zone not null default now()
);


