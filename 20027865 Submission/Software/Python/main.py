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
import os.path
import uuid
import adafruit_dht
from subprocess import call
import RPi.GPIO as GPIO

##############
#   Functions
##############

##############
#   PINGS THE FIREBASE API END POINT
##############
def testInternet_connection():
    os.system('color')
    url = 'https://the247gardener-default-rtdb.europe-west1.firebasedatabase.app/'
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
  ##############
#   MEASURMENT CYCLE TIME
 ##############   
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
  ##############
#   DEVICE CONFIGURATIUON FUNCTION
 ##############    
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
                
    else:
        print(datetime.now(), colored("Config Error Occured", "red"))

  ##############
#   READ DHT22 DEVICE
 ##############   
def readDht22Device(db, devId):
    allDhtDevices = db.child("Devices").child(devId).get()
    for attrib in allDhtDevices.each():
        if(str(attrib.key()) == "measurmentPin"):
              DHT_PIN = attrib.val()
              DHT_SENSOR = Adafruit_DHT.DHT22 
              humidity, temperature = Adafruit_DHT.read_retry(DHT_SENSOR, DHT_PIN) 
    return humidity, temperature
  ##############
#   READ MQ135 DEVICE
 ##############  
def readMQ135Device(db, devId):
    allMQDevices = db.child("Devices").child(devId).get()
    for attrib in allMQDevices.each():
        if(str(attrib.key()) == "measurmentPin"):
              MQ135_PIN = attrib.val() 
              if(GPIO.input(MQ135_PIN)):
                  return 1
              else:
                  return 0
 ##############
#   READ SOIL MOISTURE DEVICE
 ##############          
def readSoilMoistDevice(db, devId):
    allMoistDevices = db.child("Devices").child(devId).get()
    for attrib in allMoistDevices.each():
        if(str(attrib.key()) == "measurmentPin"):
                MOISTURE_PIN = attrib.val()  
                if(GPIO.input(MOISTURE_PIN)):
                  return 1
                else:
                  return 0
          
 ##############
#   CHECK PUMP SCHEDULE
 ##############  
def checkPumpScedule(db, devId, pinNo):
    allPumpDevices = db.child("PumpSchedule").child(devId).get()
    for attrib in allPumpDevices.each():
        if(str(attrib.key()) == "turnOnDate"):
          turnOnDateVal = attrib.val()
        elif(str(attrib.key()) == "turnOnTime"):
          turnOnTimeVal = attrib.val()
        elif(str(attrib.key()) == "completedOn"):
          completedFlag = attrib.val()
    
    if(checkpumpdatetime(turnOnDateVal, turnOnTimeVal, completedFlag)):
     GPIO.output(pinNo, 1)
     time.sleep(5)
     GPIO.output(pinNo, 0)
     updatePumpDevices(turnOnDateVal, turnOnTimeVal, completedFlag)

 ##############
#   UPDATE PUMP DEVICE
 ##############  
def updatePumpDevices(turnOnDateVal, turnOnTimeVal, completedFlag):
    if (completedFlag == False):
        day_today =  date.today().strftime("%d/%m/%Y")
        timestamp = time.strftime('%H:%M')
        if (day_today >= turnOnDateVal):
            if (timestamp >= turnOnTimeVal):
                return True
    else:
        return False
    
  ##############
#   CHECKS THE SHUTDOWN FLAG
 ##############   
def checkShutDownFlag(db):  
     progexe = db.child("programExecution").get()
     for prog in progexe.each():    
            if(str(prog.val()) == "True"):
                pass # user does noot want to execute Program
            else:
                input("Exiting Program")
                exit() # User wants to exit

  ##############
#   CHECKS PUMP DATE AND TIME
##############   
def checkpumpdatetime(turnOnDateVal, turnOnTimeVal, completedFlag):
    if (completedFlag == False):
        day_today =  date.today().strftime("%d/%m/%Y")
        timestamp = time.strftime('%H:%M')
        if (day_today >= turnOnDateVal):
            if (timestamp >= turnOnTimeVal):
                return True
    else:
        return False
    
def readFromDeviceDatalog():
    try:
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
       
                measureDeviceValue(devId, deviceType, measurmentPin)     
               
    except: 
        print(datetime.now(), colored("Error with config file", "red"))
   
  ##############
#   MEASURES DEVICES VALUE 
##############                        
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

 ##############
#   READS DEVICES 
##############  
def readDevice(deviceType, devmeasurmentPinId):
         if(deviceType == "1"):
             deviceName = "DHT22"
         elif(deviceType == "2"):
             deviceName = "MQ135"
         elif(deviceType == "3"):
             deviceName = "Moisture Sensor"
         elif(deviceType == "4"):
             deviceName = "Water Pump" 
         return random.randint(0, 255)
 ##############
#   DOCUMENTS DEVICES 
##############  
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
  
   ##############
#   UPLOADS DEVICES MEASURMENTS TO SERVER
##############  
def uploadDevicessMeasurments(db): 
    file_exists = os.path.exists("deviceMeasurments.csv") 
    
    if(file_exists == True):
        try:
            datalog = open("deviceMeasurments.csv", "r")
            data = datalog.read()
            data = data.strip()
            datalog.close()
            indexedData = data.split("\n")
            if(len(indexedData) > 1):
                for x in range(len(indexedData)):    
                    comma_seperated_indexed_data = indexedData[x].split(",")
                    data = {"Timestamp": comma_seperated_indexed_data[2],"DeviceID": comma_seperated_indexed_data[1], 
                            "Device": comma_seperated_indexed_data[0], "Unit": comma_seperated_indexed_data[1], "Value": comma_seperated_indexed_data[4]}
                    db.child("SensorMeasurments").push(data)
                open("deviceMeasurments.csv", "w").close()
            else:
                pass
        except Exception as e: 
            print(e)
    else:
        pass
      
 ##############
#   READS DEVICES FROM SERVER
##############          
                                                    
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
                                data = {"Timestamp": getTimestamp(),"DeviceID": str(devId), "Device": "DHT22", "Unit": "%" , "Value": str(value[0])}
                                db.child("SensorMeasurments").push(data)
                                data = {"Timestamp": getTimestamp(),"DeviceID": str(devId), "Device": "DHT22", "Unit": "oC" , "Value": str(value[1])}
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
                          
        
    except: 
        print(datetime.now(), colored("No Devices Configured", "red"))
        


##############
#   uploads logfile to server
##############
def uploadDeviceLog(db):

    with open('deviceData.json') as json_file:
        data = json.load(json_file)      
    
    for key in data:
        db.child("Devices").child(str(key)).set(data[key])
  
##############
#   WRITES LOGFILE
##############
def writeDeviceLog(db):
     devData = dict()
     
     allDevices = db.child("Devices").get()
     for device in allDevices.each():    
       devData[device.key()] = device.val() 
       
     json_object = json.dumps(devData, indent = 1)
     with open("deviceData.json", "w") as outfile:
        outfile.write(json_object)
                 
##############
#   GETS TIMESTAMP
##############
def getTimestamp():
    ct = datetime.now()
    return str(ct)

##############
#   BOOTS UP RASPBERRY PI
##############
def bootUp():
    try:    
        fle = open("rPi.config", "r")
        print(fle.read())
    except IOError:
         print(datetime.now(), colored("Config file does not exist", "red"))
##############
#   SHUTS THE RASPBERRY PI DOWN
##############        
def shutDown():
    call("sudo poweroff", shell=True)



##############
#   REGISTERS RASPBERRY PI DEVICE
##############
def registerPiDevice(db, uuid):
    data = {"Timestamp": getTimestamp(),"RaspberryPi": str(uuid)}
    db.child("RaspberryPiDevices").push(data)
    
##############
#   FIREBASE INITISATION
##############

def firebaseInit():
    
    firebaseConfig = {
  "apiKey": "AIzaSyB5ZEKfxmPISJDONFwzyAbiAswHDB2XyVw",
  "authDomain": "the247gardener.firebaseapp.com",
  "databaseURL": "https://the247gardener-default-rtdb.europe-west1.firebasedatabase.app",
  "projectId": "the247gardener",
  "storageBucket": "the247gardener.appspot.com",
  "messagingSenderId": "217798900708",
  "appId": "1:217798900708:web:91583507b4cb70463325f1",
  "measurementId": "G-07VZN847Q7"
};
    
    firebase = pyrebase.initialize_app(firebaseConfig)
    db=firebase.database()
    return db
##############
#   Main Program
##############
def main():
    uuidOne = uuid.uuid1()
    measurment_cycle_time = 0 
    GPIO.setmode(GPIO.BOARD)
    if(testInternet_connection()): 
        db = firebaseInit()
        registerPiDevice(db, uuidOne)
        measurment_cycle_time = devConfig(db, "config")
        devConfig(db, "writeConfig") 
        while(True): 
            if (testInternet_connection()):
                uploadDevicessMeasurments(db)
                measurment_cycle_time = devConfig(db, "config")  
                readDevices(db)
                exeProg = devConfig(db, "progVal")
                if(str(exeProg) == "False"):
                    devConfig(db, "writeConfig")
                    shutDown()
                time.sleep(measurment_cycle_time)  
            else:   
                readFromDeviceDatalog()
                time.sleep(measurment_cycle_time)  
         
    else:  
           shutDown()
           input("Exiting Program")
           exit()
    
if __name__ == "__main__":
    main()


