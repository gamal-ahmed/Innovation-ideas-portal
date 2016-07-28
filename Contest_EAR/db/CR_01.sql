update setting set setting_value='Reset Your Password', last_update=sysdate where setting_name='reset.mail.subject';

Insert Into SETTING  
(SETTING_ID,SETTING_NAME,SETTING_VALUE,LAST_UPDATE) 
Values
(16,'contest.url','http://developers.etisalat.com.eg',sysdate);

update CATEGORY
set DESCRIPTION_AR='لايف ستايل'
where 
CATEGORY_ID =1;

update CATEGORY
set DESCRIPTION_AR='التنمية الاقتصادية و الاجتماعية'
where 
CATEGORY_ID =2;

commit;
/** rollback

update setting set setting_value='Your Password has been resetted', last_update=sysdate where setting_name='reset.mail.subject';

delete from SETTING
where
setting_name='contest.url';

update CATEGORY
set DESCRIPTION_AR='نمط الحياة'
where 
CATEGORY_ID =1;
  
 */
