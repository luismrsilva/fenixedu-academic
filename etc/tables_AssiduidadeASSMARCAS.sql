/*
Mascon Dump
Source Host:           localhost
Source Server Version: 3.23.49-max-nt
Source Database:       AssiduidadeOracle
Date:                  2003-03-20 18:20:46
*/
#----------------------------
# Table structure for ass_marcas
#----------------------------
drop table if exists ass_marcas;
create table ass_marcas (
   ASS_MARPESSOA int(10),
   ASS_MARCARTAO int(10),
   ASS_MARDHMARCA datetime,
   ASS_MARUNID varchar(8),
   ASS_MARTIPO char(1),
   ASS_MARAUTOJUST char(1),
   ASS_MARREGUL char(1),
   ASS_MARIES char(1),
   ASS_MARSTAT char(1),
   ASS_MARSEQ int(10) not null default '0',
   ASS_MARWHEN datetime,
   ASS_MARWHO int(10),
   primary key (ASS_MARSEQ))
   type=InnoDB comment="InnoDB free: 378880 kB";

#----------------------------
# Records for table ass_marcas
#----------------------------
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-059008898 08:30:00','TM_MEST','N','N','S','N','V',1361286,'2003-02-26 16:02:25',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-059008898 10:30:00','TM_MEST','N','N','S','N','V',1361287,'2003-05-26 16:02:25',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-059008898 11:20:00','TM_MEST','N','N','S','N','V',1361288,'2003-05-26 16:02:25',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-059008898 13:00:00','TM_MEST','N','N','S','N','V',1361289,'2003-05-26 16:02:25',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-059008898 13:30:00','TM_MEST','N','N','S','N','V',1361290,'2003-05-26 16:03:42',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-059008898 16:00:00','TM_MEST','N','N','S','N','V',1361291,'2003-05-26 16:03:42',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-059008899 08:00:00','TM_MEST','N','N','S','N','V',1361294,'2003-05-26 16:07:39',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-059008899 11:00:00','TM_MEST','N','N','S','N','V',1361295,'2003-05-26 16:07:39',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-059008899 13:00:00','TM_MEST','N','N','S','N','V',1361296,'2003-05-26 16:07:40',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-059008899 17:00:00','TM_MEST','N','N','S','N','V',1361297,'2003-05-26 16:07:40',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-20 09:00:00','TM_MEST','N','N','S','N','V',1361298,'2003-05-26 16:08:14',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-20 11:00:00','TM_MEST','N','N','S','N','V',1361299,'2003-05-26 16:08:15',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-20 12:00:00','TM_MEST','N','N','S','N','V',1361300,'2003-05-26 16:08:15',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-20 17:00:00','TM_MEST','N','N','S','N','V',1361301,'2003-05-26 16:08:15',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-20 12:30:00',null,'N','N','S','N','V',1361331,'2003-05-26 15:29:18',2751);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-20 13:00:00',null,'N','N','S','N','V',1361332,'2003-05-26 15:29:18',2751);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-27 12:24:57','TM-CENTR','N','N','N','N','V',1362536,null,0);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-27 14:18:25','TM-CENTR','N','N','N','N','V',1362988,null,0);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-21 08:00:00','TM_MEST','N','N','S','N','V',1363157,'2003-05-27 17:37:39',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-21 10:00:00','TM_MEST','N','N','S','N','V',1363158,'2003-05-27 17:37:39',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-21 12:00:00','TM_MEST','N','N','S','N','V',1363159,'2003-05-27 17:37:39',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-21 13:30:00','TM_MEST','N','N','S','N','V',1363160,'2003-05-27 17:37:39',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-21 14:00:00','TM_MEST','N','N','S','N','V',1363161,'2003-05-27 17:37:59',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-21 17:00:00','TM_MEST','N','N','S','N','V',1363162,'2003-05-27 17:38:00',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-27 14:45:00','TM_MEST','N','N','S','N','V',1363164,'2003-05-27 17:39:30',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-27 17:00:00','TM_MEST','N','N','S','N','V',1363165,'2003-05-27 17:39:31',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-26 09:00:00','TM_MEST','N','N','S','N','V',1363177,'2003-05-27 17:45:53',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-26 12:30:00','TM_MEST','N','N','S','N','V',1363178,'2003-05-27 17:45:53',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-26 13:00:00','TM_MEST','N','N','S','N','V',1363179,'2003-05-27 17:45:53',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-26 13:30:00','TM_MEST','N','N','S','N','V',1363180,'2003-05-27 17:45:53',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-26 13:45:00','TM_MEST','N','N','S','N','V',1363181,'2003-05-27 17:46:07',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-26 17:00:00','TM_MEST','N','N','S','N','V',1363182,'2003-05-27 17:46:07',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-25 09:00:00','TM_MEST','N','N','S','N','V',1363191,'2003-05-27 17:48:21',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-25 11:30:00','TM_MEST','N','N','S','N','V',1363192,'2003-05-27 17:48:21',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-25 14:30:00','TM_MEST','N','N','S','N','V',1363193,'2003-05-27 17:48:22',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-25 17:00:00','TM_MEST','N','N','S','N','V',1363194,'2003-05-27 17:48:22',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-24 12:00:00',null,'N','N','S','N','V',1365796,'2003-03-03 15:03:26',3647);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-24 17:00:00',null,'N','N','S','N','V',1365797,'2003-03-03 15:03:26',3647);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-28 12:30:00',null,'N','N','S','N','V',1365798,'2003-03-03 15:04:01',3647);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-28 17:00:00',null,'N','N','S','N','V',1365799,'2003-03-03 15:04:01',3647);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-03-03 14:00:00',null,'N','N','S','N','V',1365800,'2003-03-03 15:05:57',3647);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-03-03 17:00:00',null,'N','N','S','N','V',1365801,'2003-03-03 15:05:57',3647);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-02 14:00:00',null,'N','N','S','N','V',1365803,'2003-03-03 15:12:15',3647);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-02 17:00:00',null,'N','N','S','N','V',1365804,'2003-03-03 15:12:15',3647);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-03 09:00:00','TM_MEST','N','N','S','N','V',1365842,'2003-03-03 18:32:53',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-03 12:30:00','TM_MEST','N','N','S','N','V',1365843,'2003-03-03 18:32:53',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-04 09:00:00','TM_MEST','N','N','S','N','V',1365844,'2003-03-03 18:51:52',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-04 12:00:00','TM_MEST','N','N','S','N','V',1365845,'2003-03-03 18:51:53',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-05 13:30:00','TM_MEST','N','N','S','N','V',1365846,'2003-03-03 18:59:12',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-05 17:00:00','TM_MEST','N','N','S','N','V',1365847,'2003-03-03 18:59:12',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-03-06 11:00:00','TM_MEST','N','N','S','N','V',1368130,'2003-03-06 14:02:19',2997);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-06 13:04:00',null,'N','N','S','N','V',1370940,'2003-03-07 17:59:53',3647);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-06 17:00:00',null,'N','N','S','N','V',1370941,'2003-03-07 17:59:53',3647);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-03 09:00:00','TM_MEST','N','N','S','N','V',1387542,'2003-05-20 19:36:31',5592);
INSERT INTO ASS_MARCAS VALUES(5592,900889,'2003-05-03 11:00:00','TM_MEST','N','N','S','N','V',1387543,'2003-05-20 19:36:33',5592);

#----------------------------
# Table structure for ass_marreg
#----------------------------
drop table if exists ass_marreg;
create table ass_marreg (
   ASS_MARREGPESSOA int(10),
   ASS_MARREGMARCAS int(10) not null default '0',
   ASS_MARREGREGUL varchar(8),
   ASS_MARREGSISTEMA int(10),
   primary key (ASS_MARREGMARCAS))
   type=InnoDB comment="InnoDB free: 378880 kB";
