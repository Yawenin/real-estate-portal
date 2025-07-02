-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 02, 2025 at 08:15 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `realestate_portal`
--

-- --------------------------------------------------------

--
-- Table structure for table `listings`
--

CREATE TABLE `listings` (
  `id` bigint(20) NOT NULL,
  `area` double DEFAULT NULL,
  `bathrooms` int(11) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `listing_type` enum('SALE','RENT') DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `property_type` enum('APARTMENT','HOUSE','CONDO','TOWNHOUSE','LAND') DEFAULT NULL,
  `rooms` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `listings`
--

INSERT INTO `listings` (`id`, `area`, `bathrooms`, `created_at`, `description`, `image_url`, `listing_type`, `location`, `price`, `property_type`, `rooms`, `title`, `updated_at`, `user_id`) VALUES
(5, 75.5, 1, '2025-06-20 17:57:38.000000', 'Mieszkanie na sprzedaż', '/uploads/05d1247a-3a3b-4f08-b2e1-4450bf1ab95b.jpg', 'SALE', 'Warszawa Śródmieście', 500000.00, 'APARTMENT', 3, 'Mieszkanie 3 pokojowe', '2025-06-20 17:57:38.000000', 1),
(6, 63, 1, '2025-06-20 18:31:36.000000', 'dagdsghdasgh', NULL, 'RENT', 'gdsagasg', 543463.00, 'CONDO', 3, 'fxhgsdfgh', '2025-06-20 18:31:36.000000', 1),
(7, 25, NULL, '2025-06-27 14:03:01.000000', 'wynajem mieszkania 25 m2', 'https://i.dobrzemieszkaj.pl/i/84/15/69/r3/1920/male-mieszkanie-w-stylu-japandi-piekne-wnetrze-w-bezach-i-szarosciach.jpg', 'RENT', 'Warszawa Śródmieście', 5000.00, 'APARTMENT', 2, 'Mieszkanie 2 pokojowe', '2025-06-27 14:03:01.000000', 1),
(8, 67, 1, '2025-07-02 18:10:17.000000', 'Mieszkanie 5 pokojowe', 'https://img.shmbk.pl/rimgsph/39548_27a5085a-44af-4b38-984e-722f7461c988_crop_1280_768_zdjecie-salon-styl-vintage.jpg', 'RENT', 'Gdańsk Centrum', 5000.00, 'APARTMENT', 5, 'Mieszkanie 5 pokojowe', '2025-07-02 18:10:17.000000', 2),
(9, 67.6, 1, '2025-07-02 18:11:03.000000', 'Mieszkanie 4 pokojowe', 'https://img.shmbk.pl/rimgsph/2531_56ff12c6-6790-4406-96e2-5d5723cb0d65_max_900_1200_zdjecie-salon-styl-nowoczesny.jpg', 'SALE', 'Rybnik Centrum', 500000.00, 'APARTMENT', 4, 'Mieszkanie 4 pokojowe', '2025-07-02 18:11:03.000000', 2),
(10, 89.4, 2, '2025-07-02 18:12:10.000000', 'Dom w Mikołowie', 'https://www.livehome3d.com/assets/img/social/how-to-design-a-house.jpg', 'SALE', 'Mikołów', 700000.00, 'HOUSE', 7, 'Dom 89m', '2025-07-02 18:12:10.000000', 2);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `created_at`, `email`, `first_name`, `last_name`, `password`, `phone`) VALUES
(1, '2025-06-20 17:08:48.000000', 'Jankowalski@mail.com', 'Jan', 'Kowalski', '$2a$10$DeI7vERsomZicVwCqENNEeGI7oTUgFahT.Ww7gywrX1NKbEKA4N3m', '123456789'),
(2, '2025-07-02 18:08:40.000000', 'kowalska@mail.com', 'Ewa', 'Kowalska', '$2a$10$FV1jsDVvB/3onNKmlGrSf.gnrRgo6zvZ.RtTNYIUbt7oEInKSNtIm', '987654321');

-- --------------------------------------------------------

--
-- Table structure for table `users_roles`
--

CREATE TABLE `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `listings`
--
ALTER TABLE `listings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKb5kgkbggc40jyxeq28onih2x1` (`user_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- Indexes for table `users_roles`
--
ALTER TABLE `users_roles`
  ADD KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  ADD KEY `FK2o0jvgh89lemvvo17cbqvdxaa` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `listings`
--
ALTER TABLE `listings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `listings`
--
ALTER TABLE `listings`
  ADD CONSTRAINT `FKb5kgkbggc40jyxeq28onih2x1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
