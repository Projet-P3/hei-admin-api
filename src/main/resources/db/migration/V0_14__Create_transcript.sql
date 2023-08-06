CREATE TABLE IF NOT EXISTS transcript
(
    id                  VARCHAR CONSTRAINT transcript_pk PRIMARY KEY DEFAULT uuid_generate_v4(),
    student_id          VARCHAR NOT NULL,
    semester            VARCHAR NOT NULL,
    academic_year       INTEGER NOT NULL,
    is_definitive       BOOLEAN NOT NULL,
    creation_datetime    timestamp with time zone not null default now()
);

CREATE INDEX IF NOT EXISTS transcript_student_id_index ON transcript (student_id);
