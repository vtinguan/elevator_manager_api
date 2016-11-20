# ELEVATOR MANAGER API

REST API to Manage Elevator Flow by ACME using Grails (Groovy On Rails) Framework

# How can you run this project?
To run it, you have to install on your machine: <br />
-Grails 2.2.2 (try to use [SDK MAN](http://sdkman.io/install.html)) <br />
-[Oracle JDK 1.7](https://www.digitalocean.com/community/tutorials/como-instalar-o-java-no-ubuntu-com-apt-get-pt) <br />
-[Latest Version of Groovy](http://groovy-lang.org/download.html) <br />
-[Mysql](https://www.mysql.com/downloads/) <br />
So then, you have to create the database too! <br />
<code>CREATE DATABSE elevator;</code> <br />
If you have user and pasword != "root", "" , don't forget to change it in [DataSource.groovy](https://github.com/vtinguan/elevator_manager_api/blob/master/grails-app/conf/DataSource.groovy) <br />


# Most Relevant Class Names
![alt tag](https://raw.githubusercontent.com/vtinguan/elevator_manager_api/master/classDiagram.png?token=AIrbXUUSz0InWoo1Lo1GncGWSX6NKGpgks5YOhk_wA%3D%3D)


# RUNNING THE PROJECT
You have to make a POST request to localhost with the path of CSV file on the JSON Raw <br />
<code>
POST http://localhost:8080/elevator_flow/Elevator/execute
{
	path : "grails-app/conf/elevadores.csv"
}
</code>
This example works cause I made git push with the [CSV file](https://github.com/vtinguan/elevator_manager_api/blob/master/grails-app/conf/elevadores.csv) <br />
The response is something like this for each elevator user <br />
<code>
      {
        "deliver_time_in_seconds": "126",
        "personName": "Wilhelmine Stracke PhD",
        "total_time_in_seconds": "126",
        "wait_time_in_seconds": "0"
      }
</code>

# BUUUT, IF YOU WANT THE TL;DR 
The business logic (elevators management) is in this [class](https://github.com/vtinguan/elevator_manager_api/blob/master/src/groovy/model/QueueManager.groovy) <br />

I ran it on my machine, so then, I made a push with the [response.json](https://github.com/vtinguan/elevator_manager_api/blob/master/response.json) too <br />

I made TWO unit tests: Just for the CSV extraction AND with the Elevator Management (With A Small Queue) and you can check it in this [file](https://github.com/vtinguan/elevator_manager_api/blob/master/test/unit/elevator_flow/QueueManagerSpec.groovy)


# PS
I did not have much time (almost 2 days), so then, I have some PLUS TODO-S for later: <br />
-Validate the CSV before start the flow and throw some costumized exceptions on the API <br />
-Do some reffactor: (create more class, remove multiple responsabilites for each method) <br />
-Do more unit tests <br />
-Deploy it on Elastic Beanstalk / RDS (AWS) <br />
