from datetime import datetime
from termcolor import colored
import time
import random 
import logging

sleep_time = 10

logging.basicConfig(level=logging.DEBUG)

while True:
    now = datetime.now()
    try:
            a = random.randint(1,100)
            print(now, "Temptature: ", colored(a, "green"))  
            logging.basicConfig(filename='D:\Project\testprograms\app.log', filemode='w', format='%(name)s - %(levelname)s - %(message)s')
            logging.warning('This will get logged to a file')

    except:
            print(now, colored("Error Detected", "red"))
            
    time.sleep(sleep_time)
