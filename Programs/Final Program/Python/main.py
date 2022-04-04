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
            print(now, colored("No Internet Connected", "red"))
            return False
    except:
        print(now, colored("CRITICAL ERROR OCCURED", "red"))
        print("status Code", op)
        return False

def soilMoisture():
    return random.randint(0, 255)

def gasSensor():
    return random.randint(0, 255)

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

def takeDeviceMeasurment(devPinNo):
    return random.randint(0, 255)

def checkPumpState(pumpState):
    if (pumpState == 1):   
     pass

def changePumpState(db, devId):
    pass

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
    allDhtDevices = db.child("Devices").child(devId).get()
    for attrib in allDhtDevices.each():
        if(str(attrib.key()) == "measurmentPin"):
              pinNo = attrib.val()  
              #we tget the pin number
              # we then read the device value on the pin   
              # dhtDevice = adafruit_dht.DHT11(board.D4)
    return random.randint(0, 255)

def readSoilMoistDevice(db, devId):
    allDhtDevices = db.child("Devices").child(devId).get()
    for attrib in allDhtDevices.each():
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


def readDeviceDatalog():
    pass

   
                        
                        
                                                    
def readDevices(db):
    try:
      allDevices = db.child("Devices").get()
      #print(len(db.child('Devices').get(
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

def writeDeviceLog(db):
     devData = dict()
     
     allDevices = db.child("Devices").get()
     for device in allDevices.each():    
       devData[device.key()] = device.val() 
   
     with open("deviceData.json", "w") as outfile:
        json.dump(devData, outfile)
    
              

def getTimestamp():
    ct = datetime.now()
    return str(ct)

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

def readLogfile():
    try:
        datalog = open("dataLog.log", "r")
        data = datalog.read()
        data = data.strip()
        datalog.close()
        return data
    except:
        datalog = open("dataLog.log","a")
        datalog.close()
    
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
    connectionFlag = False
    measurment_cycle_time = 0
    
    bootUp()
    if(testInternet_connection()):
        connectionFlag = True  
        db = firebaseInit()
        measurment_cycle_time = getmeasCycleTime(db)
        
        while(True): 
            if (testInternet_connection()):
                measurment_cycle_time = getmeasCycleTime(db)
                connectionFlag = True  
                readDevices(db)
                checkShutDownFlag(db)
                #writeDeviceLog(db)
                time.sleep(measurment_cycle_time)  
            else: 
                connectionFlag = False
                readDeviceDatalog()
                time.sleep(measurment_cycle_time)  
         
    else:  
         # shutDown()
          input("Exiting Program")
    exit()
    
if __name__ == "__main__":
    main()


