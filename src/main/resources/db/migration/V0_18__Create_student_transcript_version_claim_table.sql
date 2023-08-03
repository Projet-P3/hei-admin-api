do
$$
begin
        if not exists(select from pg_type where typname = 'status') then
create type "status" as enum ('OPEN','CLOSED');
end if;
end
$$;

create table if not exists "transcript_version_claim"
(
    id  varchar     primary key     not null,
    transcript_id       varchar     references "transcript" (id) not null,
    version_id  varchar references "transcript-version" (id) not null,
    status    status    not null,
    creation_datetime    timestamp with time zone not null,
    closed_datetime    timestamp with time zone not null,
    reason varchar not null
 );