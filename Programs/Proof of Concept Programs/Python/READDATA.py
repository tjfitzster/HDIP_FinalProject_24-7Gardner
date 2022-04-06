import json
import time
import random
from datetime import datetime

def getTimestamp():
    ct = datetime.now()
    return str(ct)


def documentDeviceMeasurment(deviceName, devId, value): 
     with open("deviceMeasurments.csv", "a") as f:
         f.write(str(deviceName) + "," + str(devId) + "," + getTimestamp() + "UNIT" + ","  + str(value) +  "\n")
     f.close()


def measureDeviceValue(devId, devicDate, deviceName, deviceType, gardenID, measurmentPin):
    
    if(deviceType == "1"):   #DHT22  
        value = readDevice(deviceType, measurmentPin)
        documentDeviceMeasurment(deviceName, devId, value)
    elif(deviceType == "2"): # MQ135
          value = readDevice(deviceType, measurmentPin)
          documentDeviceMeasurment(deviceName, devId, value)
    elif(deviceType == "3"):  # Soil Moisture
          value = readDevice(deviceType, measurmentPin)
          documentDeviceMeasurment(deviceName, devId, value)
    elif(deviceType == "4"): #Water pump
          value = readDevice(deviceType, measurmentPin)
          documentDeviceMeasurment(deviceName, devId, value)    


def readDevice(deviceType, devmeasurmentPinId):
              #we tget the pin number
              # we then read the device value on the pin
            # dhtDevice = adafruit_dht.DHT11(board.D4)
    return random.randint(0, 255)


   
   
def main():
    with open('deviceData.json') as json_file:
        data = json.load(json_file)      
     
    devId =""
    devicDate = ""
    deviceName = ""
    deviceType = ""
    gardenID = ""
    measurmentPin = ""

    for key in data:
        f_devId = data[key]
        for devAttr in f_devId:
            if (devAttr == "devId"):
                devId = str(f_devId[devAttr])
            elif(devAttr == "deviceDate"):
                devicDate = str(f_devId[devAttr])
            elif(devAttr == "deviceName"):
                deviceName = str(f_devId[devAttr])
            elif(devAttr == "deviceType"):
                deviceType = str(f_devId[devAttr])
            elif(devAttr == "gardenID"):
                gardenID = str(f_devId[devAttr])
            elif(devAttr == "measurmentPin"):
                measurmentPin = str(f_devId[devAttr])
                
        measureDeviceValue(devId, devicDate, deviceName, deviceType, gardenID, measurmentPin)
    
        


main()