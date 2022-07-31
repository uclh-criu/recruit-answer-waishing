# UCLH Admission Test - Backend

This backend project use java 11 and spring boot with embedded H2 database as DataSource

The sample data (same as sample csv) will load into the H2 database when boot up the spring boot.
We can find the sample data sql in src/resource/data.sql

###File structure:   
- controller : Restful API 
- model.entity  : Entity for Data Object
- model         : Pojo for normal object
- repo          : Persistence Layer 
- service       : Business Logic

###H2 console: 
http://localhost:8080/h2-console (After boot up the springboot

    jdbc url=jdbc:h2:mem:testdb    
    username=sa    
    password=

###Admission Report API with basic search query:

4 search criteria add : 
   - sexAtBirth 
   - ethnicity 
   - yearOfBirth
   - isFake (To filter overlapped logic / with filter for full admission report)

    GET http://localhost:8080/api/admission?sexAtBirth=ALL&ethnicity=ALL&yearOfBirth=ALL&isFake=TRUE

   Sample Json response:

      [
         {
            "patientId": 10,
            "yearOfBirth": "1996",
            "sexAtBirth": "M",
            "ethnicity": "Other White",
            "admissions": [
               {
               "startDateTime": "2015-03-16 17:00:00",
               "endDateTime": "2015-05-16 23:00:00",
               "source": "Inpatient",
               "outcome": "ICU"
               },
               {
               "startDateTime": "2015-03-25 17:00:00",
               "endDateTime": "2015-05-25 23:00:00",
               "source": "",
               "outcome": "Discharged Alive"
               }
            ]
         }
      ]

###How to access the api
1. You can use IDE (Eclipse/IntelliJ) to run this application directly, or
   use maven (mvn spring-boot:run) to run it
2. Run the above endpoints by (Postman / Talend API Test)
3. Done :)

### Test Case
API Test -  to test restful API call
Unit Test - mainly focusing on business logic (overlapped logic)


###Docker (backend standalone):

    docker build --tag=uclh-admission-api:latest .
    docker run -p8080:8080 uclh-admission-api:latest

###Test Coverage

    repo	100% (0/0)	100% (0/0)	100% (0/0)
    service	100% (1/1)	80% (8/10)	77% (31/40)

[Back to overview](../README.md)