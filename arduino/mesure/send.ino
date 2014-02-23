

// caracter codes 
#define END_OF_LINE     10       // \n
#define OPEN_BRACKET    "{"
#define CLOSE_BRACKET   "}"

   

void sendTeleinfoBasic() {
  //cptSerial.end();
 Process p;
 
    Console.println(F("Sd"));
    
  

   char buffer[256]="";
//sprintf(buffer, "curl 'http://54.246.90.43/mesure/create?adco=%s&hchc=1&hchp=1&hhphc=hhphc&ptec=ptec&iinst=1&papp=1&imax=1&optarif=optarif&isousc=1&motdetat=motdetat'", ADCO);
  //p.runShellCommand(buffer); 
  
  if (HCHC != 0 && HCHP!=0) {
    //String sADCO(ADCO); //sADCO.substring(0,11)
    
   sprintf(buffer, "curl 'http://54.246.90.43/mesure/create?adco=%s&hchc=%ld&hchp=%ld&hhphc=%s&ptec=%s&iinst=%i&papp=%ld&imax=%ld&optarif=%s&isousc=%i&motdetat=%s'", ADCO,HCHC,HCHP,HHPHC,PTEC,IINST,PAPP,IMAX,OPTARIF,ISOUSC,MOTDETAT);
   Console.print("buffer:");
   
  
  Console.println(buffer);
   p.runShellCommand(buffer);}
     // p.runShellCommand(F("curl 'http://54.246.90.43/mesure/create?adco=adco&hchc=1&hchp=1&hhphc=hhphc&ptec=ptec&iinst=1&papp=1&imax=1&optarif=optarif&isousc=1&motdetat=motdetat'")); 
         Console.println(F("St"));
         
          while (p.available() > 0) {
    char c = p.read();
    Console.print(c);
  }
  
         
        //  cptSerial.begin(1200);
     /*   
  while (p.available() > 0) {
    char c = p.read();
    Console.print(c);
  }

      
    sprintf(buffer, "curl -v -X GET ");
        Serial.println(buffer);  

sprintf(buffer, "%shttp://54.246.90.43/mesure/create?", buffer); 
    Serial.println(buffer);  

sprintf(buffer, "%sadco=%s&", buffer, ADCO);
sprintf(buffer, "%shchc=%s&", buffer, HCHC);
sprintf(buffer, "%shchp=%s&", buffer, HCHP);
sprintf(buffer, "%shhphc=%s&", buffer, HHPHC);
sprintf(buffer, "%sptec=%s&", buffer, PTEC);
sprintf(buffer, "%siinst=%s&", buffer, IINST);
sprintf(buffer, "%spapp=%s&", buffer, PAPP);
sprintf(buffer, "%simax=%s&", buffer, IMAX);
sprintf(buffer, "%soptarif=%s&", buffer, OPTARIF);

sprintf(buffer, "%sisousc=%s&", buffer, ISOUSC);
sprintf(buffer, "%smotdetat=%s", buffer, MOTDETAT);

//http://localhost:9000/mesure/create?adco=adco&hchc=1&hchp=1&hhphc=hhphc&ptec=ptec&iinst=1&papp=1&imax=1&optarif=optarif&isousc=1&motdetat=motdetat


    
    Serial.println(buffer);  

  
    p.runShellCommand(buffer);
    

  while (p.available() > 0) {
    char c = p.read();
    Serial.print(c);
  }
*/
}




