CREATE TABLE IF NOT EXISTS transcript_version
(
    id                    VARCHAR CONSTRAINT transcript_version_pk PRIMARY KEY DEFAULT uuid_generate_v4(),
    transcript_id         VARCHAR NOT NULL,
    ref                   INTEGER NOT NULL,
    created_by_user_id    VARCHAR NOT NULL,
    created_by_user_role  VARCHAR NOT NULL,
    creation_datetime     timestamp with time zone not null default now()
);

CREATE INDEX IF NOT EXISTS transcript_version_transcript_id_index ON transcript_version (transcript_id);
