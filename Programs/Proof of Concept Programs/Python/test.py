import time
import random


temp = random.random()

while True: 
    humidity = random.uniform(82, 83)
    temp = random.uniform(11, 12)
    print("Current Temprature is: " + str(temp) + " *C ")
    print("Current Humidity is: " + str(humidity) + " % ")
    print(" ")
    time.sleep(5)
