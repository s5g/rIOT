# rIOT
IOT sensor simulation, REST backend, Android dashboard

### URL Convention
- (StreamSensor) '/homeids/<home_id>/stream/sensornames/<sensor_name>/<sensor_data>'
- (EventSensor)  '/homeids/<home_id>/event/sensornames/<sensor_name>/<sensor_data>'

### Datastore Architecture
- the rate at which you can write to the same entity group is limited to one write to the entity group per second. 
