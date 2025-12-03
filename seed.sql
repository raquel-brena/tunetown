-- Inicia uma transação. Se algo falhar, tudo será revertido (ROLLBACK).
BEGIN;

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


-- Confirma a transação. Se tudo deu certo, os dados são salvos.
COMMIT;