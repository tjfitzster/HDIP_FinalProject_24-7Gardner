import pyrebase
#from distance import distance


config = {
    "apiKey": "",
    "authDomain": "",
    "databaseURL": "",
    "projectId": "",
    "storageBucket": "",
    "messagingSenderId": "",
    "appId": ""
};


firebase = pyrebase.initialize_app(config)



storage = firebase.storage()
database = firebase.database()
#a = distance()
#print (a)
database.child("Jim")
data = {"age": "Hello", "firstName": "Moto", "lastName": "oneil", "userName": "Erickson"}
database.set(data)

database.child("Alan")
data = {"age": "33", "firstName": "Alan", "lastName": "mcGrath", "userName": "Alanasourous"}
database.set(data)

database.child("Michael")
data = {"age": "53", "firstName": "Michael", "lastName": "tooth", "userName": "mick"}
database.set(data)

database.child("Tony")
data = {"age": "25", "firstName": "Tony", "lastName": "Montana", "userName": "TonyM"}
database.set(data)

database.child("Jim")
data = {"age": "33", "firstName": "Jim", "lastName": "Fitz", "userName": "jimmy"}
database.set(data)
