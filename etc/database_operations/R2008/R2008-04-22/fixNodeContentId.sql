update NODE N, CONTENT C1, CONTENT C2 SET N.CONTENT_ID = CONCAT(C1.CONTENT_ID,C2.CONTENT_ID) WHERE N.CONTENT_ID IS NULL AND C1.ID_INTERNAL=N.KEY_PARENT AND C2.ID_INTERNAL=N.KEY_CHILD;