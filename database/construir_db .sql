-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 18-05-2026 a las 21:22:31
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `construir_db`
--
CREATE DATABASE IF NOT EXISTS `construir_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `construir_db`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `calificacion`
--

DROP TABLE IF EXISTS `calificacion`;
CREATE TABLE `calificacion` (
  `id` bigint(20) NOT NULL,
  `comentario` varchar(500) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `puntaje` int(11) NOT NULL,
  `autor_id` bigint(20) NOT NULL,
  `destinatario_id` bigint(20) NOT NULL,
  `trabajo_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `calificacion`
--

INSERT INTO `calificacion` (`id`, `comentario`, `fecha`, `puntaje`, `autor_id`, `destinatario_id`, `trabajo_id`) VALUES
(1, 'Excelente trabajo, muy prolijo.', '2025-11-06', 5, 2, 3, 7),
(2, 'un cra', '2025-11-06', 5, 2, 3, 8),
(3, 'pago a tiempo', '2025-11-06', 5, 3, 2, 8),
(4, 'me dio agua ', '2025-11-06', 5, 3, 2, 7),
(5, 'prolijo ', '2025-11-07', 5, 5, 3, 6),
(6, 'genial', '2025-11-07', 5, 3, 2, 1),
(7, 'muy bien', '2025-11-07', 5, 3, 2, 3),
(8, 'muy buen trabajo', '2025-11-07', 5, 2, 3, 9),
(9, 'termino rapido ', '2025-11-07', 5, 2, 3, 12),
(10, 'bien ', '2025-11-07', 5, 2, 3, 11),
(11, 'prolijo', '2025-11-07', 5, 2, 8, 13),
(12, 'Cliente cumplidor y claro.', '2025-11-07', 5, 3, 2, 12),
(13, 'muy bueno ', '2025-11-07', 5, 2, 3, 14),
(14, 'exelente ya casi ', '2025-11-07', 5, 2, 3, 15),
(15, 'cumplio con el pago ', '2025-11-07', 5, 3, 2, 15),
(16, 'cumplio con todo ', '2025-11-07', 5, 2, 3, 16),
(17, 'cumlio con el pago ', '2025-11-07', 5, 3, 2, 16),
(18, 'cumplio', '2025-11-07', 5, 3, 5, 6),
(19, 'muy prolijo ', '2025-11-07', 5, 2, 3, 18),
(20, 'pago todo a tiempo', '2025-11-07', 5, 3, 2, 18),
(21, 'crack', '2025-11-07', 5, 2, 3, 19),
(22, 'crack', '2025-11-08', 5, 2, 3, 20),
(23, 'rapido', '2026-03-26', 5, 2, 3, 1),
(24, 'muy bueno ', '2026-05-12', 5, 19, 20, 23),
(25, 'pago en forma ', '2026-05-12', 5, 20, 19, 23);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `direccion` varchar(255) DEFAULT NULL,
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`direccion`, `id`) VALUES
('Esquiu 49', 2),
('Echelaine 67', 5),
('Esquiu 37', 7),
('kamehouse 333', 9),
('kamehouse 333', 13),
('Esquiu 49', 16),
('Dojo Shinji Kay 666', 17),
('Esquiu 49', 19),
('santuario 333', 21);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cotizacion`
--

DROP TABLE IF EXISTS `cotizacion`;
CREATE TABLE `cotizacion` (
  `id` bigint(20) NOT NULL,
  `creada_en` datetime(6) NOT NULL,
  `estado` enum('ACEPTADA','PENDIENTE','RECHAZADA') NOT NULL,
  `mensaje` varchar(500) DEFAULT NULL,
  `monto` decimal(12,2) NOT NULL,
  `profesional_id` bigint(20) NOT NULL,
  `trabajo_id` bigint(20) NOT NULL,
  `moneda` enum('ARS','USD') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cotizacion`
--

INSERT INTO `cotizacion` (`id`, `creada_en`, `estado`, `mensaje`, `monto`, `profesional_id`, `trabajo_id`, `moneda`) VALUES
(1, '2025-11-05 23:57:38.000000', 'ACEPTADA', 'Se termina en el dia ', 50000.00, 3, 1, NULL),
(2, '2025-11-05 23:58:27.000000', 'RECHAZADA', 'Se termina en el dia ', 40000.00, 3, 2, NULL),
(3, '2025-11-06 00:25:24.000000', 'ACEPTADA', 'se termina en el dia ', 50000.00, 3, 3, NULL),
(4, '2025-11-06 00:48:32.000000', 'ACEPTADA', 'sin materiales se termina en el dia ', 50000.00, 4, 4, NULL),
(5, '2025-11-06 00:49:14.000000', 'RECHAZADA', 'Se termina en 2 dias ', 40000.00, 3, 4, NULL),
(6, '2025-11-06 02:14:34.000000', 'ACEPTADA', 'sin materiales mas detalles ', 45000.00, 3, 6, NULL),
(7, '2025-11-06 12:54:58.000000', 'ACEPTADA', 'termini en el dia ', 50000.00, 3, 5, NULL),
(8, '2025-11-06 13:31:57.000000', 'ACEPTADA', 'con materiales ', 50000.00, 3, 7, NULL),
(9, '2025-11-06 16:38:00.000000', 'ACEPTADA', 'para hoy', 50000.00, 3, 8, NULL),
(10, '2025-11-07 00:40:43.000000', 'ACEPTADA', 'en el dia ', 5000.00, 3, 9, NULL),
(11, '2025-11-07 02:51:00.000000', 'ACEPTADA', 'en el dia ', 5000.00, 3, 11, NULL),
(12, '2025-11-07 11:16:49.000000', 'ACEPTADA', 'en el dia ', 5000.00, 3, 12, NULL),
(13, '2025-11-07 11:31:29.000000', 'RECHAZADA', 'se termina en el dia ', 5000.00, 3, 13, NULL),
(14, '2025-11-07 11:36:47.000000', 'ACEPTADA', 'termino en el dia ', 4000.00, 8, 13, NULL),
(15, '2025-11-07 12:24:45.000000', 'ACEPTADA', 'en el dia ', 5000.00, 3, 14, NULL),
(16, '2025-11-07 12:40:38.000000', 'ACEPTADA', 'sin materiales ', 5000.00, 3, 15, NULL),
(17, '2025-11-07 13:01:07.000000', 'ACEPTADA', 'con materiales ', 6000.00, 3, 16, NULL),
(18, '2025-11-07 13:56:43.000000', 'RECHAZADA', 'sin materiales mano de obra ', 5000.00, 3, 17, NULL),
(19, '2025-11-07 15:44:28.000000', 'ACEPTADA', 'sin materiales solo mano de obra', 10000.00, 3, 18, NULL),
(20, '2025-11-07 20:03:20.000000', 'ACEPTADA', 'se termina en el dia ', 5000.00, 3, 19, NULL),
(21, '2025-11-08 13:44:51.000000', 'ACEPTADA', 'lo termino en el dia ', 50000.00, 3, 20, NULL),
(22, '2026-05-09 21:44:12.000000', 'PENDIENTE', '2 semanas de trabajo ', 5000.00, 15, 21, NULL),
(23, '2026-05-09 22:46:39.000000', 'PENDIENTE', 'Espero detalles ', 50000.00, 18, 22, NULL),
(24, '2026-05-12 17:03:14.000000', 'ACEPTADA', '500 mil pesos por casa ', 500.00, 20, 23, NULL),
(25, '2026-05-12 19:31:46.000000', 'PENDIENTE', 'se termina en el dia ', 5000.00, 20, 25, NULL),
(26, '2026-05-12 22:46:00.000000', 'PENDIENTE', 'en un finde semana ', 20000.00, 20, 27, NULL),
(27, '2026-05-13 01:46:38.000000', 'PENDIENTE', 'Incluye mano de obra materiales a parte ,podemos charlarlo por mensaje para los materiales', 1500.00, 20, 26, 'USD'),
(28, '2026-05-13 01:52:24.000000', 'ACEPTADA', 'ok', 50.00, 20, 30, 'ARS'),
(29, '2026-05-13 01:58:19.000000', 'RECHAZADA', 'no incluye bronce', 80000.00, 15, 30, 'ARS'),
(30, '2026-05-13 14:51:30.000000', 'PENDIENTE', 'Sin materiales', 6000.00, 20, 29, 'USD'),
(31, '2026-05-13 16:52:53.000000', 'PENDIENTE', 'No incluye materiales', 60.00, 20, 28, 'USD');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensaje_chat`
--

DROP TABLE IF EXISTS `mensaje_chat`;
CREATE TABLE `mensaje_chat` (
  `id` bigint(20) NOT NULL,
  `contenido` varchar(1000) NOT NULL,
  `enviado_en` datetime(6) NOT NULL,
  `remitente_rol` varchar(20) NOT NULL,
  `remitente_id` bigint(20) NOT NULL,
  `trabajo_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mensaje_chat`
--

INSERT INTO `mensaje_chat` (`id`, `contenido`, `enviado_en`, `remitente_rol`, `remitente_id`, `trabajo_id`) VALUES
(1, 'Hola, ¿cuándo podrías venir a ver la obra?', '2025-11-06 01:21:23.000000', 'CLIENTE', 2, 3),
(2, 'Hola, puedo mañana a las 10:00.', '2025-11-06 01:22:46.000000', 'PROFESIONAL', 3, 3),
(3, 'mañana', '2025-11-06 01:46:43.000000', 'PROFESIONAL', 3, 3),
(4, 'hola como le va?', '2025-11-06 01:48:13.000000', 'PROFESIONAL', 3, 1),
(5, 'hola elsa como es la onda', '2025-11-06 02:35:18.000000', 'PROFESIONAL', 3, 6),
(6, 'la onda es facil llamame te paso mi telefono', '2025-11-06 02:38:33.000000', 'CLIENTE', 5, 6),
(7, 'oka paselo noma', '2025-11-06 02:38:54.000000', 'PROFESIONAL', 3, 6),
(8, 'perfecto', '2025-11-06 02:44:03.000000', 'CLIENTE', 2, 3),
(9, 'bien bien le paso detalles del trabajo', '2025-11-06 10:40:38.000000', 'CLIENTE', 2, 1),
(10, 'es para mañana', '2025-11-06 11:43:45.000000', 'CLIENTE', 2, 1),
(11, 'nv', '2025-11-06 12:29:23.000000', 'CLIENTE', 2, 3),
(12, 'listo terminado', '2025-11-06 12:29:56.000000', 'CLIENTE', 2, 3),
(13, 'hola', '2025-11-06 12:51:26.000000', 'CLIENTE', 2, 4),
(14, 'hola', '2025-11-06 12:55:30.000000', 'CLIENTE', 2, 5),
(15, 'hola lina', '2025-11-06 12:56:06.000000', 'PROFESIONAL', 3, 5),
(16, 'hola viejo choto otra vez vos?', '2025-11-06 13:32:20.000000', 'CLIENTE', 2, 7),
(17, 'jeje si ahi voy', '2025-11-06 13:32:55.000000', 'PROFESIONAL', 3, 7),
(18, 'hola viejo choto otra vez vos ?', '2025-11-06 16:40:07.000000', 'CLIENTE', 2, 8),
(19, 'jajaj', '2025-11-06 16:40:40.000000', 'PROFESIONAL', 3, 8),
(20, 'hola', '2025-11-07 01:45:45.000000', 'CLIENTE', 2, 9),
(21, 'hola', '2025-11-07 01:45:57.000000', 'PROFESIONAL', 3, 9),
(22, 'hola carlos', '2025-11-07 11:17:29.000000', 'CLIENTE', 2, 12),
(23, 'hola finalizamos el trabajo', '2025-11-07 11:19:51.000000', 'PROFESIONAL', 3, 12),
(24, 'ok', '2025-11-07 11:20:03.000000', 'CLIENTE', 2, 12),
(25, 'gracias', '2025-11-07 11:20:43.000000', 'CLIENTE', 2, 12),
(26, 'hola', '2025-11-07 11:21:21.000000', 'CLIENTE', 2, 11),
(27, 'finalizamos', '2025-11-07 11:22:44.000000', 'CLIENTE', 2, 11),
(28, 'hola alberto', '2025-11-07 11:40:13.000000', 'CLIENTE', 2, 13),
(29, 'hola', '2025-11-07 12:25:16.000000', 'CLIENTE', 2, 14),
(30, 'hola lo terminamos', '2025-11-07 12:25:46.000000', 'PROFESIONAL', 3, 14),
(31, 'hola', '2025-11-07 12:41:04.000000', 'CLIENTE', 2, 15),
(32, 'ya voy', '2025-11-07 12:41:17.000000', 'PROFESIONAL', 3, 15),
(33, 'hola acepte su cotizacion', '2025-11-07 13:02:02.000000', 'CLIENTE', 2, 16),
(34, 'genial', '2025-11-07 13:02:20.000000', 'PROFESIONAL', 3, 16),
(35, 'hola carlos a que hora puede venir?', '2025-11-07 15:46:01.000000', 'CLIENTE', 2, 18),
(36, 'a las 18', '2025-11-07 15:46:34.000000', 'PROFESIONAL', 3, 18),
(37, 'muchas gracias ya lo califico muy buen trabajo', '2025-11-07 15:46:56.000000', 'CLIENTE', 2, 18),
(38, 'hola empeza mañana', '2025-11-07 20:09:42.000000', 'CLIENTE', 2, 19),
(39, 'listo termine', '2025-11-07 20:09:55.000000', 'PROFESIONAL', 3, 19),
(40, 'hola cuando puede venir', '2025-11-08 13:45:49.000000', 'CLIENTE', 2, 20),
(41, 'mañana', '2025-11-08 13:46:06.000000', 'PROFESIONAL', 3, 20),
(42, 'hola seiya', '2026-05-12 18:49:53.000000', 'CLIENTE', 19, 23),
(43, 'hola saori', '2026-05-12 18:50:06.000000', 'PROFESIONAL', 20, 23),
(44, 'necesitas detalles del trabajo?', '2026-05-12 18:50:44.000000', 'CLIENTE', 19, 23),
(45, 'si por favor', '2026-05-12 18:50:55.000000', 'PROFESIONAL', 20, 23),
(46, 'hola seiya yo de nuevo', '2026-05-12 19:18:47.000000', 'CLIENTE', 19, 23),
(47, 'que haces atena me sacaste las medidas?', '2026-05-12 19:19:31.000000', 'PROFESIONAL', 20, 23),
(48, 'sisi ya las saque', '2026-05-12 19:22:55.000000', 'CLIENTE', 19, 23),
(49, 'te espero', '2026-05-12 19:48:20.000000', 'CLIENTE', 19, 23),
(50, 'llendo', '2026-05-12 19:48:56.000000', 'PROFESIONAL', 20, 23),
(51, 'ok', '2026-05-12 19:50:11.000000', 'PROFESIONAL', 20, 23),
(52, 'holaaa', '2026-05-13 16:46:57.000000', 'PROFESIONAL', 20, 30),
(53, 'hola seiyaa', '2026-05-13 16:47:28.000000', 'CLIENTE', 19, 30),
(54, 'que onda cuando arrancamos?', '2026-05-13 19:00:23.000000', 'PROFESIONAL', 20, 30);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

DROP TABLE IF EXISTS `persona`;
CREATE TABLE `persona` (
  `id` bigint(20) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `contraseña` varchar(255) DEFAULT NULL,
  `email` varchar(150) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `puntaje_promedio` double DEFAULT NULL,
  `telefono` varchar(30) DEFAULT NULL,
  `tipo_persona` enum('CLIENTE','PROFESIONAL') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`id`, `apellido`, `contraseña`, `email`, `nombre`, `puntaje_promedio`, `telefono`, `tipo_persona`) VALUES
(2, 'Betancourt', '$2a$10$i7nEuF.TOyFauUaHnp3akOyPxYBZ2IWO1afYwSePs6rWp2gC7mELm', 'lina@gmail.com', 'Lina', 5, '2964610238', 'CLIENTE'),
(3, 'de la Madrid', '$2a$10$NnjUB67f3B8hHivXTtY4VO/UmcQwgk.oSCZ0VmqmTQEOLuLhMN2Me', 'carlos@gmail.com', 'Carlos', 5, '2964458195', 'PROFESIONAL'),
(4, 'de la Madrid', '$2a$10$BSR8txcJqCKsIN8VYHg4GOURfHorbtdp7sizAMpxjUoE7z67/lBEC', 'dante@gmail.com', 'Dante', NULL, '2964422236', 'PROFESIONAL'),
(5, 'Alvez', '$2a$10$naXr2NQ/U9EMtmsMWJAw9.aQmiZUnhk436LGleVkNaM1GS42fS0Ae', 'elsa@gmail.com', 'Elsa', 5, '2964562188', 'CLIENTE'),
(6, 'Giorlando', '$2a$10$GqCFjrNJWX8VMxoWD6QlXOT6hRwpUlD1RZ5RpYG3daf1MOvKsqNYm', 'alberto@gmail.com', 'Alberto', NULL, '', 'PROFESIONAL'),
(7, 'de la Madrid', '$2a$10$zkYrjmqibISp9DpEZqe22.GREkg.QzveU7./NiusBbu5ZRfY1eaOq', 'paz@gmail.com', 'Maria Paz', NULL, '2964458195', 'CLIENTE'),
(8, 'de la madrid', '$2a$10$pWhu7DlgJ0c.5wQJ56bYLO5C/O6gu.e16Jj5Ygfy6xHJQIY7i0moC', 'albertocarlos@gmail.com', 'alberto carlos', 5, '02964458195', 'PROFESIONAL'),
(9, 'kakaroto', '$2a$10$irtv82NFzY9QwCLpcPUVp.f2qrwY1cq1gaDAi8MTlXEPNvT3pZxb6', 'goku@gmail.com', 'goku', 0, '2964989898', 'CLIENTE'),
(10, 'sayayins', '$2a$10$NlCuxNmvDDJ9v99569eypuEA23R0gYhTAHJIE6wfET83txWLSXBNu', 'vegetacrack@gmail.com', 'vegeta', 0, '444555', 'PROFESIONAL'),
(11, 'Dragon ball', '$2a$10$8ub.TuNueNCz4qt9JjSQmOtCrL3EpNu6pqKnszRY0zghhIMlHXcle', 'krilin@gmail.com', 'Krilin', 0, '123456', 'PROFESIONAL'),
(12, 'Dragon Ball', '$2a$10$7WkvpMnM12j/m3vny5uZ.OnsXsSXIp7ZY1rbtFoMvBQgaU6RP.Y9u', 'yamcha@gmail.com', 'Yamcha', 0, '123456', 'PROFESIONAL'),
(13, 'dai Macu', '$2a$10$4zEuBceejYHUzPXNhVdXfu4F9/LPUPctMYfJku3ycWnCygCadbdDC', 'piccolo@gmail.com', 'piccolo', 0, '2964458195', 'CLIENTE'),
(14, 'Sayayins', '$2a$10$FvmspXHsmDLHNM4cWa/xF.8kCWKwAV2UcI2Y1LCquOuXDwKAF/x.2', 'nappa@gmail.com', 'Nappa', 0, '2964458195', 'PROFESIONAL'),
(15, 'Hanma', '$2a$10$w6fCxJb7bIXZKTZZn3m8u.bLugHyzYwQ8nhCjXuau2FpPWU3VBqIu', 'baki@gmail.com', 'Baki', 0, '2964458195', 'PROFESIONAL'),
(16, 'Orochi', '$2a$10$KcVHSvhqD7OdZqfO6MY/O.yZyAlmpkwCqgvlGxGEaLZPkOd84vFOe', 'doppo@gmail.com', 'doppo', 0, '2964458195', 'CLIENTE'),
(17, 'Kaioh', '$2a$10$SvPHSj1PMi3g7AS8JtuHr.wOheFc67kzgW7h.hHsqtKs4NO4YSNQy', 'retsu@gmail.com', 'Retsu', 0, '2964458195', 'CLIENTE'),
(18, 'Orochi', '$2a$10$6N/P3o7ovEDpZYSNf2/jqu8tY5GOfdOAo2e3ym2DcpHfD9hecafhC', 'katsumi@gmail.com', 'Katsumi', 0, '2964458195', 'PROFESIONAL'),
(19, 'kurumada', '$2a$10$trBHLpkbH4R09owNLWKO2.rOaDoO22dfhmQtm2oZxyq2V7RmXSbIK', 'atena@gmail.com', 'atena', 5, '2964458195', 'CLIENTE'),
(20, 'bronce', '$2a$10$3W2tIsi6IEhFKL7LSkS9a.R.5YuF5WCDxyxOLkimri5D4kgzvetyi', 'seiya@gmail.com', 'seiya', 5, '2964458195', 'PROFESIONAL'),
(21, 'kurumada', '$2a$10$Th5eHW8WJO21he9dGQj4Ge1i6H2MQYAncurJ1SGu2soPrBnd9QB3.', 'marin@gmail.com', 'marin', 0, '2964458195', 'CLIENTE');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesional`
--

DROP TABLE IF EXISTS `profesional`;
CREATE TABLE `profesional` (
  `calificacion_promedio` double DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `especialidad` enum('ALBAÑIL','ELECTRICISTA','GASISTA','MAESTRO_MAYOR_OBRA','OTRO','PINTOR','PLOMERO') NOT NULL,
  `experiencia` varchar(500) DEFAULT NULL,
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `profesional`
--

INSERT INTO `profesional` (`calificacion_promedio`, `direccion`, `especialidad`, `experiencia`, `id`) VALUES
(0, 'esquiu 49', 'ELECTRICISTA', '5', 3),
(0, 'Echelaine 607', 'OTRO', '2', 4),
(0, '', 'MAESTRO_MAYOR_OBRA', '20', 6),
(0, 'esquiu 49', 'MAESTRO_MAYOR_OBRA', '5', 8),
(NULL, 'capsula corps 49', 'ELECTRICISTA', '5', 10),
(NULL, 'kame house 333', 'PINTOR', '10', 11),
(NULL, 'kame house 633', 'ELECTRICISTA', '1', 12),
(NULL, 'Kame House 49', 'PLOMERO', '10', 14),
(NULL, 'esquiu 49', 'GASISTA', '6', 15),
(NULL, 'Paso de los Andes 611', 'MAESTRO_MAYOR_OBRA', '7', 18),
(NULL, 'esquiu 49', 'MAESTRO_MAYOR_OBRA', '15', 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `trabajo`
--

DROP TABLE IF EXISTS `trabajo`;
CREATE TABLE `trabajo` (
  `id` bigint(20) NOT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT 1,
  `descripcion` varchar(1000) DEFAULT NULL,
  `estado` enum('BAJA','CANCELADO','EN_PROCESO','FINALIZADO','PUBLICADO') NOT NULL,
  `medidas` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `ubicacion` varchar(255) DEFAULT NULL,
  `cliente_id` bigint(20) NOT NULL,
  `profesional_asignado_id` bigint(20) DEFAULT NULL,
  `especialidad_requerida` enum('ALBAÑIL','ELECTRICISTA','GASISTA','MAESTRO_MAYOR_OBRA','OTRO','PINTOR','PLOMERO') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `trabajo`
--

INSERT INTO `trabajo` (`id`, `activo`, `descripcion`, `estado`, `medidas`, `titulo`, `ubicacion`, `cliente_id`, `profesional_asignado_id`, `especialidad_requerida`) VALUES
(1, 1, 'Colocacion en el techo de mi casa de antena', 'FINALIZADO', '3x5', 'Colocacion Antena Star Link', 'Esquiu 49', 2, 3, NULL),
(2, 1, 'Colocacion Antena Star Link en techo', 'BAJA', '3x5', 'Colocacion Antena Star Link 2', 'Esquiu 49', 2, NULL, NULL),
(3, 1, 'Colocacion Antena Star Link en techo', 'FINALIZADO', '3x5', 'Colocacion Antena Star Link 3', 'Esquiu 49', 2, 3, NULL),
(4, 1, 'cambiar tablero seccional de 3 circuitos.', 'FINALIZADO', '3x5', 'Cambio de tablero electrico', 'Esquiu 49', 2, 4, NULL),
(5, 1, 'techo de madera', 'FINALIZADO', '3x5', 'cambiar techo', 'esquiu 49', 2, 3, NULL),
(6, 1, 'Cambio de tablero de 3 circuitos', 'FINALIZADO', NULL, 'Cambio de tablero principal electrico', 'Barrio Aropuerto', 5, 3, NULL),
(7, 1, 'cambio de fuelle inodoro', 'FINALIZADO', '3x4', 'cambio de fuelle inodoro', 'esquiu 49', 2, 3, NULL),
(8, 1, 'poner una puerta', 'FINALIZADO', '3x4', 'Poner puerta', 'Esquiu 49', 2, 3, NULL),
(9, 1, 'poner enchufe', 'FINALIZADO', '3x5', 'poner enchufe', 'esquiu 49', 2, 3, NULL),
(10, 1, 'poner bacha', 'BAJA', '3x4', 'poner bacha', 'Esquiu 49', 2, NULL, NULL),
(11, 1, '5x5 pared de cemento', 'FINALIZADO', '3x5', 'cambio de pared', 'esquiu 49', 2, 3, NULL),
(12, 1, '3x4', 'FINALIZADO', '3x4', 'cambio de piso', 'esquiu 49', 2, 3, NULL),
(13, 1, 'cambio de luminaria', 'FINALIZADO', '3x4', 'cambio de luminaria', 'esquiu 49', 2, 8, NULL),
(14, 1, 'cambio de puerta', 'FINALIZADO', '3x4', 'cambio de puerta', 'esquiu 49', 2, 3, NULL),
(15, 1, 'Tablero principal de casa', 'FINALIZADO', '3x4', 'Cambio de tablero principal', 'esquiu 49', 2, 3, NULL),
(16, 1, '5 tomas corrientes', 'FINALIZADO', '5', 'Cambio de toma corriente', 'esquiu 49', 2, 3, NULL),
(17, 1, 'levantar pared medianera', 'BAJA', '10x10', 'construccion de pared', 'esquiu 49', 2, NULL, NULL),
(18, 1, 'hay que cambiar 1 canilla del baño', 'FINALIZADO', '4x4', 'cambiar la canilla de un baño', 'Rio Grande , esquiu 49', 2, 3, NULL),
(19, 1, 'cambiar de una pieza', 'FINALIZADO', '3x4', 'cambio de lampara', 'esquiu 49', 2, 3, NULL),
(20, 1, '1 techo de quincho', 'FINALIZADO', '5x5', 'cambio de  techo', 'rio grande', 2, 3, NULL),
(21, 1, 'Cambio de piso de cocina', 'PUBLICADO', '10x10', 'Cambio de piso', 'Esquiu 49', 16, NULL, NULL),
(22, 1, 'Remodelar el dojo ,piso nuevo y pintura detalles para charlar', 'PUBLICADO', '20x20 mts', 'Renovacion de dojo shinshi kai', 'Tokio-Japon', 17, NULL, NULL),
(23, 1, 'las 12 casas del santuario', 'FINALIZADO', '1000 x 1000', 'Mantenimiento', 'Grecia', 19, 20, NULL),
(24, 1, 'conexión eléctrica de la torre', 'BAJA', '10 mts de altura', 'Torre del reloj', 'Grecia', 19, NULL, NULL),
(25, 1, 'conexión eléctrica de la torre.', 'PUBLICADO', '10 mts de altura', 'torre del reloj', 'Grecia el santuario', 19, NULL, NULL),
(26, 1, 'Refaccionar la mansión kido', 'PUBLICADO', '30x30', 'Refaccion mansion kido', 'Japon tokio', 19, NULL, NULL),
(27, 1, 'Mantenimiento', 'PUBLICADO', '20x20', 'casa de las montañas', 'Japon', 19, NULL, NULL),
(28, 1, 'Cambio tablero eléctrico santuario', 'PUBLICADO', '1x1', 'cambio tablero electrico', 'grecia', 19, NULL, NULL),
(29, 1, 'Cambio de tablero electrico de mansion', 'PUBLICADO', '2x2', 'Cambio de tablero electrico', 'Tokio', 19, NULL, 'ELECTRICISTA'),
(30, 1, 'reparar 5 armaduras de bronce', 'EN_PROCESO', NULL, 'refaccion armaduras', 'tokio', 19, 20, 'PLOMERO');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `calificacion`
--
ALTER TABLE `calificacion`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_calificacion_autor_destinatario_trabajo` (`autor_id`,`destinatario_id`,`trabajo_id`),
  ADD KEY `FK4of00okebgk46a6deb0wtn8sj` (`destinatario_id`),
  ADD KEY `FKob65tc9s2jnwao7531qcyvo4e` (`trabajo_id`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `cotizacion`
--
ALTER TABLE `cotizacion`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_cotizacion_trabajo_profesional` (`trabajo_id`,`profesional_id`),
  ADD KEY `FKafoivg0dtqtv1jxkx854uwwep` (`profesional_id`);

--
-- Indices de la tabla `mensaje_chat`
--
ALTER TABLE `mensaje_chat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmk7ff1m1a6jf3k42hamviux8c` (`remitente_id`),
  ADD KEY `FK2lvkqn5mfbuyrormxptol38c2` (`trabajo_id`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKbfxfxg15pmy0c1imvi6ucoeem` (`email`);

--
-- Indices de la tabla `profesional`
--
ALTER TABLE `profesional`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `trabajo`
--
ALTER TABLE `trabajo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKaahirn16k45eobsr9vlwrp66a` (`cliente_id`),
  ADD KEY `FKc57kneqfpi1lmcwnt86oyjwu3` (`profesional_asignado_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `calificacion`
--
ALTER TABLE `calificacion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `cotizacion`
--
ALTER TABLE `cotizacion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT de la tabla `mensaje_chat`
--
ALTER TABLE `mensaje_chat`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `trabajo`
--
ALTER TABLE `trabajo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `calificacion`
--
ALTER TABLE `calificacion`
  ADD CONSTRAINT `FK4of00okebgk46a6deb0wtn8sj` FOREIGN KEY (`destinatario_id`) REFERENCES `persona` (`id`),
  ADD CONSTRAINT `FKky2vd9x5b8hj7jb4ajadi2l2h` FOREIGN KEY (`autor_id`) REFERENCES `persona` (`id`),
  ADD CONSTRAINT `FKob65tc9s2jnwao7531qcyvo4e` FOREIGN KEY (`trabajo_id`) REFERENCES `trabajo` (`id`);

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `FKkpvkbjg32bso6riqge70hwcel` FOREIGN KEY (`id`) REFERENCES `persona` (`id`);

--
-- Filtros para la tabla `cotizacion`
--
ALTER TABLE `cotizacion`
  ADD CONSTRAINT `FKafoivg0dtqtv1jxkx854uwwep` FOREIGN KEY (`profesional_id`) REFERENCES `profesional` (`id`),
  ADD CONSTRAINT `FKh43dpj9hcn3iybt73cuf1lxdu` FOREIGN KEY (`trabajo_id`) REFERENCES `trabajo` (`id`);

--
-- Filtros para la tabla `mensaje_chat`
--
ALTER TABLE `mensaje_chat`
  ADD CONSTRAINT `FK2lvkqn5mfbuyrormxptol38c2` FOREIGN KEY (`trabajo_id`) REFERENCES `trabajo` (`id`),
  ADD CONSTRAINT `FKmk7ff1m1a6jf3k42hamviux8c` FOREIGN KEY (`remitente_id`) REFERENCES `persona` (`id`);

--
-- Filtros para la tabla `profesional`
--
ALTER TABLE `profesional`
  ADD CONSTRAINT `FK7epsp7iy5f4xuk8odb3t6x69o` FOREIGN KEY (`id`) REFERENCES `persona` (`id`);

--
-- Filtros para la tabla `trabajo`
--
ALTER TABLE `trabajo`
  ADD CONSTRAINT `FKaahirn16k45eobsr9vlwrp66a` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  ADD CONSTRAINT `FKc57kneqfpi1lmcwnt86oyjwu3` FOREIGN KEY (`profesional_asignado_id`) REFERENCES `profesional` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
