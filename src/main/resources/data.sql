
-- Seat 테이블 데이터 삽입
INSERT INTO seat (seat_id, concert_schedule_id, seat_no, seat_price, status) VALUES
(1, 1001, 1, 100.00, 'PROGRESS'),
(2, 1001, 2, 100.00, 'PROGRESS'),
(3, 1001, 3, 100.00, 'PROGRESS'),
(4, 1001, 4, 100.00, 'PROGRESS'),
(5, 1001, 5, 100.00, 'PROGRESS'),
(6, 1001, 6, 100.00, 'PROGRESS'),
(7, 1001, 7, 100.00, 'PROGRESS'),
(8, 1001, 8, 100.00, 'PROGRESS'),
(9, 1001, 9, 100.00, 'PROGRESS'),
(10, 1001, 10, 100.00, 'PROGRESS'),
(11, 1001, 11, 100.00, 'PROGRESS'),
(12, 1001, 12, 100.00, 'PROGRESS'),
(13, 1001, 13, 100.00, 'PROGRESS'),
(14, 1001, 14, 100.00, 'PROGRESS'),
(15, 1001, 15, 100.00, 'PROGRESS'),
(16, 1001, 16, 100.00, 'PROGRESS'),
(17, 1001, 17, 100.00, 'PROGRESS'),
(18, 1001, 18, 100.00, 'PROGRESS'),
(19, 1001, 19, 100.00, 'PROGRESS'),
(20, 1001, 20, 100.00, 'PROGRESS'),
(21, 1001, 21, 100.00, 'PROGRESS'),
(22, 1001, 22, 100.00, 'PROGRESS'),
(23, 1001, 23, 100.00, 'PROGRESS'),
(24, 1001, 24, 100.00, 'PROGRESS'),
(25, 1001, 25, 100.00, 'PROGRESS'),
(26, 1001, 26, 100.00, 'PROGRESS'),
(27, 1001, 27, 100.00, 'PROGRESS'),
(28, 1001, 28, 100.00, 'PROGRESS'),
(29, 1001, 29, 100.00, 'PROGRESS'),
(30, 1001, 30, 100.00, 'PROGRESS'),
(31, 1001, 31, 100.00, 'PROGRESS'),
(32, 1001, 32, 100.00, 'PROGRESS'),
(33, 1001, 33, 100.00, 'PROGRESS'),
(34, 1001, 34, 100.00, 'PROGRESS'),
(35, 1001, 35, 100.00, 'PROGRESS'),
(36, 1001, 36, 100.00, 'PROGRESS'),
(37, 1001, 37, 100.00, 'PROGRESS'),
(38, 1001, 38, 100.00, 'PROGRESS'),
(39, 1001, 39, 100.00, 'PROGRESS'),
(40, 1001, 40, 100.00, 'PROGRESS'),
(41, 1001, 41, 100.00, 'PROGRESS'),
(42, 1001, 42, 100.00, 'PROGRESS'),
(43, 1001, 43, 100.00, 'PROGRESS'),
(44, 1001, 44, 100.00, 'PROGRESS'),
(45, 1001, 45, 100.00, 'PROGRESS'),
(46, 1001, 46, 100.00, 'PROGRESS'),
(47, 1001, 47, 100.00, 'PROGRESS'),
(48, 1001, 48, 100.00, 'PROGRESS'),
(49, 1001, 49, 100.00, 'PROGRESS'),
(50, 1001, 50, 100.00, 'PROGRESS');
/*
-- Token 테이블 데이터 삽입
INSERT INTO token (token_uuid, status, created_at, expired_at) VALUES
    ('uuid-1', 'WAITING', NOW(), DATE_ADD(NOW(), INTERVAL 5 MINUTE)),
    ('uuid-2', 'ACTIVE', NOW(), DATE_ADD(NOW(), INTERVAL 5 MINUTE)),
    ('uuid-3', 'WAITING', NOW(), DATE_ADD(NOW(), INTERVAL 5 MINUTE)),
    ('uuid-4', 'EXPIRED', NOW() - INTERVAL 10 MINUTE, NOW() - INTERVAL 5 MINUTE),
('uuid-5', 'WAITING', NOW(), DATE_ADD(NOW(), INTERVAL 5 MINUTE));
*/