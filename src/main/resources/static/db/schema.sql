/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50638
 Source Host           : localhost:3306
 Source Schema         : MyDB

 Target Server Type    : MySQL
 Target Server Version : 50638
 File Encoding         : 65001

 Date: 28/11/2017 20:48:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for countries
-- ----------------------------
DROP TABLE IF EXISTS `countries`;
CREATE TABLE `countries` (
  `country_code` varchar(10) NOT NULL,
  `country_name` varchar(100) NOT NULL,
  PRIMARY KEY (`country_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for courses
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses` (
  `course_id` int(11) NOT NULL,
  `course_name` varchar(100) NOT NULL,
  `school` int(11) NOT NULL COMMENT 'foreign key to schools_id in table schools',
  `syllabus` int(11) DEFAULT NULL,
  `professor` varchar(20) DEFAULT NULL,
  `course_code` varchar(20) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `website` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  KEY `fk_school_id` (`school`),
  KEY `fk_syllabus` (`syllabus`),
  CONSTRAINT `fk_school_id` FOREIGN KEY (`school`) REFERENCES `schools` (`school_id`),
  CONSTRAINT `fk_syllabus` FOREIGN KEY (`syllabus`) REFERENCES `syllabus` (`syllabus_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for schools
-- ----------------------------
DROP TABLE IF EXISTS `schools`;
CREATE TABLE `schools` (
  `school_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'school identifier',
  `school_name` varchar(100) NOT NULL COMMENT 'school name',
  `course_tree_path` varchar(100) DEFAULT NULL,
  `country` varchar(11) DEFAULT NULL,
  `website` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for syllabus
-- ----------------------------
DROP TABLE IF EXISTS `syllabus`;
CREATE TABLE `syllabus` (
  `syllabus_id` int(11) NOT NULL,
  `author` int(11) NOT NULL COMMENT 'foreign key to user_id',
  `last_modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `file_path` varchar(100) NOT NULL,
  PRIMARY KEY (`syllabus_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET latin1 NOT NULL,
  `password` varchar(100) CHARACTER SET latin1 NOT NULL COMMENT 'Only stores the checksum of the password',
  `email` varchar(45) CHARACTER SET latin1 NOT NULL COMMENT 'User email',
  `role_id` int(11) NOT NULL DEFAULT '2',
  `school` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_idx` (`name`),
  KEY `name` (`name`,`password`),
  KEY `name_2` (`name`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  KEY `fk_school` (`school`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `fk_school` FOREIGN KEY (`school`) REFERENCES `schools` (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
