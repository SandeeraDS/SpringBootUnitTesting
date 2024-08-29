CREATE TABLE public.tbl_movie
(
    ID serial NOT NULL,
    NAME character varying(100) NOT NULL,
    GENRE character varying(100) NOT NULL,
    RELEASED_DATE timestamp without time zone DEFAULT NULL,
    PRIMARY KEY (ID)
);