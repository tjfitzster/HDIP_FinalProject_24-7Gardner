"""
NAme: Main Python Program IOT Project.
Student: TJ Fitzpatrick
Student Number: 20027865
"""
##############
#   Imports
##############
from datetime import datetime
from datetime import date
import time, os, requests
from termcolor import colored
import random
import pyrebase
import time
import logging
import board
import json
#import adafruit_dht

##############
#   Functions
##############
def testInternet_connection():
    os.system('color')
    url = 'https://console.firebase.google.com/project/finalproject-83f4a/database/finalproject-83f4a-default-rtdb/'
    timeout = 2
    op = None
    now = datetime.now()
    try:
        op = requests.get(url, timeout=timeout).status_code
        if op == 200:
            print(now, colored("Internet Connected", "green"))
            return True
        else:
            print(now, colored("Database is down", "red"))
            return False
    except:
        print(now, colored("No Internet Connected", "red"))
        return False

def soilMoisture():
    return random.randint(0, 255)

def gasSensor():
    return random.randint(0, 255)

def writeConfigurationFile(db):
    pass

def uploadConfigurationFile(db):
    pass

def getmeasCycleTime(db):
  try:   
    objfreq = db.child("Configuration").child("Frequency (s)").get()
    freq = objfreq.val()
  
    if (freq < 4):
        freq = 4
    else:
        pass
  except: 
      freq = 10
  return freq

def devConfig(db, configType):
    if (configType == "config"):     
        try:   
            objfreq = db.child("Configuration").child("frequency (s)").get()
            freq = objfreq.val()
            if (freq < 4):
                freq = 4
            else:
                pass
        except: 
            freq = 10
        return freq

    elif(configType == "progVal"):
         try: 
          progExe = db.child("Configuration").child("executeProgram").get()
          exeProg = progExe.val()
          print(datetime.now(), colored("Executing program", "green"))
          return exeProg
         except: 
           exeProg = True
           print(datetime.now(), colored("Critical Error with Database Error Has Occured", "red"))
           return exeProg 
        

    elif(configType == "writeConfig"):
         try: 
          wConfig = db.child("Configuration").child("writeConfigurationFile").get()
          wConfigVal = wConfig.val()
         except: 
            wConfigVal = False
            print(datetime.now(), colored("Writing Config File Database Error Has Occured", "red"))
            return False
       
         if(wConfigVal == str(True)):
                print(datetime.now(), colored("Writing Config File", "green"))
                writeDeviceLog(db)
                return True
            
         return 0
     
    elif(configType == "uploadConfig"):
         try:
             uploadDeviceLog(db)
         except: 
             pass
           # uConfigVal = False 
        # if(uConfigVal == True):
               # uploadConfigurationFile(db)   
                
    else:
        print(datetime.now(), colored("Config Error Occured", "red"))


def readDht22Device(db, devId):
    allDhtDevices = db.child("Devices").child(devId).get()
    for attrib in allDhtDevices.each():
        if(str(attrib.key()) == "measurmentPin"):
              pinNo = attrib.val()  
              #we tget the pin number
              # we then read the device value on the pin
            # dhtDevice = adafruit_dht.DHT11(board.D4)
    return random.randint(0, 255)

def readMQ135Device(db, devId):
    allMQDevices = db.child("Devices").child(devId).get()
    for attrib in allMQDevices.each():
        if(str(attrib.key()) == "measurmentPin"):
              pinNo = attrib.val()  
              #we tget the pin number
              # we then read the device value on the pin   
              # dhtDevice = adafruit_dht.DHT11(board.D4)
    return random.randint(0, 255)

def readSoilMoistDevice(db, devId):
    allMoistDevices = db.child("Devices").child(devId).get()
    for attrib in allMoistDevices.each():
        if(str(attrib.key()) == "measurmentPin"):
              pinNo = attrib.val()  
              #we tget the pin number
              # we then read the device value on the pin   
              # dhtDevice = adafruit_dht.DHT11(board.D4)
    return random.randint(0, 255)

def checkPumpScedule(db, devId):
    allPumpDevices = db.child("PumpSchedule").child(devId).get()
    for attrib in allPumpDevices.each():
        if(str(attrib.key()) == "turnOnDate"):
          turnOnDate = attrib.val()
        elif(str(attrib.key()) == "turnOnTime"):
          turnOnTime = attrib.val() 
 
    date_obj = datetime.strptim(turnOnDate, )
    return random.randint(0, 1)

def checkShutDownFlag(db):  
     progexe = db.child("programExecution").get()
     for prog in progexe.each():    
            if(str(prog.val()) == "True"):
                pass # user does noot want to execute Program
            else:
                input("Exiting Program")
                exit() # User wants to exit


def readFromDeviceDatalog():
    #try:
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
                #print(devId +  deviceName +  deviceType+ measurmentPin)
                measureDeviceValue(devId, deviceType, measurmentPin)     
               
    #except: 
       # print(datetime.now(), colored("Error with config file", "red"))
   
                        
def measureDeviceValue(devId, deviceType, measurmentPin):
    
    if(deviceType == "1"):   #DHT22  
        value = readDevice(deviceType, measurmentPin)
        documentDeviceMeasurment(deviceType, devId, value)
    elif(deviceType == "2"): # MQ135
          value = readDevice(deviceType, measurmentPin)
          documentDeviceMeasurment(deviceType, devId, value)
    elif(deviceType == "3"):  # Soil Moisture
          value = readDevice(deviceType, measurmentPin)
          documentDeviceMeasurment(deviceType, devId, value)
    elif(deviceType == "4"): #Water pump
          value = readDevice(deviceType, measurmentPin)
          documentDeviceMeasurment(deviceType, devId, value)                 


def readDevice(deviceType, devmeasurmentPinId):
              #we tget the pin number
              # we then read the device value on the pin
            # dhtDevice = adafruit_dht.DHT11(board.D4)
    return random.randint(0, 255)

def documentDeviceMeasurment(deviceName, devId, value): 
     with open("deviceMeasurments.csv", "a") as f:
         if(deviceName == "1"):
             deviceName = "DHT22"
         elif(deviceName == "2"):
             deviceName = "MQ135"
         elif(deviceName == "3"):
             deviceName = "Moisture Sensor"
         elif(deviceName == "4"):
             deviceName = "Water Pump" 
         f.write(str(deviceName) + "," + str(devId) + "," + getTimestamp() + "," +  "UNIT" + ","  + str(value) +  "\n")
     f.close()
  
  
def uploadDevicessMeasurments(db): 
    try:
        datalog = open("deviceMeasurments.csv", "r")
        data = datalog.read()
        data = data.strip()
        datalog.close()
       
        indexedData = data.split("\n")
        for x in range(len(indexedData)):    
           comma_seperated_indexed_data = indexedData[x].split(",")
           data = {"Timestamp": comma_seperated_indexed_data[2],"DeviceID": comma_seperated_indexed_data[1], "Device": comma_seperated_indexed_data[0], "Unit": comma_seperated_indexed_data[1], "Value": comma_seperated_indexed_data[4]}
           db.child("SensorMeasurments").push(data)

        open("deviceMeasurments.csv", "w").close()
        
      
    except:
        print("Error uploading CSV file")

       
                                                    
def readDevices(db):
    try:
      allDevices = db.child("Devices").get()
      for device in allDevices.each():    
              
              devkey = str(device.key())
              devAttrs = db.child("Devices").child(devkey).get()
              
              for attrib in devAttrs.each():
                  if(str(attrib.key()) == "deviceType"):
                        if(str(attrib.val()) == str(1)): #DHT22
                                devId = str(devAttrs.key())
                                value = readDht22Device(db, devId)
                                data = {"Timestamp": getTimestamp(),"DeviceID": str(devId), "Device": "DHT22", "Unit": "oC" , "Value": str(value)}
                                db.child("SensorMeasurments").push(data)
    
                        elif(str(attrib.val()) == str(2)): # MQ135
                            devId = str(devAttrs.key())
                            value = readMQ135Device(db, devId)
                            data = {"Timestamp": getTimestamp(),"DeviceID": str(devId), "Device": "MQ135", "Unit": "" , "Value": str(value)}
                            db.child("SensorMeasurments").push(data)
                            
                        elif(str(attrib.val()) == str(3)): # Soil Moisture
                            devId = str(devAttrs.key())
                            value = readSoilMoistDevice(str(attrib.val()))
                            data = {"Timestamp": getTimestamp(),"DeviceID": str(devkey), "Device": "Moisture Sensor", "Unit": "Moisture" , "Value": str(value)}
                            db.child("SensorMeasurments").push(data)      
                            
                        elif(str(attrib.val()) == str(4)): #Water pump
                            devId = str(devAttrs.key())
                            checkPumpScedule(db, devId)
                           # value = takePumpeMeasurment(str(attrib.val()))
                            #checkPumpState(value)
                          #  changePumpState(db, devId)
                          #  data = {"Timestamp": getTimestamp(),"DeviceID": str(devkey), "Device": "Moisture Sensor", "Unit": "Moisture" , "Value": str(value)}
                        #    db.child("SensorMeasurments").push(data)      
        
    except: 
        print(datetime.now(), colored("No Devices Configured", "red"))
        
def lightSensor():
    return random.randint(0, 255)


def uploadDeviceLog(db):

    with open('deviceData.json') as json_file:
        data = json.load(json_file)      
    
    for key in data:
        db.child("Devices").child(str(key)).set(data[key])
  

def writeDeviceLog(db):
     devData = dict()
     
     allDevices = db.child("Devices").get()
     for device in allDevices.each():    
       devData[device.key()] = device.val() 
       
     json_object = json.dumps(devData, indent = 1)
     with open("deviceData.json", "w") as outfile:
        outfile.write(json_object)
                 

def getTimestamp():
    ct = datetime.now()
    return str(ct)


def bootUp():
    try:    
        fle = open("rPi.config", "r")
        print(fle.read())
    except IOError:
         print(datetime.now(), colored("Config file does not exist", "red"))
        
def shutDown():
    # write config file
    pass

    
##############
#   Standard Setup
##############

def firebaseInit():
    
    firebaseConfig = {
  "apiKey": "AIzaSyA0nZc9Rz-q5BGob_SeICUvYhreUvnLVeo",
  "authDomain": "finalproject-83f4a.firebaseapp.com",
  "databaseURL": "https://finalproject-83f4a-default-rtdb.europe-west1.firebasedatabase.app",
  "projectId": "finalproject-83f4a",
  "storageBucket": "finalproject-83f4a.appspot.com",
  "messagingSenderId": "714618999720",
  "appId": "1:714618999720:web:88509c12285efaac59839d",
  "measurementId": "G-TNHFFDFX6S"
};
    
    firebase = pyrebase.initialize_app(firebaseConfig)
    db=firebase.database()
    return db
##############
#   Main Program
##############
def main():
    measurment_cycle_time = 0 
    if(testInternet_connection()): 
        db = firebaseInit()
        measurment_cycle_time = devConfig(db, "config")
        devConfig(db, "writeConfig") 
        while(True): 
            if (testInternet_connection()):
                uploadDevicessMeasurments(db)
                measurment_cycle_time = devConfig(db, "config")  
                readDevices(db)
               # exeProg = devConfig(db, "progVal")
               # if(str(exeProg) == "False"):
              #      devConfig(db, "writeConfig")
              #      #shutdown
                time.sleep(measurment_cycle_time)  
            else:   
                readFromDeviceDatalog()
                time.sleep(measurment_cycle_time)  
         
    else:  
          # shutDown()
       #   input("Exiting Program")
     exit()
    
if __name__ == "__main__":
    main()


