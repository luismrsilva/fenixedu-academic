-- DELETE FROM ap, f USING `AVAILABILITY_POLICY` AS ap, `FUNCTIONALITY` AS f WHERE f.`UUID` = '25c5a057-ad71-4280-b5df-7dd893e2260a' AND ap.`KEY_FUNCTIONALITY` = f.`ID_INTERNAL`;
DELETE FROM `FUNCTIONALITY` WHERE `UUID` = '25c5a057-ad71-4280-b5df-7dd893e2260a';