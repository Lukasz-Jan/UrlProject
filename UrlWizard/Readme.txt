
Program UrlWizard creates rest endpoints to receive get/post requests with url links.

Pages contents are asynchronously downloaded to  database for requests 


It is possible to retrieve internet page from the database with browser GET.
It is possible to retrieve all pages from database and rolling with get parameters.

We can search the database for the page with chars sequence and retrieve that page.


1. 
 How to run...
 Building and starting server with maven menu in eclipse:

a)
   Run As -> Maven build...
   
b) Menu appears and we write in Goals:
    goal: 
    spring-boot:run

    Next runs may be started in eclipse with shortcut 
    Alt+Shift+X and we choose m (Run Maven Build)

    Spring-boot project has built-in server, no need to deploy somewhere else.
     
	Sample post commands:
	curl -d "https://tools.ietf.org/html/rfc3986#section-3.4" -X POST -H "Accept: text/plain" -H "Content-Type: text/plain" http://localhost:8082/postUrl -i
 
2.
 Run tests:
 for package launcher.restServices.test  
 test -Dtest=SearchServiceTest
 test -Dtest=SearchServiceTest#singleSearchForOneWordOnManyPages
 
 
 test -Dtest=UrlServiceRSPostTest
 
 In eclipse choose
 Run As -> Maven build... 
 In Goals write:
 test -Dtest=SearchServiceTest
 Click Apply button and Run button
 
 We can also in eclipse press:
 press alt-shift-x, press m , and choose launch configuration to run for example test -Dtest=SearchServiceTest
 
 test -Dtest=UrlServiceRSPostTest
 
3.
 DATABASES   !!!
 We can switch from h2 database to oracle and vice versa
 by copying content of h2.properties to application.properties.
 
 Then in pom.xml select dependency h2 and hide oracle dependency ojdbc8.
 
 So far no maven profile is used in this project. 

 Link to database:
 http://localhost:8082/h2-console

 We can see h2 database content via browser
    http://localhost:8082/h2-console
	login:
	JDBC URL:jdbc:h2:mem:testdb
	user name:sa
	password:empty

4. 
Sample commands to add resource to database
a)
  using curl POST
  curl -d "https://tools.ietf.org/html/rfc3986#section-3.4" -X POST -H "Accept: text/plain" -H "Content-Type: text/plain" http://localhost:8082/postUrl -i
  curl -d "https://www.komputerswiat.pl/aktualnosci/sprzet/nvidia-wprowadzi-nowe-wersje-kart-geforce-rtx-30-z-limitem-kopania-kryptowalut/pcn6t15" -X POST -H "Accept: text/plain" -H "Content-Type: text/plain" http://localhost:8082/postUrl -i
b) 
 using GET via browser 
 http://localhost:8082/url/?url=https://www.bbc.com/weather
 http://localhost:8082/url/?url=https://trustee.ietf.org/

5.
Sample commands to retrieve one resource from base
  
  http://localhost:8082/retrievePage/?url=https://www.bbc.com/weather
  http://localhost:8082/retrievePage/?url=https://www.bbc.com/news

6.

 getting summary from base for all contained pages:
 http://localhost:8082/retrieveAll
 
7.

Searching for pages in database which
contain the given string of characters:

Examples:

 a)
 Searched String= 
 " media types may define their own restrictions"

 Request for searched String:
 http://localhost:8082/searchForText/?word= media types may define their own restrictions

 b)
 Searched String= 
 " types"
 
 Request for searched String:
 http://localhost:8082/searchForText/?word= types
 
 8.
 Closing server
 http://localhost:8082/close