#include <SPI.h>         // needed for Arduino versionslater than 0018
//#include <Ethernet.h>
//#include <EthernetUdp.h>
#include <SoftwareSerial.h>
//#include <RestClient.h>
#include <Process.h>


#define LEDPIN 13


#define MY_DEVICE F("arduino-meurisse")

// Maximal size of xPL messages that could be processed
// This size should not be reduce unless you are sure of what you are doing
#define MAX_XPL_MESSAGE_SIZE 200

// Heartbeat interval : time in minutes between 2 sends of a xpl hbeat message
#define HBEAT_INTERVAL 5

// Duration of a minute in seconds (to allow shorter delay in dev)
#define MINUTE 1

// Teleinfo interval : time in minutes between 2 sends of a xpl stat message
#define TELEINFO_INTERVAL 1

/***************** Teleinfo configuration part *******************/
char CaractereRecu = '\0';
char Checksum[32] = "";
char Ligne[32] = "";
char Etiquette[9] = "";
char Donnee[13] = "";
char Trame[512] = "";
int i = 0;
int j = 0;

unsigned long Chrono = 0;
unsigned long LastChrono = 0;

char ADCO[12] ;      // Adresse du concentrateur de téléreport (numéro de série du compteur), 12 numériques + \0
long HCHC = 0;      // Index option Heures Creuses - Heures Creuses, 8 numériques, Wh
long HCHP = 0;      // Index option Heures Creuses - Heures Pleines, 8 numériques, Wh
char PTEC[4] ;      // Période Tarifaire en cours, 4 alphanumériques
char HHPHC[2] ; // Horaire Heures Pleines Heures Creuses, 1 alphanumérique (A, C, D, E ou Y selon programmation du compteur)
int IINST = 0;     // Monophasé - Intensité Instantanée, 3 numériques, A  (intensité efficace instantanée)
long PAPP = 0;      // Puissance apparente, 5 numérique, VA (arrondie à la dizaine la plus proche)
long IMAX = 0;      // Monophasé - Intensité maximale appelée, 3 numériques, A
char OPTARIF[4] ;    // Option tarifaire choisie, 4 alphanumériques (BASE => Option Base, HC.. => Option Heures Creuses, EJP. => Option EJP, BBRx => Option Tempo [x selon contacts auxiliaires])
char MOTDETAT[10] = "";  // Mot d'état du compteur, 10 alphanumériques
int ISOUSC = 0;    // Intensité souscrite, 2 numériques, A

int check[11];  // Checksum by etiquette
int trame_ok = 1; // global trame checksum flag
int finTrame = 0;
/******************* END OF CONFIGURATION *******************/


/********************* time management **********************/

int second = 0;
int lastTimeHbeat = 0;
int lastTimeTeleinfo = 0;

/*********************** Global vars ************************/

// UDP related vars

SoftwareSerial cptSerial(10, 11);

int packetSize = 0;      // received packet's size
byte remoteIp[4];        // received packet's IP
unsigned int remotePort; // received packet's port
byte packetBuffer[MAX_XPL_MESSAGE_SIZE];   // place to store received packet

// status
int result;



/********************* Set up arduino ***********************/
void setup() {
  //cptSerial.begin(1200);
  

  pinMode(LEDPIN, OUTPUT);

  // Serial to EDF cpt
 
  Serial.begin(9600);
  

 
  digitalWrite(LEDPIN, LOW);
  Bridge.begin();  // make contact with the linux processor
  Console.begin();
  Console.noBuffer(); //(64);
 // while (!Console) { }
 Console.println(F("Hi!"));




}

void loop() {
 
      digitalWrite(LEDPIN, HIGH);
      getTeleinfo();
      digitalWrite(LEDPIN, LOW);
      sendTeleinfoBasic();
     

 

  delay(30000);
}


/*------------------------------------------------------------------------------*/
/* Test checksum d'un message (Return 1 si checkum ok)				*/
/*------------------------------------------------------------------------------*/
int checksum_ok(char *etiquette, char *valeur, char checksum)
{

  unsigned char sum = 32 ;		// Somme des codes ASCII du message + un espace
  int i ;

  for (i = 0; i < strlen(etiquette); i++) sum = sum + etiquette[i] ;
  for (i = 0; i < strlen(valeur); i++) sum = sum + valeur[i] ;
  sum = (sum & 63) + 32 ;
  Serial.print(etiquette); Serial.print(" ");
  Serial.print(valeur); Serial.print(" ");
  Serial.println(checksum);
  Serial.print("Sum = "); Serial.println(sum);
  Serial.print("Cheksum = "); Serial.println(int(checksum));
  //Console.print("Cheksum = "); Console.print(int(checksum));Console.print("Sum= "); Console.println(sum);
  if ( sum == checksum) return 1 ;	// Return 1 si checkum ok.
  return 0 ;
}

/***********************************************
   getTeleinfo
   Decode Teleinfo from serial
   Input : n/a
   Output : n/a
***********************************************/
void getTeleinfo() {

  /* vider les infos de la dernière trame lue */
  memset(Ligne, '\0', 32);
  memset(Trame, '\0', 512);
  int trameComplete = 0;

  memset(ADCO, '\0', 12);
  HCHC = 0;
  HCHP = 0;
  memset(PTEC, '\0', 4);
  memset(HHPHC, '\0', 2);
  IINST = 0;
  PAPP = 0;
  IMAX = 0;
  memset(OPTARIF, '\0', 4);
  memset(MOTDETAT, '\0', 10);
  ISOUSC = 0;

 cptSerial.begin(1200);

  while (!trameComplete) {
    while (CaractereRecu != 0x02) // boucle jusqu'a "Start Text 002" début de la trame
    {
      if (cptSerial.available()) {
        CaractereRecu = cptSerial.read() & 0x7F;
      }
    }

    i = 0;
    while (CaractereRecu != 0x03) // || !trame_ok ) // Tant qu'on est pas arrivé à "EndText 003" Fin de trame ou que la trame est incomplète
    {
      if (cptSerial.available()) {
        CaractereRecu = cptSerial.read() & 0x7F;
        Trame[i++] = CaractereRecu;
      }
    }
    finTrame = i;
    Trame[i++] = '\0';

    Serial.println(Trame);


    lireTrame(Trame);

    // on vérifie si on a une trame complète ou non
    for (i = 0; i < 11; i++) {
      trameComplete += check[i];
    }
    Serial.print("Nb lignes valides :"); Serial.println(trameComplete);
    if (trameComplete < 11) trameComplete = 0; // on a pas les 11 valeurs, il faut lire la trame suivante
    else {
      trameComplete = 1;
    }
  }
  cptSerial.end();
}

void lireTrame(char *trame) {

  int i;
  int j = 0;
  for (i = 0; i < strlen(trame); i++) {
    if (trame[i] != 0x0D) { // Tant qu'on est pas au CR, c'est qu'on est sur une ligne du groupe
      Ligne[j++] = trame[i];
    }
    else { //On vient de finir de lire une ligne, on la décode (récupération de l'etiquette + valeur + controle checksum
      decodeLigne(Ligne);
      memset(Ligne, '\0', 32); // on vide la ligne pour la lecture suivante
      j = 0;
    }

  }
}

int decodeLigne(char *ligne) {




  //Checksum='\0';

  int debutValeur;
  int debutChecksum;
  // Décomposer en fonction pour lire l'étiquette etc ...
  debutValeur = lireEtiquette(ligne);
  debutChecksum = lireValeur(ligne, debutValeur);
  lireChecksum(ligne, debutValeur + debutChecksum - 1);

  if (checksum_ok(Etiquette, Donnee, Checksum[0])) { // si la ligne est correcte (checksum ok) on affecte la valeur à l'étiquette
    return affecteEtiquette(Etiquette, Donnee);
  }
  else {
    Serial.println("checksum ko");
    return 0;
  }

}


int lireEtiquette(char *ligne) {

  int i;
  int j = 0;
  memset(Etiquette, '\0', 9);
  for (i = 1; i < strlen(ligne); i++) {
    if (ligne[i] != 0x20) { // Tant qu'on est pas au SP, c'est qu'on est sur l'étiquette
      Etiquette[j++] = ligne[i];
    }
    else { //On vient de finir de lire une etiquette
      //  Serial.print("Etiquette : ");
      //  Serial.println(Etiquette);
      return j + 2; // on est sur le dernier caractère de l'etiquette, il faut passer l'espace aussi (donc +2) pour arriver à la valeur
    }

  }
}


int lireValeur(char *ligne, int offset) {


  int i;
  int j = 0;
  memset(Donnee, '\0', 13);
  for (i = offset; i < strlen(ligne); i++) {
    if (ligne[i] != 0x20) { // Tant qu'on est pas au SP, c'est qu'on est sur l'étiquette
      Donnee[j++] = ligne[i];
    }
    else { //On vient de finir de lire une etiquette
      //  Serial.print("Valeur : ");
      //  Serial.println(Donnee);
      return j + 2; // on est sur le dernier caractère de la valeur, il faut passer l'espace aussi (donc +2) pour arriver à la valeur
    }

  }
}


void lireChecksum(char *ligne, int offset) {

  int i;
  int j = 0;
  memset(Checksum, '\0', 32);
  for (i = offset; i < strlen(ligne); i++) {
    Checksum[j++] = ligne[i];
    //  Serial.print("Chekcsum : ");
    //  Serial.println(Checksum);
  }

}




int affecteEtiquette(char *etiquette, char *valeur) {


  if (strcmp(etiquette, "ADCO") == 0) {
    memset(ADCO, '\0', 12); memcpy(ADCO, valeur, strlen(valeur)); check[1] = 1;
    //Serial.print(F("ADCO=")); Serial.println(ADCO);
    //Serial.print(F("valeur=")); Serial.println(valeur);
  }
  else if (strcmp(etiquette, "HCHC") == 0) {
    HCHC = atol(valeur); check[2] = 1;
    //Serial.print("HCHC="); Serial.println(HCHC);
    //Serial.print("valeur="); Serial.println(valeur);
  }
  else if (strcmp(etiquette, "HCHP") == 0) {
    HCHP = atol(valeur); check[3] = 1;
    //Serial.print("HCHP="); Serial.println(HCHP);
    //Serial.print("valeur="); Serial.println(valeur);
  }
  else if (strcmp(etiquette, "HHPHC") == 0) {
    memset(HHPHC, '\0', 2); strcpy(HHPHC, valeur); check[4] = 1;
    //Serial.print("HHPHC="); Serial.println(HHPHC);
    //Serial.print("valeur="); Serial.println(valeur);
  }
  else if (strcmp(etiquette, "PTEC") == 0) {
    memset(PTEC, '\0', 4); memcpy(PTEC, valeur, strlen(valeur)); check[5] = 1;
    //Serial.print("PTEC="); Serial.println(PTEC);
    //Serial.print("valeur="); Serial.println(valeur);
    
    //Console.print("PTEC="); Console.println(PTEC);
    //Console.print("valeur="); Console.println(valeur);
  }
  else if (strcmp(Etiquette, "IINST") == 0) {
    IINST = atoi(valeur); check[6] = 1;
    //Serial.print("IINST="); Serial.println(IINST);
    //Serial.print("valeur="); Serial.println(valeur);
  }
  else if (strcmp(Etiquette, "PAPP") == 0) {
    PAPP = atol(valeur); check[7] = 1;
    //Serial.print(F("PAPP=")); Serial.println(PAPP);
    //Serial.print(F("valeur=")); Serial.println(valeur);
  }
  else if (strcmp(Etiquette, "IMAX") == 0) {
    IMAX = atol(valeur); check[8] = 1;
    //Serial.print("IMAX="); Serial.println(IMAX);
    //Serial.print("valeur="); Serial.println(valeur);
  }
  else if (strcmp(Etiquette, "OPTARIF") == 0) {
    memset(OPTARIF, '\0', 4); memcpy(OPTARIF, valeur, strlen(valeur)); check[9] = 1;
    //Serial.print("OPTARIF="); Serial.println(OPTARIF);
    //Serial.print("valeur="); Serial.println(valeur);
  }
  else if (strcmp(Etiquette, "ISOUSC") == 0) {
    ISOUSC = atoi(valeur); check[10] = 1;
    //Serial.print("ISOUSC="); Serial.println(ISOUSC);
    //Serial.print("valeur="); Serial.println(valeur);
  }
  else if (strcmp(Etiquette, "MOTDETAT") == 0) {
    memset(MOTDETAT, '\0', 10); memcpy(MOTDETAT, valeur, strlen(valeur)); check[0] = 1;
    //Serial.print("MOTDETAT="); Serial.println(MOTDETAT);
    //Serial.print("valeur="); Serial.println(valeur);
  }
  else
    return 0;

  return 1;
}



