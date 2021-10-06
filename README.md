# RandomUserAPI_BigDataProcessing

In this project, we recieve data in real time from the following API
https://randomuser.me/api/?inc=name,gender,nat,location&noinfo

We consume these data in real time using kafka.

After that, we apply Lambda architcture.
1. Thus we store raw data in cassandra database.
2. In parallel, we store processed/cleaned data in mysql database.

![Architecture](https://github.com/HoussemBL/RandomUserAPI_BigDataProcessing/blob/main/image/Architecture.png)



Processed data (stored in mysql) are sent then to Superset apache a dashboard tool.
Subsequently, we have defined in Superset a dashboard containing some charts (about new entries).

![superset](https://github.com/HoussemBL/RandomUserAPI_BigDataProcessing/blob/main/image/superset.png)
