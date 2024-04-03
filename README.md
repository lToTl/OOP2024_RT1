# OOP2024 rümatöö 1: Järjekordne Produktiivsuse Äpp
autorid: Ander Saarniit, Arno Pilvar\
litsentsid:\
CC-BY-SA-4.0 (Creative Commons Attribution ShareAlike 4.0) | klass WindowsShortcut + koodijupid üksikute käskude jaoks\
CC BY-NC-SA 2.5 CA (Creative Commons Attribution-NonCommercial-ShareAlike 2.5 Canada) | klass VBSUtils
 

## Programmi kirjeldus

### Eeldused ja piirangud
* Äpp eeldab, et töökeskkonna operatsioonisüsteemiks on Windows 11.
  Võimalik, et töötab ka varasematega, aga pole testitud.

* Eeldab, et Steam on installeeritud vaikekausta C:\Program Files(x86)\Steam
  ja mänguraamatukogusid on ainult üks ning see asub Steami kaustas

* Käsurealt töötamiseks eeldab, et windows on seadistatud käsureal kasutama UTF-8 kodeeringut

  https://stackoverflow.com/questions/57131654/using-utf-8-encoding-chcp-65001-in-command-prompt-windows-powershell-window

### Ülevaade

Järjekordne Produktiivsuse Äpp on valmistatud produktiivsuse tõstmise eesmärgil mille peamine eripära seisneb punktisüsteemil. 

Programmi esimesel käivitamisel on tarvis läbida esmane seadistamine:
* Programm proovib Windows 11 failisüsteemist leida kõik installeeritud rakendused.
* Programm kuvab ekraanile kõik leitud rakendused ja esmalt peab kasutaja nimekirjast välja filtreerima kõik neutraalsed rakendused, mida ei saa lugeda produktiivseks ega ebaproduktiivseks. Valiku sisestamiseks peab kasutaja programmile andma sisendiks rakendusele vastava järjekorra numbri, mis on kuvatud nimekirjas iga rakenduse ees, kui neutraalsete rakenduste eemaldamine nimekirjast on sooritatud, peab sisediks andma 'jätka'.
* Programm kuvab ekraanile uue nimekirja, ilma neutraalsete rakendusteta ja nimekirjast tuleb välja valida need rakendused, mis on kasutaja jaoks produktiivsed. Valiku sisestamiskes peab taas kasutaja programmile andma sisendiks rakendusele vastava järjekorra numbri, ning korrektse sisendi andmisel, liigutatakse vastav rakendus produktiivsete rakenduste nimekirja, need rakendused mida kasutaja ei pidanud produktiivseks, jäävad ebaproduktiivsete rakenduste nimekirja. Kui kasutaja on oma rakenduste nimekirjaga rahul, tuleb anda sisendiks 'jätka'.

Sellega on esmane seadistus tehtud ja programmi järgmistel käivitustel ei pea enam esmast seadistust läbima, rakenduste nimekiri salvestatakse peale programmi sulgemist tekst faili, kust programm edaspidi leiab kasutaja rakenduste nimekirja ilma neutraalsete rakendusteta. Tekst failis on rakendused salvestatud järgmisel kujul:
* "Programmi nimi";"Programmi asukoht"; 1/0 ("produktiivne/ebraproduktiivne)"

Kuvatakse programmi peamenüü viie valikuga, mille navigeerimiseks peab kasutaja sisestama valikule vastava numbri, mis on kuvatud valiku ees.\
Vahetult enne valikut kuvatakse ka TaskMonitori seisund ja tänane skoor.
#### peamenüü
     Järjekordne Produktiivsuse Äpp
    --------------------------------
    1. alusta jälgimist
    2. "Im feeling lucky" - ava juhuslik rakendus
    3. näita rakendusi
    4. tee setup uuesti
    5. EXIT
    --------------------------------
    Jälgimine: seisab; Hetke skoor: 0
    tee valik: 

Kasutaja sisestab "1" (alusta jälgimist/peata jälgimine):
* Kui jälgimine alustatakse, programm hakkab jälgima rakenduste nimekirjas olevate rakenduste tegevust. Kui programm näeb,
et ebaproduktiivne rakendus hetkel jookseb, eemaldatakse kasutaja skoorist 1 punkt. Kui programm näeb, et produktiivne rakendus hetkel jookseb ja ebaproduktiivseid hetkel ei ole, lisatakse kasutaja skoorile 1 punkt.
* Programm kontrollib rakenduste jooksmist iga 10 sekundi tagant, ehk ka punkte eemaldatakse/lisatakse iga 10 sekundi tagant.
* Kui jälgimine peatatakse, programm lõpetab rakenduste nimekirjas olevate rakenduste tegevuse jälgimist.
* Lisab sessiooni skoori logi faili lõppu. Logifaili puudumisel see luuakse.

Kasutaja sisestab "2" ("Im feeling lucky" - ava juhuslik rakendus):
* Kasutaja rakenduste nimekirjast avatakse suvaline rakendus. Selle jaoks genereeritakse suvaline arv.
* Kui kasutaja skoor on negatiivne, siis see valik on keelatud. 

Kasutaja sisestab "3" (näita rakendusi):
* Ekraanile kuvatakse produktiivsete ja ebaproduktiivsete rakenduste nimekiri.
* Kasutaja saab sellelt ekraanilt ka rakendusi avada, sisestades rakendusele vastava nimekirja numbri, mis on kuvatud rakenduste nime ees.
* Kasutaja saab avada ebaproduktiivseid rakendusi ainult juhul, kui skoor on suurem kui null, kui skoor on väiksem kui null, kuvatakse selle kohta teade ja rakendust ei avata.
* Kui skoor on väiksem kui null, saab kasutaja avada ainult produktiivseid rakendusi.
* Väljumiseks tuleb vajutada Enter.

Kasutaja sisestab "4" (tee setup uuesti):
* Käivitatakse uuesti esmane seadistus.
* Valik on selle jaoks kui kasutajal läks eelmises esmases seadistuses midagi valesti, või kasutaja on rakendusi arvutist eemaldanud või juurde lisanud. Muudatuste jõustumiseks tuleb "Järjekordne Produktiivsuse Äpp" taaskäivitada.

Kasutaja sisestab "5" (EXIT):
* Programm suletakse.
* Tekitatakse/uuendatakse log tekstfail, mis sisaldab viimase sessiooni kuupäeva, kellaaega ja viimast skoori. Tekstfaili peamine eesmärk on salvestada kasutaja skoori, et see oleks järgmistel sessioonidel alles.



### TaskMonitor
Klass TaskMonitor täidab programmi põhifunktsiooni, milleks on taustal jälgida avatud rakendusi ja pidada skoori.
TaskMonitor on hargtöötluse toega loodud klass. Selleks realiseeritakse liidest Runnable, mis lubab meil
 luua uus lõim (klass Thread), mille parameetriks on klassi TaskMonitori isend.

Runnable liides eeldab, et seda realiseerivas klassis on isendimeetod run(), milles olev kood hakkab eraldi lõimes jooksma.\
Lõime töö peatamiseks on vajalik tõeväärtusmuutuja võtmesõnaga volatile.\
Meetodis run() on while kordus, mis kontrollib selle tõväärtusmuutuja olekut.\
Volatile võtmesõna käsib Javal muutujat hoida põhimälus, mitte protsessori vahemälus. Nii garanteeritakse, et erinevad lõimed näevad alati sama väärtust.

Lõime töö seikamine käib läbi isendimeetodi stop(), mis muudab tõeväärtusmuutaja vääraks.\
Lõim väljub while kordusest ning salvestab skoori log.txt faili, mis puudumisel ka luuakse.




## Töö protsess

### Rühmaliikmete panus

Ander Saarniit:
* projekti idee
* klass - FirstTimeSetup (alpha versioonis. Lõpptööst eemaldatud, selle asemel klass - Setup)
* klass - avaApp (Lõpptöös Abi klassi meetod - käivita)
* peameetodis lisatud funktsionaalsused
* Notioni lehekülg - detailne töö planeering
* debugimine
* README.md suurem osa dokumentatsiooni koostamisest\
ORIENTEERUV AJAKULU: 13 tundi

Arno Pilvar:
* github repo loomine ja haldamine
* peaklass
* klass KaardistaProgrammid
* klass Setup
* klass Abi
* klass TaskMonitor (hargtöötlus)
* veebist otsitud klass VBSUtils
* veebist otsitud klass WindowsShortcut
* litsentsi valik
* README.md dokumentatsiooni täiendused ja viimistlus\
ORIENTEERUV AJAKULU: 12 tundi

### Tegemise mured

Windows 11 failisüsteemist kasutaja allalaetud rakenduste leidmine:
* Peamine rühmatöö ajakulu läks selle probleemi lahendamisele.
* Windows 11 failisüsteemis ei ole kindlat kohta kust kõik kasutaja allalaetud rakendused leida.
* Enamus lahendustest nõudsid kõrgendatud õiguseid, mida tahtsime vältida.
* Sama probleem on varem lahendatud MacOS peal, kus kõik kasutaja allalaetud rakendused asuvad Application kaustas ja nime lõpus on ".app". Rühmatöö kõikidel liikmetel ei olnud Mac arvutit ja Windows 11 üldiselt populaarsem, seega keskenduti ainult Windows 11 peale.

Programmi eesmärgi lahendamiseks tuli kasutada hargtöötlust:
* Rakenduste taustal jälgimiseks oli vaja kasutada eraldi lõime, et kasutaja saaks jätkata muude tegevustega, samal ajal kui programm jälgib rakendusi.
* Hargtöötluse programmeerimisega varasem kokkupuude puudus.

## Programmi testimine

Programmi testimiseks, üritati programmi kasutada igat võimaliku erijuhtu silmas pidades. Programmi üritati meelega katki teha, kui selle meetodiga mingi viga leiti, parandati viga ära ja prooviti uuesti katki teha.

## Hinnang

Arvame, et töö vastab ootustele ja autorid on programmi tööga rahul. Programmiga on hästi hakkama saadud, kuigi veel on mõned funktsioonid mis on puudu või ei tööta täiesti perfektselt. Näiteks võiks veel programm:
* Leida Steami kaust Windowsi registrist
* Leida teiste platvormide mängud
* Leida kõik Steami mänguraamatukogud
* Karistada kasutajat, kui ta kasutab ebaproduktiivset rakendust ja skoor jookseb nulli
* Sulgeda ebaproduktiivseid rakendusi kui skoor on madal
* Näidata selgelt kasutajale millised rakendused jooksevad ja millised mitte
* Anda kasutajale võimaluse ise sulgeda programme mis hetkel jooksevad (näiteks nupp mis sulgeb kõik produktiivsed rakendused, ebaproduktiivsed rakendused või kõik rakendused)
