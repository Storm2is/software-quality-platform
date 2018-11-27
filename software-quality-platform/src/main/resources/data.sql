INSERT INTO `user` (`userId`, `firstName`, `lastName`, `phone`, `email`, `username`) VALUES
(1, 'Tamer', 'ABDULGHANI', '0682197891', 'Tamer.abdulghani@gmail.com', 'Tamer'),
(2, 'Rayhane', 'Belaroussi', '068012693', 'belarayhane@gmail.com', 'Rayhane'),
(3, 'Oluchi ', 'Onuorah', '0678321367', 'oluuchii@gmail.com', 'Oluchi'),
(4, 'Phuong ', 'Pham Kim', '0689431276', 'phamkimphuong.hcm@gmail.com', 'Phuong'),
(5, 'Mikhail', 'Ryzhov', '0690431276', 'msryzhov@gmail.com', 'Mikhail'),
(6, 'Shujun ', 'Hou', '0612743287', 'shujunhou99@gmail.com', 'Shujun'),
(7, 'Collective ', 'Reviewer', '0123456789', 'collective_review@gmail.com', 'Collective Reviewer');


INSERT INTO `status` (`statusId`, `statusName`) VALUES
(1, 'Ready'),
(2, 'InProgress'),
(3, 'Validated'),
(4, 'Reviewed');

INSERT INTO `file` (`fileId`, `fileName`, `extension`, `filePath`, `fileLength`, `tags`, `pushTime`, `userId`, `statusId`) VALUES 
(1, 'Initializing spring boot maven', 'java', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\src', 100, '#java, #mvc, #spring', '2018-10-06 08:30:24', 1, 3),
(2, 'logPage html', 'html', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 68, '#html, #javascript, #php', '2018-10-06 08:32:17', 2, 2),
(3, 'log control', 'java', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 54, '#java, #hashmap, #maven', '2018-10-06 12:00:00', 4, 4),
(4, 'Add validate button', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\src', 10, '#html,#javascript', '2018-10-09 10:30:24', 3, 3),
(5, 'Annotation page JS/CSS', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 68, '#javascript, #css', '2018-10-11 07:32:17', 5, 2),
(6, 'Display annotated file', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 12, '#html,#javascript, #css,#java', '2018-10-14 08:30:00', 2, 2),
(7, 'Review Page CSS', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 40, '#css', '2018-10-14 09:25:00', 6, 3),
(8, 'Review page backend', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 85, '#java', '2018-10-15 12:08:00', 1, 4),
(9, 'Modify CSS ', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 25, '#css', '2018-10-17 15:00:00', 4, 3),
(10, 'Update Database', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 15, '#javascript', '2018-10-20 13:40:00', 3, 4),
(11, 'Notify file owner', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 73, '#java', '2018-10-22 16:08:06', 5, 2),
(12, 'Create annotation page', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 85, '#html,#javascript', '2018-10-23 15:30:50', 6, 4),
(13, 'Select File to review ', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 46, '#html,#javascript, #css,', '2018-10-23 16:40:20', 2, 2),
(14, 'Fix Architecture ', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 36, '#java, #maven', '2018-10-24 11:23:50', 2, 3),
(15, 'Entity binding', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 46, '#java, #maven', '2018-10-25 14:56:13', 5, 3),
(16, 'Listting files integration', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 25, '#html,#javascript, #css,#java', '2018-10-26 10:05:40', 4, 2),
(17, 'Create html page for listing files', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src',86, '#html', '2018-10-28 16:09:15', 6, 2),
(18, 'Create controller for listing files ', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src',93, '#java, #hashmap, #maven', '2018-11-03 10:25:00', 3, 2),
(19, 'Create html page for pushing code', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src',71, '#html', '2018-11-03 11:41:20', 2, 2),
(20, 'Create controller for handling uploaded files', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 54, '#java, #hashmap, #maven', '2018-11-04 15:25:20', 1, 2),
(21, 'Add review button to NavBar', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 10, '#html,#javascript, #css', '2018-11-05 16:41:00', 5, 2),
(22, 'Notification service', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src',68, '#java, #maven', '2018-11-06 16:53:05', 4, 2),
(23, 'Store nb of lines', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 18, '#java', '2018-11-07 14:55:25', 3, 2),
(24, 'Fix Login Process ', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 81, '#java,#html,#javascript', '2018-11-07 09:50:00', 1, 2),
(25, 'update login controller', 'txt', 'C:\\Users\\Documents\\GitHub\\software-quality-platform\\software-quality-platform\\src', 48, '#html,#javascript, #css', '2018-11-08 10:00:00', 3, 2);

INSERT INTO `annotation` (`annotationId`, `lineNb`, `comment`, `time`, `isResolved`, `userId`, `fileId`) VALUES
(1, 14, 'no password', '2018-10-06 14:00:00', 1, 6, 2),
(2, 56, 'add collictive', '2018-10-06 14:38:00', 1, 6, 2),
(3, 36, 'log in is not conected', '2018-10-07 09:45:00', 1, 2, 3),
(4, 26, 'wrong method', '2018-10-11 14:26:08', 1, 3, 5),
(5, 59, 'cannot review ananotation', '2018-10-11 14:59:30', 1, 3, 5),
(6, 15, 'button is oversize', '2018-10-14 16:32:56', 1, 6, 7),
(7, 40, 'other reviewers is not locked after the file is chosen', '2018-10-16 11:00:00', 1, 5, 8),
(8, 6, 'not fit the type', '2018-10-20 15:20:00', 1, 4, 10),
(9, 36, 'wrong link', '2018-10-23 09:42:25', 1, 1, 11),
(10, 56, 'server not allowed', '2018-10-23 10:56:00', 1, 1, 11),
(11, 18, 'call controller', '2018-10-26 14:08:00', 1, 5, 12),
(12, 69, 'files not be orderd', '2018-10-23 16:09:10', 1, 6, 13),
(13, 33, 'need line number', '2018-10-26 16:20:00', 1, 5, 16),
(14, 35, 'wrong button button id', '2018-10-26 16:56:00', 1, 5, 16),
(15, 43, 'need footer', '2018-10-29 09:20:00', 1, 4, 17),
(16, 26, 'import image helper', '2018-10-29 09:10:00', 1, 2, 15),
(17, 25, 'need get method', '2018-11-03 14:00:00', 1, 1, 18),
(18, 86, 'useful function', '2018-11-03 15:06:00', 1, 1, 18),
(19, 6, 'code be pushed is messy code', '2018-10-03 13:59:00', 1, 4, 19),
(20, 10, 'function not work', '2018-11-05 10:00:00', 1, 6, 20),
(21, 45, 'need add tags', '2018-11-05 11:40:00', 1, 6, 20),
(22, 09, 'link not works', '2018-11-06 15:16:40', 1, 3, 21),
(23, 15, 'store wrong place', '2018-11-06 14:58:50', 1, 2, 23),
(24, 56, 'cannot click', '2018-11-07 15:00:00', 0, 4, 24),
(25, 35, 'cannot be connected', '2018-11-08 14:26:32', 1, 6, 25);

INSERT INTO `point` (`userId`, `value`) VALUES 
(1, 110), 
(2, 150),
(3, 233),
(4, 245),
(5, 270),
(6, 360),
(7, 0);

INSERT INTO `sprint` (`id`,`name`, `start`,`end`,`goal`) VALUES 
(1,'Sprint 1', '2018-10-01 00:00:00','2018-10-14 00:00:00',60),
(2,'Sprint 2','2018-10-15 00:00:00','2018-10-28 00:00:00',50),
(3,'Sprint 3','2018-10-29 00:00:00','2018-11-11 00:00:00',50),
(4,'Sprint 4','2018-11-12 00:00:00','2018-11-25 00:00:00',60),
(5,'Sprint 5','2018-11-26 00:00:00','2018-12-09 00:00:00',80),
(6,'Sprint 6','2018-12-10 00:00:00','2018-12-23 00:00:00',100);

INSERT INTO `quality` (`qualityId`, `sprintId`,`label`,`value`) VALUES 
(1,1,'good',10),
(2,1,'medium',15),
(3,1,'bad',75),
(4,2,'good',30),
(5,2,'medium',25),
(6,2,'bad',45),
(7,3,'good',50),
(8,3,'medium',15),
(9,3,'bad',35),
(10,4,'good',60),
(11,4,'medium',25),
(12,4,'bad',15),
(13,5,'good',70),
(14,5,'medium',15),
(15,5,'bad',15),
(16,6,'good',80),
(17,6,'medium',15),
(18,6,'bad',5)
;