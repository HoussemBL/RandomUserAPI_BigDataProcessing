# RandomUserAPI_BigDataProcessing

In this project, we recieve in real time from the following API
https://randomuser.me/api/?inc=name,gender,nat,location&noinfo

We consume these data in real time using kafka.

After that, we apply Lambda architcture.
1.1 Thus we store raw data in cassandra database.
1.1 In parallel, we store processed/cleaned data in msql database.

![Architecture](https://user-images.githubusercontent.com/22003268/124357551-64024f00-dc1c-11eb-9709-1312d9139720.png)



Stored data sent to Superset apache a dashboard tool.
Subsequently, we have defined in Superset a dashboard containing some charts ( about new entries) 
![superset](https://github.com/HoussemBL/RandomUserAPI_BigDataProcessing/blob/main/image/superset.png)
