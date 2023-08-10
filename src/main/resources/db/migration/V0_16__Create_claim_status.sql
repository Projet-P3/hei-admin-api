do
$$
begin
        if not exists(select from pg_type where typname = 'claim_status') then
create type "claim_status" as enum ('OPEN', 'CLOSED');
end if;
end
$$;