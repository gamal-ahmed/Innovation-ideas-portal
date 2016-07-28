update category set description_en='M-Commerce' where category_id=1;
update category set description_en='Saytar' where category_id=2;
update category set description_en='Customer Experience' where category_id=3;

update contest_stage set STAGE_START_DATE=to_date('14-NOV-13','DD-MON-RR'), stage_english_desc ='Tech Awards is the 1st contest targeting Etisalat IT Staff & Outsource to bring out all those innovative and cool ideas that you always dreamt of having.' where stage_id='1';
update contest_stage set STAGE_START_DATE=to_date('8-DEC-13','DD-MON-RR'), stage_english_desc ='Tech Awards is the 1st contest targeting Etisalat IT Staff & Outsource to bring out all those innovative and cool ideas that you always dreamt of having.' where stage_id='2';
update contest_stage set STAGE_START_DATE=to_date('15-DEC-13','DD-MON-RR'), stage_english_desc ='Tech Awards is the 1st contest targeting Etisalat IT Staff & Outsource to bring out all those innovative and cool ideas that you always dreamt of having.' where stage_id='3';
update contest_stage set STAGE_START_DATE=to_date('22-DEC-13','DD-MON-RR'), stage_english_desc ='Tech Awards is the 1st contest targeting Etisalat IT Staff & Outsource to bring out all those innovative and cool ideas that you always dreamt of having.' where stage_id='4';
update contest_stage set STAGE_START_DATE=to_date('29-DEC-13','DD-MON-RR'), stage_english_desc ='Tech Awards is the 1st contest targeting Etisalat IT Staff & Outsource to bring out all those innovative and cool ideas that you always dreamt of having.' where stage_id='5';

update idea_status set description='Mobility Award' where idea_status_id=4;
update idea_status set description='Business Enabler Award' where idea_status_id=5;
update idea_status set description='Customer Experience Award' where idea_status_id=6;

update setting set setting_value='http://<IP>:<port>' where setting_id=16;

delete from blog_comment;
delete from vote;
delete from idea_blog;
delete from idea;
delete from thread_message;
