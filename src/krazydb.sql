-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 22, 2025 at 06:01 PM
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
-- Database: `hoteldb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `adminID` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`adminID`, `username`, `password`, `firstName`, `lastName`) VALUES
(1, 'yan', '12345678', 'yen', 'yun');

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `userID` int(11) NOT NULL,
  `paymentID` int(11) NOT NULL,
  `reservationID` int(11) DEFAULT NULL,
  `paymentDate` date NOT NULL,
  `amountPaid` decimal(10,2) NOT NULL,
  `status` varchar(20) DEFAULT 'Paid',
  `paymentMethod` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payments`
--

INSERT INTO `payments` (`userID`, `paymentID`, `reservationID`, `paymentDate`, `amountPaid`, `status`, `paymentMethod`) VALUES
(2, 1, NULL, '2025-04-22', 4.00, 'Paid', 'GCash'),
(2, 2, 37, '2025-04-22', 4.00, 'Paid', 'GCash'),
(3, 3, 38, '2025-04-22', 9.00, 'Paid', 'GCash'),
(2, 4, 39, '2025-04-22', 5.00, 'Paid', 'GCash'),
(1, 5, 40, '2025-04-22', 9.00, 'Paid', 'GCash'),
(2, 6, 43, '2025-04-22', 1.00, 'Paid', 'GCash'),
(2, 7, 44, '2025-04-22', 8.00, 'Paid', 'GCash');

-- --------------------------------------------------------

--
-- Table structure for table `reservationdetails`
--

CREATE TABLE `reservationdetails` (
  `detailID` int(11) NOT NULL,
  `reservationID` int(11) NOT NULL,
  `roomID` int(11) NOT NULL,
  `amount` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservationdetails`
--

INSERT INTO `reservationdetails` (`detailID`, `reservationID`, `roomID`, `amount`) VALUES
(12, 5, 5, 1.00),
(13, 6, 5, 10.00),
(21, 14, 8, 12.00),
(26, 19, 8, 6.00),
(41, 34, 5, 1.00),
(42, 35, 6, 5.00),
(43, 36, 7, 4.00),
(44, 37, 7, 4.00),
(45, 38, 9, 9.00),
(46, 39, 6, 5.00),
(47, 40, 9, 9.00),
(49, 42, 9, 9.00),
(50, 43, 5, 1.00),
(51, 44, 5, 8.00);

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `reservationID` int(11) NOT NULL,
  `reservationNo` int(11) DEFAULT NULL,
  `userID` int(11) NOT NULL,
  `checkIn` date NOT NULL,
  `checkOut` date NOT NULL,
  `status` varchar(20) DEFAULT 'Pending',
  `roomTypeID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservations`
--

INSERT INTO `reservations` (`reservationID`, `reservationNo`, `userID`, `checkIn`, `checkOut`, `status`, `roomTypeID`) VALUES
(5, 2, 1, '2025-04-25', '2025-04-26', 'Approved', 5),
(6, 3, 2, '2025-04-30', '2025-05-02', 'Approved', 6),
(14, 3, 1, '2025-04-28', '2025-04-30', 'Approved', 8),
(19, 1, 3, '2025-04-21', '2025-04-22', 'Approved', 8),
(34, 2, 2, '2025-04-22', '2025-04-23', 'Approved', 5),
(35, 3, 2, '2025-04-22', '2025-04-23', 'Approved', 6),
(36, 4, 2, '2025-04-22', '2025-04-23', 'Approved', 7),
(37, 5, 2, '2025-04-24', '2025-04-25', 'Approved', 7),
(38, 2, 3, '2025-04-22', '2025-04-23', 'Approved', 9),
(39, 6, 2, '2025-04-29', '2025-04-30', 'Approved', 6),
(40, 3, 1, '2025-04-24', '2025-04-25', 'Approved', 9),
(42, 7, 2, '2025-05-21', '2025-05-22', 'Approved', 9),
(43, 8, 2, '2025-06-01', '2025-06-02', 'Approved', 5),
(44, 9, 2, '2025-09-02', '2025-09-10', 'Approved', 5);

-- --------------------------------------------------------

--
-- Table structure for table `reservation_history`
--

CREATE TABLE `reservation_history` (
  `reservationID` int(11) NOT NULL,
  `reservationNo` varchar(50) DEFAULT NULL,
  `userID` varchar(50) DEFAULT NULL,
  `checkIn` date DEFAULT NULL,
  `checkOut` date DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `roomTypeID` varchar(50) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `historyID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservation_history`
--

INSERT INTO `reservation_history` (`reservationID`, `reservationNo`, `userID`, `checkIn`, `checkOut`, `status`, `roomTypeID`, `amount`, `historyID`) VALUES
(3, '1', '1', '2025-04-19', '2025-04-20', 'Approved', '6', 5.00, 4),
(15, '1', '3', '2025-04-19', '2025-04-20', 'Approved', '8', 6.00, 5),
(4, '2', '2', '2025-04-20', '2025-04-21', 'Approved', '5', 1.00, 6),
(17, '3', '2', '2025-04-20', '2025-04-21', 'Approved', '7', 4.00, 7);

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `roomID` int(11) NOT NULL,
  `roomNumber` varchar(10) NOT NULL,
  `roomTypeID` int(11) NOT NULL,
  `roomStatus` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`roomID`, `roomNumber`, `roomTypeID`, `roomStatus`) VALUES
(5, '1', 5, 'Occupied'),
(6, '2', 6, 'Occupied'),
(7, '3', 7, 'Occupied'),
(8, '4', 8, 'Occupied'),
(9, '5', 9, 'Occupied');

-- --------------------------------------------------------

--
-- Table structure for table `roomtypes`
--

CREATE TABLE `roomtypes` (
  `roomTypeID` int(11) NOT NULL,
  `type` varchar(20) NOT NULL,
  `occupancy` varchar(20) NOT NULL,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roomtypes`
--

INSERT INTO `roomtypes` (`roomTypeID`, `type`, `occupancy`, `price`) VALUES
(5, 'AC', 'Single', 1.00),
(6, 'NON/AC', 'Single', 5.00),
(7, 'AC', 'Double', 4.00),
(8, 'NON/AC', 'Single', 6.00),
(9, 'AC', 'Single', 9.00);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `gender` varchar(10) DEFAULT NULL CHECK (`gender` in ('Male','Female')),
  `securityQuestion` varchar(255) NOT NULL DEFAULT '',
  `securityAnswer` varchar(255) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userID`, `username`, `password`, `firstName`, `lastName`, `gender`, `securityQuestion`, `securityAnswer`) VALUES
(1, 'hayian', '12345678', 'Rias', 'Gremory', 'Female', 'gwapo ko?', 'oo'),
(2, 'yen', '12345678', 'a', 'v', 'Female', 'Krazy Rapid Boots\r\n', 'haha'),
(3, 'miku', '12345678', 'ichika', 'nakano', 'Female', 'What was the name of your first pet?', 'aron');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`adminID`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`paymentID`),
  ADD KEY `fk_reservationID_new` (`reservationID`),
  ADD KEY `fk_userID` (`userID`);

--
-- Indexes for table `reservationdetails`
--
ALTER TABLE `reservationdetails`
  ADD PRIMARY KEY (`detailID`),
  ADD KEY `roomID` (`roomID`),
  ADD KEY `fk_reservationID` (`reservationID`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`reservationID`),
  ADD KEY `userID` (`userID`),
  ADD KEY `fk_roomTypeID` (`roomTypeID`);

--
-- Indexes for table `reservation_history`
--
ALTER TABLE `reservation_history`
  ADD PRIMARY KEY (`historyID`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`roomID`),
  ADD UNIQUE KEY `roomNumber` (`roomNumber`),
  ADD KEY `roomTypeID` (`roomTypeID`);

--
-- Indexes for table `roomtypes`
--
ALTER TABLE `roomtypes`
  ADD PRIMARY KEY (`roomTypeID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `adminID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `paymentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `reservationdetails`
--
ALTER TABLE `reservationdetails`
  MODIFY `detailID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `reservationID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `reservation_history`
--
ALTER TABLE `reservation_history`
  MODIFY `historyID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `rooms`
--
ALTER TABLE `rooms`
  MODIFY `roomID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `roomtypes`
--
ALTER TABLE `roomtypes`
  MODIFY `roomTypeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `fk_reservationID_new` FOREIGN KEY (`reservationID`) REFERENCES `reservations` (`reservationID`),
  ADD CONSTRAINT `fk_userID` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE;

--
-- Constraints for table `reservationdetails`
--
ALTER TABLE `reservationdetails`
  ADD CONSTRAINT `fk_reservationID` FOREIGN KEY (`reservationID`) REFERENCES `reservations` (`reservationID`) ON DELETE CASCADE,
  ADD CONSTRAINT `reservationdetails_ibfk_2` FOREIGN KEY (`roomID`) REFERENCES `rooms` (`roomID`);

--
-- Constraints for table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `fk_roomType` FOREIGN KEY (`roomTypeID`) REFERENCES `roomtypes` (`roomTypeID`),
  ADD CONSTRAINT `fk_roomTypeID` FOREIGN KEY (`roomTypeID`) REFERENCES `roomtypes` (`roomTypeID`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`);

--
-- Constraints for table `rooms`
--
ALTER TABLE `rooms`
  ADD CONSTRAINT `rooms_ibfk_1` FOREIGN KEY (`roomTypeID`) REFERENCES `roomtypes` (`roomTypeID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
