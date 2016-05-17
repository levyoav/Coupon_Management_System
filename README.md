# Coupon_Management_System
The Coupon Management System (CMS) allows companies to create coupons and for the companies customers to purchase these coupons. This part of the CMS is the core of the system which manages the transaction/creation of coupons and stores them in Derby DB using SQL queries.

Coupon Management System (CMS)
By: Yoav Levy
ID: 049806391
Email: levyoav28@gmail.com
System Versions:
Db-derby-10.11.1.1
Eclipse IDE for Java Developers Version: Luna Service Release 2 (4.4.2)
For opening the JAR file ‘Coupon_Management_System.core.jar.final’ , omit the suffix ‘.final’.

CREATING THE DATABASE:
Under package ‘utility’ you will find the class ‘SQLPopulator.java’. Run its main to populate the database. You
should see on the console all the tables appear. The population sequence is hard coded, so for each run, the same
values will be populated in the tables.
Note: on startup, all the existing tables are being deleted from the database. If you will run the populator without
having any tables in the database to begin with, you will get an exception when the populator attempts to delete
tables that don’t exist. If this is the case, simply prior to the startup, comment out the lines with ‘drop*Table’ (lines
29-33). Once the tables have been created, uncomment those lines so that the deletion process could take place in
the next database population.

RUNNING THE TEST:
Under the package ‘test’ you will find the class ‘MainTest.java’. This test works with the database created by the
populator. The test is hardcoded and was tailored specifically for the resulting database from the populaor. So for
best and most comprehensive test results, make sure to invoke the SQLPopulator before each test run, so it would
repopulate the database with the proper values.
Note: In the test class, there is an instantiation of the DBDAO classes rather than the DAO classes. This is due to
the reason that the DBDAO classes contain some utility methods for creating, showing and dropping the SQL tables
of each DBDAO. These methods are not part of the CMS functionality, thus they do not appear in the DAO’s.
How to review the test results: on the console, you should see the command that is being executed each time
under the text ‘COMMAND’, followed by the result of that command, under the text RESULT. For some command,
relevant tables will be printed out to the console for more thorough examination of the results (commands such as
‘deleteCoupon’ or ‘updateCustomer’).
