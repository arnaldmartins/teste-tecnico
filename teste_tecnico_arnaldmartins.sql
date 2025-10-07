--
-- PostgreSQL database dump
--

\restrict iNIE3QkaXg0Ma5nmkyFXSp8oaBayV2SKiRhJf5C7187LZr6z9v3LrLBelSeATkj

-- Dumped from database version 16.10 (Ubuntu 16.10-0ubuntu0.24.04.1)
-- Dumped by pg_dump version 16.10 (Ubuntu 16.10-0ubuntu0.24.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: medicamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medicamento (
    id bigint NOT NULL,
    descricao character varying(255),
    nome character varying(255)
);


ALTER TABLE public.medicamento OWNER TO postgres;

--
-- Name: medicamento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medicamento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.medicamento_id_seq OWNER TO postgres;

--
-- Name: medicamento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medicamento_id_seq OWNED BY public.medicamento.id;


--
-- Name: medicamento_receita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medicamento_receita (
    idmedicamentoreceitado bigint NOT NULL,
    quantidade integer,
    medicamento_id bigint NOT NULL,
    receita_id bigint NOT NULL
);


ALTER TABLE public.medicamento_receita OWNER TO postgres;

--
-- Name: medicamento_receita_idmedicamentoreceitado_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.medicamento_receita_idmedicamentoreceitado_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.medicamento_receita_idmedicamentoreceitado_seq OWNER TO postgres;

--
-- Name: medicamento_receita_idmedicamentoreceitado_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.medicamento_receita_idmedicamentoreceitado_seq OWNED BY public.medicamento_receita.idmedicamentoreceitado;


--
-- Name: paciente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.paciente (
    id bigint NOT NULL,
    cpf character varying(255),
    nome character varying(255)
);


ALTER TABLE public.paciente OWNER TO postgres;

--
-- Name: paciente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.paciente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.paciente_id_seq OWNER TO postgres;

--
-- Name: paciente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.paciente_id_seq OWNED BY public.paciente.id;


--
-- Name: receita; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.receita (
    id bigint NOT NULL,
    datareceita timestamp without time zone,
    paciente_id bigint NOT NULL
);


ALTER TABLE public.receita OWNER TO postgres;

--
-- Name: receita_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.receita_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.receita_id_seq OWNER TO postgres;

--
-- Name: receita_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.receita_id_seq OWNED BY public.receita.id;


--
-- Name: medicamento id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicamento ALTER COLUMN id SET DEFAULT nextval('public.medicamento_id_seq'::regclass);


--
-- Name: medicamento_receita idmedicamentoreceitado; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicamento_receita ALTER COLUMN idmedicamentoreceitado SET DEFAULT nextval('public.medicamento_receita_idmedicamentoreceitado_seq'::regclass);


--
-- Name: paciente id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paciente ALTER COLUMN id SET DEFAULT nextval('public.paciente_id_seq'::regclass);


--
-- Name: receita id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receita ALTER COLUMN id SET DEFAULT nextval('public.receita_id_seq'::regclass);


--
-- Data for Name: medicamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.medicamento (id, descricao, nome) FROM stdin;
\.


--
-- Data for Name: medicamento_receita; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.medicamento_receita (idmedicamentoreceitado, quantidade, medicamento_id, receita_id) FROM stdin;
\.


--
-- Data for Name: paciente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.paciente (id, cpf, nome) FROM stdin;
\.


--
-- Data for Name: receita; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.receita (id, datareceita, paciente_id) FROM stdin;
\.


--
-- Name: medicamento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medicamento_id_seq', 24, true);


--
-- Name: medicamento_receita_idmedicamentoreceitado_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.medicamento_receita_idmedicamentoreceitado_seq', 18, true);


--
-- Name: paciente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.paciente_id_seq', 10, true);


--
-- Name: receita_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.receita_id_seq', 10, true);


--
-- Name: medicamento medicamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicamento
    ADD CONSTRAINT medicamento_pkey PRIMARY KEY (id);


--
-- Name: medicamento_receita medicamento_receita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicamento_receita
    ADD CONSTRAINT medicamento_receita_pkey PRIMARY KEY (idmedicamentoreceitado);


--
-- Name: paciente paciente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT paciente_pkey PRIMARY KEY (id);


--
-- Name: receita receita_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receita
    ADD CONSTRAINT receita_pkey PRIMARY KEY (id);


--
-- Name: medicamento_receita fkc0xk0runjbgr0bb6nvs3689y8; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicamento_receita
    ADD CONSTRAINT fkc0xk0runjbgr0bb6nvs3689y8 FOREIGN KEY (medicamento_id) REFERENCES public.medicamento(id);


--
-- Name: receita fkf7pyhn2uddmyy3xjqn7tc7d12; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.receita
    ADD CONSTRAINT fkf7pyhn2uddmyy3xjqn7tc7d12 FOREIGN KEY (paciente_id) REFERENCES public.paciente(id);


--
-- Name: medicamento_receita fklld9u6vwrnamhhxyse1cevsh4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medicamento_receita
    ADD CONSTRAINT fklld9u6vwrnamhhxyse1cevsh4 FOREIGN KEY (receita_id) REFERENCES public.receita(id);


--
-- PostgreSQL database dump complete
--

\unrestrict iNIE3QkaXg0Ma5nmkyFXSp8oaBayV2SKiRhJf5C7187LZr6z9v3LrLBelSeATkj

