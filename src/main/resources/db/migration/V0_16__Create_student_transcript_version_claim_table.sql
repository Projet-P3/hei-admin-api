do
$$
begin
        if not exists(select from pg_type where typname = 'claim_status') then
create type "claim_status" as enum ('OPEN','CLOSED');
end if;
end
$$;

create table if not exists "transcript_version_claim"
(
    id   varchar
        constraint transcript_version_claim_pk primary key default uuid_generate_v4(),
    transcript_id       varchar,
    version_id  varchar
        constraint transcript_version_id_fk references "transcript_version" (id),
    status    claim_status    not null,
    creation_datetime    timestamp with time zone not null,
    closed_datetime    timestamp with time zone not null,
    reason varchar not null
 );