# Webentwicklung Backend / ük 295

Das Modul befindet sich aktuell in Entwicklung.

## Wichtige Ordner

|               |                                                               |
|---            |---                                                            |
|**database**:  |Schema und SQL-Files mit MySQLWorkbench erzeugt                |
|**docs**:      |Aktuelle für die Entwicklung relevante Dokumente und Bilder    |
|**src**:       |Java-Source-Code                                               |

## Applikation starten

1. Lokal eine MySQL Instanz installieren
2. Datenbank anhand des Auftrages "Auftrag Datenbank erstellen.docx" erstellen
    * alternativ das Script database/CreateDatabase-with-data.sql ausführen um Testdaten zu erhalten
3. Im Terminal mit ```gradlew bootRun``` oder über das Gradle-Tab in der IDE die App starten
4. Applikation kann über ```http://localhost:8080``` angesprochen werden
5. Wurde DB mit Daten geladen, existiert der Benutzer "user" mit dem Passwort "test"

### Nützliche urls

* Api-Docs und Swagger
    * [http://localhost:8080/v3/api-docs/](http://localhost:8080/v3/api-docs/)
    * [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* API-Endpunkte
    * [http://localhost:8080/users](http://localhost:8080/users)
    * [http://localhost:8080/items](http://localhost:8080/items)
    * [http://localhost:8080/tags](http://localhost:8080/tags)
