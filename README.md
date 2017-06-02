# rIOT
IOT sensor simulation, REST backend, Android dashboard

### URL Convention
- (StreamSensor) '/homeids/<home_id>/stream/sensornames/<sensor_name>/sensordatas/<sensor_data>'
- (EventSensor)  '/homeids/<home_id>/event/sensornames/<sensor_name>/sensordatas/<sensor_data>'

### Datastore Architecture
Using Heroku postgresql database  
Table name - 'sensorFlow'  
schema - (id SERIAL PRIMARY KEY, homeId VARCHAR(40) not null, sensorName VARCHAR(40) not null, sensorData VARCHAR(40) not null), timeAdded TIMESTAMP DEFAULT now())
