java 11 - maven - Spring boot 2.7.8 - postgres16

download the zip (secilstore-main)

extract

open eclipse

  -open project from file system
  
  -choose task folder which is located under secilstore-main
  
  -right click on the project -> run as -> maven build... -> Goals "clean install" and check skip test -> run

I assume that docker, docker desktop and postgres16 are already installed.

right click on the project -> show in -> terminal

  run the commands below

  -docker pull postgres:16
  -docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=yourpassword -e POSTGRES_USER=postgres -e POSTGRES_DB=secilstoretask --name=secilstoretask postgres:16
  
  -docker build -t task .   #### don't miss the dot ###
  -docker run -d -p 8080:8080 task

  
go to docker desktop and see if containers are running.If it is not, you can use docker desktop to run images.

Now it's ready to test APIs by postman.

##############################################################################################################################################################################

POST http://localhost:8080/secilstoreapi/auth/register
requestBody examples:
{
    "surname":"duman",
    "name":"eren",
    "userRole":"PATIENT",
    "phone":"123123"
}
{
    "surname":"zalim",
    "name":"mahmut",
    "userRole":"DOCTOR",
    "phone":"123"
}

POST http://localhost:8080/secilstoreapi/doctor/changeExaminationFee [Params are userID and examinationFee]

GET http://localhost:8080/secilstoreapi/patient/listAllDoctors

POST http://localhost:8080/secilstoreapi/patient/increaseWallet [Params are userID and increaseAmount] 

POST http://localhost:8080/secilstoreapi/patient/newAppointment [Params are userID, doctorName, doctorSurname, startTime and endTime]

DELETE http://localhost:8080/secilstoreapi/patient/cancelAppointment  [Params are userID, doctorName and doctorSurname]




  

