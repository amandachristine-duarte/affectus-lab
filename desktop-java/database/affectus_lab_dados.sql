--
-- PostgreSQL database dump
--

\restrict LEhgSiuIuY2Fgmb7Wsx9bJ87elzSqMJOonWbd4QeYHm3D8TdzjiRxfiNuSnphC1

-- Dumped from database version 18.7
-- Dumped by pg_dump version 18.7

-- Started on 2026-11-22 16:39:44

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5032 (class 0 OID 16405)
-- Dependencies: 222
-- Data for Name: emocao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.emocao (id, nome, descricao, categoria, padrao_ativacao_sugerido, descricao_simples, sensacoes_corpo) FROM stdin;
1	Ansiedade	Experiência de apreensão e antecipação.	Experiência emocional	Ativação simpática	A ansiedade pode surgir quando percebemos desafios, incertezas ou possíveis ameaças. É como se o corpo estivesse se preparando para agir, mesmo quando não existe um perigo imediato.	Você pode perceber coração acelerado, respiração mais curta, tensão muscular, aperto no estômago ou inquietação.
2	Tristeza	Experiência relacionada a perdas e frustrações.	Emoção	Variável	A tristeza pode aparecer diante de perdas, decepções ou situações difíceis. Às vezes, ela nos convida a diminuir o ritmo, reconhecer o que aconteceu e buscar acolhimento.	Você pode sentir um nó na garganta, vontade de chorar, peso no peito, redução da energia ou necessidade de recolhimento.
3	Irritação	Experiência de incômodo e tensão.	Experiência emocional	Ativação simpática	A irritação pode surgir quando estamos sobrecarregados, frustrados ou percebemos que nossos limites foram ultrapassados.	Você pode perceber mandíbula contraída, ombros tensos, sensação de calor, inquietação ou vontade de reagir rapidamente.
4	Medo	Emoção relacionada à percepção de ameaça.	Emoção	Ativação variável	O medo é uma experiência de proteção diante de algo percebido como ameaçador. Dependendo da situação, podemos sentir vontade de fugir, reagir ou ficar imóveis.	Você pode perceber coração acelerado, frio na barriga, tremores, tensão muscular ou sensação de imobilidade.
5	Cansaço	Experiência de redução da energia física ou mental.	Estado físico e emocional	Baixa energia ou variável	O cansaço pode aparecer após esforço, noites mal dormidas ou períodos prolongados de exigência. Às vezes, é um sinal de que precisamos reconhecer nossos limites.	Você pode perceber peso nos olhos, lentidão, músculos cansados, dificuldade de concentração ou necessidade de descanso.
6	Solidão	Experiência de desconexão ou ausência de pertencimento.	Experiência relacional	Variável	A solidão pode surgir quando sentimos falta de conexão, acolhimento ou compreensão, mesmo estando acompanhados.	Você pode perceber aperto no peito, sensação de vazio, desânimo ou vontade de se recolher.
7	Confusão	Experiência de dificuldade para organizar pensamentos e sentimentos.	Estado cognitivo e emocional	Ativação variável	A confusão pode aparecer quando vivemos situações contraditórias, recebemos muitas informações ou ainda não conseguimos compreender o que sentimos.	Você pode perceber tensão na cabeça, inquietação, dificuldade de concentração ou mudanças no ritmo da respiração.
8	Vazio Emocional	Experiência subjetiva de distanciamento das próprias emoções.	Experiência emocional	Baixa ativação ou variável	O vazio emocional pode ser percebido como dificuldade de reconhecer sentimentos, ausência de interesse ou sensação de desconexão. Essa experiência pode ter diferentes causas e não significa necessariamente que o corpo esteja se desligando.	Você pode perceber pouca energia, sensação de distanciamento, dificuldade de identificar sensações ou ausência de reações corporais evidentes.
\.


--
-- TOC entry 5033 (class 0 OID 16476)
-- Dependencies: 229
-- Data for Name: emocao_correlacionada; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.emocao_correlacionada (emocao_id, correlacionada_id, ordem) FROM stdin;
7	1	1
4	1	1
3	1	1
8	2	1
6	2	1
5	2	1
1	3	2
1	4	1
8	5	2
7	5	2
3	5	2
2	6	1
4	7	2
6	8	2
5	8	2
2	8	2
\.


--
-- TOC entry 5039 (class 0 OID 0)
-- Dependencies: 221
-- Name: emocao_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.emocao_id_seq', 8, true);


-- Completed on 2026-11-22 16:39:45

--
-- PostgreSQL database dump complete
--

\unrestrict LEhgSiuIuY2Fgmb7Wsx9bJ87elzSqMJOonWbd4QeYHm3D8TdzjiRxfiNuSnphC1

