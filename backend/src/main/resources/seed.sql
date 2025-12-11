-- Inicia uma transação. Se algo falhar, tudo será revertido (ROLLBACK).
BEGIN;
TRUNCATE TABLE likes RESTART IDENTITY CASCADE;
TRUNCATE TABLE tuneet_comments RESTART IDENTITY CASCADE;
TRUNCATE TABLE follows RESTART IDENTITY CASCADE;
TRUNCATE TABLE tuneets RESTART IDENTITY CASCADE;
TRUNCATE TABLE profiles RESTART IDENTITY CASCADE;
TRUNCATE TABLE users RESTART IDENTITY CASCADE;
TRUNCATE TABLE movie_likes RESTART IDENTITY CASCADE;
TRUNCATE TABLE movie_comments RESTART IDENTITY CASCADE;
TRUNCATE TABLE movie_reviews RESTART IDENTITY CASCADE;
TRUNCATE TABLE movie_comments RESTART IDENTITY CASCADE;
TRUNCATE TABLE movie_likes RESTART IDENTITY CASCADE;

-- ===================================================================
-- 1. Inserir Usuários (UserEntity)
-- ===================================================================
-- IDs dos usuários (UUIDs)
-- Alice: 232f5486-d623-4c54-8de5-665ba9e679d9
-- Bob:   b0b12345-6789-4abc-def0-1234567890ab
-- Carol: ca101112-1314-4151-6171-81920212223a
INSERT INTO users (id, email, username, password)
VALUES
('232f5486-d623-4c54-8de5-665ba9e679d9', 'alice@tunetown.com', 'alice', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92s.agIeRQ/Dbcq7DVIvu'),
('b0b12345-6789-4abc-def0-1234567890ab', 'bob@tunetown.com', 'bob', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92s.agIeRQ/Dbcq7DVIvu'),
('ca101112-1314-4151-6171-81920212223a', 'carol@tunetown.com', 'carol', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92s.agIeRQ/Dbcq7DVIvu');

-- ===================================================================
-- 2. Inserir Perfis (ProfileEntity)
-- ===================================================================
-- IDs dos perfis (UUIDs)
-- Perfil Alice: a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11
-- Perfil Bob:   b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12
-- Perfil Carol: ca101003-9c0b-4ef8-bb6d-6bb9bd380a13
INSERT INTO profiles (id, bio, favorite_song, user_id, created_at)
VALUES
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'Bio de Alice. Amante de música indie.', 'Eu Não Sou Boa Influência pra Você', '232f5486-d623-4c54-8de5-665ba9e679d9', NOW()),
('b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', 'Bio de Bob. Apenas pelos tunes.', 'Smells Like Teen Spirit', 'b0b12345-6789-4abc-def0-1234567890ab', NOW()),
('ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', 'Bio de Carol. Seguindo as tendências.', 'Blinding Lights', 'ca101112-1314-4151-6171-81920212223a', NOW());

-- ===================================================================
-- 3. Inserir Tuneets (TuneetEntity)
-- ===================================================================
-- IDs dos tuneets (UUIDs Válidos)
INSERT INTO tuneets (id, author_id, text_content, tunable_item_id, tunable_item_plataform, tunable_item_title, tunable_item_artist, tunable_item_type, tunable_item_artwork_url, created_at)
VALUES
('c95f7ec5-e0b3-4da4-b492-5da0a24ba23c', '232f5486-d623-4c54-8de5-665ba9e679d9', 'Amo essa música!', '77nBlfiqjoo0bKQT0fkGr6', 'spotify', 'Eu Não Sou Boa Influência pra Você', 'Seu Pereira e Coletivo 401', 'music', 'https://i.scdn.co/image/ab67616d0000b2730504fd92867c6ded742de228', NOW() - INTERVAL '2 day'),
('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'b0b12345-6789-4abc-def0-1234567890ab', 'Clássico. Nunca envelhece.', '4CeeEOM32jQcH3eN9Q2dGj', 'spotify', 'Smells Like Teen Spirit', 'Nirvana', 'music', 'https://i.scdn.co/image/ab67616d0000b273fbc71c99f9c1296c56dd51b6', NOW() - INTERVAL '1 day'),
('1b9d6bcd-bbfd-4b2d-9b5d-ab8dfbbd4bed', 'ca101112-1314-4151-6171-81920212223a', 'Minha música do momento!', '0VjIjW4GlUZAMYd2vXMi3b', 'spotify', 'Blinding Lights', 'The Weeknd', 'music', 'https://i.scdn.co/image/ab67616d0000b2738863bc11d2aa12b54f5aeb36', NOW() - INTERVAL '1 hour'),
('a4667a55-f2fa-4b81-9ab8-07f90f23f890', '232f5486-d623-4c54-8de5-665ba9e679d9', 'Ouvindo no repeat.', '3AJwUDP919kvQ9QcozQPxg', 'spotify', 'Yellow', 'Coldplay', 'music', 'https://i.scdn.co/image/ab67616d0000b2739164bafe9aaa168d93f4816a', NOW());

-- ===================================================================
-- 4. Inserir "Segue" (Follow)
-- ===================================================================
-- 'follower_id' e 'followed_id' DEVEM corresponder aos IDs da tabela 'profiles'.
INSERT INTO follows (follower_id, followed_id, created_at)
VALUES
-- Alice ('a11ce001-...') segue Bob ('b0b00002-...')
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', NOW()),

-- Alice ('a11ce001-...') segue Carol ('ca101003-...')
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', NOW()),

-- Bob ('b0b00002-...') segue Alice ('a11ce001-...')
('b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', 'a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', NOW());


-- ===================================================================
-- 4. Inserir comentários para os Tuneets
-- ===================================================================

INSERT INTO tuneet_comments (author_id, tuneet_id, content_text, created_at)
VALUES
-- Comentários para o primeiro Tuneet
('ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', 'c95f7ec5-e0b3-4da4-b492-5da0a24ba23c', 'Que música incrível! 🎵', NOW() - INTERVAL '36 hour'),
('b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', 'c95f7ec5-e0b3-4da4-b492-5da0a24ba23c', 'Adorei o seu gosto musical 😎', NOW() - INTERVAL '30 hour'),

-- Comentários para o segundo Tuneet
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'Clássico eterno! 🤘', NOW() - INTERVAL '20 hour'),

-- Comentários para o terceiro Tuneet
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', '1b9d6bcd-bbfd-4b2d-9b5d-ab8dfbbd4bed', 'Essa do The Weeknd é meu vício 😍', NOW() - INTERVAL '45 minute'),

-- Comentários para o quarto Tuneet
('ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', 'a4667a55-f2fa-4b81-9ab8-07f90f23f890', 'Coldplay sempre no repeat 🌟', NOW() - INTERVAL '5 minute');

-- ===================================================================
-- 4. Inserir Likes para Tuneets
-- ===================================================================
-- IDs de exemplo para perfis (Profiles) válidos
-- Ajuste os UUIDs conforme os existentes na sua tabela de perfis

INSERT INTO likes (tuneet_id, profile_id, created_at)
VALUES
-- Like do usuário 232f5486 em tuneet c95f7ec5
('c95f7ec5-e0b3-4da4-b492-5da0a24ba23c', 'b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', NOW() - INTERVAL '2 hour'),

-- Like do usuário b0b12345 em tuneet f47ac10b-58cc-4372-a567-0e02b2c3d479
('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', NOW() - INTERVAL '1 hour'),

-- Like do usuário ca101112 em tuneet 1b9d6bcd-bbfd-4b2d-9b5d-ab8dfbbd4bed
('1b9d6bcd-bbfd-4b2d-9b5d-ab8dfbbd4bed', 'b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', NOW() - INTERVAL '30 minute'),

-- Like do usuário 232f5486 em tuneet a4667a55-f2fa-4b81-9ab8-07f90f23f890
('a4667a55-f2fa-4b81-9ab8-07f90f23f890', 'b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', NOW() - INTERVAL '15 minute');

-- ===================================================================
-- Inserir MovieReviews
-- ===================================================================
INSERT INTO movie_reviews (
    id, author_id, text_content, movie_id, movie_title, movie_platform,
    movie_artwork_url, movie_director, movie_release_year, movie_type, user_rating, created_at
)
VALUES
    ('mr1', '232f5486-d623-4c54-8de5-665ba9e679d9', 'Review da Alice', 'tt0111161', 'The Shawshank Redemption', 'TMDB',
     'https://image.tmdb.org/t/p/w500/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg', 'Frank Darabont', '1994', 'movie', 5, NOW() - INTERVAL '2 day'),

    ('mr2', 'b0b12345-6789-4abc-def0-1234567890ab', 'Review do Bob', 'tt0468569', 'The Dark Knight', 'TMDB',
     'https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg', 'Christopher Nolan', '2008', 'movie', 4, NOW() - INTERVAL '1 day'),

    ('mr3', 'ca101112-1314-4151-6171-81920212223a', 'Review da Carol', 'tt1375666', 'Inception', 'TMDB',
     'https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg', 'Christopher Nolan', '2010', 'movie', 5, NOW() - INTERVAL '3 hour');

INSERT INTO movie_comments (author_id, movie_review_id, content_text, minute_mark, created_at)
VALUES
-- Comentários para a review 'mr1' (Alice)
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'mr1', 'Concordo, um clássico atemporal!', NULL, NOW() - INTERVAL '1 day'),
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'mr1', 'Melhor filme de todos os tempos! 😍', NULL, NOW() - INTERVAL '20 hour'),

-- Comentários para a review 'mr2' (Bob)
('b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', 'mr2', 'O Coringa é sensacional, mas o filme é incrível também!', NULL, NOW() - INTERVAL '18 hour'),
('b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', 'mr2', 'Batalha de heróis inesquecível!', NULL, NOW() - INTERVAL '15 hour'),

-- Comentários para a review 'mr3' (Carol)
('ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', 'mr3', 'Mind-bending! 😲 Vale cada minuto.', 45, NOW() - INTERVAL '3 hour'),
('ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', 'mr3', 'Inception é demais, adoro a direção de Nolan.', 72, NOW() - INTERVAL '2 hour');

-- ===================================================================
-- Inserir likes para MovieReviews
-- ===================================================================
INSERT INTO movie_likes (profile_id, movie_review_id, reaction, created_at)
VALUES
-- Likes para a review 'mr1'
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'mr1', 'RIU', NOW() - INTERVAL '5 hour'),
('b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', 'mr1', 'REFLEXIVO', NOW() - INTERVAL '4 hour'),

-- Likes para a review 'mr2'
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'mr2', 'SUSTO', NOW() - INTERVAL '3 hour'),
('ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', 'mr2', 'CHOROU', NOW() - INTERVAL '2 hour'),

-- Likes para a review 'mr3'
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'mr3', 'REFLEXIVO', NOW() - INTERVAL '90 minute'),
('b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', 'mr3', 'RIU', NOW() - INTERVAL '30 minute'),
('ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', 'mr3', 'SUSTO', NOW() - INTERVAL '15 minute');

-- ===================================================================
-- 1. Inserir BookReviews
-- ===================================================================
INSERT INTO book_reviews (
    id, author_id, book_id, book_title, book_platform, book_artwork_url,
    book_author, book_isbn, book_page_count, reading_status, text_content, created_at
)
VALUES
-- Review de Alice
('br1', '232f5486-d623-4c54-8de5-665ba9e679d9', 'bk001', 'O Conto das Sombras', 'GoogleBooks',
 'https://example.com/book1.jpg', 'Clarisse Lispector', '9788572324810', 256, 'COMPLETED', 'Incrível e profundo.', NOW() - INTERVAL '2 day'),

-- Review de Bob
('br2', 'b0b12345-6789-4abc-def0-1234567890ab', 'bk002', 'Programação para Todos', 'GoogleBooks',
 'https://example.com/book2.jpg', 'B. Kernighan', '9788572324827', 320, 'READING', 'Excelente didática.', NOW() - INTERVAL '1 day'),

-- Review de Carol
('br3', 'ca101112-1314-4151-6171-81920212223a', 'bk003', 'Mistérios do Universo', 'GoogleBooks',
 'https://example.com/book3.jpg', 'Stephen Hawking', '9788572324834', 400, 'WANT_TO_READ', 'Ansiosa para começar!', NOW() - INTERVAL '3 hour');

-- ===================================================================
-- 2. Inserir BookComments
-- ===================================================================
INSERT INTO book_comments (
    author_id, book_review_id, content_text, page_number, chapter_name, created_at
)
VALUES
-- Comentários na review de Alice
('b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', 'br1', 'Concordo, muito emocionante!', 34, 'Capítulo 2', NOW() - INTERVAL '25 hour'),
('ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', 'br1', 'Amei sua análise!', 120, 'Capítulo 8', NOW() - INTERVAL '20 hour'),

-- Comentários na review de Bob
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'br2', 'Ótimas dicas de programação!', NULL, NULL, NOW() - INTERVAL '18 hour'),

-- Comentários na review de Carol
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'br3', 'Também quero ler esse livro!', NULL, NULL, NOW() - INTERVAL '2 hour'),
('b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', 'br3', 'Espero que compartilhe suas impressões!', NULL, NULL, NOW() - INTERVAL '1 hour');

-- ===================================================================
-- 3. Inserir BookLikes
-- ===================================================================
INSERT INTO book_likes (profile_id, book_review_id, impact_level, created_at)
VALUES
-- Likes na review de Alice
('b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', 'br1', 'FORTE', NOW() - INTERVAL '12 hour'),
('ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', 'br1', 'MEDIO', NOW() - INTERVAL '10 hour'),

-- Likes na review de Bob
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'br2', 'FRACO', NOW() - INTERVAL '8 hour'),

-- Likes na review de Carol
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'br3', 'MUDOU_DE_NOVO_MEU_JEITO_DE_VER_O_MUNDO', NOW() - INTERVAL '3 hour'),
('b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', 'br3', 'FORTE', NOW() - INTERVAL '2 hour');

-- Confirma a transação. Se tudo deu certo, os dados são salvos.
COMMIT;