-- MySQL dump 10.13  Distrib 5.7.20, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courses` (
  `courseid` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(45) NOT NULL,
  `course_code` varchar(45) NOT NULL,
  `school` int(11) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `prof` varchar(45) DEFAULT NULL,
  `last_modify` varchar(45) DEFAULT NULL,
  `author` int(11) DEFAULT NULL,
  `syllabuspath` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`courseid`),
  KEY `courses_schools_idx` (`school`),
  KEY `courses_users_idx` (`author`),
  CONSTRAINT `FK29jax68kfe82aen41k2janajf` FOREIGN KEY (`author`) REFERENCES `users` (`id`),
  CONSTRAINT `FK8txbq8on0io31c4aye0ixkq98` FOREIGN KEY (`school`) REFERENCES `schools` (`schoolid`),
  CONSTRAINT `courses_schools` FOREIGN KEY (`school`) REFERENCES `schools` (`schoolid`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `courses_users` FOREIGN KEY (`author`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schools`
--

DROP TABLE IF EXISTS `schools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schools` (
  `schoolid` int(11) NOT NULL AUTO_INCREMENT,
  `school_name` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `manager` int(11) DEFAULT NULL,
  `website` varchar(45) DEFAULT NULL,
  `tree_path` varchar(45) CHARACTER SET big5 DEFAULT NULL,
  PRIMARY KEY (`schoolid`),
  KEY `school_users_idx` (`manager`),
  CONSTRAINT `FK5fdys28aevrh10pf7f5vlwvqd` FOREIGN KEY (`manager`) REFERENCES `users` (`id`),
  CONSTRAINT `school_users` FOREIGN KEY (`manager`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schools`
--

LOCK TABLES `schools` WRITE;
/*!40000 ALTER TABLE `schools` DISABLE KEYS */;
INSERT INTO `schools` VALUES (4,'SUSTech','Best school in the world!',NULL,'sustc.edu.cn',NULL);
/*!40000 ALTER TABLE `schools` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET latin1 NOT NULL,
  `password` varchar(100) CHARACTER SET latin1 NOT NULL COMMENT 'Only stores the checksum of the password',
  `email` varchar(45) CHARACTER SET latin1 NOT NULL COMMENT 'User email',
  `role_id` int(11) NOT NULL DEFAULT '2',
  `schoolid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_idx` (`name`),
  KEY `name` (`name`,`password`),
  KEY `name_2` (`name`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  KEY `FK4tx6k55g3yfnvdfuo54guxjw5` (`schoolid`),
  CONSTRAINT `FK4tx6k55g3yfnvdfuo54guxjw5` FOREIGN KEY (`schoolid`) REFERENCES `schools` (`schoolid`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (15,'admin','$2a$10$rvqFyLWPR4A6hMo5hAAgJeuYUB.leooCfvIycWvfqfsMuPAJMMMNG','wanggy97@gmail.com',1,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-12 21:26:37
