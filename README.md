# rIOT
IOT sensor simulation, REST backend, Android dashboard

### URLs  
- local URL : "http://192.168.1.108:5000/"  
- remote URL : ""https://endless-shadow.herokuapp.com/"

### URL Convention  
- (GET) '/rIOT/db/create'  
> creates new table with name sensorFlow  
  
- (POST) '/rIOT/db/sensorFlow'  
> inserts a row into 'sensorFlow  
>> var houseName = [your house name]  
>> var sensorName = [your sensor name]  
>> var sensorData = [your sensor data]  
>> var sensorType = [0 | 1], {0=event, 1=stream}  
  
- (GET) '/rIOT/db/sensorFlow'  
> returns sensorData of all rows  
  
- (GET)  '/rIOT/db/delete'  
> deletes all rows in sensorData  

### Datastore Architecture
Using Heroku postgresql database  
Table name - 'sensorFlow'  
schema - (id SERIAL PRIMARY KEY, homeId VARCHAR(40) not null, sensorName VARCHAR(40) not null, sensorData VARCHAR(40) not null), timeAdded TIMESTAMP DEFAULT now())
