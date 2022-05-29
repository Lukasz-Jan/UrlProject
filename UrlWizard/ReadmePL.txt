
1. Testowanie za pomocą http POST:

	endpoint dla http post: 
	http://localhost:8082/postUrl
	Przykładowa komenda pasująca do dostarczonego api dla curl-a:
	
	curl -d "https://tools.ietf.org/html/rfc3986#section-3.4" -X POST -H "Accept: text/plain" -H "Content-Type: text/plain" http://localhost:8082/postUrl -i
	
	curl -d "https://www.komputerswiat.pl/aktualnosci/sprzet/nvidia-wprowadzi-nowe-wersje-kart-geforce-rtx-30-z-limitem-kopania-kryptowalut/pcn6t15" -X POST -H "Accept: text/plain" -H "Content-Type: text/plain" http://localhost:8082/postUrl -i
	
	Api dla POST działa dla typu payload-u "text/plain"



3.
a)	Pobieranie strony (jednego zasobu z bazy).

	Api wystawione na enpoint:
	
	http://localhost:8082/retrievePage/
	pobiera 2 "query" parametry: url i fragment. Parametr "fragment" jest opcjonalny.
	
	Np:
	http://localhost:8082/retrievePage/?url=https://tools.ietf.org/html/rfc3986&fragment=section-3.4
	http://localhost:8082/retrievePage/?url=https://tools.ietf.org/html/rfc3986
	
	http://localhost:8082/retrievePage/?url=https://www.komputerswiat.pl/aktualnosci/sprzet/nvidia-wprowadzi-nowe-wersje-kart-geforce-rtx-30-z-limitem-kopania-kryptowalut/pcn6t15
	
	http://localhost:8082/retrievePage/?url=https://www.defence24.com/polish-modernization-of-bwp-1-analysis

b) Pobieranie wszystkich zasobów z bazy

	Jest możliwe przewijanie za pomocą parametrów w url.

	Pobieranie wszystkich linków do stron w bazie jest wystawione an adresach:

	http://localhost:8082/retrieveAll?page=1&size=4

	http://localhost:8082/retrieveAll  (tutaj default page jest 0 i default size jest 80)


c)  Pobieranie zasobów z bazy Json format - polecam tutaj wtyczkę chrome
	http://localhost:8082/retrieveJson
	http://localhost:8082/retrieveJson?page=0&size=1
	
	Rezultat dobrze się ogląda z wtyczką chrome "JSON Viewer".
	

4. Szukanie w których zasobach występuje ciąg znaków:

	Rozwiązanie zostało poprawione (zmodyfikowane)

	endpoint: 
	http://localhost:8082/searchForText/
	przyjmuje jeden "query" parametr <word> będący ciągiem szukanych znaków np:

	http://localhost:8082/searchForText/?word=DOCTYPE


5.
Budowanie i uruchamianie servera za 
pomocą menu do maven w eclipse:

a)
Run As -> Maven build...
b) Pojawia się menu i wpisujemy w Goals:
goal: 
spring-boot:run

Potem uruchamiać server z eclips-a można za pomocą skrótu
Alt+Shift+X i wybieramy m (Run Maven Build)

Projekt spring-boot ma wbudowany w siebie 
server, nie trzeba deploy-ować na oddzielnym serverze.

6.

Wyniki zobaczymy w wbudowanej bazie

http://localhost:8082/h2-console

Dane logowania:
JDBC URL:jdbc:h2:mem:testdb
user name:sa
hasło puste

komenda:
select * from CI_URL

Jeśli strona pobrała się na podstawie linku 
to kolumna "CONTENT" zawiera zawartość strony.

W przeciwnym wypadku wypełniony jest tylko klucz główny 
i kolumna "CONTENT" ustawiona z zawartością null.

7.
W pliku 
oraPropertiesHome.properties
umieściłem konfigurację do bazy oracle
postawionej lokalnie.
Wymaga instalacji sterownika w maven goal:

install:install-file -DgroupId=com.oracle -DartifactId=ojdbc8 -Dversion=12.2.0.1.0 -Dpackaging=jar -Dfile=C:\app\User\product\12.2.0\client_1\jdbc\lib\ojdbc8.jar -DgeneratePom=true



8.
Zamknięcie servera za pomocą komendy:
http://localhost:8082/close

9. Niektóre komendy do testów:

mvn test -Dtest=SearchServiceTest


10.
Wgranie linków i zawartości stron do bazy lokalnej oracle, potem pobiorę do skryptu fillBase.sql na potrzeby 
testu SearchServiceTest.

	W pom. xml trzeba odchaczyć jdbc driver dla oracle:		<!-- homeORA -->
	W pliku SearchServiceTest wyłączam kasowanie w tearDown:
	//	jpaRepo.deleteAll();
	
	komenda:
	mvn test -Dtest=SearchServiceTest
	tworzy tablicę CI_URL i uruchamia testy w tej klasie. Tablica po testach nie jest kasowana: //	jpaRepo.deleteAll();


