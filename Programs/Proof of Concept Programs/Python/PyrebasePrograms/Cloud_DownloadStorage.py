################################################################
#Storage
################################################################
#Pyrebase tutorial
import pyrebase
firebaseConfig = {
  "apiKey": "AIzaSyAp9ePL5zkDKhvcXOrTANVygg25ZM1TMu8",
  "authDomain": "rpidatabase2.firebaseapp.com",
  "databaseURL": "https://rpidatabase2-default-rtdb.europe-west1.firebasedatabase.app",
  "projectId": "rpidatabase2",
  "storageBucket": "rpidatabase2.appspot.com",
  "messagingSenderId": "477017064476",
  "appId": "1:477017064476:web:489538a7d097af2d504bd9",
  "measurementId": "G-TNHFFDFX6S"
};
firebase = pyrebase.initialize_app(firebaseConfig)
storage = firebase.storage()

filename = "output.txt"
#cloudfilename = input("Enter the name of the file  on the cloud ")
cloudfilename = "cloudfile_output.txt"
storage.child(cloudfilename).download("", filename)