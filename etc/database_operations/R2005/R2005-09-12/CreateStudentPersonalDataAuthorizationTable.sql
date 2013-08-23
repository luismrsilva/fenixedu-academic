CREATE TABLE `STUDENT_PERSONAL_DATA_AUTHORIZATION` (
  `ID_INTERNAL` int(11) NOT NULL auto_increment,
  `ACK_OPT_LOCK` int(11) default NULL,
  `ANSWER` varchar(50) NOT NULL default '',
  `KEY_EXECUTION_YEAR` int(11) NOT NULL,
  `KEY_STUDENT` int(11) NOT NULL,
  PRIMARY KEY  (`ID_INTERNAL`),
  UNIQUE KEY `U1` (`KEY_STUDENT`,`KEY_EXECUTION_YEAR`)
) Type=InnoDB;