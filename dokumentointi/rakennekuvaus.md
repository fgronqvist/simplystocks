# Rakennekuvaus

Kuvaus ohjelman rakenteesta.

## simplystocks.gui
Ohjelman käynnistävä MainForm toimii samalla ohjelman pääikkunana. Tästä voidaan avata TransactionForm tai MainHelp. TransactionForm huolehtii transaktion (osakkeiden oston tai myyniin) lomakkeen näyttämisestä. MainHelp taas ohjelman perusohjeen.

## simplystocks.helpers
Paketin luokat toimivat "apuluokkina" käyttöliittymän ja logiikan välillä. ErrorHandler on rajapinta jonka avulla voi välittää virheilmoituksia ohjelman sisällä. GenericErrorHandler on yksinkertainen ErrorHandlerin toteutus.
StockHandler luokka toimii osakkeiden "ohjausoliona" jonka avulla käyttöliittymä ja logiikkaa kytketään yhteen.

## simplystocks.logic
Paketin luokat toteuttavat suuren osan ohjelman logiikasta. Database luokkaan on koottu kaikki tietokantatoiminnot ja se toimii Singleton periaatteella. Portfolio luokka toteuttaa portfolioon liittyvät toiminnot, kuten transaktioiden lisäämisen. Transaction luokat perustuu Transaction rajapintaan, jonka toteuttaa abstrakti TransactionBase luokka. Tätä TransactionBase luokkaa laajentaa TransactionSell ja TransactionBuy luokat. Rakenteen ajatus on, että tällöin voidaan toteuttaa metodeja jotka hyväksyvät Transaction rajapinnan, jos transaktion tyypillä ei ole väliä ja taas tietty transaktio (Buy,Sell) jos tyypillä on väliä. Näin Java pitää huolen siitä että vain oikean tyyppinen transaktio pääsee tiettyyn metodiin, eikä metodin sisällä tarvitse testata tyyppiä.


