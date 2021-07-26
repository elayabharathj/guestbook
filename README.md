guestbook
Guest Book Application - Used to post entries by guest, Admin can view all posts created by guest review/modify/approve/delete the posts. Had Guest try to access the post view page, a cutomized alert for 403 page will display Similar thing will happen when Admin try to create posts.

For all other errors, it will redirect to common error page.

Role checks are made using Spring Security Authentication - Users and Authorities Table. User Password has been encrypted using BCrypt - With Random 10.

Pre Conditions: The below tables should be created

1)Post
2)Users
3)Authorities

Table creation scripts and test screenshots are attached.

