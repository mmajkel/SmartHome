#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>
#include <Timers.h>
//config
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
//config file
#include <FS.h>

Timer timerMainLoop, timer, timerButton, timerTimeout, timerChangeMode;

//input/outputs microcontrolers constans
const int buttonPin = 13;     // the number of the pushbutton pin
const int ledPin = 14;        // the number of the LED pin

//Connection variables
//char* ssid     = "automation";
//char* password = "";
const char* host = "192.168.1.200";

//temp variable
bool notConnected = true;
bool submitFileLoaded = false;
bool indexFileLoaded = false;

//input/output comunication variables
String macAddressNumber;
String line;
String lineFromFile;

//temporary light status variable
bool lightStatus = false;
bool interrupt = false;
//change mode variable
bool tryingChangeMode = false;
//mode variable 1-Standard process 2-accesPoint to configure standard way communication
bool standardMode = true;
bool configureMode = false;

//configuration webservice
//char* confSsid = "ESPap";
char* confPassword = "";  //password to network - probably available to change
String htmlContent;
String htmlSubmitContent;

int i = 0;

ESP8266WebServer server(80);

void setup() {

  Serial.begin(115200);
  SPIFFS.begin();
  // SPIFFS.format();
  // Serial.println("Spiffs formatted");

  timerMainLoop.begin(600);
  timerButton.begin(300);
  timerTimeout.begin(5000);
  timerChangeMode.begin(5000);

  pinMode(ledPin, OUTPUT);
  pinMode(buttonPin, INPUT_PULLUP);

  attachInterrupt(digitalPinToInterrupt(buttonPin), handleInterrupt, FALLING);

  setWifiConnection();

  byte mac[6];
  WiFi.macAddress(mac);

  for (int i = 0; i < 6; i++) {
    String tempMacAddress = String(mac[i], HEX);
    macAddressNumber += tempMacAddress;
  }

  //accesspoint config

  //reading html files
  File htmlSubmitFile = SPIFFS.open("/submit.html", "r");
  while (htmlSubmitFile.available()) {
    htmlSubmitContent += htmlSubmitFile.readStringUntil('\n');
  }
  htmlSubmitFile.close();

  File htmlFile = SPIFFS.open("/index.html", "r");
  while (htmlFile.available()) {
    htmlContent += htmlFile.readStringUntil('\n');
  }
  htmlFile.close();


  //must add reading html from file

  Serial.print("Configuring access point...");
  /* You can remove the password parameter if you want the AP to be open. */
  const char* confSsid = macAddressNumber.c_str();
  Serial.println(macAddressNumber);
  // WiFi.softAPConfig();
  WiFi.softAP(confSsid, confPassword);
  /*
    konfiguracja access pointa przez serwer dns pod tym linkiem
    https://gist.github.com/Cyclenerd/7c9cba13360ec1ec9d2ea36e50c7ff77
    dnsServer.start();
  */
  IPAddress myIP = WiFi.softAPIP();
  Serial.print("AP IP address: ");
  Serial.println(myIP);
  server.on("/", handleRoot);
  server.on("/index", handleRoot);
  server.on("/submit", handleSubmit);
  server.on("/exit", handleExit);
  server.begin();
  Serial.println("HTTP server started");
}

void setWifiConnection() {

  //read data from files

  File ssidFile = SPIFFS.open("/ssid.txt", "r");

  // while(ssidFile.available()){
  lineFromFile = ssidFile.readStringUntil('\n');
  //     }
  ssidFile.close();

  String ssidString = lineFromFile;
  const char *ssid = ssidString.c_str();

  File passFile = SPIFFS.open("/pass.txt", "r");

  // while(passFile.available()){
  lineFromFile = passFile.readStringUntil('\n');
  //   }
  passFile.close();

  String passString = lineFromFile;
  const char *pass = passString.c_str();
  Serial.println("ssid:");
  Serial.println(ssid);
  Serial.println("hasło:");
  Serial.println(pass);
  Serial.println("koniec z plikow");

  WiFi.begin(ssid, pass);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
    i++;
    if (i > 50) {
      notConnected = false;
      configureMode = true;
      standardMode = false;
      loop();
      i = 0;
      return;
    }
  }
  Serial.println("connected");
  notConnected = false;
}

void writeInFile(String fileName, String textToWrite) {

  // open file for writing
  File f = SPIFFS.open(fileName, "w");
  if (!f) {
    Serial.println("file open failed");
  }
  Serial.println("====== Writing to SPIFFS file =========");
  f.print(textToWrite);
  //    char buf[12];

  //spiffs_file fd = SPIFFS_open(&fs, "my_file", SPIFFS_CREAT | SPIFFS_TRUNC | SPIFFS_RDWR, 0);
  //    if (SPIFFS_write(&fs, fd, (u8_t *)"Hello world", 12) < 0) {
  //    printf("errno %i\n", SPIFFS_errno(&fs));
  //   return;

  f.close();
  delay(3000);
}

void handleInterrupt() {
  if (timerButton.available() && (digitalRead(buttonPin) == LOW)) {
    lightStatus = !lightStatus;
    if (lightStatus) {
      digitalWrite(ledPin, HIGH);
    } else {
      digitalWrite(ledPin, LOW);
    }
    interrupt = true;
    Serial.println("funkcja przerwania");
    timerButton.restart();
  }
  Serial.println("przerwanie");

  delayMicroseconds(100);

  if (digitalRead(buttonPin) == LOW) {
    Serial.println("trzymam przycisk");
    tryingChangeMode = true;
  }
}

void handleRoot() {
  server.send(200, "text/html", htmlContent);
}

void handleSubmit() {

  //      Serial.println("szukam pliku submit");
  if (server.args() > 0 ) {
    for ( uint8_t i = 0; i < server.args(); i++ ) {
      if (server.argName(i) == "ssid") {
        //wywołaj funkcję wpisywania do pliku z argumentami nazwa pliku, ssid
        String odczytaneZPost = server.arg(i);
        writeInFile("/ssid.txt", odczytaneZPost);
        Serial.println(odczytaneZPost);
      }
      if (server.argName(i) == "pass") {
        //wywołaj funkcję wpisywania do pliku z argumentami nazwa pliku, pass
        String odczytaneZPost = server.arg(i);
        writeInFile("/pass.txt", odczytaneZPost);
        Serial.println(odczytaneZPost);
      }
    }
  }

  server.send(200, "text/html", htmlSubmitContent);
}
void handleExit() {

  //exit configuration
  configureMode = false;
  standardMode = true;
  notConnected = true;

  server.send(200, "text/html", "Configuration finished...");
}


void loop() {

  //read ssid and password from file
  if (notConnected) {
    setWifiConnection();
  }

  if (timerMainLoop.available() && standardMode) {
    Serial.println("nowa standardowa petla");
    WiFi.mode(WIFI_STA);

    if ((tryingChangeMode) && (digitalRead(buttonPin) == LOW)) {
      Serial.println("nadal trzymam przycisk");
      delay(5000);
      if (digitalRead(buttonPin) == LOW) {
        Serial.println("teraz juz trzymam 5 sekund");
        submitFileLoaded = false;
        indexFileLoaded = false;
        standardMode = false;
        configureMode = true;
      }
    }
    tryingChangeMode = false;


    // Use WiFiClient class to create TCP connections
    WiFiClient client;
    const int httpPort = 8080;
    if (!client.connect(host, httpPort) && timerMainLoop.available()) {
      Serial.println("connection failed");

      client.stop();
      timerMainLoop.restart();
      return;
    }
    // sending actual data to server
    Serial.println("wysylam zadanie");
    client.print(String("GET /automation/witaj?status=" +
                        (String)lightStatus + "&client=" + macAddressNumber + " HTTP/1.1\r\n") +
                 "Host: " + host + "\r\n" +
                 "Connection: close\r\n\r\n");

    // lost connection
    while (client.available() == 0) {
      if (timerTimeout.available()) {
        client.stop();
        Serial.println("client not available");
        timerTimeout.restart();
        timerMainLoop.restart();
        return;
      }
    }

    while (client.available()) {
      line = client.readStringUntil('\n');
    }
    Serial.println(line);
    if (line == "0" && !interrupt) {
      lightStatus = false;
    }
    else if (line == "1" && !interrupt) {
      lightStatus = true;
    }

    timerMainLoop.restart();
    timerTimeout.restart();
  }
  //konfiguracja danych połączenia w przeglądarce
  if (timerMainLoop.available() && configureMode) {
    WiFi.mode(WIFI_AP);
    Serial.println("nowa petla konfiguracyjna ");

    server.handleClient();
    timerMainLoop.restart();

  }

  if (lightStatus && !interrupt) {
    digitalWrite(ledPin, HIGH);
  }
  if (!lightStatus && !interrupt) {
    digitalWrite(ledPin, LOW);
  }
  interrupt = false;

}
