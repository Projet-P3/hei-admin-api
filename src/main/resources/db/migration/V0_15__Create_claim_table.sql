create table if not exists "claim"
(
    id                  varchar
    constraint claim_pk primary key default uuid_generate_v4(),
    transcript_id          varchar not null
    constraint transcript_id_fk references "transcript"(id),
    version_id          varchar not null
    constraint transcript_version_id_fk references "version"(id),
    status            varchar,
    creation_datetime   timestamp with time zone not null default now,
    closed_datetime   timestamp with time zone not null default now,
    reason varchar
                                      );