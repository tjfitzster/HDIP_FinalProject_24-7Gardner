################################################################
#Storage
################################################################

#Pyrebase tutorial
import pyrebase

firebaseConfig = {
  "apiKey": "AIzaSyBcbLqGgsB4ZqpT-HVL8miDF0qSAOkD0LM",
  "authDomain": "fir-auth-f1f14.firebaseapp.com",
  "databaseURL": "https://fir-auth-f1f14-default-rtdb.europe-west1.firebasedatabase.app",
  "projectId": "fir-auth-f1f14",
  "storageBucket": "fir-auth-f1f14.appspot.com",
  "messagingSenderId": "884348390673",
  "appId": "1:884348390673:web:de4c1af8f1b87b571b50b0",
  "measurementId": "G-RN13FTPFYJ"
};
firebase = pyrebase.initialize_app(firebaseConfig)
storage = firebase.storage()

#filename = input("Enter the name of the file you wish to upload ")
filename = "output.txt"
#cloudfilename = input("Enter the name of the file  on the cloud ")
cloudfilename = "cloudfile_output.txt"

storage.child(cloudfilename).put(filename)





#print(storage.child(cloudfilename).get_url(None))
#storage.child(cloudfilename).download("", "output.txt")