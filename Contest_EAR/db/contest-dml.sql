SET DEFINE OFF;
Insert into CATEGORY
   (CATEGORY_ID, DESCRIPTION_EN, DESCRIPTION_AR)
 Values
   (1, 'Lifestyle', 'نمط الحياة');
Insert into CATEGORY
   (CATEGORY_ID, DESCRIPTION_EN, DESCRIPTION_AR)
 Values
   (2, 'Social & Economic Development', 'التنمية الاقتصادية و');
Insert into CATEGORY
   (CATEGORY_ID, DESCRIPTION_EN, DESCRIPTION_AR)
 Values
   (3, 'Business', 'الأعمال');
COMMIT;


Insert into COMMENT_STATUS
   (COMMENT_STATUS_ID, DESCRIPTION)
 Values
   (1, 'New');
Insert into COMMENT_STATUS
   (COMMENT_STATUS_ID, DESCRIPTION)
 Values
   (2, 'Rejected');
Insert into COMMENT_STATUS
   (COMMENT_STATUS_ID, DESCRIPTION)
 Values
   (3, 'Verified');
COMMIT;


Insert into CONTEST_STAGE
   (STAGE_ID, STAGE_ARABIC_NAME, STAGE_ARABIC_DESC, STAGE_START_DATE, STAGE_ORDER, 
    STAGE_ENGLISH_NAME, STAGE_ENGLISH_DESC)
 Values
   (4, 'المرحلة الاولى', 'تقديم الافكار', TO_DATE('11/14/2012 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 1, 
    'Stage 1', 'post ideas');
Insert into CONTEST_STAGE
   (STAGE_ID, STAGE_ARABIC_NAME, STAGE_ARABIC_DESC, STAGE_START_DATE, STAGE_ORDER, 
    STAGE_ENGLISH_NAME, STAGE_ENGLISH_DESC)
 Values
   (2, 'Evaluation', 'Evaluation stage', TO_DATE('01/22/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 2, 
    'Evaluation', 'Evaluation');
Insert into CONTEST_STAGE
   (STAGE_ID, STAGE_ARABIC_NAME, STAGE_ARABIC_DESC, STAGE_START_DATE, STAGE_ORDER, 
    STAGE_ENGLISH_NAME, STAGE_ENGLISH_DESC)
 Values
   (3, 'Submission', 'Submission', TO_DATE('01/26/2013 00:00:00', 'MM/DD/YYYY HH24:MI:SS'), 3, 
    'Submission', ' ');
COMMIT;


Insert into IDEA_STATUS
   (IDEA_STATUS_ID, DESCRIPTION)
 Values
   (3, 'Shortlisted');
Insert into IDEA_STATUS
   (IDEA_STATUS_ID, DESCRIPTION)
 Values
   (4, 'WINNER1ST');
Insert into IDEA_STATUS
   (IDEA_STATUS_ID, DESCRIPTION)
 Values
   (5, 'WINNER2ND');
Insert into IDEA_STATUS
   (IDEA_STATUS_ID, DESCRIPTION)
 Values
   (6, 'WINNER3RD');
Insert into IDEA_STATUS
   (IDEA_STATUS_ID, DESCRIPTION)
 Values
   (1, 'Submitted');
Insert into IDEA_STATUS
   (IDEA_STATUS_ID, DESCRIPTION)
 Values
   (2, 'Rejected');
COMMIT;


Insert into MESSAGE_TYPE
   (MESSAGE_TYPE_ID, DESCRIPTION)
 Values
   (1, 'Request');
Insert into MESSAGE_TYPE
   (MESSAGE_TYPE_ID, DESCRIPTION)
 Values
   (2, 'Response');
COMMIT;

Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (1, 'mail.user.name', 'o-hamada.elnopy', TO_DATE('11/12/2012 16:54:02', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (2, 'mail.user.passwd', '3edc$RFV', TO_DATE('11/12/2012 16:55:46', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (3, 'mail.smtp.host', '10.203.101.80', TO_DATE('11/12/2012 16:55:47', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (4, 'mail.smtp.port', '25', TO_DATE('11/12/2012 16:55:47', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (5, 'mail.smtp.submitter', 'o-hamada.elnopy', TO_DATE('11/12/2012 16:55:47', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (6, 'IDEA_PATH', 'C://', TO_DATE('11/14/2012 19:15:27', 'MM/DD/YYYY HH24:MI:SS'));
COMMIT;

Insert into THREAD_STATUS
   (THREAD_STATUS_ID, DESCRIPTION)
 Values
   (1, 'Waiting For Response');
Insert into THREAD_STATUS
   (THREAD_STATUS_ID, DESCRIPTION)
 Values
   (2, 'Closed');
COMMIT;

Insert into USER_STATUS
   (USER_STATUS_ID, DESCRIPTION)
 Values
   (1, 'Active');
Insert into USER_STATUS
   (USER_STATUS_ID, DESCRIPTION)
 Values
   (2, 'Suspended');
Insert into USER_STATUS
   (USER_STATUS_ID, DESCRIPTION)
 Values
   (3, 'Opted out');
Insert into USER_STATUS
   (USER_STATUS_ID, DESCRIPTION)
 Values
   (4, 'Blacklisted');
COMMIT;

Insert into USER_TYPE
   (USER_TYPE_ID, DESCRIPTION)
 Values
   (1, 'Contender');
Insert into USER_TYPE
   (USER_TYPE_ID, DESCRIPTION)
 Values
   (2, 'Voter');
Insert into USER_TYPE
   (USER_TYPE_ID, DESCRIPTION)
 Values
   (3, 'Judge');
Insert into USER_TYPE
   (USER_TYPE_ID, DESCRIPTION)
 Values
   (4, 'Admin');
COMMIT;



SET DEFINE OFF;
DELETE FROM SETTING;
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (1, 'mail.user.name', 'ETP.Services@etisalat.com', TO_DATE('11/12/2012 16:54:02', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (2, 'mail.user.passwd', 'MailServ_por321', TO_DATE('11/12/2012 16:55:46', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (3, 'mail.smtp.host', '10.203.101.80', TO_DATE('11/12/2012 16:55:47', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (4, 'mail.smtp.port', '25', TO_DATE('11/12/2012 16:55:47', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (5, 'mail.smtp.submitter', 'o-hamada.elnopy', TO_DATE('11/12/2012 16:55:47', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (7, 'regestration.mail.subject', 'Welcome ', TO_DATE('11/19/2012 23:20:57', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (8, 'regestration.mail.body', 'Thank you for registering in Yalla N''Saytar competition', TO_DATE('11/19/2012 23:20:57', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (6, 'IDEA_PATH', 'C://', TO_DATE('11/14/2012 19:15:27', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (9, 'changeStatus.vm', 'changeStatus.vm', TO_DATE('11/22/2012 15:39:39', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (10, 'resetPassword.vm', 'resetPassword.vm', TO_DATE('11/22/2012 15:39:39', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (11, 'registration.vm', 'registration.vm', TO_DATE('11/22/2012 15:39:39', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (12, 'template.images.path', 'C://Templates', TO_DATE('11/22/2012 15:39:40', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (13, 'mail.from', 'ETP.Services@etisalat.com', TO_DATE('11/22/2012 15:39:40', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (14, 'status.mail.subject', 'Idea Status Changed', TO_DATE('11/22/2012 15:39:40', 'MM/DD/YYYY HH24:MI:SS'));
Insert into SETTING
   (SETTING_ID, SETTING_NAME, SETTING_VALUE, LAST_UPDATE)
 Values
   (15, 'reset.mail.subject', 'Your Passord has been resetted', TO_DATE('11/22/2012 15:39:40', 'MM/DD/YYYY HH24:MI:SS'));
COMMIT;

delete from CONTEST_STAGE;

INSERT INTO CONTEST_STAGE ( STAGE_ID, STAGE_ARABIC_NAME, STAGE_ARABIC_DESC, STAGE_START_DATE,
STAGE_ORDER, STAGE_ENGLISH_NAME, STAGE_ENGLISH_DESC ) VALUES ( 
1, 'المرحلة الأولى', 'يلا N''Saytar هي مسابقة فريدة من نوعها لتطبيقات الهاتف المتحرك حيث تحصل على عرض المواهب الخاصة بك عن طريق بناء تطبيقات الهاتف المتحرك لخدمة المجتمع المصري. وغني عن القول أنك سوف تفوز على جائزة نقدية وفرصة محتملة لاتصالات ان ترعى التطبيق الخاص بك'
,  TO_Date( '12/27/2012 12:00:00 AM', 'MM/DD/YYYY HH:MI:SS AM'), 1, 'Stage 1', 'Yalla N’Saytar is a unique mobile application contest where you get to showcase your talent by building mobile applications intended to serve the Egyptian community. Needless to say that you will win cash prize'); 
INSERT INTO CONTEST_STAGE ( STAGE_ID, STAGE_ARABIC_NAME, STAGE_ARABIC_DESC, STAGE_START_DATE,
STAGE_ORDER, STAGE_ENGLISH_NAME, STAGE_ENGLISH_DESC ) VALUES ( 
2, 'المرحلة الثانية', 'يلا N''Saytar هي مسابقة فريدة من نوعها لتطبيقات الهاتف المتحرك حيث تحصل على عرض المواهب الخاصة بك عن طريق بناء تطبيقات الهاتف المتحرك لخدمة المجتمع المصري. وغني عن القول أنك سوف تفوز على جائزة نقدية وفرصة محتملة لاتصالات ان ترعى التطبيق الخاص بك'
,  TO_Date( '02/01/2013 12:00:00 AM', 'MM/DD/YYYY HH:MI:SS AM'), 2, 'Stage 2', 'Yalla N’Saytar is a unique mobile application contest where you get to showcase your talent by building mobile applications intended to serve the Egyptian community. Needless to say that you will win cash prize'); 
INSERT INTO CONTEST_STAGE ( STAGE_ID, STAGE_ARABIC_NAME, STAGE_ARABIC_DESC, STAGE_START_DATE,
STAGE_ORDER, STAGE_ENGLISH_NAME, STAGE_ENGLISH_DESC ) VALUES ( 
4, 'المرحلة الرابعة', 'يلا N''Saytar هي مسابقة فريدة من نوعها لتطبيقات الهاتف المتحرك حيث تحصل على عرض المواهب الخاصة بك عن طريق بناء تطبيقات الهاتف المتحرك لخدمة المجتمع المصري. وغني عن القول أنك سوف تفوز على جائزة نقدية وفرصة محتملة لاتصالات ان ترعى التطبيق الخاص بك'
,  TO_Date( '04/10/2013 12:00:00 AM', 'MM/DD/YYYY HH:MI:SS AM'), 4, 'Stage 4', 'Yalla N’Saytar is a unique mobile application contest where you get to showcase your talent by building mobile applications intended to serve the Egyptian community. Needless to say that you will win cash prize'); 
INSERT INTO CONTEST_STAGE ( STAGE_ID, STAGE_ARABIC_NAME, STAGE_ARABIC_DESC, STAGE_START_DATE,
STAGE_ORDER, STAGE_ENGLISH_NAME, STAGE_ENGLISH_DESC ) VALUES ( 
3, 'المرحلة الثالثة', 'يلا N''Saytar هي مسابقة فريدة من نوعها لتطبيقات الهاتف المتحرك حيث تحصل على عرض المواهب الخاصة بك عن طريق بناء تطبيقات الهاتف المتحرك لخدمة المجتمع المصري. وغني عن القول أنك سوف تفوز على جائزة نقدية وفرصة محتملة لاتصالات ان ترعى التطبيق الخاص بك'
,  TO_Date( '02/08/2013 12:00:00 AM', 'MM/DD/YYYY HH:MI:SS AM'), 3, 'Stage 3', 'Yalla N’Saytar is a unique mobile application contest where you get to showcase your talent by building mobile applications intended to serve the Egyptian community. Needless to say that you will win cash prize'); 
commit;

INSERT INTO CONTEST_STAGE ( STAGE_ID, STAGE_ARABIC_NAME, STAGE_ARABIC_DESC, STAGE_START_DATE,
STAGE_ORDER, STAGE_ENGLISH_NAME, STAGE_ENGLISH_DESC ) VALUES ( 
5, 'المسابقة أغلقت', 'المسابقة أغلقت',  TO_Date( '04/18/2012', 'MM/DD/YYYY')
, 4, 'Competation is closed', 'Competation is Closed'); 
commit;
 
delete from setting where setting_id=15; 
 
INSERT INTO SETTING ( SETTING_ID, SETTING_NAME, SETTING_VALUE,
LAST_UPDATE ) VALUES ( 
15, 'reset.mail.subject', 'Your password has been reset',  sysdate); 
COMMIT;

UPDATE CATEGORY
SET DESCRIPTION_EN='Productivity'
WHERE CATEGORY_ID= 3;

update CONTEST_STAGE 
set STAGE_ORDER=5
WHERE STAGE_ID=5; 
commit;



