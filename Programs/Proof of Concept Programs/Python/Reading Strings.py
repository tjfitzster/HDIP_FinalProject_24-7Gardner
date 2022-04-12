import pyrebase

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
    
def main():
    firebaseInit()
    db = firebaseInit()
    data ={"age": str("55"), "firstName": "jjjj","lastName": "thisisatest","userName": "tjf", "password": "into"}
    
    db.child("Users").push(data)

    data ={"age": str("22"), "firstName": "ttttt","lastName": "ttttttt","userName": "jjs", "password": "into"}

    db.child("Users").push(data)
    
    data ={"age": str("66"), "firstName": "ddd","lastName": "aa","userName": "fdf", "password": "jim"}

    db.child("Users").push(data)
    
    
if __name__ == "__main__":
    main()