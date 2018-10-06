INSERT INTO `user` (`userId`, `firstName`, `lastName`, `phone`, `email`, `username`, `password`) VALUES
(1, 'Tamer', 'ABDULGHANI', '0682197891', 'Tamer.abdulghani@gmail.com', 'tamer', 'tamer'),
(2, 'Rayhane', 'Belaroussi', '068012693', 'belarayhane@gmail.com', 'rayhane', 'rayhane'),
(3, 'Oluchi ', 'Onuorah', '0678321367', 'oluuchii@gmail.com', 'oluchi', 'oluchi'),
(4, 'Phuong ', 'Pham Kim', '0689431276', 'phamkimphuong.hcm@gmail.com', 'phuong', 'phuong'),
(5, 'Mikhail', 'Ryzhov', '0690431276', 'msryzhov@gmail.com', 'mikhail', 'mikhail'),
(6, 'shujun ', 'Hou', '0612743287', 'shujunhou99@gmail.com', 'shujun', 'shujun');

INSERT INTO `status` (`statusId`, `statusName`) VALUES
(1, 'Ready'),
(2, 'InProgress'),
(3, 'Reviewed');

INSERT INTO `file` (`fileId`, `fileName`, `extension`, `filePath`, `pushTime`, `userId`, `statusId`) VALUES
(1, 'Initializing spring boot maven', 'java', 'C:\\Users\\Documents\\GitHub\\software-quality-platform', '2018-10-06 08:30:24', 1, 3),
(2, 'logPage html', 'html', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', '2018-10-06 08:32:17', 2, 2),
(3, 'log control', 'java', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', '2018-10-06 12:00:00', 4, 2);

INSERT INTO `annotation` (`annotationId`, `lineNb`, `comment`, `time`, `isResolved`, `userId`, `fileId`) VALUES
(1, 45, 'function not work', '2018-10-01 14:00:00', 1, 3, 1),
(2, 108, 'too complicated', '2018-10-01 14:38:00', 1, 3, 1);

INSERT INTO `filelog` (`userId`, `fileId`, `accessed`, `reviewed`) VALUES
(3, 1, '2018-10-01 09:00:00', '2018-10-01 13:00:00');

