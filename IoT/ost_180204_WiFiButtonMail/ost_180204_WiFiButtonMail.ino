#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>
#include <Timers.h>

Timer timer,timerButton,timerTimeout;

//input/outputs microcontrolers constans
const int buttonPin = 13;     // the number of the pushbutton pin
const int ledPin = 14;        // the number of the LED pin
      
//Connection variables
const char* ssid     = "linksys";
const char* password = "36120413";
const char* host = "192.168.1.200";

//input/output comunication variables 
String macAddressNumber;
String line;

//temporary light status variable 
bool lightStatus = false;

void setup() {
  
  Serial.begin(115200);

  timerMainLoop.begin(300);
  timerButton.begin(300);
  timerTimeout.begin(5000);

  pinMode(ledPin, OUTPUT);
  pinMode(buttonPin, INPUT_PULLUP);

  attachInterrupt(digitalPinToInterrupt(buttonPin), handleInterrupt, FALLING);

  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  byte mac[6];
  WiFi.macAddress(mac);
  
  for(int i=0;i<6;i++){
    String tempMacAddress = String(mac[i], HEX);
   macAddressNumber+=tempMacAddress;
  }
}

void handleInterrupt() {
  if (timerButton.available()){
    lightStatus= !lightStatus;

    if (lightStatus){
      digitalWrite(ledPin, HIGH);
    } else{
      digitalWrite(ledPin, LOW);
    }
   timerMainLoop.restart();
   timerButton.restart();
  }
 }


void loop() {
  if(timerMainLoop.available()){
    
              // Use WiFiClient class to create TCP connections
                WiFiClient client;
                const int httpPort = 8080;
                if (!client.connect(host, httpPort) && timer.available()) {
                Serial.println("connection failed");
                client.stop();
                timerMainLoop.restart();
                return;
              }
          // sending actual data to server
          client.print(String("GET /Automation2/email?status=" +
                      (String)lightStatus + " HTTP/1.1\r\n") +
                       "Host: " + host + "\r\n" + 
                       "Connection: close\r\n\r\n");
          
          // lost connection   
          while (client.available() == 0) {
            if (timerTimeout.available()){
              client.stop();
              timerTimeout.restart();
              timerMainLoop.restart();
              return;
            }
          }

        while(client.available()){
         line = client.readStringUntil('\n');

          if(line=="0"){
            lightStatus = false;
          }
          else if(line=="1"){
            lightStatus = true;
          }
        }

   if (lightStatus){
       digitalWrite(ledPin, HIGH);
   }else{
       digitalWrite(ledPin, LOW);
   }
    
  timerMainLoop.restart();
  timerTimeout.restart();
 }
}
