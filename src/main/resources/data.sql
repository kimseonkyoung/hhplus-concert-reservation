TRUNCATE TABLE seat;
TRUNCATE TABLE user;
-- User 테이블 데이터 삽입
INSERT INTO user (user_id, balance_value) VALUE (1, 1000);
INSERT INTO user (user_id, balance_value) VALUE (2, 1000);
INSERT INTO user (user_id, balance_value) VALUE (3, 1000);
-- Seat 테이블 데이터 삽입
INSERT INTO seat (seat_id, concert_schedule_id, seat_no, seat_price, status) VALUES
(1, 1001, 1, 100, 'AVAILABLE'),
(2, 1001, 2, 100, 'PROGRESS'),
(3, 1001, 3, 100, 'PROGRESS'),
(4, 1001, 4, 100, 'PROGRESS'),
(5, 1001, 5, 100, 'PROGRESS'),
(6, 1001, 6, 100, 'PROGRESS'),
(7, 1001, 7, 100, 'PROGRESS'),
(8, 1001, 8, 100, 'PROGRESS'),
(9, 1001, 9, 100, 'PROGRESS'),
(10, 1001, 10, 100, 'PROGRESS'),
(11, 1001, 11, 100, 'PROGRESS'),
(12, 1001, 12, 100, 'PROGRESS'),
(13, 1001, 13, 100, 'PROGRESS'),
(14, 1001, 14, 100, 'PROGRESS'),
(15, 1001, 15, 100, 'PROGRESS'),
(16, 1001, 16, 100, 'PROGRESS'),
(17, 1001, 17, 100, 'PROGRESS'),
(18, 1001, 18, 100, 'PROGRESS'),
(19, 1001, 19, 100, 'PROGRESS'),
(20, 1001, 20, 100, 'PROGRESS'),
(21, 1001, 21, 100, 'PROGRESS'),
(22, 1001, 22, 100, 'PROGRESS'),
(23, 1001, 23, 100, 'PROGRESS'),
(24, 1001, 24, 100, 'PROGRESS'),
(25, 1001, 25, 100, 'PROGRESS'),
(26, 1001, 26, 100, 'PROGRESS'),
(27, 1001, 27, 100, 'PROGRESS'),
(28, 1001, 28, 1000, 'PROGRESS'),
(29, 1001, 29, 1000, 'PROGRESS'),
(30, 1001, 30, 100, 'PROGRESS'),
(31, 1001, 31, 100, 'PROGRESS'),
(32, 1001, 32, 100, 'PROGRESS'),
(33, 1001, 33, 100, 'PROGRESS'),
(34, 1001, 34, 100, 'PROGRESS'),
(35, 1001, 35, 100, 'PROGRESS'),
(36, 1001, 36, 100, 'PROGRESS'),
(37, 1001, 37, 100, 'PROGRESS'),
(38, 1001, 38, 100, 'PROGRESS'),
(39, 1001, 39, 100, 'PROGRESS'),
(40, 1001, 40, 100, 'PROGRESS'),
(41, 1001, 41, 100, 'PROGRESS'),
(42, 1001, 42, 100, 'PROGRESS'),
(43, 1001, 43, 100, 'PROGRESS'),
(44, 1001, 44, 100, 'PROGRESS'),
(45, 1001, 45, 100, 'PROGRESS'),
(46, 1001, 46, 100, 'PROGRESS'),
(47, 1001, 47, 100, 'PROGRESS'),
(48, 1001, 48, 100, 'PROGRESS'),
(49, 1001, 49, 100, 'PROGRESS'),
(50, 1001, 50, 100, 'PROGRESS');