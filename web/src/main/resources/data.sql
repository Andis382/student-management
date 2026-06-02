-- ============================================================
-- Të dhëna fillestare për studentët
-- Ekzekutohen automatikisht në nisje (spring.sql.init.mode=always)
-- ============================================================
INSERT INTO students (first_name, last_name, email, date_of_birth, enrollment_date, gpa, major) VALUES
('Andis', 'Ramja', 'andis.ramja@example.com', '2000-05-15', '2019-10-01', 3.8, 'Inxhinieri Informatike'),
('Erion', 'Hoxha', 'erion.hoxha@example.com', '2001-02-20', '2020-10-01', 3.5, 'Shkenca Kompjuterike'),
('Ola', 'Marku', 'ola.marku@example.com', '1999-11-03', '2018-10-01', 3.9, 'Inxhinieri Softueri');
