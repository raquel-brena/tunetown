-- Inicia uma transação. Se algo falhar, tudo será revertido (ROLLBACK).
BEGIN;

-- ===================================================================
-- 1. Inserir Usuários (UserEntity)
-- ===================================================================
-- Hash bcrypt para a senha "password"
INSERT INTO users (id, email, username, password)
VALUES 
('232f5486-d623-4c54-8de5-665ba9e679d9', 'alice@tunetown.com', 'alice', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92s.agIeRQ/Dbcq7DVIvu'),
('b0b12345-6789-4abc-def0-1234567890ab', 'bob@tunetown.com', 'bob', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92s.agIeRQ/Dbcq7DVIvu'),
('ca101112-1314-4151-6171-81920212223a', 'carol@tunetown.com', 'carol', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92s.agIeRQ/Dbcq7DVIvu'),
('da71d001-1a2b-4c3d-8e4f-5a6b7c8d9e0f', 'david@tunetown.com', 'david', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92s.agIeRQ/Dbcq7DVIvu'),
('e7e00002-2a2b-4c3d-8e4f-5a6b7c8d9e1a', 'eve@tunetown.com', 'eve', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92s.agIeRQ/Dbcq7DVIvu');

-- ===================================================================
-- 2. Inserir Perfis (ProfileEntity)
-- ===================================================================
INSERT INTO profiles (id, bio, favorite_song, user_id, created_at)
VALUES 
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'Bio de Alice. Amante de música indie.', 'Eu Não Sou Boa Influência pra Você', '232f5486-d623-4c54-8de5-665ba9e679d9', NOW()),
('b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', 'Bio de Bob. Apenas pelos tunes.', 'Smells Like Teen Spirit', 'b0b12345-6789-4abc-def0-1234567890ab', NOW()),
('ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', 'Bio de Carol. Seguindo as tendências.', 'Blinding Lights', 'ca101112-1314-4151-6171-81920212223a', NOW()),
('pd-da71d-1a2b-4c3d-8e4f-5a6b7c8d9e0f', 'Bio de David. Gosto de tudo um pouco.', 'Billie Jean', 'da71d001-1a2b-4c3d-8e4f-5a6b7c8d9e0f', NOW()),
('pe-e7e0-2a2b-4c3d-8e4f-5a6b7c8d9e1a', 'Bio de Eve. MPB e forró.', 'Flor E O Beija-Flor', 'e7e00002-2a2b-4c3d-8e4f-5a6b7c8d9e1a', NOW());

-- ===================================================================
-- 3. Inserir Tuneets (TuneetEntity)
-- ===================================================================
INSERT INTO tuneets (id, author_id, text_content, tunable_item_id, tunable_item_plataform, tunable_item_title, tunable_item_artist, tunable_item_type, tunable_item_artwork_url, created_at)
VALUES 
-- 4 tuneets originais (com IDs corrigidos)
('c95f7ec5-e0b3-4da4-b492-5da0a24ba23c', '232f5486-d623-4c54-8de5-665ba9e679d9', 'Amo essa música!', '77nBlfiqjoo0bKQT0fkGr6', 'spotify', 'Eu Não Sou Boa Influência pra Você', 'Seu Pereira e Coletivo 401', 'music', 'https://i.scdn.co/image/ab67616d0000b2730504fd92867c6ded742de228', NOW() - INTERVAL '5 day'),
('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'b0b12345-6789-4abc-def0-1234567890ab', 'Clássico. Nunca envelhece.', '4CeeEOM32jQcH3eN9Q2dGj', 'spotify', 'Smells Like Teen Spirit', 'Nirvana', 'music', 'https://i.scdn.co/image/ab67616d0000b273fbc71c99f9c1296c56dd51b6', NOW() - INTERVAL '4 day'),
('1b9d6bcd-bbfd-4b2d-9b5d-ab8dfbbd4bed', 'ca101112-1314-4151-6171-81920212223a', 'Minha música do momento!', '0VjIjW4GlUZAMYd2vXMi3b', 'spotify', 'Blinding Lights', 'The Weeknd', 'music', 'https://i.scdn.co/image/ab67616d0000b2738863bc11d2aa12b54f5aeb36', NOW() - INTERVAL '3 day'),
('a4667a55-f2fa-4b81-9ab8-07f90f23f890', '232f5486-d623-4c54-8de5-665ba9e679d9', 'Ouvindo no repeat.', '3AJwUDP919kvQ9QcozQPxg', 'spotify', 'Yellow', 'Coldplay', 'music', 'https://i.scdn.co/image/ab67616d0000b2739164bafe9aaa168d93f4816a', NOW() - INTERVAL '2 day'),

-- 8 novos tuneets
('550e8400-e29b-41d4-a716-446655440001', 'b0b12345-6789-4abc-def0-1234567890ab', 'Essa aqui é estouro!', '6c6brFif6MTMSq0sVwgMAT', 'spotify', 'Beija Flor', 'João Gomes', 'music', 'https://i.scdn.co/image/ab67616d0000b273dc69ce46ee5e5a9034be3f0f', NOW() - INTERVAL '40 hour'),
('550e8400-e29b-41d4-a716-446655440002', '232f5486-d623-4c54-8de5-665ba9e679d9', 'Hee hee!', '7J1uxwnxfQLu4APicE5Rnj', 'spotify', 'Billie Jean', 'Michael Jackson', 'music', 'https://i.scdn.co/image/ab67616d0000b273de437d960dda1ac0a3586d97', NOW() - INTERVAL '36 hour'),
('550e8400-e29b-41d4-a716-446655440003', 'ca101112-1314-4151-6171-81920212223a', 'Perfeita, Marília!', '66N2SB1lgHPgJXVCjg6GXH', 'spotify', 'Flor E O Beija-Flor', 'Marília Mendonça', 'music', 'https://i.scdn.co/image/ab67616d0000b273ef7c57e758a8db5c42aca11b', NOW() - INTERVAL '30 hour'),
('550e8400-e29b-41d4-a716-446655440004', 'da71d001-1a2b-4c3d-8e4f-5a6b7c8d9e0f', 'Primeiro tuneet! Começando com um clássico.', '3AJwUDP919kvQ9QcozQPxg', 'spotify', 'Yellow', 'Coldplay', 'music', 'https://i.scdn.co/image/ab67616d0000b2739164bafe9aaa168d93f4816a', NOW() - INTERVAL '24 hour'),
('550e8400-e29b-41d4-a716-446655440005', 'e7e00002-2a2b-4c3d-8e4f-5a6b7c8d9e1a', 'Cheguei. Saudades da rainha.', '66N2SB1lgHPgJXVCjg6GXH', 'spotify', 'Flor E O Beija-Flor', 'Marília Mendonça', 'music', 'https://i.scdn.co/image/ab67616d0000b273ef7c57e758a8db5c42aca11b', NOW() - INTERVAL '20 hour'),
('550e8400-e29b-41d4-a716-446655440006', '232f5486-d623-4c54-8de5-665ba9e679d9', 'Dançando muito com essa.', '5iJXXtOCk0Fx4eOC8LduBR', 'spotify', 'Brasiliana', 'BaianaSystem', 'music', 'https://i.scdn.co/image/ab67616d0000b27336470a0d759d1b2ce7b320b4', NOW() - INTERVAL '15 hour'),
('550e8400-e29b-41d4-a716-446655440007', 'da71d001-1a2b-4c3d-8e4f-5a6b7c8d9e0f', 'Ouvindo o que a Carol postou.', '0VjIjW4GlUZAMYd2vXMi3b', 'spotify', 'Blinding Lights', 'The Weeknd', 'music', 'https://i.scdn.co/image/ab67616d0000b2738863bc11d2aa12b54f5aeb36', NOW() - INTERVAL '10 hour'),
('550e8400-e29b-41d4-a716-446655440008', 'b0b12345-6789-4abc-def0-1234567890ab', 'Que som é esse?', '5iJXXtOCk0Fx4eOC8LduBR', 'spotify', 'Brasiliana', 'BaianaSystem', 'music', 'https://i.scdn.co/image/ab67616d0000b27336470a0d759d1b2ce7b320b4', NOW() - INTERVAL '5 hour');

-- ===================================================================
-- 4. Inserir "Segue" (Follow)
-- ===================================================================
-- IDs dos perfis:
-- Alice: a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11
-- Bob:   b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12
-- Carol: ca101003-9c0b-4ef8-bb6d-6bb9bd380a13
-- David: pd-da71d-1a2b-4c3d-8e4f-5a6b7c8d9e0f
-- Eve:   pe-e7e0-2a2b-4c3d-8e4f-5a6b7c8d9e1a
INSERT INTO follows (follower_id, followed_id, created_at)
VALUES 
-- Relações originais
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', NOW()), -- Alice segue Bob
('a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', 'ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', NOW()), -- Alice segue Carol
('b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', 'a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', NOW()), -- Bob segue Alice

-- Novas Relações
('ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', 'a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', NOW()), -- Carol segue Alice
('ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', 'b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', NOW()), -- Carol segue Bob
('pd-da71d-1a2b-4c3d-8e4f-5a6b7c8d9e0f', 'a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', NOW()), -- David segue Alice
('pd-da71d-1a2b-4c3d-8e4f-5a6b7c8d9e0f', 'b0b00002-9c0b-4ef8-bb6d-6bb9bd380a12', NOW()), -- David segue Bob
('pd-da71d-1a2b-4c3d-8e4f-5a6b7c8d9e0f', 'ca101003-9c0b-4ef8-bb6d-6bb9bd380a13', NOW()), -- David segue Carol
('pe-e7e0-2a2b-4c3d-8e4f-5a6b7c8d9e1a', 'a11ce001-9c0b-4ef8-bb6d-6bb9bd380a11', NOW()); -- Eve segue Alice


-- Confirma a transação. Se tudo deu certo, os dados são salvos.
COMMIT;