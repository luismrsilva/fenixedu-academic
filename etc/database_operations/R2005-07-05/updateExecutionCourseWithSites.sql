select 
	concat('update EXECUTION_COURSE set EXECUTION_COURSE.KEY_SITE = ', 
		SITE.ID_INTERNAL, 
		' where EXECUTION_COURSE.ID_INTERNAL = ',
		SITE.KEY_EXECUTION_COURSE, ';') 
as "" from SITE;