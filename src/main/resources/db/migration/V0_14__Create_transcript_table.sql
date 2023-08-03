do
$$
    begin
        if not exists(select from pg_type where typname = 'semester') then
            create type "semester" as enum ('M1','M2','M3','M4','M5','M6');
        end if;
    end
$$;

create table if not exists "transcript"
(
    id  varchar     primary key     not null,
    student_id      varchar     references "user" (id)      not null,
    semester    semester    not null,
    academic_year   integer     not null,
    is_definitive   boolean     not null,
    creation_datetime    timestamp with time zone not null
);