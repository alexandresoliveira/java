insert into games
    (id, actual_player, status, winner, created_at, updated_at)
    values
    ('cd64aa77-c933-4f86-b90e-2e624395dc0f', 'X', 'INPROGRESS', '-', CURRENT_TIMESTAMP() - 4, CURRENT_TIMESTAMP() - 4);

insert into movements
    (id, game_id, player, x, y, created_at, updated_at)
    values
    ('a2b5cdec-2ca2-4a8f-b1c5-68ea9bf2d806', 'cd64aa77-c933-4f86-b90e-2e624395dc0f', 'O', 1, 2, CURRENT_TIMESTAMP() - 4, CURRENT_TIMESTAMP() - 4);

insert into movements
    (id, game_id, player, x, y, created_at, updated_at)
    values
    ('a1749def-a2d5-4ac6-a2c0-f432c70879ab', 'cd64aa77-c933-4f86-b90e-2e624395dc0f', 'X', 1, 1, CURRENT_TIMESTAMP() - 3, CURRENT_TIMESTAMP() - 3);

insert into movements
    (id, game_id, player, x, y, created_at, updated_at)
    values
    ('0cc176d6-93d7-4e7e-b4e7-fefa0191974b', 'cd64aa77-c933-4f86-b90e-2e624395dc0f', 'X', 2, 2, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
